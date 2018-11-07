package baking.training.udacity.com.bakingappproject.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import baking.training.udacity.com.bakingappproject.model.Recipe;

public class BakingAppWidgetService extends IntentService {

    public static final String RECIPE_SELECTED = "recipeSelected";

    private Recipe recipeSelect;

    public BakingAppWidgetService() {
        super("BakingAppWidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //Recover the Recipe selected by Use or when load the first time.
        this.recipeSelect = (Recipe) intent.getExtras().getSerializable(RECIPE_SELECTED);

        // Object of Widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));

        //Now update all widgets
        BakingAppWidgetProvider.updateRecipeWidgets(getApplicationContext(), appWidgetManager, appWidgetIds, recipeSelect, recipeSelect.getName());

    }
}
