package baking.training.udacity.com.bakingappproject.recipedetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.fragments.StepDetailsFragment;
import baking.training.udacity.com.bakingappproject.model.Step;

public class StepDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        if (savedInstanceState == null) {

            Bundle bundle = getIntent().getExtras();

            Step stepSelected = (Step) bundle.getSerializable(StepDetailsFragment.STEP_SELECTED);

            setTitle(bundle.getString(StepDetailsFragment.RECIPE_NAME));

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            StepDetailsFragment fragment = StepDetailsFragment.newInstance(stepSelected);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_frame_layout, fragment).commit();
        }
    }
}
