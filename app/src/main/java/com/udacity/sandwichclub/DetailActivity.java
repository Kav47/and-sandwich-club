package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Initialization
        TextView mOriginTextView = findViewById(R.id.origin_tv);
        TextView mDescriptionTextView = findViewById(R.id.description_tv);
        TextView mIngredientsTextView = findViewById(R.id.ingredients_tv);
        TextView mAkaTextView= findViewById(R.id.also_known_tv);

        //fetch place of origin
        if(!(sandwich.getPlaceOfOrigin().isEmpty())){
            mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }else{
            mOriginTextView.setText(R.string.unknown_error_message);
        }

        //fetch description
        if(!(sandwich.getDescription().isEmpty())){
            mDescriptionTextView.setText(sandwich.getDescription());
        }else{
            mDescriptionTextView.setText(R.string.unknown_error_message);
        }

        //fetch Ingredients
        if(sandwich.getIngredients().size() > 0){
            StringBuilder builder = new StringBuilder();
            for(String ingredient: sandwich.getIngredients()){
                builder.append(ingredient).append(", ");
            }
            //remove COMMA and SPACE
            builder.setLength(builder.length() -2);
            mIngredientsTextView.setText(builder);
        }else{
            mIngredientsTextView.setText(R.string.unknown_error_message);
        }

        //fetch aka
        if(sandwich.getAlsoKnownAs().size()>0){
            StringBuilder builder = new StringBuilder();
            for(String s: sandwich.getAlsoKnownAs()){
                //add COMMA and SPACE
                builder.append(s).append(", ");
            }
            //remove COMMA and SPACE
            builder.setLength(builder.length() -2);
            mAkaTextView.setText(builder);
        }else{
            mAkaTextView.setText(R.string.unknown_error_message);
        }

    }
}
