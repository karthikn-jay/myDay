package com.karthiknjay.mydays.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private String lastDay = "29/04/2016";

    private static final String FORMAT = "%02d:%02d:%02d";

    int seconds , minutes;

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

        Calendar lastDay = Calendar.getInstance();
        lastDay.set(Calendar.YEAR, Utils.LAST_DAY_YEAR);
        lastDay.set(Calendar.MONTH, Utils.LAST_DAY_MONTH);
        lastDay.set(Calendar.DAY_OF_MONTH, Utils.LAST_DAY_DAY);
        lastDay.set(Calendar.HOUR_OF_DAY, Utils.LAST_DAY_HOUR);
        lastDay.set(Calendar.MINUTE, Utils.LAST_DAY_MINUTE);
        lastDay.set(Calendar.SECOND, Utils.LAST_DAY_SECOND);

        Date dt = lastDay.getTime();
        Log.d("myDays", "Date: " + dt);

        final TextView txtDays = (TextView) findViewById(R.id.txtDays);
        final TextView txtTime = (TextView) findViewById(R.id.txtTime);
        ////txtDays.setText( Integer.toString(Utils.getTimeRemaining(dt)));
        //txtDays.setText( Integer.toString(Utils.daysBetween(Calendar.getInstance(), lastDay)));


        new CountDownTimer(lastDay.getTimeInMillis(), 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                int days = (int) (millisUntilFinished / (1000*60*60*24));

                //int days = (int) ((millisUntilFinished / (1000*60*60*24)) % 7);
                int weeks = (int) (millisUntilFinished / (1000*60*60*24*7));

                txtDays.setText(""+String.format("%02d", days));

                txtTime.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                txtTime.setText("");
            }
        }.start();
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
