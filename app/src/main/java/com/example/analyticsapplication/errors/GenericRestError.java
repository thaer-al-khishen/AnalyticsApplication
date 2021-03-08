package com.example.analyticsapplication.errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericRestError {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errors")
    @Expose
    private Error errors;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;

    public GenericRestError() {
    }

    public GenericRestError(String message, Error errors, Integer statusCode) {
        super();
        this.message = message;
        this.errors = errors;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error getErrors() {
        return errors;
    }

    public void setErrors(Error errors) {
        this.errors = errors;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}

