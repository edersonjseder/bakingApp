package baking.training.udacity.com.bakingappproject.services;

import java.util.List;

import baking.training.udacity.com.bakingappproject.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> loadRecipeList();

}
