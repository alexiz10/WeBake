package io.cayeta.webake;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.cayeta.webake.adapters.InstructionListAdapter;
import io.cayeta.webake.models.Ingredient;
import io.cayeta.webake.models.Instruction;
import io.cayeta.webake.models.Recipe;

public class RecipeOverviewFragment extends Fragment implements InstructionListAdapter.InstructionListAdapterOnClickHandler {

    private static final String ARGUMENT_RECIPE_KEY = "recipe";

    private boolean mDualPane;

    public static RecipeOverviewFragment newInstance(Recipe recipe) {
        RecipeOverviewFragment recipeOverviewFragment = new RecipeOverviewFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_RECIPE_KEY, recipe);
        recipeOverviewFragment.setArguments(args);

        return recipeOverviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_overview, container, false);

        mDualPane = getResources().getBoolean(R.bool.isTablet);

        Bundle bundle = getArguments();

        Recipe recipe = bundle.getParcelable(ARGUMENT_RECIPE_KEY);

        ArrayList<Ingredient> ingredients = recipe.getIngredients();

        String bulletPoint = "\u2022";
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {
            stringBuilder.append(bulletPoint)
                    .append(ingredients.get(i).getIngredient())
                    .append(", ");

            if ((ingredients.get(i).getQuantity() % 1) == 0) {
                Double d = ingredients.get(i).getQuantity();
                Integer integer = d.intValue();
                stringBuilder.append(String.valueOf(integer));
            } else {
                stringBuilder.append(String.valueOf(ingredients.get(i).getQuantity()));
            }
            stringBuilder.append(" ")
                    .append(ingredients.get(i).getMeasurement());

            if (i != ingredients.size()) {
                stringBuilder.append("\n");
            }
        }

        String ingredientsToDisplay = stringBuilder.toString();

        TextView ingredientsTextView = (TextView) view.findViewById(R.id.tv_ingredients);

        ingredientsTextView.setText(ingredientsToDisplay);

        ArrayList<Instruction> instructions = recipe.getInstructions();

        LinearLayoutManager instructionsLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        InstructionListAdapter instructionListAdapter = new InstructionListAdapter(this);

        RecyclerView instructionsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_instructions);
        instructionsRecyclerView.setLayoutManager(instructionsLayoutManager);
        instructionsRecyclerView.setHasFixedSize(true);
        instructionsRecyclerView.setAdapter(instructionListAdapter);

        instructionListAdapter.setInstructionsData(instructions);

        return view;
    }

    @Override
    public void onClick(ArrayList<Instruction> instructions, int position) {
        if (mDualPane) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_instruction_details_container,
                            RecipeInstructionDetailFragment.newInstance(instructions, position))
                    .commit();
        } else {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_overview_container,
                            RecipeInstructionDetailFragment.newInstance(instructions, position))
                    .addToBackStack(null)
                    .commit();
        }
    }

}