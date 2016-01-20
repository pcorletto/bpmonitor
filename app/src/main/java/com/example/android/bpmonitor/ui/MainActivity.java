package com.example.android.bpmonitor.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bpmonitor.R;
import com.example.android.bpmonitor.model.Keeper;
import com.example.android.bpmonitor.model.Reading;

public class MainActivity extends ActionBarActivity {

    private EditText lastSystolicEditText, lastDiastolicEditText,
            systolicEditText, diastolicEditText, readingCountEditText;
    private Button bpCheckButton, storeReadingButton, displayReadingsButton, resetUIButton,
            clearWeeklyReadingsButton;
    private int mSystolic, mDiastolic;
    private String bpCheckStatus;
    private Reading mReading = new Reading();
    private Keeper mWeeklyReadingKeeper = new Keeper();
    private int mIndex;
    private String mWeeklyReadings;
    private String mSystolicDiastolicString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastSystolicEditText = (EditText) findViewById(R.id.lastSystolicEditText);
        lastDiastolicEditText = (EditText) findViewById(R.id.lastDiastolicEditText);
        systolicEditText = (EditText) findViewById(R.id.systolicEditText);
        diastolicEditText = (EditText) findViewById(R.id.diastolicEditText);
        bpCheckButton = (Button) findViewById(R.id.bpCheckButton);
        storeReadingButton = (Button) findViewById(R.id.storeReadingButton);
        displayReadingsButton = (Button) findViewById(R.id.displayReadingsButton);
        resetUIButton = (Button) findViewById(R.id.resetUIButton);
        readingCountEditText = (EditText) findViewById(R.id.readingCountEditText);
        clearWeeklyReadingsButton = (Button) findViewById(R.id.clearWeeklyReadingsButton);

        // Retrieve any previous reading stored on the SharedPreferences file

        SharedPreferences sharedPreferences = MainActivity.this
                .getSharedPreferences(getString(R.string.BP_PREF_FILE), MODE_PRIVATE);
        mSystolic = sharedPreferences.getInt(getString(R.string.SYSTOLIC_READING),0);
        mDiastolic = sharedPreferences.getInt(getString(R.string.DIASTOLIC_READING),0);
        mIndex = sharedPreferences.getInt(getString(R.string.READING_COUNT), 0);
        mWeeklyReadings = sharedPreferences.getString(getString(R.string.WEEKLY_READINGS),"");
        mSystolicDiastolicString = sharedPreferences.getString(getString(R.string.SYSTOLIC_DIASTOLIC_STRING), "");

        // Display any previous readings in the edit texts for last systolic and diastolic
        // and readingCountEditText

        lastSystolicEditText.setTextColor(Color.BLACK);
        lastSystolicEditText.setText(mSystolic + "");

        lastDiastolicEditText.setTextColor(Color.BLACK);
        lastDiastolicEditText.setText(mDiastolic + "");

        readingCountEditText.setText(mIndex + "");

        // Mark any high or low readings in red.

        if (anySystolicAbnormalReadings(mSystolic)){
            lastSystolicEditText.setTextColor(Color.RED);
        }

        if (anyDiastolicAbnormalReadings(mDiastolic)){
            lastDiastolicEditText.setTextColor(Color.RED);
        }

        bpCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mSystolicString = systolicEditText.getText().toString();

                // Check if the user did not enter anything. If no entry, then alert
                if(TextUtils.isEmpty(mSystolicString)){
                    systolicEditText.setError("You need to enter a reading!");
                    return;
                }

                mSystolic = Integer.parseInt(mSystolicString);

                String mDiastolicString = diastolicEditText.getText().toString();

                // Check if the user did not enter anything. If no entry, then alert
                if(TextUtils.isEmpty(mDiastolicString)){
                    diastolicEditText.setError("You need to enter a reading!");
                    return;
                }

                mDiastolic = Integer.parseInt(mDiastolicString);

                // Mark any high or low readings in red.

                if (anySystolicAbnormalReadings(mSystolic)){
                    systolicEditText.setTextColor(Color.RED);
                }

                if (anyDiastolicAbnormalReadings(mDiastolic)){
                    diastolicEditText.setTextColor(Color.RED);
                }

                // Check whether pressure is low, normal or high, and
                // display the check status in a toast
                bpCheckStatus = mReading.getBPStatus(mSystolic, mDiastolic);
                Toast.makeText(MainActivity.this, "Your blood pressure is " +
                        bpCheckStatus, Toast.LENGTH_LONG).show();

                // Store the last reading on a SharedPreferences file so that it can be
                // displayed even after the app is stopped.

                SharedPreferences sharedPreferences = MainActivity.this
                        .getSharedPreferences(getString(R.string.BP_PREF_FILE), MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.SYSTOLIC_READING), mSystolic);
                editor.putInt(getString(R.string.DIASTOLIC_READING), mDiastolic);
                editor.commit();

                // Display this checked readings in the edit texts for last systolic and diastolic

                lastSystolicEditText.setTextColor(Color.BLACK);
                lastSystolicEditText.setText(mSystolic + "");

                lastDiastolicEditText.setTextColor(Color.BLACK);
                lastDiastolicEditText.setText(mDiastolic + "");

                // Mark any high or low readings in red.

                if (anySystolicAbnormalReadings(mSystolic)){
                    lastSystolicEditText.setTextColor(Color.RED);
                }

                if (anyDiastolicAbnormalReadings(mDiastolic)){
                    lastDiastolicEditText.setTextColor(Color.RED);
                }

            }
        });

        // Clear any prior readings
        systolicEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                systolicEditText.setTextColor(Color.BLACK);
                systolicEditText.requestFocus();
                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.showSoftInput(systolicEditText, 0);
                }
                systolicEditText.setText("");
                return true;
            }
        });

        diastolicEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                diastolicEditText.setTextColor(Color.BLACK);
                diastolicEditText.requestFocus();
                InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.showSoftInput(diastolicEditText, 0);
                }
                diastolicEditText.setText("");
                return true;
            }
        });

        storeReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mWeeklyReadingKeeper.isFull()) {
                    Toast.makeText(MainActivity.this, "Keeper is full. It can store a maximum of 7 days! Clear it first.",
                            Toast.LENGTH_LONG).show();
                } else {

                    String mSystolicString = systolicEditText.getText().toString();

                    // Check if the user did not enter anything. If no entry, then alert
                    if (TextUtils.isEmpty(mSystolicString)) {
                        systolicEditText.setError("You need to enter a reading!");
                        return;
                    }

                    mSystolic = Integer.parseInt(mSystolicString);

                    String mDiastolicString = diastolicEditText.getText().toString();

                    // Check if the user did not enter anything. If no entry, then alert
                    if (TextUtils.isEmpty(mDiastolicString)) {
                        diastolicEditText.setError("You need to enter a reading!");
                        return;
                    }

                    mDiastolic = Integer.parseInt(mDiastolicString);

                    // Set a reading at last index position.

                    SharedPreferences sharedPreferences = MainActivity.this
                            .getSharedPreferences(getString(R.string.BP_PREF_FILE), MODE_PRIVATE);
                    mIndex = sharedPreferences.getInt(getString(R.string.READING_COUNT), 0);



                    mWeeklyReadingKeeper.setIndex(mIndex);

                    // Re-fill the readings keeper array with the values previously stored
                    // in the mSystolicDiastolicString stored in the SharedPreferences file.
                    // I will use a method I called "reloadArray".
                    // Otherwise, we would get a Null Pointer Exception for trying to
                    // get values from an array with empty positions when we call the
                    // getAllReadings method. I will store the extracted values from
                    // systolic and diastolic (mSystolicDiastolicString) into index from
                    // 0 to currentIndex

                    mWeeklyReadingKeeper.setSystolicDiastolicString(mSystolicDiastolicString);

                    mWeeklyReadingKeeper.reloadArray();

                    mWeeklyReadingKeeper.setAReading(mSystolic, mDiastolic);

                    // Store the last reading on a SharedPreferences file so that it can be
                    // displayed even after the app is stopped.

                    mIndex = mWeeklyReadingKeeper.getIndex();

                    mWeeklyReadings = mWeeklyReadingKeeper.getAllReadings();

                    mSystolicDiastolicString = mWeeklyReadingKeeper.getAllSystolicDiastolic();

                    sharedPreferences = MainActivity.this
                            .getSharedPreferences(getString(R.string.BP_PREF_FILE), MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(getString(R.string.SYSTOLIC_READING), mSystolic);
                    editor.putInt(getString(R.string.DIASTOLIC_READING), mDiastolic);
                    editor.putInt(getString(R.string.READING_COUNT), mIndex);
                    editor.putString(getString(R.string.WEEKLY_READINGS), mWeeklyReadings);
                    editor.putString(getString(R.string.SYSTOLIC_DIASTOLIC_STRING), mSystolicDiastolicString);
                    editor.commit();

                    // Display this stored reading in the edit texts for last systolic and diastolic
                    // Also, update the reading count (INDEX) and display it in readingCountEditText
                    lastSystolicEditText.setTextColor(Color.BLACK);
                    lastSystolicEditText.setText(mSystolic + "");

                    lastDiastolicEditText.setTextColor(Color.BLACK);
                    lastDiastolicEditText.setText(mDiastolic + "");

                    readingCountEditText.setText(mIndex + "");

                    // Mark any high or low readings in red.

                    if (anySystolicAbnormalReadings(mSystolic)){
                        lastSystolicEditText.setTextColor(Color.RED);
                    }

                    if (anyDiastolicAbnormalReadings(mDiastolic)){
                        lastDiastolicEditText.setTextColor(Color.RED);
                    }

                    // Mark any high or low readings in red.

                    if (anySystolicAbnormalReadings(mSystolic)){
                        systolicEditText.setTextColor(Color.RED);
                    }

                    if (anyDiastolicAbnormalReadings(mDiastolic)){
                        diastolicEditText.setTextColor(Color.RED);
                    }


                }
            }
        });

        displayReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Get stored readings from SharedPreferences file
                SharedPreferences sharedPreferences = MainActivity.this
                        .getSharedPreferences(getString(R.string.BP_PREF_FILE), MODE_PRIVATE);

                mIndex = sharedPreferences.getInt(getString(R.string.READING_COUNT),0);
                mWeeklyReadings = sharedPreferences.getString(getString(R.string.WEEKLY_READINGS),"");

                // If the keeper is empty, alert the user.

                if(mIndex==0){
                    Toast.makeText(MainActivity.this,
                            "You do not have any stored readings to display yet, store some first!",
                            Toast.LENGTH_LONG).show();

                }

                else {


                    // Start a new Intent and transmit the weeklyReadings string to a new screen
                    // that displays the results on a list view.

                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    intent.putExtra(getString(R.string.weekly_readings), mWeeklyReadings);
                    startActivity(intent);
                }

            }
        });

        resetUIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                systolicEditText.requestFocus();
                systolicEditText.setTextColor(Color.BLACK);
                diastolicEditText.setTextColor(Color.BLACK);
                systolicEditText.setText("");
                diastolicEditText.setText("");

            }
        });

        clearWeeklyReadingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Reset index back to zero and initialize array. This is done in
                // clearAllReadings of Keeper.java

                mWeeklyReadingKeeper.clearAllReadings();

                // Clear all EditTexts
                lastSystolicEditText.setText("");
                lastDiastolicEditText.setText("");
                readingCountEditText.setText("");
                systolicEditText.setText("");
                diastolicEditText.setText("");
                systolicEditText.requestFocus();

                SharedPreferences sharedPreferences = MainActivity.this
                        .getSharedPreferences(getString(R.string.BP_PREF_FILE), MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

            }
        });

    }

    public boolean anySystolicAbnormalReadings(int s){

        if((s>120)||(s<90)){
            return true;
        }


        else{
            return false;
        }

    }

    public boolean anyDiastolicAbnormalReadings(int d){

        if((d>80)||(d<60)){
            return true;
        }

        else{
            return false;
        }
    }



}