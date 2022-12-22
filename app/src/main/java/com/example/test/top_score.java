package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class top_score extends AppCompatActivity {

    RecyclerView rc;
    ScoreAdapter scoreAdapter;
    ArrayList<Score> scoreArrayList = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase;

    TopScoreDatabase db = new TopScoreDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score);

        rc = findViewById(R.id.topScore);
        rc.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        Cursor cursor = db.getListScore();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Chưa Có Danh Sách", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                String Name = cursor.getString(1);
                Integer Score = cursor.getInt(2);
                scoreArrayList.add(new Score(Name,Score));

            }
            scoreAdapter = new ScoreAdapter(this, R.layout.list_top_design, scoreArrayList, sqLiteDatabase);
            rc.setAdapter(scoreAdapter);
        }

    }
}