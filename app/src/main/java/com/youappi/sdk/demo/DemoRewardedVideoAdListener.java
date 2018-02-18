package com.youappi.sdk.demo;

import android.widget.Toast;

import com.youappi.ai.sdk.YAErrorCode;
import com.youappi.ai.sdk.ads.YARewardedVideoAd.RewardedVideoAdListener;

/**
 * User: Bashan
 * Date: 02/10/2017
 * Time: 16:22
 */
public class DemoRewardedVideoAdListener implements RewardedVideoAdListener {

    private MainActivity activity;

    DemoRewardedVideoAdListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onRewarded(String adUnitId) {
        Toast.makeText(activity, "Rewarded ad unit: " + adUnitId, Toast.LENGTH_LONG).show();
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
        activity.setButtonState(activity.buttonRewardedVideo, MainActivity.ButtonState.SHOW);
    }

    @Override
    public void onLoadFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Toast.makeText(activity, "Failed loading ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
        activity.setButtonState(activity.buttonRewardedVideo, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onShowFailure(String adUnitId, YAErrorCode yaErrorCode, Exception e) {
        Toast.makeText(activity, "Failed showing ad unit: " + adUnitId +
                " for reason: " + yaErrorCode, Toast.LENGTH_LONG).show();
        activity.setButtonState(activity.buttonRewardedVideo, MainActivity.ButtonState.LOAD);
    }

    @Override
    public void onAdStarted(String s) {

    }

    @Override
    public void onAdEnded(String s) {
        activity.setButtonState(activity.buttonRewardedVideo, MainActivity.ButtonState.LOAD);
    }
}
