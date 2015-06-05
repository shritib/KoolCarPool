package com.flipkart.koolcarpool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class LoginActivity extends Activity {
    public String userId;
    public final static String USER_TYPE = "userType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();

        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

        Button loginButton = (Button) findViewById(R.id.loginBtn);
        final RadioButton leecher = (RadioButton) findViewById(R.id.radio1);
        final RadioButton seeder = (RadioButton) findViewById(R.id.radio0);
        RadioGroup radiogrp = ((RadioGroup) findViewById (R.id.radiogrp));

        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        break;
                    case R.id.radio0:
                        break;
                }
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.userId);
                userId = editText.getText().toString();
                if (leecher.isChecked()) {
                    startActivityForLeecher(userId);
                } else if (seeder.isChecked()) {
                    startActivityForSeeder(userId);
                }
            }
        });

    }

    public void startActivityForLeecher(String userId) {
        /*Intent intent = new Intent(this, LeecherActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);*/
    }

    public void startActivityForSeeder(String userId) {
        Intent intent = new Intent(this, SeederActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        LoginActivity.this.finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
