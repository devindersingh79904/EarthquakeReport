package com.example.earthquakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, List<Earthquake> list){
        super(context,0,list);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;

        if(listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_view,parent,false);
        }


        Earthquake currentEarthquake = getItem(position);


        TextView magnitudeView = (TextView) listView.findViewById(R.id.magnitude_text_view);
        magnitudeView.setText(formateMagnitude(currentEarthquake.getMagnitude()));


        GradientDrawable magnitudeColor = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColornew = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeColor.setColor(magnitudeColornew);


        TextView locationView = (TextView) listView.findViewById(R.id.location_text_view);
        TextView countryView = (TextView) listView.findViewById(R.id.country_text_view);


        String fulllocaAndCountry = currentEarthquake.getLocation();

        if(fulllocaAndCountry.contains("of")){
            String[] ar = fulllocaAndCountry.split("of");
            locationView.setText(ar[0] + " of");
            countryView.setText(ar[1]);

        }
        else{
            locationView.setText("near the");
            countryView.setText(fulllocaAndCountry);
        }
        TextView dateView = (TextView) listView.findViewById(R.id.date_text_view);
        TextView timeView = (TextView) listView.findViewById(R.id.time_text_view);

        Date dateObject = new Date(currentEarthquake.getTimeInMilliSeconds());

        dateView.setText(formatDate(dateObject));
        timeView.setText(formatTime(dateObject));




        return listView;
    }


    private  String formateMagnitude(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(double magnitude){

        int mag = (int) Math.floor(magnitude);

        int magnitudeColorResourceId;
        switch (mag){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10 :
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
        }

        int color = ContextCompat.getColor(getContext(),magnitudeColorResourceId);

        return color;

    }
}
