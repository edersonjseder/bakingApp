package baking.training.udacity.com.bakingappproject.typeconverter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import baking.training.udacity.com.bakingappproject.model.Step;

public class DataStepConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Step> stringToStepList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Step>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String stepListToString(List<Step> someObjects) {
        return gson.toJson(someObjects);
    }

}
