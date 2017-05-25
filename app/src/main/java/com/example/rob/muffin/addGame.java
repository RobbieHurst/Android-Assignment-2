package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class addGame extends Activity {


    //Declaration of variables that are used for gaining values from controls.
    DBAdapter dbAdapter;
    Button addButton;
    Button cancelButton;
    TextView nameTextView;
    TextView pubTextView;
    TextView ratingTextView;
    TextView titleTextView;
    String newRecord;
    private String ID;

    //On create that will Gain all the data that gets passed from the previous intent.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_game_layout);

        dbAdapter = new DBAdapter(this);    //dbAdapter object used to communicate to the database.

        titleTextView = (TextView) findViewById(R.id.textViewTitle); //Finding the titleTextView

        Intent intent = getIntent(); //Creating intent object.

        String name = intent.getStringExtra(DBMainActivity.SELECTED_NAME);          //Getting all the Intent variables to populate the textViews in this intent.
        String address = intent.getStringExtra(DBMainActivity.SELECTED_PUBLISHER);
        String rating = intent.getStringExtra(DBMainActivity.SELECTED_RATING);
        ID = intent.getStringExtra(DBMainActivity.SELECTED_ID);
        newRecord = intent.getStringExtra(DBMainActivity.ISSELECTED);

        nameTextView = (TextView) findViewById(R.id.edtName);       //Setting the text for the text views in this intent.
        nameTextView.setText(name);

        pubTextView = (TextView) findViewById(R.id.edtPublisher);
        pubTextView.setText(address);

        ratingTextView = (TextView) findViewById(R.id.edtRating);
        ratingTextView.setText(rating);

        if(newRecord.equals("true")){
            titleTextView.setText("Edit Game");         //Checking if the intent is passing a record that has data. (Not making a new one)
        }

        addListenerOnButton(); //Creating listeners on buttons.

    }

        //Method that will bind buttons to events.
    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd);         //Finding buttons by ID in XML
        cancelButton = (Button) findViewById(R.id.btnCancel);

        if(newRecord.equals("true")){       //If there is data, then change the text for the buttons.

            cancelButton.setText("Delete");     //update and delete.
            addButton.setText("Update");

        }

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(addButton.getText().equals("Update")){       //If the button has the text "Update"

                    Boolean check = true; //Boolean check for validation.


                    //If statments that will check if the textview is empty, or if the textview does not meet the correct
                    if(nameTextView.getText().toString().isEmpty()){ //data requirements.

                        nameTextView.setBackgroundColor(Color.parseColor("#e89795"));       //Changing the color to red if there is nothing present.
                        check = false;  //Making check false, so nothing will be Update in the database.

                    }
                    else{

                        nameTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));

                    }

                    if(pubTextView.getText().toString().isEmpty()){
                        pubTextView.setBackgroundColor(Color.parseColor("#e89795"));
                        check = false;
                    }
                    else{
                        pubTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));

                    }
                                                                            //isNumeric method that will check if the data is a number.
                    if(ratingTextView.getText().toString().isEmpty() || !isNumeric(ratingTextView.getText().toString()) ){
                        ratingTextView.setBackgroundColor(Color.parseColor("#e89795"));
                        check = false;
                    }
                    else{
                        ratingTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));
                    }

                    //If all the textViews pass then Update record in database.
                    if(check == true){

                        String name = nameTextView.getText()+"";
                        String publisher = pubTextView.getText()+"";
                        String rating = ratingTextView.getText()+"";

                        dbAdapter.updateGame(Long.parseLong(ID),name, publisher,rating);    //Calling the update method. That will update a record by ID.

                        Intent intent = new Intent(context, DBMainActivity.class);  //Calling the previous intent to be displayed.
                        startActivity(intent);

                        dbAdapter.close();  //Closing the Db.

                        finish(); //Closing this intent.

                    }

                }

                if(addButton.getText().equals("Add")){  //If the text of the button is Add.

                    Boolean check = true; //Check variable for validation.

                    if(nameTextView.getText().toString().isEmpty()){ //If statments that will check if there is text and proper data in textViews.

                        nameTextView.setBackgroundColor(Color.parseColor("#e89795")); //Changing the color if correct or incorrect.
                        check = false;

                    }
                    else{

                        nameTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));

                    }

                    if(pubTextView.getText().toString().isEmpty()){
                        pubTextView.setBackgroundColor(Color.parseColor("#e89795"));
                        check = false;
                    }
                    else{
                        pubTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));

                    }
                                                                            //Checking if text entered is indeed a number
                    if(ratingTextView.getText().toString().isEmpty() || !isNumeric(ratingTextView.getText().toString()) ){
                        ratingTextView.setBackgroundColor(Color.parseColor("#e89795"));
                        check = false;
                    }
                    else{
                        ratingTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));
                    }

                    if(check == true){ //If all is well then write to the database.

                        String name = nameTextView.getText()+"";
                        String publisher = pubTextView.getText()+"";
                        String rating = ratingTextView.getText()+"";

                        dbAdapter.insertGame(name, publisher,rating); //Insert method.

                        Intent intent = new Intent(context, DBMainActivity.class); //Calling previous intent.
                        startActivity(intent);

                        dbAdapter.close(); //Closing DB.

                        finish(); //Closing this intent.

                    }

                }

            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(cancelButton.getText().equals("Cancel")){ //Checking if the button is Cancel.

                    Intent intent = new Intent(context, DBMainActivity.class); //Calling previous intent.
                    startActivity(intent);

                    finish(); //Closing this intent.

                }

                if(cancelButton.getText().equals("Delete")){ //Checking if buttons text is "Delete"

                    dbAdapter.deleteGame(Long.parseLong(ID)); //Deleting from the database. By ID.

                    Intent intent = new Intent(context, DBMainActivity.class); //Calling previous intent.
                    startActivity(intent);

                    dbAdapter.close(); //Closing DB.

                    finish(); //Closing this intent.

                }

            }

        });

    }
    @Override
    public void onBackPressed(){ //If the android back button is pressed then take us to the previous intent and destroy this one,

        super.onBackPressed();

        Intent intent = new Intent(this, DBMainActivity.class); //Calling previous intent.
        startActivity(intent);

        finish(); //Closing this intent.
    }
    public static boolean isNumeric(String num){ //Method that checks if text is indeed a number.
        try {
            double d = Double.parseDouble(num); //Trying to pass string into a double.
        }
        catch (NumberFormatException nfe)
        {
            return false; //If it was unsuccessful, then return false.
        }
        return true; // Else return true;
    }


}
