package com.example.android.bpmonitor.model;

/**
 * Created by hernandez on 1/11/2016...
 */
public class BPStatus{

    private String mBPStatus = "";

    public String getBPStatus(int s, int d){
        if((s>120)||(d>80)){

            mBPStatus = "high                ";

            if(s<90){
                mBPStatus = "syst:low, diast:high";
            }

            if(d<60){
                mBPStatus = "syst:high, diast:low";
            }

        }
        else if (((s>=90)&&(s<=120)&&((d>=60)&&(d<=80)))) {
            mBPStatus = "normal              ";
        }
        else{
            mBPStatus = "low                 ";
        }
        return mBPStatus;
    }


}
