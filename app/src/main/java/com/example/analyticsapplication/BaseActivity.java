package com.example.analyticsapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.analyticsapplication.utils.Preferences;

public class BaseActivity extends AppCompatActivity {

    public Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = Preferences.getInstance(this);
    }

}