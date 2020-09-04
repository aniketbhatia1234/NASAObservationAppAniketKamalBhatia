package com.example.android.nasaobservationapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APODActivity extends AppCompatActivity {


    WebView webview;
    TextView displaydate,txtviewtitle,txtviewexplanation;
    ImageView apodimage;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar cal;
    int yearselected=-1,monthselected=-1,dayselected=-1;
    int yearnow=-1,monthnow=-1,daynow=-1;
    String date;
    apodapi apodapi;
    ProgressDialog progressDialog;
    boolean firstrun= true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        displaydate = findViewById(R.id.displaydate);
        txtviewtitle= findViewById(R.id.title);
        txtviewexplanation = findViewById(R.id.explanation);
        apodimage= findViewById(R.id.apodimage);
        webview = findViewById(R.id.apodvideo);
        progressDialog = new ProgressDialog(APODActivity.this);
        progressDialog.setMessage("Loading");



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apodapi = retrofit.create(com.example.android.nasaobservationapp.apodapi.class);





        displaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstrun==true){
                    cal = Calendar.getInstance();
                    yearnow = cal.get(Calendar.YEAR);
                    monthnow = cal.get(Calendar.MONTH);
                    daynow = cal.get(Calendar.DAY_OF_MONTH);
                    yearselected=yearnow;
                    monthselected=monthnow+1;
                    dayselected=daynow;
                }

                DatePickerDialog dialog= new DatePickerDialog(
                        APODActivity.this, android.R.style.Theme_Holo_Dialog, dateSetListener,yearselected,monthselected-1, dayselected);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                firstrun= false;
            }
        });

        dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                yearselected=year;
                monthselected=month;
                dayselected=dayOfMonth;

                if((yearselected==yearnow&& monthselected<monthnow+1)||yearselected<yearnow||(yearselected==yearnow&&monthselected==monthnow+1&&dayselected<=daynow)) {
                    date = String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                    displaydate.setText(date);
                    progressDialog.show();
                    getapod();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter a valid date",Toast.LENGTH_SHORT).show();

                }
            }
        };




    }

    private void getapod() {

        Call<APODObject> call = apodapi.getadpodobj("XHLx1CB8ui1kqoxudWBfbmFWdMUFnuZLcMfjY257", date );

        call.enqueue(new Callback<APODObject>() {
            @Override
            public void onResponse(Call<APODObject> call, Response<APODObject> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Code:"+ response.code(),Toast.LENGTH_SHORT).show();

                    return;
                }

                APODObject apodObject =  response.body();
                String url= apodObject.geturl();
                String explanation = apodObject.getExplanation();
                String title = apodObject.getTitle();


                if(apodObject.getMedia_type().equals("image")) {
                    apodimage.setVisibility(View.VISIBLE);
                    webview.setVisibility(View.INVISIBLE);
                    txtviewtitle.setText(title);
                    txtviewexplanation.setText(explanation);
                    Picasso.with(APODActivity.this).load(url).fit().into(apodimage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }

                else if(apodObject.getMedia_type().equals("video")){
                    apodimage.setVisibility(View.INVISIBLE);
                    webview.setVisibility(View.VISIBLE);


                    txtviewtitle.setText(title);
                    txtviewexplanation.setText(explanation);
                    webview.getSettings().setJavaScriptEnabled(true);
                    webview.setWebViewClient(new WebViewClient());
                    webview.loadUrl(url);
                    progressDialog.dismiss();


                }

            }

            @Override
            public void onFailure(Call<APODObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });






    }
}
