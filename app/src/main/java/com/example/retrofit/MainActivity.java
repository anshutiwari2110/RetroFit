package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.NewsURLClickListener {

    RecyclerView mRcNews;
    private TextView mTvTry;
    String ofMarquee;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRcNews = findViewById(R.id.rc_NewHeadLines);
        mTvTry = findViewById(R.id.tv_try);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Wait a moment...Getting Information :-)");
        ofMarquee = "";
        mRcNews.setLayoutManager(new LinearLayoutManager(this));

        getNewsHeadlines();

    }

    private void getNewsHeadlines() {

        progressDialog.show();

        API_Interface api_interface = APIClient.getClient().create(API_Interface.class);

        //    Call <String> getNews = api_interface.getNews("google-news","4c82d7e8131841f484c6cf169bb83ae4");
        //    Call <String> getNews = api_interface.getNews("bbc-news","top","4dbc17e007ab436fb66416009dfb59a8");

        Call<String> getNews = api_interface.getNews();

        getNews.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseValue = response.body();
                ArrayList<Articles> articles = new ArrayList<>();
                //         ArrayList<Articles> headlines = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(responseValue);
                    JSONArray articlesArray = responseObject.getJSONArray("articles");

                    for(int i = 0;i<articlesArray.length();i++){
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);

                        ofMarquee = ofMarquee.concat("    (").concat(String.valueOf(i+1)).concat(") .   ").concat(newArticles.title);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //
                mTvTry.setText(ofMarquee);

                //MARQUEE is used to display the truncated lines in the TextView
                mTvTry.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTvTry.setSelected(true);
                progressDialog.hide();

                NewsAdapter adapter = new NewsAdapter(MainActivity.this,articles);
                mRcNews.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }



    public void moveToHomeFromHeadlines(View view){
        startActivity(new Intent(MainActivity.this, CategoryActivity.class));
        finish();
    }



    @Override
    public void onURLClicked(String url) {
        if(url.isEmpty()){
            Toast.makeText(MainActivity.this, "Error 404", Toast.LENGTH_SHORT).show();
        }else {
            Intent urlIntent = new Intent(Intent.ACTION_VIEW);
            urlIntent.setData(Uri.parse(url));
            startActivity(urlIntent);
        }
    }
}