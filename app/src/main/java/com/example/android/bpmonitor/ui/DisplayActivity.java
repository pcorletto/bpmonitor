package com.example.android.bpmonitor.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.bpmonitor.R;

public class DisplayActivity extends ListActivity {

    private Button returnMainScreenButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        returnMainScreenButton = (Button) findViewById(R.id.returnMainButton);
        listView = (ListView) findViewById(android.R.id.list);

        Intent intent = getIntent();
        String weeklyReadings = intent.getStringExtra(getString(R.string.weekly_readings));
        String[] displayArray = intent.getStringArrayExtra(getString(R.string.display_array));

        // Display the weekly readings.

        //Toast.makeText(DisplayActivity.this, weeklyReadings, Toast.LENGTH_LONG).show();

        String[] myStringArray = {weeklyReadings};

        // This code worked. Comment it out temporarily to test the passed displayArray.
        //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);

       // Display dummy data from displayArray (Array of 7 strings): "Day 1, Day 2, ..." temporarily,
       // until it get it to work
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,displayArray);

        listView.setAdapter(myAdapter);

        returnMainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}