package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class addGame extends Activity {

    DBAdapter dbAdapter;
    Button addButton;
    Button cancelButton;
    TextView nameTextView;
    TextView pubTextView;
    TextView ratingTextView;
    TextView titleTextView;
    String newRecord;
    private String ID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game_layout);

        dbAdapter = new DBAdapter(this);

        titleTextView = (TextView) findViewById(R.id.textViewTitle);

        Intent intent = getIntent();

        String name = intent.getStringExtra(DBMainActivity.SELECTED_NAME);
        String address = intent.getStringExtra(DBMainActivity.SELECTED_PUBLISHER);
        String rating = intent.getStringExtra(DBMainActivity.SELECTED_RATING);
        ID = intent.getStringExtra(DBMainActivity.SELECTED_ID);
        newRecord = intent.getStringExtra(DBMainActivity.ISSELECTED);

        nameTextView = (TextView) findViewById(R.id.edtName);
        nameTextView.setText(name);

        pubTextView = (TextView) findViewById(R.id.edtPublisher);
        pubTextView.setText(address);

        ratingTextView = (TextView) findViewById(R.id.edtRating);
        ratingTextView.setText(rating);

        if(newRecord.equals("true")){
            titleTextView.setText("Edit Game");
        }

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd);
        cancelButton = (Button) findViewById(R.id.btnCancel);

        if(newRecord.equals("true")){

            cancelButton.setText("Delete");
            addButton.setText("Update");

        }

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(addButton.getText().equals("Update")){

                    String name = nameTextView.getText()+"";
                    String publisher = pubTextView.getText()+"";
                    String rating = ratingTextView.getText()+"";

                    dbAdapter.updateTeam(Long.parseLong(ID),name, publisher,rating);

                    Intent intent = new Intent(context, DBMainActivity.class);
                    startActivity(intent);

                    dbAdapter.close();

                    finish();

                }

                if(addButton.getText().equals("Add")){

                    String name = nameTextView.getText()+"";
                    String publisher = pubTextView.getText()+"";
                    String rating = ratingTextView.getText()+"";

                    dbAdapter.insertGame(name, publisher,rating);

                    Intent intent = new Intent(context, DBMainActivity.class);
                    startActivity(intent);

                    dbAdapter.close();

                    finish();

                }

            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(cancelButton.getText().equals("Cancel")){

                    Intent intent = new Intent(context, DBMainActivity.class);
                    startActivity(intent);

                    finish();

                }

                if(cancelButton.getText().equals("Delete")){

                    dbAdapter.deleteTeam(Long.parseLong(ID));

                    Intent intent = new Intent(context, DBMainActivity.class);
                    startActivity(intent);

                    dbAdapter.close();

                    finish();

                }

            }

        });

    }
    @Override
    public void onBackPressed(){

        super.onBackPressed();

        Intent intent = new Intent(this, DBMainActivity.class);
        startActivity(intent);

        finish();
    }


}
