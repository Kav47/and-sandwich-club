package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        //if the json is empty return null
        if (json.isEmpty()) {
            return null;
        }

        try{
            JSONObject reader = new JSONObject(json);
            //for name
            JSONObject name =reader.getJSONObject("name");
            //main name
            String mainName = name.getString("mainName");
            //aka
            JSONArray aka = name.getJSONArray("alsoKnownAs");
            List<String> akaList = new ArrayList<>();
            for(int i =0; i <aka.length(); i++){
                akaList.add(aka.getString(i));
            }
            //place of origin
            String placeOfOrigin = reader.getString("placeOfOrigin");
            //description
            String description = reader.getString("description");
            //image
            String image = reader.getString("image");
            //ingredients
            JSONArray ingredients = reader.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for(int i =0; i<ingredients.length(); i++){
                ingredientsList.add(ingredients.getString(i));
            }

            //return
            return new Sandwich(mainName, akaList, placeOfOrigin, description, image, ingredientsList);

        }catch(JSONException e){
            Log.e(TAG, "Error while parsing JSON Object", e);
        }

        return null;
    }
}
