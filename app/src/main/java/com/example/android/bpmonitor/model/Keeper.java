package com.example.android.bpmonitor.model;

/**
 * Created by hernandez on 12/9/2015.
 */
public class Keeper {

    public Reading[] mReading = new Reading[7];

    private int mIndex;

    private String mSystolicDiastolicString;

    private BPStatus mBPStatus = new BPStatus();

    public void reloadArray(){

        for(int i=0; i<mIndex; i++) {

            // Extract the diastolic and systolic values from the readings
            // from the stored mSystolicDiastolicString string passed in from MainActivity.

            String systolic = mSystolicDiastolicString.substring((5*i),(5*i+3));

            int s = Integer.parseInt(systolic);

            String diastolic = mSystolicDiastolicString.substring((5*i+3),(5*i+5));

            int d = Integer.parseInt(diastolic);

            mReading[i] = new Reading(s, d);

        }


    }


    public void setAReading(int s, int d){
        if(mIndex==7){
            //Keeper is full. Do not store any more readings...
        }
        else {
            mReading[mIndex] = new Reading(s, d);

            mIndex++;
        }
    }


    public String getAllReadings(){
        String msg = "";

        for(int i=0; i<mIndex; i++){

            msg = msg + "Day " + (i+1) + ":\n\n" +
                    mReading[i].getSystolic() + " / " +
                    mReading[i].getDiastolic() + " : " +
                    mReading[i].getBPStatus(mReading[i].getSystolic(), mReading[i].getDiastolic())
                    + "\n\n";

        }
        return msg + showAverage();
    }

    public String getAllSystolicDiastolic(){
        String msg = "";

        for(int i=0; i<mIndex; i++){

            msg = msg + mReading[i].getSystolic() + mReading[i].getDiastolic();

        }
        return msg;

    }

    public void clearAllReadings(){

        // Reset index back to 0, and initialize the array of readings.

        mIndex = 0;
        mReading = new Reading[7];
    }


    public int getIndex(){

        return mIndex;
    }

    public void setIndex(int index){
        mIndex = index;
    }

    public void setSystolicDiastolicString(String systolicDiastolicString){

        mSystolicDiastolicString = systolicDiastolicString;

    }

    public String getSystolicDiastolicString(){

        return mSystolicDiastolicString;

    }

    public String showAverage(){
        String avg = "Average";
        int sumSystolic = 0;
        int sumDiastolic = 0;
        int avgSystolic = 0;
        int avgDiastolic = 0;
        String bpStatus="";
        for(int i=0; i<mIndex; i++){
            sumSystolic = sumSystolic + mReading[i].getSystolic();
            sumDiastolic = sumDiastolic + mReading[i].getDiastolic();
        }
        avgSystolic = sumSystolic / mIndex;
        avgDiastolic = sumDiastolic / mIndex;
        bpStatus = mBPStatus.getBPStatus(avgSystolic, avgDiastolic);

        avg = avg + "\n\n" +  avgSystolic + " / " + avgDiastolic + " : " + bpStatus;

        return avg;

    }

    public boolean isEmpty(){
        if(mIndex==0) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isFull(){
        if(mIndex==7) {
            return true;
        }
        else{
            return false;
        }
    }

}