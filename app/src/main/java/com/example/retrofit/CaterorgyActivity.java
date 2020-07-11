package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


public class CaterorgyActivity extends AppCompatActivity {
    private TextView mTvHeadline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caterorgy);
        mTvHeadline = findViewById(R.id.tv_marquee_text);

    }

    public void onSportsPressed(View view) {
        startActivity(new Intent(CaterorgyActivity.this, SportsActivity.class));
    }

    public void onBusinessPressed(View view) {
        startActivity(new Intent(CaterorgyActivity.this, BusinessActivity.class));
    }

    public void onHealthPressed(View view) {
        startActivity(new Intent(CaterorgyActivity.this, HealthActivity.class));
    }
    public void onTopNewsClicked(View view){
        startActivity(new Intent(CaterorgyActivity.this,MainActivity.class));
    }
}