package com.example.earthquakereport;

import android.net.Uri;

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private long mTimeInMilliSeconds;
    private String mURL;

    public Earthquake(double magnitude,String location,long date,String url){
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliSeconds = date;
        mURL = url;
    }

    public double getMagnitude(){
        return mMagnitude;
    }

    public String getLocation(){
        return mLocation;
    }

    public long getTimeInMilliSeconds() {
        return mTimeInMilliSeconds;
    }

    public String getURL(){
        return mURL;
    }
}
