package com.example.rob.muffin;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_layout);

        dbAdapter = new DBAdapter(this);
        nameOfTeamEdt = (EditText) findViewById(R.id.edtTeamName);
        positionEdt = (EditText) findViewById(R.id.edtPosition);
        pointsEdt = (EditText) findViewById(R.id.edtPoints);
        idEdt = (EditText) findViewById(R.id.edtID);

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
