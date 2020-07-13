package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthActivity extends AppCompatActivity {

    private RecyclerView mRcHealth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        mRcHealth = findViewById(R.id.rc_health);
        mRcHealth.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(HealthActivity.this);

        progressDialog.setMessage("Getting the News...");
        getHealthNews();

    }

    private void getHealthNews() {
        progressDialog.show();

        API_Interface api_interface = APIClient.getClient().create(API_Interface.class);
        Call<String> getHealth = api_interface.getHealth();
        getHealth.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String value = response.body();
                ArrayList<Articles> articles  = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(value);

                    JSONArray articlesArray = jsonObject.getJSONArray("articles");

                    for(int i = 0;i<articlesArray.length();i++){
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();

                NewsAdapter adapter = new NewsAdapter(HealthActivity.this,articles);
                mRcHealth.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }

    public void moveToHomeFromHealth(View view){
        startActivity(new Intent(HealthActivity.this, CategoryActivity.class));
    }
}