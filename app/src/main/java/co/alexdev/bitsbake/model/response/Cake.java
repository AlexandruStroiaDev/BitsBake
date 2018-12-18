package co.alexdev.bitsbake.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;

/*Response Class */
public class Cake {

    private int id;
    private String name;
    @SerializedName("ingredients")
    private List<Ingredients> ingredients;
    @SerializedName("steps")
    private List<Steps> steps;
    private boolean isFavorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
