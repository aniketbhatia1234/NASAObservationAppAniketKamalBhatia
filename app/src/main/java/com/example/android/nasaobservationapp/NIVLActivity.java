package com.example.android.nasaobservationapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NIVLActivity extends AppCompatActivity {

    nivlapi nivlapi;
    EditText searchfield;
    String query;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> nasa_id = new ArrayList<>();
    ListView listView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivl);

        listView = findViewById(R.id.list);
        searchfield = findViewById(R.id.searchfield);
        progressDialog = new ProgressDialog(NIVLActivity.this);
        progressDialog.setMessage("Loading");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://images-api.nasa.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nivlapi = retrofit.create(nivlapi.class);


        searchfield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                query = searchfield.getText().toString();
                nasa_id.clear();
                titles.clear();
                getnivl();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nivlimage(position);
            }
        });


    }

    private void getnivl() {

        Call<NIVLObj> call = nivlapi.nivlsearch(query);

        call.enqueue(new Callback<NIVLObj>() {
            @Override
            public void onResponse(Call<NIVLObj> call, Response<NIVLObj> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                NIVLObj nivlObj = response.body();
                int itemlength = nivlObj.getCollections().getItemarray().length;


                for (int i = 0; i < itemlength; i++) {
                    titles.add(nivlObj.getCollections().getItems(i).getData().getTitle());
                    nasa_id.add(nivlObj.getCollections().getItems(i).getData().getNasa_id());
                }
                ArrayAdapter adapter = new ArrayAdapter(NIVLActivity.this, android.R.layout.simple_list_item_1, titles);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<NIVLObj> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void nivlimage(int position) {

        Call<NIVLObj> call = nivlapi.nivlimage(nasa_id.get(position));

        call.enqueue(new Callback<NIVLObj>() {
            @Override
            public void onResponse(Call<NIVLObj> call, Response<NIVLObj> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                NIVLObj nivlObj = response.body();
                String href = nivlObj.getCollections().getItems(0).getHref();

                Intent intent = new Intent(NIVLActivity.this, nivlimageActivity.class);
                intent.putExtra("href",href);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<NIVLObj> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}

