package com.example.rob.muffin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class addGame extends Activity {

    DBAdapter dbAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game_layout);

        dbAdapter = new DBAdapter(this);

        


//        Intent intent = getIntent();
//
//        String name = intent.getStringExtra(FileActivity.SELECTED_NAME);
//        String address = intent.getStringExtra(FileActivity.SELECTED_ADDRESS);
//        String rating = intent.getStringExtra(FileActivity.SELECTED_RATING);
//        ID = intent.getStringExtra(FileActivity.SELECTED_ID);
//
//        newRecord = intent.getStringExtra(FileActivity.ISSELECTED);
//
//        nameTextView = (TextView) findViewById(R.id.edtName);
//        nameTextView.setText(name);
//
//        addressTextView = (TextView) findViewById(R.id.edtAddress);
//        addressTextView.setText(address);
//
//        ratingTextView = (TextView) findViewById(R.id.edtRating);
//        ratingTextView.setText(rating);
//
//        addListenerOnButton();

    }


}
