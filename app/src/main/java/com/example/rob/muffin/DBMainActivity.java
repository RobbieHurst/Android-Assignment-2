package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberthurst on 24/05/2017.
 */

public class DBMainActivity extends Activity {

    //Declarations
    DBAdapter dbAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "DB Activity. ";
    private List<Game> list = new ArrayList<Game>();    //Array of Games.
    Button addButton;

    public static String SELECTED_ID;
    public static String SELECTED_NAME;
    public static String SELECTED_PUBLISHER;        //Intent variables.
    public static String SELECTED_RATING;
    public static String ISSELECTED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_main_layout);

        dbAdapter = new DBAdapter(this);    //db object that will be used to communicate with the database.

        DisplayGames();  //Method that will Find all games in the database.

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);     //Recyler View that is used for Card Views.
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        SELECTED_ID = "";
        SELECTED_NAME = "";
        SELECTED_PUBLISHER = "";        //Ensuring that intent variables are blank on load.
        SELECTED_RATING = "";
        ISSELECTED = "true";

        addListenerOnButton();          //Adding listeners to buttons.

    }

    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd); //Finding add button by reference.

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, addGame.class);     //Creating addGame intent.
                intent.putExtra(SELECTED_NAME, "");
                intent.putExtra(SELECTED_PUBLISHER,"");     //Sending through blank data to intent.
                intent.putExtra(SELECTED_RATING, "");
                intent.putExtra(ISSELECTED,"false");
                startActivity(intent);
                finish(); //Closing this intent.

            }

        });

    }@Override
    protected void onResume() {     //OnResume.
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                SELECTED_ID = list.get(position).getID();
                SELECTED_NAME = list.get(position).getName();
                SELECTED_PUBLISHER = list.get(position).getPublisher();     //Getting the selected item in the list.
                SELECTED_RATING = list.get(position).getRating()+"";
                ISSELECTED = "true";
                itemTap(); //Calling method that will call the next intent with new intent data.
            }
        });
    }
    public void itemTap(){

        Intent intent = new Intent(this, addGame.class);
        intent.putExtra(SELECTED_ID, SELECTED_ID);
        intent.putExtra(SELECTED_NAME, SELECTED_NAME);      //calling and adding data.
        intent.putExtra(SELECTED_PUBLISHER,SELECTED_PUBLISHER);
        intent.putExtra(SELECTED_RATING, SELECTED_RATING);
        intent.putExtra(ISSELECTED,ISSELECTED);
        startActivity(intent);
        finish(); //Closing this intent.


    }
    private ArrayList<DataObject> getDataSet() {        //Getting dataset from the array that has been read from the data.
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < list.size(); index++) {     //For loop that will increment until the list is done.

            String name = list.get(index).getName();
            String address = list.get(index).getPublisher();        //Assigning local variables for creationg of display object.
            String rating = list.get(index).getRating()+"";

            DataObject obj = new DataObject("Game Name: "+name,"Publisher: "+ address,"Rating: "+ rating); //Making dataobject.
            results.add(index, obj); //Adding reults.
        }
        return results; //Returning array.
    }

    public void DisplayGames()
    {
        Cursor cursor = dbAdapter.getAllGames();    //Cursor from the database class.

        cursor.moveToFirst(); //Got to first point.

        while(!cursor.isAfterLast()){

            Game game = new Game(); //Game object used to populate list.

            game.setID(cursor.getString(cursor.getColumnIndex("_id")));
            game.setName(cursor.getString(cursor.getColumnIndex("gameName")));      //object variable setting.
            game.setPublisher(cursor.getString(cursor.getColumnIndex("publisher")));
            game.setRating(cursor.getString(cursor.getColumnIndex("rating")));
            list.add(game); //Adding to list.

            cursor.moveToNext(); //Move on.
        }

        cursor.close(); //Close cursor.

    }

}
