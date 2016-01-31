package com.example.android.bpmonitor.model;

/**
 * Created by hernandez on 12/9/2015.
 */
public class Reading {
    private int mSystolic, mDiastolic;
    private String mDateTime;
    private String mBPStatus;
    private BPStatus bpStatus = new BPStatus();

    // Constructors..

    public Reading(){

    }

    public Reading (int systolic, int diastolic, String dateTime){

        mSystolic = systolic;
        mDiastolic = diastolic;
        mBPStatus = this.getBPStatus(systolic, diastolic);
        mDateTime = dateTime;

    }

    public void setSystolic(int s){
        mSystolic = s;
    }

    public int getSystolic(){
        return mSystolic;
    }

    public void setDiastolic(int d){
        mDiastolic = d;
    }

    public int getDiastolic(){
        return mDiastolic;
    }

    public void setBPStatus(String status){
        mBPStatus = status;
    }

    public String getBPStatus(int s, int d) {

        mBPStatus = bpStatus.getBPStatus(s, d);

        return mBPStatus;

    }

    public String getDateAndTime(){
        return mDateTime;
    }

}

