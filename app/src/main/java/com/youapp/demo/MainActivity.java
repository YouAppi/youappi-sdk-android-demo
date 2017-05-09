package com.youapp.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.youappi.ai.sdk.BaseAd;
import com.youappi.ai.sdk.YouAPPi;
import com.youappi.ai.sdk.ads.RewardedVideoAd;
import com.youappi.ai.sdk.logic.Logger;

public class MainActivity extends AppCompatActivity implements
        RewardedVideoAd.RewardedVideoAdListener, Logger.LogListener, View.OnClickListener {

    // Please note this is YouAppi's demo access token
    private static final String DEMO_TOKEN = "821cfa77-3127-42b5-9e6b-0afcecf77c67";
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button buttonRewardedVideo;
    private Button buttonInterstitialAd;
    private Button buttonInterstitialVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouAPPi.init(MainActivity.this, DEMO_TOKEN);
        YouAPPi.getInstance().setLogListener(MainActivity.this);
        YouAPPi.getInstance().rewaredVideoAd().setRewardedListener(MainActivity.this);
        YouAPPi.getInstance().cardAd().setCardAdListener(MainActivity.this);

        buttonRewardedVideo = (Button) findViewById(R.id.button_rewarded_video);
        buttonRewardedVideo.setEnabled(false);
        buttonRewardedVideo.setOnClickListener(this);

        buttonInterstitialAd = (Button) findViewById(R.id.button_interstitial_ad);
        buttonInterstitialAd.setEnabled(false);
        buttonInterstitialAd.setOnClickListener(this);

        buttonInterstitialVideo = (Button) findViewById(R.id.button_interstitial_video);
        buttonInterstitialVideo.setEnabled(false);
        buttonInterstitialVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        BaseAd ad = null;

        switch (v.getId()) {

            case R.id.button_rewarded_video:
                ad = YouAPPi.getInstance().rewaredVideoAd();
                break;

            case R.id.button_interstitial_ad:
                ad = YouAPPi.getInstance().cardAd();
                break;

            case R.id.button_interstitial_video:
                ad = YouAPPi.getInstance().videoAd();
                break;
        }

        if (ad != null && ad.isAvailable())
        {
            ad.show();
        }
    }

    @Override
    public void log(String tag, String log)
    {
        Log.w("YouAppiDemo", "## Tag : " + tag + ", Log : " + log + " ##");
    }

    @Override
    public void onCardShow()
    {
        Log.w(TAG, "onCardShow ");
    }

    @Override
    public void onCardClose()
    {
        Log.w(TAG, "onCardClose ");
    }

    @Override
    public void onCardClick()
    {
        Log.w(TAG, "onCardClick ");
    }

    @Override
    public void onInitSuccess()
    {
        Log.w(TAG, "onInitSuccess ");
    }

    @Override
    public void onLoadFailed(Exception e)
    {
        Log.w(TAG, "onLoadFailed ");
    }

    @Override
    public void onPreloadFailed(Exception e)
    {
        Log.w(TAG, "onPreloadFailed ");

    }

    @Override
    public void onAvailabilityChanged(boolean isAvailable)
    {
        buttonRewardedVideo.setEnabled(YouAPPi.getInstance().rewaredVideoAd().isAvailable());
        buttonInterstitialAd.setEnabled(YouAPPi.getInstance().cardAd().isAvailable());
        buttonInterstitialVideo.setEnabled(YouAPPi.getInstance().videoAd().isAvailable());
        Log.w(TAG, "onAvailabilityChanged : rewardedVideoAd().isAvailable() = " + YouAPPi.getInstance().rewaredVideoAd().isAvailable());
        Log.w(TAG, "onAvailabilityChanged : cardAd().isAvailable() = " + YouAPPi.getInstance().cardAd().isAvailable());
        Log.w(TAG, "onAvailabilityChanged : videoAd().isAvailable() = " + YouAPPi.getInstance().videoAd().isAvailable());
    }

    @Override
    public void onRewarded()
    {
        Log.w(TAG, "onRewarded ");
    }

    @Override
    public void onVideoStart()
    {
        Log.w(TAG, "onVideoStart ");
    }

    @Override
    public void onVideoEnd()
    {
        Log.w(TAG, "onVideoEnd ");
    }

    @Override
    public void onVideoSkipped(int i)
    {
        Log.w(TAG, "onVideoSkipped ");
    }
}