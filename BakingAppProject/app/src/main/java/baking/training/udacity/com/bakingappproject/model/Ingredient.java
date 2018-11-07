package baking.training.udacity.com.bakingappproject.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "ingredient")
public class Ingredient implements Serializable {

    @JsonIgnore
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("measure")
    private String measure;

    @JsonProperty("ingredient")
    private String ingredient;

    public Ingredient(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getMeasure(), that.getMeasure());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getQuantity(), getMeasure());
    }
}
