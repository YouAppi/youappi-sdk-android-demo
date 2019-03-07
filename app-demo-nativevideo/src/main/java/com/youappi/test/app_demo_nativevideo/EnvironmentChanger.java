package com.youappi.test.app_demo_nativevideo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.youappi.sdk.nativeads.Environment;

public class EnvironmentChanger {

    private static final String PREF_ENV_KEY = "PREF_ENV";

    static void openEnvironment(final Context context) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        Environment[] environments = Environment.values();
        String[] names = new String[environments.length];
        for (int i = 0; i < environments.length; i++) {
            names[i] = environments[i].name();
        }

        alertDialogBuilder.setSingleChoiceItems(names, sharedPreferences.getInt(PREF_ENV_KEY, 0), new DialogInterface.OnClickListener() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                sharedPreferences.edit().putInt(PREF_ENV_KEY, which).commit();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.setTitle("Select Environment");
        alertDialogBuilder.show();
    }

}
