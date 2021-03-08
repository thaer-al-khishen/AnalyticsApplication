package com.example.analyticsapplication.events;

import android.os.Bundle;

public class OptionSelectedGenericEvent extends GenericEvent {
    private static final String LABEL = "first_option_selected";
    private static final String STORE_NAME_PARAMETER = "store_name";

    public OptionSelectedGenericEvent() {
        super(LABEL);
        setCustomParameters();
    }
    @Override
    public void setCustomParameters() {
        Bundle bundle = new Bundle();
        this.customParameters = bundle;
    }
}
