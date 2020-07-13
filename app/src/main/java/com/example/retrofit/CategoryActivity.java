package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class CategoryActivity extends AppCompatActivity {
    private TextView mTvHeadline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mTvHeadline = findViewById(R.id.tv_marquee_text);

    }

    public void onSportsPressed(View view) {
        startActivity(new Intent(CategoryActivity.this, SportsActivity.class));
    }

    public void onBusinessPressed(View view) {
        startActivity(new Intent(CategoryActivity.this, BusinessActivity.class));
    }

    public void onHealthPressed(View view) {
        startActivity(new Intent(CategoryActivity.this, HealthActivity.class));
    }
    public void onTopNewsClicked(View view){
        startActivity(new Intent(CategoryActivity.this,MainActivity.class));
    }
    public void onEntertainmentPressed(View view){
        startActivity(new Intent(CategoryActivity.this,EntertainmentActivity.class));
    }
}