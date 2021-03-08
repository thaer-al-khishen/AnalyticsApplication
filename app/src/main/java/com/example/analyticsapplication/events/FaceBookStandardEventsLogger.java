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

package com.example.analyticsapplication.events;


import android.content.Context;
import android.os.Bundle;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

import java.math.BigDecimal;
import java.util.Currency;

import timber.log.Timber;

public class FaceBookStandardEventsLogger {

    public static void logViewContentEvent (Context context, String contentType, String contentData, String contentId, String currency, double price) {
        try {
            Bundle params = new Bundle();
            params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
            params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
            params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
            params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
            AppEventsLogger.newLogger(context).logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, price, params);
        }catch (Exception e){
            Timber.e(e);
        }
    }

    public static void logAddToCartEvent (Context context, String contentData, String contentId, String contentType, String currency, double price) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT, contentData);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        AppEventsLogger.newLogger(context).logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, price, params);
    }

    public static void logAddPaymentInfoEvent (Context context, boolean success) {
        Bundle params = new Bundle();
        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, success ? 1 : 0);
        AppEventsLogger.newLogger(context).logEvent(AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO, params);
    }

    public static void logInitiateCheckoutEvent (Context context, String contentId, int numItems, String currency, double totalPrice) {
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, numItems);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        AppEventsLogger.newLogger(context).logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, totalPrice, params);
    }

    public static void logPurchasedEvent (Context context, int numItems, String contentId, String currency, BigDecimal price) {
        Bundle params = new Bundle();
        params.putInt(AppEventsConstants.EVENT_PARAM_NUM_ITEMS, numItems);
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId);
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        AppEventsLogger.newLogger(context).logPurchase(price, Currency.getInstance(currency),params);
    }
}
