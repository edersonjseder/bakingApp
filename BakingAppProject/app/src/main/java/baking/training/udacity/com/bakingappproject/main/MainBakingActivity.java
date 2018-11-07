package baking.training.udacity.com.bakingappproject.main;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.adapter.RecipeAdapter;
import baking.training.udacity.com.bakingappproject.listeners.OnRecipeItemSelectedListener;
import baking.training.udacity.com.bakingappproject.loaders.RecipeTaskLoader;
import baking.training.udacity.com.bakingappproject.model.Recipe;
import baking.training.udacity.com.bakingappproject.recipedetail.IngredientsActivity;
import baking.training.udacity.com.bakingappproject.utils.AlertDialogManager;
import baking.training.udacity.com.bakingappproject.utils.ConnectionDetector;
import baking.training.udacity.com.bakingappproject.widget.BakingAppWidgetService;

public class MainBakingActivity extends AppCompatActivity implements OnRecipeItemSelectedListener,
        LoaderManager.LoaderCallbacks<List<Recipe>> {
    private static final String TAG = MainBakingActivity.class.getSimpleName();

    private ConnectionDetector detector;

    private AlertDialogManager dialogManager;

    private RecyclerView mRecyclerView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private RecipeAdapter recipeAdapter;

    private static final int MOVIE_LOADER_ID = 0;

    private Bundle bundleForLoader;

    private boolean isOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baking);

        mRecyclerView = findViewById(R.id.recyclerview_recipes);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        // Columns for the grid
        int numColumns = 2;

        // Dialog to show the message if there is no connection available
        dialogManager = new AlertDialogManager();

        // Detects if there is connection available
        detector = new ConnectionDetector(getApplicationContext());

        Log.i(TAG, "onCreate() internet detector: " + detector.isConnectingToInternet());

        isOffline = (!detector.isConnectingToInternet());

        if (isOffline) {

            // Internet Connection is not present
            dialogManager.showAlertDialog(MainBakingActivity.this, "Internet Connection Error",
                    "There is no Internet connection", false);

            return;

        }

        // Boolean to verify whether is tablet mode or not
        boolean isTableMode = getResources().getBoolean(R.bool.isTablet);

        if(isTableMode){

            // inflate tablet mode
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));

        }else{

            // inflate phone mode
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        }

        mRecyclerView.setHasFixedSize(true);

        /*
         * This ID will uniquely identify the Loader. We can use it, for example, to get a handle
         * on our Loader at a later point in time through the support LoaderManager.
         */
        int loaderId = MOVIE_LOADER_ID;

        /*
         * The second parameter of the initLoader method below is a Bundle. Optionally, you can
         * pass a Bundle to initLoader that you can then access from within the onCreateLoader
         * callback. In our case, we don't actually use the Bundle, but it's here in case we wanted
         * to.
         */
        bundleForLoader = null;

        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, this);

    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipeTaskLoader(this, mLoadingIndicator);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        Log.i(TAG, "onLoadFinished() inside method");

        try {

            if (data != null){

                showRecipeList(data);

            }else{
                throw new Exception();
            }

        } catch (Exception e){
            Log.i(TAG, "onLoadFinished() inside catch block - message: " + e.getMessage());
            showErrorMessage();
        }

    }

    private void showRecipeList(List<Recipe> recipeList) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);

        /*
         * The RecipeAdapter is responsible for linking our recipe data with the Views that
         * will end up displaying our recipe data.
         */
        recipeAdapter =
                new RecipeAdapter(recipeList, this, getApplicationContext());

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(recipeAdapter);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

    }

    @Override
    public void onRecipeItemSelected(final Recipe recipeSelected, int position) {
        Log.i(TAG, "onRecipeItemSelected() inside method: " + recipeSelected.getName());

        startIntentServiceWidgetUpdate(recipeSelected);

        Context context = this;
        Class destinationClass = IngredientsActivity.class;

        Bundle bundle = new Bundle();

        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        bundle.putSerializable(IngredientsActivity.RECIPE_KEY, recipeSelected);

        bundle.putString(IngredientsActivity.TITLE, recipeSelected.getName());

        intentToStartDetailActivity.putExtras(bundle);

        startActivity(intentToStartDetailActivity);

    }


    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public void startIntentServiceWidgetUpdate(Recipe recipeSelected){

        // Add the baking app service click handler
        Intent bakingIntent = new Intent(this, BakingAppWidgetService.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable(BakingAppWidgetService.RECIPE_SELECTED, recipeSelected);

        bakingIntent.putExtras(bundle);

        PendingIntent bakingPendingIntent =
                PendingIntent.getService(this, 0,
                        bakingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        startService(bakingIntent);

    }

}
