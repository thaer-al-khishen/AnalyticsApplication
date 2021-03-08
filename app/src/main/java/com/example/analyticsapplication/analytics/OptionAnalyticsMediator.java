package com.example.analyticsapplication.analytics;

import android.content.Context;

import com.example.analyticsapplication.events.OptionSelectedGenericEvent;

public class OptionAnalyticsMediator extends AnalyticsMediator {
    public void logFirstOptionSelected(Context context) {
        OptionSelectedGenericEvent event = new OptionSelectedGenericEvent();
        dispatchEvent(context, event);
    }
}
