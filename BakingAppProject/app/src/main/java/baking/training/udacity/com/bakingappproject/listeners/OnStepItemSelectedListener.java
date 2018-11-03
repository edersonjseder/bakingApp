package baking.training.udacity.com.bakingappproject.listeners;

import baking.training.udacity.com.bakingappproject.model.Step;

public interface OnStepItemSelectedListener {

    // called when user selects a step from the list
    void onStepItemSelected(Step step, String recipeName, int position);

}
