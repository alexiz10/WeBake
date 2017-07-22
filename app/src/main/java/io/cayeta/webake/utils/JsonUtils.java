package io.cayeta.webake.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import io.cayeta.webake.models.*;

public class JsonUtils {

    public static ArrayList<Recipe> getRecipesFromJson(String jsonResponseString) throws JSONException {
        JSONArray jsonResponse = new JSONArray(jsonResponseString);

        ArrayList<Recipe> recipes = new ArrayList<>();

        for (int i = 0; i < jsonResponse.length(); i++) {
            int id = jsonResponse.getJSONObject(i).getInt("id");
            int servings = jsonResponse.getJSONObject(i).getInt("servings");

            String name = jsonResponse.getJSONObject(i).getString("name");
            String image = jsonResponse.getJSONObject(i).getString("image");

            ArrayList<Ingredient> ingredients = new ArrayList<>();
            ArrayList<Instruction> instructions = new ArrayList<>();

            JSONArray ingredientsArray = jsonResponse.getJSONObject(i).getJSONArray("ingredients");

            for (int j = 0; j < ingredientsArray.length(); j++) {
                double quantity = ingredientsArray.getJSONObject(j).getDouble("quantity");

                String measurement = ingredientsArray.getJSONObject(j).getString("measure");
                String ingredient = ingredientsArray.getJSONObject(j).getString("ingredient");

                ingredients.add(new Ingredient(quantity, measurement, ingredient));
            }

            JSONArray instructionsArray = jsonResponse.getJSONObject(i).getJSONArray("steps");

            for (int j = 0; j < instructionsArray.length(); j++) {
                int instructionId = instructionsArray.getJSONObject(j).getInt("id");

                String shortDescription = instructionsArray.getJSONObject(j).getString("shortDescription");
                String description = instructionsArray.getJSONObject(j).getString("description");
                String videoURL = instructionsArray.getJSONObject(j).getString("videoURL");
                String thumbnailURL = instructionsArray.getJSONObject(j).getString("thumbnailURL");

                instructions.add(new Instruction(instructionId, shortDescription, description, videoURL, thumbnailURL));
            }
            recipes.add(new Recipe(id, servings, name, image, ingredients, instructions));
        }
        return recipes;
    }

    public static String convertToJSON(Recipe recipe) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(recipe);
    }

    public static Recipe convertFromJSON(String json) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, Recipe.class);
    }

}