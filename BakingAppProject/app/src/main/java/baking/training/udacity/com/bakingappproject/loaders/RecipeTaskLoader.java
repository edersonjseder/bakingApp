package baking.training.udacity.com.bakingappproject.loaders;

import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import baking.training.udacity.com.bakingappproject.main.MainBakingActivity;
import baking.training.udacity.com.bakingappproject.model.Recipe;
import baking.training.udacity.com.bakingappproject.services.RecipeHttpClient;
import baking.training.udacity.com.bakingappproject.services.RecipeService;
import retrofit2.Call;

public class RecipeTaskLoader extends AsyncTaskLoader<List<Recipe>> {
    private static final String TAG = RecipeTaskLoader.class.getSimpleName();


    List<Recipe> recipeList = null;

    private ProgressBar mLoadingIndicator;
//          ParseJSONToJava parseJSONToJava;

    // MainActivity.
    private MainBakingActivity context;

    //Constructor with context
    public RecipeTaskLoader(MainBakingActivity mainActivity, ProgressBar progressBar) {
        super(mainActivity);
        this.context = mainActivity;
        this.mLoadingIndicator = progressBar;
    }

    @Override
    protected void onStartLoading() {

        if (recipeList != null) {
            deliverResult(recipeList);
        } else {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            forceLoad();
        }

    }

    @Nullable
    @Override
    public List<Recipe> loadInBackground() {

        recipeList = new ArrayList<>();
        RecipeService recipeService = RecipeHttpClient.create();
        Call<List<Recipe>> call = recipeService.loadRecipeList();

        try {

            recipeList = call.execute().body();
            Log.i(TAG, "doInBackground() inside method - : " + recipeList);

        } catch (Exception e) {

            Log.e(TAG, "doInBackground() inside catch - : " + e.getMessage());

        }

/**
         parseJSONToJava = new ParseJSONToJava();

         URL recipeRequestUrl = ConnectionPathUtils.buildJsonUrl();

         Log.i(TAG, "loadInBackground() inside method: " + recipeRequestUrl);

         try {

         // This does the query to the API to retrieve the JSON result
         String jsonRecipeResponse = ConnectionPathUtils.doQuery(recipeRequestUrl);

         //Execute method to get the JSON Object and convert it to Java Object
         recipeList = parseJSONToJava.convertJsonToRecipeJavaClass(jsonRecipeResponse);

         Log.i(TAG, "doInBackground() inside method - Pages: " + recipeList);

         } catch (Exception e) {
         e.printStackTrace();
         recipeList = null;

         }
 **/
        return recipeList;
    }

    /**
     * Sends the result of the load to the registered listener.
     *
     * @param data The result of the load
     */
    @Override
    public void deliverResult(@Nullable List<Recipe> data) {
        recipeList = data;
        super.deliverResult(data);
    }

}
