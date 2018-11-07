package baking.training.udacity.com.bakingappproject.recipedetail;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.fragments.IngredientsFragment;
import baking.training.udacity.com.bakingappproject.fragments.StepDetailsFragment;
import baking.training.udacity.com.bakingappproject.listeners.OnStepItemSelectedListener;
import baking.training.udacity.com.bakingappproject.model.Recipe;
import baking.training.udacity.com.bakingappproject.model.Step;

public class IngredientsActivity extends AppCompatActivity implements OnStepItemSelectedListener {
    private static final String TAG = IngredientsActivity.class.getSimpleName();


    public static final String RECIPE_KEY = "recipeSelected";
    public static final String TITLE = "recipeName";
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ingredients);
        Log.i(TAG, "onCreate() inside method");

        Bundle extrasComingFromThisActivity = getIntent().getExtras();

        //recover the bundle
        if(savedInstanceState == null){

            mRecipe = (Recipe) extrasComingFromThisActivity.getSerializable(RECIPE_KEY);

            setTitle(extrasComingFromThisActivity.getString(TITLE));

            IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(mRecipe);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.id_content_fragments, ingredientsFragment).commit();

        }

    }

    @Override
    public void onStepItemSelected(Step step, String recipeName, int position) {
        Log.i(TAG, "onStepItemSelected() inside method: " + step.getId() + " - " + step.getShortDescription());

        // Boolean to verify whether is tablet mode or not
        boolean isTableMode = getResources().getBoolean(R.bool.isTablet);

        // Check if it's a tablet
        if(isTableMode) {

            StepDetailsFragment stepDetailsFragmentTablet = StepDetailsFragment.newInstance(step);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_details_container_tablet, stepDetailsFragmentTablet)
                    .commit();

        } else {

            Intent intent = new Intent(getApplicationContext(), StepDetailsActivity.class);

            Bundle args = new Bundle();

            args.putSerializable(StepDetailsFragment.STEP_SELECTED, step);
            args.putString(StepDetailsFragment.RECIPE_NAME, recipeName);

            intent.putExtras(args);

            startActivity(intent);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(RECIPE_KEY, mRecipe);

        super.onSaveInstanceState(outState);
    }
}
