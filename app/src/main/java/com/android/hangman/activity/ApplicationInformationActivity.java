package com.android.hangman.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.hangman.R;

import androidx.appcompat.app.AppCompatActivity;


public class ApplicationInformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_information);
    }

    @Override
    public void onBackPressed() {
        Intent intentHomeActivity = new Intent(ApplicationInformationActivity.this, HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }
}
