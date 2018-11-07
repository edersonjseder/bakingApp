package baking.training.udacity.com.bakingappproject.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import baking.training.udacity.com.bakingappproject.typeconverter.DataIngredientConverter;
import baking.training.udacity.com.bakingappproject.typeconverter.DataStepConverter;

@Entity(tableName = "recipe")
public class Recipe implements Serializable {

    @JsonProperty("id")
    @PrimaryKey
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ingredients")
    @TypeConverters(DataIngredientConverter.class)
    private List<Ingredient> ingredients = null;

    @JsonProperty("steps")
    @TypeConverters(DataStepConverter.class)
    private List<Step> steps = null;

    @JsonProperty("servings")
    private Integer servings;

    @JsonProperty("image")
    private String image;

    public Recipe() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(getId(), recipe.getId()) &&
                Objects.equals(getName(), recipe.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName());
    }
}
