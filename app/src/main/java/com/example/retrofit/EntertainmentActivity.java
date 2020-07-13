package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntertainmentActivity extends AppCompatActivity {
    RecyclerView mRcEntertainment;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        mRcEntertainment = findViewById(R.id.rc_entertainment);
        mRcEntertainment.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(EntertainmentActivity.this);
        progressDialog.setMessage("Everything you wanted to know is on other side of time...");

        getEntertainmentNews();
    }

    private void getEntertainmentNews() {
        progressDialog.show();
        API_Interface apiInterface = APIClient.getClient().create(API_Interface.class);
        Call<String> getEntertainment = apiInterface.getEntertainment();
        getEntertainment.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseValue = response.body();
                ArrayList<Articles> articles = new ArrayList<>();

                try {
                    JSONObject responseObject = new JSONObject(responseValue);
                    JSONArray articlesArray = responseObject.getJSONArray("articles");

                    for(int i = 0; i<articlesArray.length();i++){
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();

                NewsAdapter adapter = new NewsAdapter(EntertainmentActivity.this,articles);
                mRcEntertainment.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EntertainmentActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        });

    }


    public void moveToHomeFromEntertainment(View view){
        startActivity(new Intent(EntertainmentActivity.this, CategoryActivity.class));
        finish();
    }
}