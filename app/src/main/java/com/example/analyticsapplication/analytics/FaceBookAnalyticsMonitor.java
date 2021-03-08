/*
 * Copyright (c) 2019. SuperMAC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.analyticsapplication.analytics;

import android.content.Context;

import com.example.analyticsapplication.events.GenericEvent;
import com.facebook.appevents.AppEventsLogger;

import timber.log.Timber;

public class FaceBookAnalyticsMonitor extends AnalyticsMonitor {

    private static FaceBookAnalyticsMonitor appEventsLogger;

    private FaceBookAnalyticsMonitor(){
    }

    public static FaceBookAnalyticsMonitor getInstance(){
        if (appEventsLogger == null) appEventsLogger = new FaceBookAnalyticsMonitor();
        return appEventsLogger;
    }
    @Override
    public void logEvent(GenericEvent genericEvent, Context context) {
        Timber.i("Facebook Events : %s", genericEvent.toString());
        AppEventsLogger.newLogger(context).logEvent(genericEvent.getLabel(), genericEvent.getCustomParameters());
    }
}
