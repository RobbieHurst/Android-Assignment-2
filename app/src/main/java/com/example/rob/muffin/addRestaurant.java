package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class addRestaurant extends Activity {

    Button addButton;
    Button cancelButton;
    private String ID;
    TextView nameTextView;          //Declaration of variables.
    TextView addressTextView;
    TextView ratingTextView;
    TextView titleTextView;
    String newRecord;       //String that will check if the record is new.

    @Override
    public void onCreate(Bundle savedInstanceState) {           //OnCreate that will take all the previous intents information across.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant_layout);

        Intent intent = getIntent(); //Creating the intent object.

        titleTextView = (TextView) findViewById(R.id.textViewTitle); //Finding the title object.

        String name = intent.getStringExtra(FileActivity.SELECTED_NAME);
        String address = intent.getStringExtra(FileActivity.SELECTED_ADDRESS);      //Gaing the previous intents variables.
        String rating = intent.getStringExtra(FileActivity.SELECTED_RATING);
        ID = intent.getStringExtra(FileActivity.SELECTED_ID);

        newRecord = intent.getStringExtra(FileActivity.ISSELECTED);

        nameTextView = (TextView) findViewById(R.id.edtName);       //Setting this intents textViews with the passed data.
        nameTextView.setText(name);

        addressTextView = (TextView) findViewById(R.id.edtAddress);
        addressTextView.setText(address);

        ratingTextView = (TextView) findViewById(R.id.edtRating);
        ratingTextView.setText(rating);

        if(newRecord.equals("true")){

            titleTextView.setText("View Review");

            nameTextView.setFocusable(false);
            nameTextView.setEnabled(false);
            nameTextView.setClickable(false);
            nameTextView.setFocusableInTouchMode(false);

            addressTextView.setFocusable(false);
            addressTextView.setEnabled(false);
            addressTextView.setClickable(false);
            addressTextView.setFocusableInTouchMode(false);

            ratingTextView.setFocusable(false);
            ratingTextView.setEnabled(false);
            ratingTextView.setClickable(false);
            ratingTextView.setFocusableInTouchMode(false);

        }

        addListenerOnButton(); //Adding button listeners.

    }
    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd);
        cancelButton = (Button) findViewById(R.id.btnCancel);       //Finding Buttons by ID in XML.

        if(newRecord.equals("true")){
            cancelButton.setText("Back");       //Changing the Cancel button to Back if it is a selected item in the list.
        }

        addButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                Boolean check = true; //Validation Boolean.

                if(nameTextView.getText().toString().isEmpty()){ //If statements that will check if the textView is empty or does not have the correct data type.
                    check = false;
                    nameTextView.setBackgroundColor(Color.parseColor("#e89795"));       //Changing the color of the text box if it is not correct.
                }
                else{
                    nameTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));
                }

                if(addressTextView.getText().toString().isEmpty()){
                    check=false;
                    addressTextView.setBackgroundColor(Color.parseColor("#e89795"));
                }
                else{
                    addressTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));
                }
                                                                        //method that will check if the text is a number.
                if(ratingTextView.getText().toString().isEmpty() || !isNumeric(ratingTextView.getText().toString()) ){
                    check=false;
                    ratingTextView.setBackgroundColor(Color.parseColor("#e89795"));
                }
                else{
                    ratingTextView.setBackgroundColor(Color.parseColor("#8CCD8c"));
                }


                //Check if all the textViews are correct.

                if(check == true){

                    String name = nameTextView.getText()+"";
                    String address = addressTextView.getText()+"";
                    String rating = ratingTextView.getText()+"";


                    WriteToFile(ID,name,address,rating);    //Calling Write to file method.

                    Intent intent = new Intent(context, FileActivity.class); //Calling previous intent.
                    startActivity(intent);

                    finish(); //Closing this intent.

                }


            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FileActivity.class); //Cancel will bring back to the previous intent.
                startActivity(intent);

                finish(); //Closing this intent.

            }

        });

        if(newRecord.equalsIgnoreCase("true")){

            addButton.setVisibility(View.INVISIBLE);        //Making add button invisible

        }

    }
    public static boolean isNumeric(String num){    //Check method.
        try {
            double d = Double.parseDouble(num);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed(){    //If back button is pressed then go back to previous intent.
        super.onBackPressed();

        Intent intent = new Intent(this, FileActivity.class); //Previous intent.
        startActivity(intent);
        finish(); //Closing this intent.

    }
    public void WriteToFile(String ID, String name, String Address, String rating){ //Write to file method.

        String fileName = "Restaurant.txt"; //Declaring the name of the file.

        File file = new File(getApplicationContext().getFilesDir(), fileName);      //Creating the file in the Apps Context file directory.

        String writeLine = ID+1 + "," + name + "," + Address + "," + rating+"\n";  //The line that needs to be written to the file.

        FileOutputStream outputStream;

        try{

            outputStream = openFileOutput(fileName,Context.MODE_APPEND); //Appending
            outputStream.write(writeLine.getBytes()); //Writing.
            outputStream.close(); //Closing.

            Toast.makeText(this, "File saved Successfully!", Toast.LENGTH_LONG).show(); //Making the user know that the record has been written.


        }
        catch (IOException error){
            error.printStackTrace();
        }

    }
}
