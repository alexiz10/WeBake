package io.cayeta.webake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.cayeta.webake.R;
import io.cayeta.webake.models.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder> {

    private ArrayList<Recipe> mRecipes = new ArrayList<>();

    private final RecipeListAdapterOnClickHandler mClickHandler;

    public interface RecipeListAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipeListAdapter(RecipeListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {
        String image = mRecipes.get(position).getImage();
        String name = mRecipes.get(position).getName();
        Context context = holder.mRecipeImage.getContext();
        if (!image.isEmpty()) {
            Picasso.with(context).load(image).into(holder.mRecipeImage);
        } else {
            holder.mRecipeImage.setImageResource(R.drawable.image_placeholder);
        }
        holder.mRecipeName.setText(name);
    }

    @Override
    public int getItemCount() {
        if (mRecipes.isEmpty()) {
            return 0;
        }
        return mRecipes.size();
    }

    public void setRecipeData(ArrayList<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mRecipeImage;
        final TextView mRecipeName;

        public RecipeListAdapterViewHolder(View view) {
            super(view);
            mRecipeImage = (ImageView) view.findViewById(R.id.iv_recipe_image);
            mRecipeName = (TextView) view.findViewById(R.id.tv_recipe_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Recipe recipe = mRecipes.get(position);
            mClickHandler.onClick(recipe);
        }

    }

}