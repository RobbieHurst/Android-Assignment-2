package com.example.rob.muffin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by roberthurst on 24/05/2017.
 */

public class FileActivity extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_layout);
    }

}
