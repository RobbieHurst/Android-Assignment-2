package com.example.rob.muffin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class addRestaurant extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant_layout);

        Intent intent = getIntent();

        String name = intent.getStringExtra(FileActivity.SELECTED_NAME);
        String address = intent.getStringExtra(FileActivity.SELECTED_ADDRESS);
        String rating = intent.getStringExtra(FileActivity.SELECTED_RATING);

        TextView nameTextView = (TextView) findViewById(R.id.edtName);
        nameTextView.setText(name);

        TextView addressTextView = (TextView) findViewById(R.id.edtAddress);
        addressTextView.setText(address);

        TextView ratingTextView = (TextView) findViewById(R.id.edtRating);
        ratingTextView.setText(rating);


    }



}
