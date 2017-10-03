package com.youapp.demo;

import android.widget.Toast;

import com.youappi.ai.sdk.YAErrorCode;
import com.youappi.ai.sdk.ads.YAInterstitialVideoAd;

/**
 * User: Bashan
 * Date: 02/10/2017
 * Time: 16:21
 */
public class DemoInterstitialVideoAdListener implements YAInterstitialVideoAd.InterstitialVideoAdListener {

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
    public void onCardClick(String s) {

    }

    @Override
    public void onLoadSuccess(String adUnitId) {
        activity.setButtonState(activity.buttonInterstitialVideo, MainActivity.ButtonState.SHOW);
        Toast.makeText(activity, "Loaded ad unit: " + adUnitId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Toast.makeText(activity, "Failed loading ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShowFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Toast.makeText(activity, "Failed showing ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAdStarted(String s) {

    }

    @Override
    public void onAdEnded(String s) {
        activity.setButtonState(activity.buttonInterstitialVideo, MainActivity.ButtonState.LOAD);
    }
}
