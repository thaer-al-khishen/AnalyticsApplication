package com.example.analyticsapplication.errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericExceptionApiError {
    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;

    public GenericExceptionApiError() {
    }

    public GenericExceptionApiError(String message, String statusCode) {
        super();
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}

