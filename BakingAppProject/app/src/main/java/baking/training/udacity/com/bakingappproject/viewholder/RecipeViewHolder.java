package baking.training.udacity.com.bakingappproject.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.listeners.OnItemClickListener;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView textViewRecipeServings;
    private TextView textViewRecipeName;

    private OnItemClickListener onItemClickListener;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        textViewRecipeName = itemView.findViewById(R.id.tv_recipe_name);
        textViewRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onItemClickListener.onItemClick(view, getAdapterPosition());

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TextView getTextViewRecipeServings() {
        return textViewRecipeServings;
    }

    public TextView getTextViewRecipeName() {
        return textViewRecipeName;
    }
}
