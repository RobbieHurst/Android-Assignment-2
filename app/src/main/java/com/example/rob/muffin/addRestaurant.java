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
    TextView nameTextView;
    TextView addressTextView;
    TextView ratingTextView;
    TextView titleTextView;
    String newRecord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurant_layout);

        Intent intent = getIntent();

        titleTextView = (TextView) findViewById(R.id.textViewTitle);

        String name = intent.getStringExtra(FileActivity.SELECTED_NAME);
        String address = intent.getStringExtra(FileActivity.SELECTED_ADDRESS);
        String rating = intent.getStringExtra(FileActivity.SELECTED_RATING);
        ID = intent.getStringExtra(FileActivity.SELECTED_ID);

        newRecord = intent.getStringExtra(FileActivity.ISSELECTED);

        nameTextView = (TextView) findViewById(R.id.edtName);
        nameTextView.setText(name);

        addressTextView = (TextView) findViewById(R.id.edtAddress);
        addressTextView.setText(address);

        ratingTextView = (TextView) findViewById(R.id.edtRating);
        ratingTextView.setText(rating);

        if(newRecord.equals("true")){

            titleTextView.setText("View Review");

        }

        addListenerOnButton();

    }
    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd);
        cancelButton = (Button) findViewById(R.id.btnCancel);

        if(newRecord.equals("true")){
            cancelButton.setText("Back");
        }

        addButton.setOnClickListener(new View.OnClickListener() {

            Boolean check = true;


            @Override
            public void onClick(View v) {

                if(nameTextView.getText().toString().isEmpty()){
                    check = false;
                    nameTextView.setBackgroundColor(Color.parseColor("#e89795"));
                }


                if(check == true){

                    String name = nameTextView.getText()+"";
                    String address = addressTextView.getText()+"";
                    String rating = ratingTextView.getText()+"";


                    WriteToFile(ID,name,address,rating);

                    Intent intent = new Intent(context, FileActivity.class);
                    startActivity(intent);

                    finish();

                }


            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FileActivity.class);
                startActivity(intent);

                finish();

            }

        });

        if(newRecord.equalsIgnoreCase("true")){

            addButton.setVisibility(View.INVISIBLE);

        }

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Intent intent = new Intent(this, FileActivity.class);
        startActivity(intent);
        finish();

    }
    public void WriteToFile(String ID, String name, String Address, String rating){

        String fileName = "Restaurant.txt";

        File file = new File(getApplicationContext().getFilesDir(), fileName);

        String writeLine = ID+1 + "," + name + "," + Address + "," + rating+"\n";

        FileOutputStream outputStream;

        try{

            outputStream = openFileOutput(fileName,Context.MODE_APPEND);
            outputStream.write(writeLine.getBytes());
            outputStream.close();

            Toast.makeText(this, "File saved Successfully!", Toast.LENGTH_LONG).show();


        }
        catch (IOException error){
            error.printStackTrace();
        }

    }





}
