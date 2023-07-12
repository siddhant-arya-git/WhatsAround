package com.example.whatsaround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class EmergencyActivity extends AppCompatActivity {

    public void emergency(View view){
        ImageButton imageButton = (ImageButton) view;

        //The tag was assigned so that the location type corresponds to the name in the API documentation.
        String tappedButton = imageButton.getTag().toString();

        Log.i("Tapped Tag",tappedButton);
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        intent.putExtra("tapped",tappedButton);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
    }
}