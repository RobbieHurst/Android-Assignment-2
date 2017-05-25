package com.example.rob.muffin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by roberthurst on 24/05/2017.
 */

public class FileActivity extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_layout);

        readFromFile();

    }
    public void readFromFile(){

        BufferedReader fileRead = null;
        String textFileData = " ";
        String line;

        try{

            fileRead = new BufferedReader(new InputStreamReader(getAssets().open("Restaurant")));

            while((line = fileRead.readLine()) != null){
                textFileData += line + "\n";
            }

            Toast.makeText(this, textFileData, Toast.LENGTH_LONG).show();

        }
        catch (IOException error){
            error.printStackTrace();
        }
    }

}
