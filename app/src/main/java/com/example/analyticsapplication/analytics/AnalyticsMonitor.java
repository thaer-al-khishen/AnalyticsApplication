package com.example.analyticsapplication.analytics;

import android.content.Context;

import com.example.analyticsapplication.events.GenericEvent;

public abstract class AnalyticsMonitor {
    public abstract void logEvent(GenericEvent genericEvent, Context context);
}
