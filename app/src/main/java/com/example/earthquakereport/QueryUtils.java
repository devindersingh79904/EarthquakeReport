package com.example.earthquakereport;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
     /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */


    public static List<Earthquake> fetchEarthquakeData(String reqestedUrl){

        URL url = CreateUrl(reqestedUrl);
        String jsonRespone = null;
        List<Earthquake> l = null;
        try
        {
            jsonRespone = makeHttpRequest(url);
            l = extractEarthquakes(jsonRespone);
        }
        catch (IOException e){
            Log.e("LOG_DATA","Problem makking http request" + e);
        }
        return l;
    }

    public static List<Earthquake> extractEarthquakes(String earthquakeJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject earthquakesObject = new JSONObject(earthquakeJSON);

            JSONArray featuresArray = earthquakesObject.getJSONArray("features");

            for(int i=0;i<featuresArray.length();i++){
                JSONObject featureObject = featuresArray.getJSONObject(i);

                JSONObject propertiesObject = featureObject.getJSONObject("properties");

                double mag = propertiesObject.getDouble("mag");
                String location = propertiesObject.getString("place");
                long time = propertiesObject.getLong("time");
                String url = propertiesObject.getString("url");
                earthquakes.add(new Earthquake(mag,location,time,url));


            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        // Return the list of earthquakes
        return earthquakes;
    }


    private static URL CreateUrl(String surl){
        URL url = null;

        try {
            url = new URL(surl);
        }
        catch (MalformedURLException e){
            Log.e("LOG_TAG","Problem Bilding the URL : " + e.getMessage());
        }


        return url;

    }



    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e("LOG_TAG","Error While fetching data." + urlConnection.getResponseCode());
            }
        }
        catch (IOException e){
            Log.e("LOG_TAG","Problem reteiving the earthquake JSON result." + e.getMessage());
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null){
                output.append(line);
                line = reader.readLine();

            }
        }
        return output.toString();
    }

}