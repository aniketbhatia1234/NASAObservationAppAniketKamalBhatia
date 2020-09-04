package com.example.android.nasaobservationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button apod;
    Button nivl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apod= findViewById(R.id.apod);
        nivl = findViewById(R.id.nivl);

        apod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, APODActivity.class);
                startActivity(intent);
            }
        });

        nivl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( MainActivity.this, NIVLActivity.class);
                startActivity(intent);
            }
        });

    }
}