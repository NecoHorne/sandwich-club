package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandWichJSON = new JSONObject(json);
            JSONObject sandWhichDetails = sandWichJSON.getJSONObject("name");

            sandwich.setImage(sandWichJSON.getString("image"));
            sandwich.setMainName(sandWhichDetails.getString("mainName"));
            sandwich.setDescription(sandWichJSON.getString("description"));
            sandwich.setPlaceOfOrigin(sandWichJSON.getString("placeOfOrigin"));

            JSONArray ingredientsArray = sandWichJSON.getJSONArray("ingredients");
            List<String> ingredients = getStringListFromJsonArray(ingredientsArray);
            sandwich.setIngredients(ingredients);

            JSONArray alsoKnownAsArray = sandWhichDetails.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = getStringListFromJsonArray(alsoKnownAsArray);
            sandwich.setAlsoKnownAs(alsoKnownAs);

        } catch(JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> returnList = new ArrayList<>();
        for (int i = 0; i < jArray.length(); i++) {
            String val = jArray.getString(i);
            returnList.add(val);
        }
        return returnList;
    }
}
