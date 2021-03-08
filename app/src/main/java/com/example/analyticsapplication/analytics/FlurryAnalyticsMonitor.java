/*
 * Copyright 2019 Supermac
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.analyticsapplication.analytics;

import android.content.Context;
import android.os.Bundle;

import com.example.analyticsapplication.events.GenericEvent;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import timber.log.Timber;

public class FlurryAnalyticsMonitor extends AnalyticsMonitor {

    private static FlurryAnalyticsMonitor appEventsLogger;

    public FlurryAnalyticsMonitor() {
    }

    public static FlurryAnalyticsMonitor getInstance(){
        if (appEventsLogger == null) appEventsLogger = new FlurryAnalyticsMonitor();
        return appEventsLogger;
    }

    @Override
    public void logEvent(GenericEvent genericEvent, Context context) {
        Timber.i("Flurry Events : %s", genericEvent.toString());
        // Capture the event parameters in Map
        Map<String, String> eventParams = new HashMap<String, String>();
        eventParams = bundleToMap(genericEvent.getCustomParameters());
        FlurryAgent.logEvent(genericEvent.getLabel(), eventParams);
    }

    public static Map<String, String> bundleToMap(Bundle extras) {
        Map<String, String> map = new HashMap<String, String>();

        if(extras !=null) {
            Set<String> ks = extras.keySet();
            Iterator<String> iterator = ks.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                map.put(key, extras.getString(key));
            }
        }
        return map;
    }
}
