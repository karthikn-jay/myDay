package com.karthiknjay.mydays.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.karthiknjay.mydays.R;

/**
 * Created by karthik on 10-04-2016.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
