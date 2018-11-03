package baking.training.udacity.com.bakingappproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.listeners.OnItemClickListener;
import baking.training.udacity.com.bakingappproject.listeners.OnRecipeItemSelectedListener;
import baking.training.udacity.com.bakingappproject.model.Recipe;
import baking.training.udacity.com.bakingappproject.viewholder.RecipeViewHolder;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {


    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private OnRecipeItemSelectedListener mOnRecipeItemSelectedListener;
    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipeList,
                         OnRecipeItemSelectedListener onRecipeItemSelectedListener,
                         Context context) {

        this.mOnRecipeItemSelectedListener = onRecipeItemSelectedListener;
        this.recipeList = recipeList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycler_view_recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new RecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int position) {

        final Recipe recipe = recipeList.get(position);

        /**
        Glide.with(context)
                .load(R.drawable.restaurant)
                .into(recipeViewHolder.getImageViewMoviePicture());**/

        recipeViewHolder.getTextViewRecipeName().setText(recipe.getName());
        recipeViewHolder.getTextViewRecipeServings().setText(context.getString(R.string.servings_title, recipe.getServings()));

        recipeViewHolder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mOnRecipeItemSelectedListener.onRecipeItemSelected(recipe, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (recipeList != null) ? recipeList.size() : 0;
    }
}
