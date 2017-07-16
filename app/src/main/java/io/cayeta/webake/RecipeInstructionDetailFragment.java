package io.cayeta.webake;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.cayeta.webake.models.Instruction;

public class RecipeInstructionDetailFragment extends Fragment {

    private static final String ARGUMENT_INSTRUCTIONS_KEY = "instructions";
    private static final String ARGUMENT_SELECTED_INSTRUCTION_POSITION = "selected_instruction";

    private boolean mDualPane;

    public static RecipeInstructionDetailFragment newInstance(ArrayList<Instruction> instructions, int position) {
        RecipeInstructionDetailFragment recipeInstructionDetailFragment = new RecipeInstructionDetailFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_INSTRUCTIONS_KEY, instructions);
        args.putInt(ARGUMENT_SELECTED_INSTRUCTION_POSITION, position);
        recipeInstructionDetailFragment.setArguments(args);

        return recipeInstructionDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_recipe_instruction_detail, container, false);

        mDualPane = getResources().getBoolean(R.bool.isTablet);

        Bundle bundle = getArguments();

        ArrayList<Instruction> instructions = bundle.getParcelableArrayList(ARGUMENT_INSTRUCTIONS_KEY);
        int currentPosition = bundle.getInt(ARGUMENT_SELECTED_INSTRUCTION_POSITION);

        // TODO: Set Details Here

        return view;
    }

}