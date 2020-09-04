package com.example.android.nasaobservationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class nivlimageActivity extends AppCompatActivity {

    ImageView nivlimage;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivlimage);


        nivlimage = findViewById(R.id.nivlimage);
        Intent intent = getIntent();
        String href = intent.getStringExtra("href");
        progressDialog = new ProgressDialog(nivlimageActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();


        Picasso.with(nivlimageActivity.this).load(href).fit().into(nivlimage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onError() {

            }
        });

    }

}

