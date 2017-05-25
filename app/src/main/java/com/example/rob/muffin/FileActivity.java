package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by roberthurst on 24/05/2017.
 */

public class FileActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private List<Restaurant> list = new ArrayList<Restaurant>();
    private String SELECTED_ID;
    private String SELECTED_NAME;
    private String SELECTED_ADDRESS;
    private String SELECTED_RATING;
    Button addButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_layout);

        readFromFile();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        addListenerOnButton();


    }
    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }
    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, addRestaurant.class);
                startActivity(intent);

            }

        });

    }

    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < list.size(); index++) {

            String name = list.get(index).getName();
            String address = list.get(index).getAddress();
            String rating = list.get(index).getRating()+"";

            DataObject obj = new DataObject("Name: "+name,"Address: "+ address,"Rating: "+ rating);
            results.add(index, obj);
        }
        return results;
    }
    public void readFromFile(){

        BufferedReader fileRead = null;
        String line;

        try{

            fileRead = new BufferedReader(new InputStreamReader(getAssets().open("Restaurant.txt")));

            while((line = fileRead.readLine()) != null){

                String[] var = line.split(",");

                Restaurant restaurant = new Restaurant();

                restaurant.setId(var[0]);
                restaurant.setName(var[1]);
                restaurant.setAddress(var[2]);
                restaurant.setRating(Integer.parseInt(var[3]));

                list.add(restaurant);

            }

            Toast.makeText(this, "File Read Successfully", Toast.LENGTH_LONG).show();

        }
        catch (IOException error){
            error.printStackTrace();
        }
    }

}
