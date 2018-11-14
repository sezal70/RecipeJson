package com.sejal.recipejson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeList {
    @SerializedName("Recipe")
    public ArrayList<Recipe> recipeList;

    public RecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public String toString() {
        return "RecipeList{" +
                "recipeList=" + recipeList +
                '}';
    }
}
