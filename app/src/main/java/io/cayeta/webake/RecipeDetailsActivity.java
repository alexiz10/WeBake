package io.cayeta.webake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.cayeta.webake.models.Recipe;
import io.cayeta.webake.utils.JsonUtils;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "WeBakePrefs";

    private static final String ARGUMENT_RECIPE_KEY = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(ARGUMENT_RECIPE_KEY);

        if (recipe == null) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
            String recipeJson = preferences.getString(ARGUMENT_RECIPE_KEY, null);
            recipe = JsonUtils.convertFromJSON(recipeJson);
        }

        if (recipe == null) {
            Intent intentToHome = new Intent(RecipeDetailsActivity.this, RecipeListActivity.class);
            startActivity(intentToHome);
            finish();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_overview_container,
                            RecipeOverviewFragment.newInstance(recipe))
                    .commit();
        }
    }

}