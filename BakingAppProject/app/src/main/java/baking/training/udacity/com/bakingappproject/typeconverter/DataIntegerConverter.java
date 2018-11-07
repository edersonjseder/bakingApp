package baking.training.udacity.com.bakingappproject.typeconverter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataIntegerConverter {

    @TypeConverter
    public String fromIntegerValuesList(List<Integer> listValues) {

        if (listValues == null) {
            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        String json = gson.toJson(listValues, type);

        return json;

    }

    @TypeConverter
    public List<Integer> toIntegerValuesList(String listValuesString) {

        if (listValuesString == null) {
            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> integerList = gson.fromJson(listValuesString, type);

        return integerList;

    }

}
