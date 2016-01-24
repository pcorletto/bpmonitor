package com.example.android.bpmonitor.model;

/**
 * Created by hernandez on 1/11/2016...
 */
public class BPStatus{

    private String mBPStatus = "";

    public String getBPStatus(int s, int d){
        if((s>120)||(d>80)){

            mBPStatus = "high";

            if(s<90){
                mBPStatus = "systolic low and diastolic high";
            }

            if(d<60){
                mBPStatus = "systolic high and diastolic low";
            }

        }
        else if (((s>=90)&&(s<=120)&&((d>=60)&&(d<=80)))) {
            mBPStatus = "normal";
        }
        else{
            mBPStatus = "low";
        }
        return mBPStatus;
    }


}
