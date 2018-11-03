package baking.training.udacity.com.bakingappproject.listeners;

import baking.training.udacity.com.bakingappproject.model.Recipe;

public interface OnRecipeItemSelectedListener {

    // called when user selects a recipe from the list
    void onRecipeItemSelected(Recipe recipe, int position);

}
