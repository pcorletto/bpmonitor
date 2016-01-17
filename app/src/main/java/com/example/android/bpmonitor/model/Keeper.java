package com.example.android.bpmonitor.model;

/**
 * Created by hernandez on 12/9/2015.
 */
public class Keeper {

    public Reading[] mReading = new Reading[7];

    public int index = 0;

    private BPStatus mBPStatus = new BPStatus();


    public void setAReading(int s, int d){
        if(index==7){
            //Keeper is full. Do not store any more readings...
        }
        else {
            mReading[index] = new Reading(s, d);

            index++;
        }
    }


    public String getAllReadings(){
        String msg = "";

        for(int i=0; i<index; i++){

            msg = msg + "Day " + (i+1) + ":\t\t" + mReading[i].getSystolic() + " / " +
                    mReading[i].getDiastolic() + " : " +
                    mReading[i].getBPStatus(mReading[i].getSystolic(), mReading[i].getDiastolic())
                    + "\n\n";
        }
        return msg + showAverage();
    }

    public int getIndex(){

        return index;
    }

    public String showAverage(){
        String avg = "Average";
        int sumSystolic = 0;
        int sumDiastolic = 0;
        int avgSystolic = 0;
        int avgDiastolic = 0;
        String bpStatus="";
        for(int i=0; i<index; i++){
            sumSystolic = sumSystolic + mReading[i].getSystolic();
            sumDiastolic = sumDiastolic + mReading[i].getDiastolic();
        }
        avgSystolic = sumSystolic / index;
        avgDiastolic = sumDiastolic / index;
        bpStatus = mBPStatus.getBPStatus(avgSystolic, avgDiastolic);

        avg = avg + "\n\n" +  avgSystolic + " / " + avgDiastolic + " : " + bpStatus;

        return avg;

    }

    public boolean isEmpty(){
        if(index==0) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isFull(){
        if(index==7) {
            return true;
        }
        else{
            return false;
        }
    }

}