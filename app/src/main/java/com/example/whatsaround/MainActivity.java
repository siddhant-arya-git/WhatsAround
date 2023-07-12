package com.example.whatsaround;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public void buttonClick(View view){
        ImageButton imageButton = (ImageButton) view;

        //The tag was assigned so that the location type corresponds to the name in the API documentation.
        String tappedButton = imageButton.getTag().toString();

        if(!tappedButton.equals("emergency")){
            Log.i("Tapped Tag",tappedButton);
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            intent.putExtra("tapped",tappedButton);
            startActivity(intent);
        }
        else{
            Intent intent= new Intent(getApplicationContext(),EmergencyActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}