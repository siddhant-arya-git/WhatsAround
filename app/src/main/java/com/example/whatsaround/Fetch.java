package com.example.whatsaround;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fetch extends AppCompatActivity {
    public JSONArray result;
    private final static String TAG = MainActivity.class.getSimpleName();

    private final static String RAPIDAPI_KEY = "af4b1c82e4mshf193d7825c9ce5cp14720djsnfb2bd162f921";
    private final static String RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com";

    private final OkHttpClient client = new OkHttpClient();

    public void getRapidApiAsync(String url, String rapidApiKey, String rapidApiHost, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", rapidApiKey)
                .addHeader("x-rapidapi-host", rapidApiHost)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void findPlacesNearby(double lat, double lng, String type, int radius, String language, Callback callback) {
        getRapidApiAsync(String.format(Locale.US, "https://%s/FindPlacesNearby?location=%.6f,%.6f&type=%s&radius=%d&language=%s", RAPIDAPI_TRUEWAY_PLACES_HOST, lat, lng, type, radius, language),
                RAPIDAPI_KEY,
                RAPIDAPI_TRUEWAY_PLACES_HOST,
                callback);
    }

    private void showResults(String responseStr) {
        try {
            JSONObject jsonObj = new JSONObject(responseStr);
            // Getting results JSON Array node
            JSONArray results = jsonObj.getJSONArray("results");
            Log.d(TAG, "found places: " + results.length());
            // looping through All Results
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String id = result.getString("id");
                String name = result.getString("name");
                JSONObject location = result.getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                Log.d( "LAT", String.format(Locale.US, "%f",lat));
                Log.d( "LNG", String.format(Locale.US, "%f",lng));
                Integer distance = result.has("distance") ? result.getInt("distance") : 0; // present for FindPlacesNearby only
                Log.d(TAG, String.format(Locale.US, "result[%s]: id=%s; name=%s; lat=%.6f; lng=%.6f; distance=%d", i, id, name, lat, lng, distance));
//                LatLng userLocation = new LatLng(lat,lng);
//                mMap.addMarker(new MarkerOptions().position(userLocation).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }
            result=results;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void details(Location userlocation, String type) {

        findPlacesNearby(userlocation.getLatitude(),userlocation.getLongitude(), type, 10000, "en",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Something went wrong
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseStr = response.body().string();
                            // Do what you want to do with the response.
                            Log.d(TAG, "findPlacesNearby response: " + responseStr);
                            showResults(responseStr);

                        } else {
                            // Request not successful
                        }
                    }
                });


    }
}