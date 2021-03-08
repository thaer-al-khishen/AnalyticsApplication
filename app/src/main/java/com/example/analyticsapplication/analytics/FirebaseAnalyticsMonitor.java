/*
 *  Copyright (c) 2018. SuperMAC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * imitations under the License.
 *
 */

package com.example.analyticsapplication.analytics;

import android.content.Context;

import com.example.analyticsapplication.events.GenericEvent;
import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsMonitor extends AnalyticsMonitor {

    private static FirebaseAnalyticsMonitor instance;

    private FirebaseAnalyticsMonitor() {

    }

    public static FirebaseAnalyticsMonitor getInstance() {
        if (instance == null) instance = new FirebaseAnalyticsMonitor();
        return instance;
    }

    @Override
    public void logEvent(GenericEvent genericEvent, Context context) {
        FirebaseAnalytics.getInstance(context).logEvent(genericEvent.getLabel(), genericEvent.getCustomParameters());
    }
}
