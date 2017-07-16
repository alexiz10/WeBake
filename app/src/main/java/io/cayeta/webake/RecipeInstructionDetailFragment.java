package io.cayeta.webake;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.cayeta.webake.models.Instruction;

public class RecipeInstructionDetailFragment extends Fragment {

    public static RecipeInstructionDetailFragment newInstance(ArrayList<Instruction> instructions, int position) {
        return new RecipeInstructionDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_instruction_detail, container, false);
    }

}