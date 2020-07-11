package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class BusinessActivity extends AppCompatActivity {

    RecyclerView mRcBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        mRcBusiness = findViewById(R.id.rc_business);
        mRcBusiness.setLayoutManager(new LinearLayoutManager(this));
    }
    public void onBusinessNewsClicked(View view){
        API_Interface api_interface = APIClient.getClient().create(API_Interface.class);
        Call<String> getBusiness = api_interface.getBusiness();
        getBusiness.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String responseValue = response.body();
                ArrayList<Articles> articles = new ArrayList<>();

                try {
                    JSONObject responseObject = new JSONObject(responseValue);
                    JSONArray jsonArray = responseObject.getJSONArray("articles");

                    for(int i=0;i<jsonArray.length();i++){
                        Articles newArticles = Articles.parseJSONObject(jsonArray.optJSONObject(i));
                        articles.add(newArticles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NewsAdapter adapter = new NewsAdapter(BusinessActivity.this,articles);
                mRcBusiness.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void moveToHomeFromBusiness(View view){
        startActivity(new Intent(BusinessActivity.this,CaterorgyActivity.class));
        finish();
    }
}