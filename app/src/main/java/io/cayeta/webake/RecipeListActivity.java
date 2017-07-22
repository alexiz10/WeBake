package io.cayeta.webake;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import io.cayeta.webake.adapters.RecipeListAdapter;
import io.cayeta.webake.models.Recipe;
import io.cayeta.webake.utils.JsonUtils;
import io.cayeta.webake.utils.NetworkUtils;

public class RecipeListActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListAdapterOnClickHandler {

    private static final String ARGUMENT_RECIPE_KEY = "recipe";

    private RecyclerView mRecipesRecyclerView;

    private TextView mErrorMessageTextView;

    private ProgressBar mLoadingIndicator;

    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        int columns;

        if (isTablet) {
            columns = 3;
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                columns = 1;
            } else {
                columns = 2;
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, columns, LinearLayoutManager.VERTICAL, false);

        mRecipesRecyclerView = (RecyclerView) findViewById(R.id.rv_recipes);
        mRecipesRecyclerView.setLayoutManager(layoutManager);
        mRecipesRecyclerView.setHasFixedSize(true);

        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mAdapter = new RecipeListAdapter(this);

        mRecipesRecyclerView.setAdapter(mAdapter);

        if (NetworkUtils.isConnected(this)) {
            loadRecipeData();
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(ARGUMENT_RECIPE_KEY, recipe);
        startActivity(intent);
    }

    private void loadRecipeData() {
        showRecipes();

        new FetchRecipeDataTask().execute();
    }

    private void showRecipes() {
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mRecipesRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecipesRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    private class FetchRecipeDataTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (recipes != null && !recipes.isEmpty()) {
                showRecipes();
                mAdapter.setRecipeData(recipes);
            } else {
                showErrorMessage();
            }
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void... params) {
            URL requestURL;
            try {
                requestURL = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

                String response = NetworkUtils.getResponseFromHttp(requestURL);

                return JsonUtils.getRecipesFromJson(response);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

    }

}