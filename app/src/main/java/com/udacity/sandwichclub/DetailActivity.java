package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Sandwich sandwich = getSandwich();
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
        }else {
            populateUI(sandwich);
        }
    }

    private Sandwich getSandwich() {

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return null;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        return JsonUtils.parseSandwichJson(json);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        ImageView sandwichImageIv = findViewById(R.id.image_iv);
        TextView descriptionTV = findViewById(R.id.description_tv);
        TextView originTV = findViewById(R.id.origin_tv);
        TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        TextView alsoKnownAsTV = findViewById(R.id.also_known_tv);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichImageIv);
        setTitle(sandwich.getMainName());

        descriptionTV.setText(sandwich.getDescription());

        if(sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equals("")){
            originTV.setText(sandwich.getPlaceOfOrigin());
        }else {
            originTV.setText(R.string.Unknown);
        }

        if(sandwich.getIngredients().toString() != null){
            ingredientsTV.setText(getIngredients(sandwich));
        }

        if(sandwich.getAlsoKnownAs() != null){
            alsoKnownAsTV.setText(getAKA(sandwich));
        }
    }

    public String getIngredients(Sandwich sandwich){
        String ingredientsString = "";
        List<String> ingredients = sandwich.getIngredients();
        if(ingredients.size() == 0){
            return getString(R.string.Unknown);
        }else {
            for (int i = 0; i < ingredients.size(); i++) {
                String val = ingredients.get(i);
                ingredientsString = ingredientsString + val + "\n";
            }
            return ingredientsString;
        }
    }

    public String getAKA(Sandwich sandwich){
        String aKAString = "";
        List<String> aKA = sandwich.getAlsoKnownAs();
        if(aKA.size() == 0){
            return getString(R.string.not_applicable);
        }else {
            for (int i = 0; i < aKA.size(); i++) {
                String val = aKA.get(i);
                aKAString = aKAString + val + ", ";
            }
            return aKAString;
        }
    }
}
