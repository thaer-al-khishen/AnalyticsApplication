package com.example.analyticsapplication.events;

import android.os.Bundle;

public abstract class GenericEvent {
    private String label;
    protected Bundle customParameters;

    public GenericEvent(String label) {
        this.label = label;
    }

    public abstract void setCustomParameters();

    public String getLabel() {
        return label;
    }

    public Bundle getCustomParameters() {
        return customParameters;
    }

    @Override
    public String toString() {
        return "label: " +label+ " parameters:" + (customParameters != null ? customParameters.toString(): "");
    }
}
