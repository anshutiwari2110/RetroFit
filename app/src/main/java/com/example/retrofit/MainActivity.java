package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRcNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcNews = findViewById(R.id.rc_NewHeadLines);

        mRcNews.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onGetNewsClicked(View view){
        API_Interface api_interface = APIClient.getClient().create(API_Interface.class);

        Call <String> getNews = api_interface.getNews("google-news","4c82d7e8131841f484c6cf169bb83ae4");

        getNews.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseValue = response.body();
                ArrayList<Articles> articles = new ArrayList<>();

                try {
                    JSONObject responseObject = new JSONObject(responseValue);
                    JSONArray articlesArray = responseObject.getJSONArray("articles");

                    for(int i = 0;i<articlesArray.length();i++){
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NewsAdapter adapter = new NewsAdapter(MainActivity.this,articles);
                mRcNews.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}