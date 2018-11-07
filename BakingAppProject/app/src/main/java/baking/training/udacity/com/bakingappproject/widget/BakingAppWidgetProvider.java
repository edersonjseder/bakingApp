package baking.training.udacity.com.bakingappproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.List;

import baking.training.udacity.com.bakingappproject.R;
import baking.training.udacity.com.bakingappproject.main.MainBakingActivity;
import baking.training.udacity.com.bakingappproject.model.Ingredient;
import baking.training.udacity.com.bakingappproject.model.Recipe;
import baking.training.udacity.com.bakingappproject.recipedetail.IngredientsActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipeSelected, String recipeName) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        //Mount the view
        views.setTextViewText(R.id.widget_recipe_name, recipeName);

        views.removeAllViews(R.id.widget_ingredients_container);

        //Mount the list of Ingredients on TextView
        onOrganizeIngredients(views, recipeSelected.getIngredients(), context);

        Intent intent = new Intent(context, IngredientsActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.widget_baking_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    //Method called to mount the Views.
    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           int[] appWidgetIds, Recipe recipe, String recipeName) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe, recipeName);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //Method to mount the list of Ingredents inside the TextView.
    private static void onOrganizeIngredients(RemoteViews views, List<Ingredient> ingredients, Context context) {

        double qtd;
        String measure;
        String actualIngredient;
        String fullDescription = null;

        for (Ingredient ingredient : ingredients) {

            RemoteViews mIngredientView = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_description);

            qtd = ingredient.getQuantity();
            measure = ingredient.getMeasure();
            actualIngredient = ingredient.getIngredient();

            String concat = String.valueOf(qtd) + " " + measure;
            String joined = context.getString(R.string.format_ingredient, concat);
            fullDescription = "- " + joined + " " + actualIngredient + "\n";

            mIngredientView.setTextViewText(R.id.app_widget_ingredient_name, fullDescription);

            views.addView(R.id.widget_ingredients_container, mIngredientView);
        }


    }
}

