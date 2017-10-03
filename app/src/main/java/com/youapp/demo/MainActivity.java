package com.youapp.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.youappi.ai.sdk.BaseAd;
import com.youappi.ai.sdk.YouAPPi;
import com.youappi.ai.sdk.ads.YAInterstitialAd;
import com.youappi.ai.sdk.ads.YAInterstitialVideoAd;
import com.youappi.ai.sdk.ads.YARewardedVideoAd;
import com.youappi.ai.sdk.logic.Logger;

public class MainActivity extends AppCompatActivity implements Logger.LogListener, View.OnClickListener {

    enum ButtonState {
        LOAD, LOADING, SHOW
    }

    // Please note this is YouAppi's demo access token. It should be replaced with your app access token.
    private static final String DEMO_TOKEN = "821cfa77-3127-42b5-9e6b-0afcecf77c67";
    public static final String TAG = MainActivity.class.getSimpleName();

    Button buttonRewardedVideo;
    Button buttonInterstitialAd;
    Button buttonInterstitialVideo;

    ProgressBar progressBarRewardedVideo;
    ProgressBar progressBarInterstitialAd;
    ProgressBar progressBarInterstitialVideo;

    private YARewardedVideoAd rewardedVideoAd;
    private YAInterstitialVideoAd interstitialVideoAd;
    private YAInterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouAPPi.init(MainActivity.this, DEMO_TOKEN);

        progressBarRewardedVideo = (ProgressBar) findViewById(R.id.progress_rewarded_video);
        progressBarInterstitialAd = (ProgressBar) findViewById(R.id.progress_interstitial_ad);
        progressBarInterstitialVideo = (ProgressBar) findViewById(R.id.progress_interstitial_video);

        buttonRewardedVideo = (Button) findViewById(R.id.button_rewarded_video);
        buttonRewardedVideo.setOnClickListener(this);
        setButtonState(buttonRewardedVideo, ButtonState.LOAD);

        buttonInterstitialAd = (Button) findViewById(R.id.button_interstitial_ad);
        buttonInterstitialAd.setOnClickListener(this);
        setButtonState(buttonInterstitialAd, ButtonState.LOAD);

        buttonInterstitialVideo = (Button) findViewById(R.id.button_interstitial_video);
        buttonInterstitialVideo.setOnClickListener(this);
        setButtonState(buttonInterstitialVideo, ButtonState.LOAD);

        rewardedVideoAd = YouAPPi.getInstance().rewaredVideoAd("test_rewarded_video_ad");
        interstitialVideoAd = YouAPPi.getInstance().interstitialVideoAd("test_interstitial_ad");
        interstitialAd = YouAPPi.getInstance().interstitialAd("test_interstitial_ad");

        rewardedVideoAd.setRewardedVideoAdListener(new DemoRewardedVideoAdListener(this));
        interstitialVideoAd.setInterstitialVideoAdListener(new DemoInterstitialVideoAdListener(this));
        interstitialAd.setInterstitialAdListener(new DemoInterstitialAdListener(this));
    }

    void setButtonState(Button button, ButtonState buttonState) {
        String buttonText = null;
        switch (button.getId()) {
            case R.id.button_rewarded_video:
                buttonText = "Rewarded Video Ad";
                progressBarRewardedVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.button_interstitial_ad:
                buttonText = "Interstitial Ad";
                progressBarInterstitialAd.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.button_interstitial_video:
                buttonText = "Interstitial Video";
                progressBarInterstitialVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
        }


        switch (buttonState) {
            case LOAD:
                button.setText("Load " + buttonText);
                break;
            case LOADING:
                button.setText("Loading " + buttonText);
                break;
            case SHOW:
                button.setText("Show " + buttonText);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        BaseAd ad = null;

        switch (v.getId()) {

            case R.id.button_rewarded_video:
                ad = rewardedVideoAd;
                break;

            case R.id.button_interstitial_ad:
                ad = interstitialAd;
                break;

            case R.id.button_interstitial_video:
                ad = interstitialVideoAd;
                break;
        }

        if (ad != null) {
            if (!ad.isAvailable()) {
                setButtonState((Button) v, ButtonState.LOADING);
                ad.load();
            } else {
                ad.show();
            }
        }
    }

    @Override
    public void log(String tag, String msg) {
        Log.i(tag, msg);
    }
}