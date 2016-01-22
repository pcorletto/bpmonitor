package com.example.android.bpmonitor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.bpmonitor.R;

public class DisplayActivity extends ActionBarActivity {

    private Button returnMainScreenButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        returnMainScreenButton = (Button) findViewById(R.id.returnMainButton);
        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        String weeklyReadings = intent.getStringExtra(getString(R.string.weekly_readings));

        // Display the weekly readings.

        //Toast.makeText(DisplayActivity.this, weeklyReadings, Toast.LENGTH_LONG).show();

        String[] myStringArray = {weeklyReadings};
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray);

        listView.setAdapter(myAdapter);

        returnMainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}