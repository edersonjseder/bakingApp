package baking.training.udacity.com.bakingappproject.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import baking.training.udacity.com.bakingappproject.model.Recipe;

public class ParseJSONToJava {
    private static final String TAG = ParseJSONToJava.class.getSimpleName();

    private ObjectMapper mapper;

    /**
     *
     * This method parses from JSON format to Java Objects with the Jackson API
     *
     * @param json
     * @return
     * @Original_Base WerbSystique - http://websystique.com/java/json/jackson-convert-java-object-to-from-json/
     */
    public List<Recipe> convertJsonToRecipeJavaClass(String json){
        Log.i(TAG, "ParseJSONToJava.convertJsonToRecipeJavaClass() inside method - param value: " + json);

        mapper = new ObjectMapper();

        List<Recipe> recipeListObject = null;

        try {
            Log.i(TAG, "ParseJSONToJava.convertJsonToRecipeJavaClass() inside try/catch block - param value: " + json);

            // Deserializes a JSON list
            TypeReference<List<Recipe>> recipeListType = new TypeReference<List<Recipe>>() {};

            recipeListObject = mapper.reader().forType(recipeListType).readValue(json);

        } catch (JsonGenerationException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToRecipeJavaClass() inside catch block: Exception - JsonGenerationException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (JsonMappingException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToRecipeJavaClass() inside catch block: Exception - JsonMappingException - message: " + ex.getMessage());
            ex.printStackTrace();

        } catch (IOException ex) {
            Log.i(TAG, "ParseJSONToJava.convertJsonToRecipeJavaClass() inside catch block: Exception - IOException - message: " + ex.getMessage());
            ex.printStackTrace();

        }

        return recipeListObject;

    }

}
