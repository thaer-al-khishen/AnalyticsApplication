package com.example.analyticsapplication;

import android.os.Bundle;
import android.widget.Button;

import com.example.analyticsapplication.analytics.OptionAnalyticsMediator;

public class MainActivity extends BaseActivity {
    private final OptionAnalyticsMediator dispatcher = new OptionAnalyticsMediator();
    private Button firstOptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstOptionButton = findViewById(R.id.firstOptionButton);
        firstOptionButton.setOnClickListener(v -> dispatcher.logFirstOptionSelected(v.getContext()));
    }

}