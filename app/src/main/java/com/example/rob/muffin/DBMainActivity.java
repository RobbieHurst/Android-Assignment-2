package com.example.rob.muffin;

import android.app.Activity;
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
    private List<Restaurant> list = new ArrayList<Restaurant>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_main_layout);

        dbAdapter = new DBAdapter(this);
        nameOfTeamEdt = (EditText) findViewById(R.id.edtTeamName);
        positionEdt = (EditText) findViewById(R.id.edtPosition);
        pointsEdt = (EditText) findViewById(R.id.edtPoints);
        idEdt = (EditText) findViewById(R.id.edtID);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

    }
    private ArrayList<DataObject> getDataSet() {
        ArrayList results = new ArrayList<DataObject>();
        for (int index = 0; index < list.size(); index++) {

            String name = list.get(index).getName();
            String address = list.get(index).getAddress();
            String rating = list.get(index).getRating()+"";

            DataObject obj = new DataObject("Game Name: "+name,"Publisher: "+ address,"Rating: "+ rating);
            results.add(index, obj);
        }
        return results;
    }

    public void AddGameTap(View v)
    {
        dbAdapter.open();
        if(dbAdapter.insertTeam(nameOfTeamEdt.getText().toString(),positionEdt.getText().toString(),pointsEdt.getText().toString())!= -1)
        {
            Toast.makeText(this, "Add Successful", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Add unSuccessful", Toast.LENGTH_LONG).show();
        }
        dbAdapter.close();
    }

    public void ShowGameTap(View v)
    {
        dbAdapter.open();
        Cursor dbCursor = dbAdapter.getAllTeams();
        String allTeamRecords = "";

        if(dbCursor.moveToFirst())
        {
            do
            {
                allTeamRecords += "id: " + dbCursor.getInt(0) +
                        "Name: " +  dbCursor.getString(1) +
                        "Position: " +  dbCursor.getString(2) +
                        "Points: " +  dbCursor.getString(3) + "\n";
            }
            while(dbCursor.moveToNext());
        }
        DisplayGames(allTeamRecords);
        dbAdapter.close();
    }

    public void DisplayGames(String teams)
    {

        Toast.makeText(this,teams,Toast.LENGTH_LONG).show();
    }

    public void EditTeamGame(View view)
    {
        dbAdapter.open();
        if(dbAdapter.updateTeam(Long.parseLong(idEdt.getText().toString()),nameOfTeamEdt.getText().toString(),
                positionEdt.getText().toString(),pointsEdt.getText().toString()))
        {
            Toast.makeText(this, "Update Successful", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Update Unsuccessful", Toast.LENGTH_LONG).show();
        }
        dbAdapter.close();
    }

    public void DeleteGameTap(View view)
    {
        dbAdapter.open();
        if(dbAdapter.deleteTeam(Long.parseLong(idEdt.getText().toString())))
        {
            Toast.makeText(this, "Record Deleted", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Update Deleted", Toast.LENGTH_LONG).show();
        }
        dbAdapter.close();
    }

    public void SearchGameTap(View view)
    {
        Toast.makeText(this,idEdt.getText().toString(),Toast.LENGTH_LONG).show();
        dbAdapter.open();
        Cursor getTeamCursor = dbAdapter.getTeam(Long.parseLong(idEdt.getText().toString()));

        if(getTeamCursor.getCount() > 0)
        {
            displayTeam(getTeamCursor);
        }
        else
        {
            Toast.makeText(this, "Game Does not Exist", Toast.LENGTH_LONG).show();
        }
    }

    public void displayTeam(Cursor curTeam)
    {
        idEdt.setText(String.valueOf(curTeam.getInt(0)));
        nameOfTeamEdt.setText(curTeam.getString(1));
        positionEdt.setText(curTeam.getString(2));
        pointsEdt.setText(curTeam.getString(3));
    }

}
