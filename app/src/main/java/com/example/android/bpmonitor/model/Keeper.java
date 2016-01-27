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

            String systolic = mSystolicDiastolicString.substring((6*i),(6*i+3));

            int s = Integer.parseInt(systolic);

            String diastolic1 = mSystolicDiastolicString.substring((6*i+3),(6*i+5));

            char c = mSystolicDiastolicString.charAt(6*i+5);

            String diastolic = diastolic1 + c;

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
        int systolicValue;
        int diastolicValue;
        String systolicString;
        String diastolicString;

        for(int i=0; i<mIndex; i++){

            systolicValue = mReading[i].getSystolic();
            diastolicValue = mReading[i].getDiastolic();

            // Validate: if the systolic or diastolic values have only 1 or 2 digits, make them
            // three digits wide by putting in one or two leading zeroes.

            systolicString = putLeadingZeroes(systolicValue);
            diastolicString = putLeadingZeroes(diastolicValue);


            // Concatenate the days, the systolic and diastolic values and the pressure status.

            msg = msg + "Day " + (i+1) + ": " +
                    systolicString + "/" +
                    diastolicString + ": " +
                    mReading[i].getBPStatus(mReading[i].getSystolic(), mReading[i].getDiastolic()) + ".";


        }
        return msg;
    }

    public String getAllSystolicDiastolic(){
        String msg = "";
        int systolicValue;
        int diastolicValue;
        String systolicString;
        String diastolicString;

        for(int i=0; i<mIndex; i++){

            systolicValue = mReading[i].getSystolic();
            diastolicValue = mReading[i].getDiastolic();

            // Validate: if the systolic or diastolic values have only 1 or 2 digits, make them
            // three digits wide by putting in one or two leading zeroes.

            systolicString = putLeadingZeroes(systolicValue);

            diastolicString = putLeadingZeroes(diastolicValue);

            msg = msg + systolicString + diastolicString;

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

    public String showAverage(int dayCount){


        String avg = "AVRG: ";
        int sumSystolic = 0;
        int sumDiastolic = 0;
        int avgSystolic = 0;
        int avgDiastolic = 0;
        String bpStatus="";

        for(int i=0; i<dayCount; i++){
            //sumSystolic = sumSystolic + 120;
            sumSystolic = sumSystolic + mReading[i].getSystolic();
            //sumDiastolic = sumDiastolic + 80;
            sumDiastolic = sumDiastolic + mReading[i].getDiastolic();
        }
        avgSystolic = sumSystolic / dayCount;
        avgDiastolic = sumDiastolic / dayCount;
        bpStatus = mBPStatus.getBPStatus(avgSystolic, avgDiastolic);

        String avgSystolicString = putLeadingZeroes(avgSystolic);
        String avgDiastolicString = putLeadingZeroes(avgDiastolic);

        avg = avg + avgSystolicString + "/" + avgDiastolicString + ": " + bpStatus +".";

        //String dummy = "abc";

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

    public String putLeadingZeroes(int value){

        if(value<10){
            return "00" + value;

        }

        else if((value>=10)&&(value<100)){
            return "0" + value;
        }

        else{
            // if value >=100

            return value + "";

        }

    }


}