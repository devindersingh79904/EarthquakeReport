package com.example.earthquakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);


        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();


        ListView listView = (ListView) findViewById(R.id.list_view);
        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Earthquake currentEarthquake = earthquakes.get(position);
//                Log.v("EarthquackActicty","" + position);
//                Log.v("EarthquackActictyURL",earthquakes.get(position).getURL());

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentEarthquake.getURL()));

                if(intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(intent);
                }
            }
        });


    }
}
