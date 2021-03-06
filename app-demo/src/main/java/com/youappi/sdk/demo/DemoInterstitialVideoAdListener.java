package com.youappi.sdk.demo;

import android.util.Log;
import android.widget.Toast;

import com.youappi.sdk.YAErrorCode;
import com.youappi.sdk.ads.YAInterstitialAd;

/**
 * User: Bashan
 * Date: 02/10/2017
 * Time: 16:21
 */
public class DemoInterstitialVideoAdListener implements YAInterstitialAd.InterstitialAdListener {

    private static final String TAG = DemoInterstitialVideoAdListener.class.getSimpleName();

    private MainActivity activity;

    DemoInterstitialVideoAdListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onVideoStart(String s) {

    }

    @Override
    public void onVideoEnd(String s) {

    }

    @Override
    public void onVideoSkipped(String s, int i) {

    }

    @Override
    public void onCardShow(String s) {

    }

    @Override
    public void onCardClose(String s) {

    }

    @Override
    public void onAdClick(String s) {

    }

    @Override
    public void onLoadSuccess(String adUnitId) {
        activity.setButtonState(activity.buttonInterstitialVideo, MainActivity.ButtonState.SHOW);
    }

    @Override
    public void onLoadFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Log.e(TAG, "Failed loading ad unit: " + adUnitId, e);
        Toast.makeText(activity, "Failed loading ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
        activity.setButtonState(activity.buttonInterstitialVideo, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onShowFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Log.e(TAG, "Failed showing ad unit: " + adUnitId, e);
        Toast.makeText(activity, "Failed showing ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
        activity.setButtonState(activity.buttonInterstitialVideo, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onAdStarted(String s) {

    }

    @Override
    public void onAdEnded(String s) {
        activity.setButtonState(activity.buttonInterstitialVideo, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onAdLeftApplication(String s) {

    }
}
