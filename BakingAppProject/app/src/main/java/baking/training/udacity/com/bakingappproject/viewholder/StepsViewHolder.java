package baking.training.udacity.com.bakingappproject.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.listeners.OnItemClickListener;

public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnItemClickListener onItemClickListener;
    private TextView textViewStepId;
    private TextView textViewStepShortDescription;

    public StepsViewHolder(View itemView) {
        super(itemView);

        textViewStepId = itemView.findViewById(R.id.step_text_id);
        textViewStepShortDescription = itemView.findViewById(R.id.step_short_description);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        onItemClickListener.onItemClick(view, getAdapterPosition());

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TextView getTextViewStepId() {
        return textViewStepId;
    }

    public TextView getTextViewStepShortDescription() {
        return textViewStepShortDescription;
    }
}
