package io.cayeta.webake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.cayeta.webake.models.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String ARGUMENT_RECIPE_KEY = "recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(ARGUMENT_RECIPE_KEY);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_overview_container,
                            RecipeOverviewFragment.newInstance(recipe))
                    .commit();
        }
    }

}