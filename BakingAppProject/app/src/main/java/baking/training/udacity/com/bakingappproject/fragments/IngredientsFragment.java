package baking.training.udacity.com.bakingappproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.adapter.StepsAdapter;
import baking.training.udacity.com.bakingappproject.listeners.OnStepItemSelectedListener;
import baking.training.udacity.com.bakingappproject.model.Ingredient;
import baking.training.udacity.com.bakingappproject.model.Recipe;
import baking.training.udacity.com.bakingappproject.model.Step;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {
    private static final String TAG = IngredientsFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECIPE_KEY = "recipeSelected";

    private Recipe mRecipe;

    private TextView tvIngredientName;

    private RecyclerView mRecyclerView;

    private StepsAdapter stepsAdapter;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe Parameter 1.
     * @return A new instance of fragment IngredientsFragment.
     */
    public static IngredientsFragment newInstance(Recipe recipe) {

        IngredientsFragment fragment = new IngredientsFragment();

        Bundle args = new Bundle();

        args.putSerializable(RECIPE_KEY, recipe);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mRecipe = (Recipe) getArguments().getSerializable(RECIPE_KEY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);

        tvIngredientName = view.findViewById(R.id.ingredient_name);

        mRecyclerView = view.findViewById(R.id.item_steps_list);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        onShowStepsList(mRecipe.getSteps(), mRecipe.getName());

        onDescribeIngredients(mRecipe.getIngredients());

        return view;
    }

    private void onShowStepsList(List<Step> steps, String recipeName) {

        stepsAdapter = new StepsAdapter(steps, recipeName, getContext());

        stepsAdapter.updateRecipeData(steps);

        mRecyclerView.setAdapter(stepsAdapter);

    }

    private void onDescribeIngredients(List<Ingredient> ingredients) {

        double qtde;
        String measure;
        String ingredient;

        for (int i = 0; i < ingredients.size(); i++) {

            qtde = ingredients.get(i).getQuantity();
            measure = ingredients.get(i).getMeasure();
            ingredient = ingredients.get(i).getIngredient();

            String desc = String.valueOf(qtde) + " " + measure;
            String concat = getString(R.string.format_ingre, desc);
            String complete = "- " + concat + " " + ingredient +"\n";

            tvIngredientName.append(complete);

        }

    }

}
