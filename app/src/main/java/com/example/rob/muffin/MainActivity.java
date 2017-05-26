package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    Button button;          //Declaration of variables.
    Button secondButton;
    Button thirdButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();      //Adding listener
    }

    public void addListenerOnButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.button1);
        secondButton = (Button) findViewById(R.id.button2);
        thirdButton = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {        //Creating intents.

                Intent intent = new Intent(context, FileActivity.class);
                startActivity(intent);

            }

        });

        secondButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DBMainActivity.class);
                startActivity(intent);
            }
        });

        thirdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Help.class);
                startActivity(intent);
            }
        });


    }
}
