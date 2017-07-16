package io.cayeta.webake.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.cayeta.webake.R;
import io.cayeta.webake.models.Instruction;

public class InstructionListAdapter extends RecyclerView.Adapter<InstructionListAdapter.InstructionListAdapterViewHolder> {

    private ArrayList<Instruction> mInstructions = new ArrayList<>();

    private final InstructionListAdapterOnClickHandler mClickHandler;

    public interface InstructionListAdapterOnClickHandler {
        void onClick(ArrayList<Instruction> instructions, int position);
    }

    public InstructionListAdapter(InstructionListAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public InstructionListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instruction_item, parent, false);
        return new InstructionListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstructionListAdapterViewHolder holder, int position) {
        int instructionId = mInstructions.get(position).getId();
        String instructionShortDescription = mInstructions.get(position).getShortDescription();

        String textToDisplay = String.format("Step %d: %s", instructionId, instructionShortDescription);

        holder.mInstructionTextView.setText(textToDisplay);
    }

    @Override
    public int getItemCount() {
        if (mInstructions.isEmpty()) {
            return 0;
        }
        return mInstructions.size();
    }

    public void setInstructionsData(ArrayList<Instruction> instructions) {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public class InstructionListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mInstructionTextView;

        public InstructionListAdapterViewHolder(View view) {
            super(view);
            mInstructionTextView = (TextView) view.findViewById(R.id.tv_instruction);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mInstructions, position);
        }

    }

}