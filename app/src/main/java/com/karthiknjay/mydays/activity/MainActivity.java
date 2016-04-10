package com.karthiknjay.mydays.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.karthiknjay.mydays.R;
import com.karthiknjay.mydays.util.Utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String FORMAT_DAY = "%02d Days";
    private static final String FORMAT_TIME = "%02d Hr %02d min %02d sec";

    private TextView txtDays = null;
    private TextView txtTime = null;

    private CountDownTimer cnt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.registerOnSharedPreferenceChangeListener(this);

        txtDays = (TextView) findViewById(R.id.txtDays);
        txtTime = (TextView) findViewById(R.id.txtTime);

        runCountDown(sharedPref);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if ("lastDay".equals(key) || "lastTime".equals(key))
            runCountDown(prefs);
    }

    private void runCountDown(SharedPreferences sharedPref) {
        String lastDayStr = sharedPref.getString("lastDay", Utils.DEFAULT_LAST_DAY);
        String lastDayStr2 = setMonth(getMonth(lastDayStr), lastDayStr);
        String lastTimeStr = sharedPref.getString("lastTime", Utils.DEFAULT_LAST_TIME); // add seconds
        String str = lastDayStr2 + " " + lastTimeStr;
        //str = Utils.DEFAULT_DAY_DEBUG; // for debug
        Log.d("str", str);

        Calendar lastDay = null;
        try {
            lastDay = Utils.getCalendar(str, Utils.DATEFORMAT_YYYYMMDD_HHMMSS);
        } catch (ParseException e) {
            lastDay = Calendar.getInstance();
            e.printStackTrace();
        }

        if(cnt != null) {
            cnt.cancel();
        }

        cnt = new CountDownTimer(lastDay.getTimeInMillis(), 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                Calendar today = Calendar.getInstance();

                Calendar lastDay = Calendar.getInstance();
                lastDay.setTimeInMillis(millisUntilFinished);

                txtDays.setText(""+String.format(FORMAT_DAY, Utils.daysBetween(today, lastDay)));

                long diff = 0;

                diff = lastDay.getTimeInMillis() - today.getTimeInMillis();

                txtTime.setText(""+String.format(FORMAT_TIME,
                        TimeUnit.MILLISECONDS.toHours(diff) - TimeUnit.MILLISECONDS.toHours(diff),
                        TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(diff)),
                        TimeUnit.MILLISECONDS.toSeconds(diff) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(diff))));
            }

            public void onFinish() {
                txtTime.setText("");
            }
        };
        cnt.start();
    }

    public static int getMonth(String dateval) {
        String[] pieces = dateval.split("-");
        return Integer.parseInt(pieces[1])+1;// saved n -1 based. so increment month by 1.
    }

    public static String setMonth(int month, String dateval) {
        String[] pieces = dateval.split("-");
        int month2 = getMonth(dateval); // saved n -1 based. so increment month by 1.
        return pieces[0]+"-"+ String.format("%02d", month2)+"-"+pieces[2];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings :
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.action_about :
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
