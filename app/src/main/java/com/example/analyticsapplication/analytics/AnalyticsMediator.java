package com.example.analyticsapplication.analytics;

import android.content.Context;

import com.example.analyticsapplication.events.GenericEvent;

import timber.log.Timber;

public class AnalyticsMediator {

    private final FirebaseAnalyticsMonitor firebaseAnalyticsTracker;
    private final FaceBookAnalyticsMonitor faceBookAnalyticsTracker;
    private final FlurryAnalyticsMonitor flurryAnalyticsTracker;
//    private final HuaweiAnalyticsTracker huaweiAnalyticsTracker;

    public AnalyticsMediator() {
        firebaseAnalyticsTracker = FirebaseAnalyticsMonitor.getInstance();
        faceBookAnalyticsTracker = FaceBookAnalyticsMonitor.getInstance();
        flurryAnalyticsTracker = FlurryAnalyticsMonitor.getInstance();
//        huaweiAnalyticsTracker = HuaweiAnalyticsTracker.getInstance();
    }

    protected void dispatchEvent(Context context, GenericEvent genericEvent) {
        Timber.i(genericEvent.toString());
        firebaseAnalyticsTracker.logEvent(genericEvent, context);
        faceBookAnalyticsTracker.logEvent(genericEvent, context);
        flurryAnalyticsTracker.logEvent(genericEvent, context);
//        huaweiAnalyticsTracker.logEvent(event, context);
    }
}
