package com.example.analyticsapplication.errors;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.analyticsapplication.application.MyApplication;
import com.example.analyticsapplication.utils.Preferences;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.BODY_SENT;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.USER_ID;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.USER_NAME;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.ERROR_CODE;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.ERROR_DOMAIN;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.JSON_DATA;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.MESSAGE;
import static com.example.analyticsapplication.crashlytics.CrashlyticsCustomKeys.METHOD;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class GenericNetworkError extends Throwable {
    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong! Please try again.";
    private static final String REQUEST_TIMEOUT_ERROR_MESSAGE = "Request timeout error.";
    private static final String NETWORK_ERROR_MESSAGE = "No internet connection!";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private final Throwable error;
    private Preferences preferences;

    public GenericNetworkError(Throwable error) {
        super(error);
        this.error = error;
        preferences = Preferences.getInstance(MyApplication.getInstance().getApplicationContext());
    }

    public String getMessage() {
        return error.getMessage();
    }

    public boolean isAuthFailure() {
        return error instanceof HttpException &&
                ((HttpException) error).code() == HTTP_UNAUTHORIZED;
    }

    public boolean isResponseNull() {
        return error instanceof HttpException && ((HttpException) error).response() == null;
    }

    public String getAppErrorMessage() {
        if (this.error instanceof UnknownHostException) return DEFAULT_ERROR_MESSAGE;

        if (this.error instanceof SocketTimeoutException) return REQUEST_TIMEOUT_ERROR_MESSAGE;

        if (this.error instanceof IOException) return NETWORK_ERROR_MESSAGE;

        if (!(this.error instanceof HttpException)) return DEFAULT_ERROR_MESSAGE;

        retrofit2.Response<?> response = ((HttpException) this.error).response();
        String status = getJsonStringFromResponse(response);
        if (!TextUtils.isEmpty(status)) return status;

        Map<String, List<String>> headers = response.headers().toMultimap();
        if (headers.containsKey(ERROR_MESSAGE_HEADER))
            return Objects.requireNonNull(headers.get(ERROR_MESSAGE_HEADER)).get(0);

        return DEFAULT_ERROR_MESSAGE;
    }

    @NonNull
    private String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        Request request = response.raw().request();
        String requestString = requestToJsonString(request.body());
        String errorString = errorToJsonString(response);
        try {
            GenericRestError error = new Gson().fromJson(errorString, GenericRestError.class);
            String message = error.getErrors() != null ? error.getErrors().getMessage().toString().substring(1,
                    error.getErrors().getMessage().toString().length() - 1) : "";
            logNonFatalExceptionsToFireBase(response, requestString, errorString, message, Integer.toString(error.getStatusCode()));
            return error.getErrors().getMessage().toString().substring(1,
                    error.getErrors().getMessage().toString().length() - 1);
        } catch (Exception e) {
            try {
                GenericExceptionApiError genericExceptionApiError = new Gson().fromJson(errorString, GenericExceptionApiError.class);
                String message = error != null ? error.getMessage().substring(1, error.getMessage().length() - 1) : "";
                logNonFatalExceptionsToFireBase(response, requestString, errorString, message, genericExceptionApiError.getStatusCode());
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
            return "";
        }
    }

    private String requestToJsonString(RequestBody requestBody) {
        try {
            FormBody formBody = (FormBody) requestBody;
            JSONObject jsonObject = new JSONObject();
            for(int i = 0; i < formBody.size(); i++) {
                if (!formBody.encodedName(i).equals("password")) {
                    String value;
                    try {
                        value = URLDecoder.decode(formBody.encodedValue(i), "UTF-8");
                        jsonObject.put(formBody.encodedName(i), value);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jsonObject.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private String errorToJsonString(Response<?> response){
        try {
            return response.errorBody() != null ? response.errorBody().string() : "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void logNonFatalExceptionsToFireBase(Response<?> response, String requestString, String errorString, String message, String statusCode) {
        FirebaseCrashlytics firebaseCrashlytics = FirebaseCrashlytics.getInstance();
        HttpUrl url = response.raw().request().url();
        String method = response.raw().request().method();

        firebaseCrashlytics.setUserId(Integer.toString(preferences.getUserId()));
        firebaseCrashlytics.setCustomKey(BODY_SENT, requestString);
        if(preferences.getUserId() != 0) {
            firebaseCrashlytics.setCustomKey(USER_ID, preferences.getUserId());
            firebaseCrashlytics.setCustomKey(USER_NAME, preferences.getUserFullName());
        }
        firebaseCrashlytics.setCustomKey(JSON_DATA, errorString);
        firebaseCrashlytics.setCustomKey(MESSAGE, message);
        firebaseCrashlytics.setCustomKey(METHOD, method);
        firebaseCrashlytics.setCustomKey(ERROR_CODE, statusCode);
        firebaseCrashlytics.setCustomKey(ERROR_DOMAIN, url.toString());
        firebaseCrashlytics.recordException(new Throwable("URL: " + url + "\n"
                + "Response : { message: " + message + ", status code: " + statusCode + "}"));
    }

    public Throwable getError() {
        return error;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericNetworkError that = (GenericNetworkError) o;

        return Objects.equals(error, that.error);

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}

