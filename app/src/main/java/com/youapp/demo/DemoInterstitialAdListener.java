package com.youapp.demo;

import android.util.Log;
import android.widget.Toast;

import com.youappi.ai.sdk.YAErrorCode;
import com.youappi.ai.sdk.ads.YAInterstitialAd;

/**
 * User: Bashan
 * Date: 02/10/2017
 * Time: 16:14
 */
public class DemoInterstitialAdListener implements YAInterstitialAd.InterstitialAdListener {

    private MainActivity activity;

    DemoInterstitialAdListener(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onCardShow(String s) {

    }

    @Override
    public void onCardClose(String s) {

    }

    @Override
    public void onCardClick(String s) {

    }

    @Override
    public void onLoadSuccess(String adUnitId) {
        activity.setButtonState(activity.buttonInterstitialAd, MainActivity.ButtonState.SHOW);
    }

    @Override
    public void onLoadFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Toast.makeText(activity, "Failed loading ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
        Log.e(MainActivity.TAG, "Failed loading ad unit: " + adUnitId, e);
        activity.setButtonState(activity.buttonInterstitialAd, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onShowFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Toast.makeText(activity, "Failed showing ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
        Log.e(MainActivity.TAG, "Failed showing ad unit: " + adUnitId, e);
        activity.setButtonState(activity.buttonInterstitialAd, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onAdStarted(String s) {

    }

    @Override
    public void onAdEnded(String s) {
        activity.setButtonState(activity.buttonInterstitialAd, MainActivity.ButtonState.LOAD);
    }
}
