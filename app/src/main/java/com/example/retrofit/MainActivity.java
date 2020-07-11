package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRcNews;
    private TextView mTvTry;
    String ofMarquee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcNews = findViewById(R.id.rc_NewHeadLines);
        mTvTry = findViewById(R.id.tv_try);

        mRcNews.setLayoutManager(new LinearLayoutManager(this));

    }

    public void onGetNewsClicked(View view){
        API_Interface api_interface = APIClient.getClient().create(API_Interface.class);

    //    Call <String> getNews = api_interface.getNews("google-news","4c82d7e8131841f484c6cf169bb83ae4");
     //   Call <String> getNews = api_interface.getNews("bbc-news","top","4dbc17e007ab436fb66416009dfb59a8");

        Call<String> getNews = api_interface.getNews();

        getNews.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseValue = response.body();
                ArrayList<Articles> articles = new ArrayList<>();
                ArrayList<Articles> headlines = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(responseValue);
                    JSONArray articlesArray = responseObject.getJSONArray("articles");

                    for(int i = 0;i<articlesArray.length();i++){
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);

                    }

                    JSONObject headlineObject = new JSONObject(responseValue);
                    JSONArray headlineArray = headlineObject.getJSONArray("articles");
                    for (int j=0;j<headlineArray.length();j++){
                        Articles newHeadlines = Articles.parseJSONHeadlines(headlineArray.optJSONObject(j));
                        headlines.add(newHeadlines);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ofMarquee = headlines.toString();
                mTvTry.setText(ofMarquee);
                mTvTry.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTvTry.setSelected(true);
                NewsAdapter adapter = new NewsAdapter(MainActivity.this,articles);
                mRcNews.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}