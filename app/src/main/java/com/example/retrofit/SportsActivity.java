package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsActivity extends AppCompatActivity {
    RecyclerView mRcSports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        mRcSports = findViewById(R.id.rc_sports);
        mRcSports.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onSportsNewsClicked(View view){
        API_Interface apiInterface = APIClient.getClient().create(API_Interface.class);
        Call<String> getSports = apiInterface.getSports();
        getSports.enqueue(new Callback<String>() {
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
                NewsAdapter adapter = new NewsAdapter(SportsActivity.this,articles);
                mRcSports.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(SportsActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void moveToHomeFromSports(View view){
        startActivity(new Intent(SportsActivity.this,CaterorgyActivity.class));
        finish();
    }
}