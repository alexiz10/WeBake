package io.cayeta.webake.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import io.cayeta.webake.R;
import io.cayeta.webake.models.Recipe;
import io.cayeta.webake.utils.JsonUtils;

public class RecipeWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String PREFS_NAME = "WeBakePrefs";

    private static final String ARGUMENT_RECIPE_KEY = "recipe";

    private Intent mIntent;
    private Context mContext;
    private Recipe mRecipe;

    public RecipeWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mIntent = intent;
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = mContext.getSharedPreferences(PREFS_NAME, 0);
        String recipeJson = preferences.getString(ARGUMENT_RECIPE_KEY, null);
        mRecipe = JsonUtils.convertFromJSON(recipeJson);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION || mRecipe == null) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_ingredient_item);

        remoteViews.setTextViewText(R.id.tv_widget_recipe_name, mRecipe.getName());

        String ingredient = mRecipe.getIngredients().get(position).getIngredient() +
                ", " +
                mRecipe.getIngredients().get(position).getQuantity() +
                " " +
                mRecipe.getIngredients().get(position).getMeasurement();

        remoteViews.setTextViewText(R.id.tv_widget_ingredient, ingredient);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}