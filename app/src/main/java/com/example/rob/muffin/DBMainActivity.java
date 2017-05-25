package com.example.rob.muffin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberthurst on 24/05/2017.
 */

public class DBMainActivity extends Activity {

    //Declerations
    DBAdapter dbAdapter;
    EditText nameOfTeamEdt;
    EditText positionEdt;
    EditText pointsEdt;
    EditText idEdt;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";
    private List<Game> list = new ArrayList<Game>();
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_main_layout);

        dbAdapter = new DBAdapter(this);

        DisplayGames();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        addListenerOnButton();

    }

    public void addListenerOnButton() {

        final Context context = this;

        addButton = (Button) findViewById(R.id.btnAdd);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, addGame.class);
//                intent.putExtra(SELECTED_ID, getLastId());
//                intent.putExtra(SELECTED_NAME, "");
//                intent.putExtra(SELECTED_ADDRESS,"");
//                intent.putExtra(SELECTED_RATING, "");
//                intent.putExtra(ISSELECTED,"false");
                startActivity(intent);
                finish();

            }

        });

    }
    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < list.size(); index++) {

            String name = list.get(index).getName();
            String address = list.get(index).getPublisher();
            String rating = list.get(index).getRating()+"";

            DataObject obj = new DataObject("Game Name: "+name,"Publisher: "+ address,"Rating: "+ rating);
            results.add(index, obj);
        }
        return results;
    }

    public void DisplayGames()
    {
        Cursor cursor = dbAdapter.getAllGames();

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            Game game = new Game();

            game.setName(cursor.getString(cursor.getColumnIndex("gameName")));
            game.setPublisher(cursor.getString(cursor.getColumnIndex("publisher")));
            game.setRating(cursor.getString(cursor.getColumnIndex("rating")));
            list.add(game);

            cursor.moveToNext();
        }

        cursor.close();

        //Toast.makeText(this,teams,Toast.LENGTH_LONG).show();
    }

}
