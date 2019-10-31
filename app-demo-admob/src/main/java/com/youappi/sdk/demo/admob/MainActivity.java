package com.youappi.sdk.demo.admob;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.youappi.demo.admob.R;
import com.youappi.sdk.YouAPPi;
import com.youappi.sdk.mediation.admob.YouAppiAdmob;
import com.youappi.sdk.mediation.admob.YouAppiInterstitialAd;
import com.youappi.sdk.mediation.admob.YouAppiInterstitialVideo;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ADMOB_APP_ID = "ca-app-pub-9942448998483932~5444617199";

    private static final String ADMOB_REWARDED_VIDEO_ADUNIT_ID = "ca-app-pub-9942448998483932/6503382149";
    private static final String ADMOB_UNIT_ID_INTERSTITIAL_AD = "ca-app-pub-9942448998483932/8581916428";
    private static final String ADMOB_UNIT_ID_INTERSTITIAL_VIDEO = "ca-app-pub-9942448998483932/3243450527";

    private Button loadRewardedVideo;
    private Button loadInterstitialVideo;
    private Button loadInterstitialAd;
    private ProgressBar progressBarRewardedVideo;
    private ProgressBar progressBarInterstitialVideo;
    private ProgressBar progressBarInterstitialAd;


    private RewardedVideoAd rewardedVideo;
    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialVideo;

    private RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {
            setButtonState(loadRewardedVideo, ButtonState.SHOW);
        }

        @Override
        public void onRewardedVideoAdOpened() {

        }

        @Override
        public void onRewardedVideoStarted() {

        }

        @Override
        public void onRewardedVideoAdClosed() {

        }

        @Override
        public void onRewarded(RewardItem rewardItem) {

        }

        @Override
        public void onRewardedVideoAdLeftApplication() {

        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {
            setButtonState(loadRewardedVideo, ButtonState.LOAD);
            showToast("Failed to load");
        }

        @Override
        public void onRewardedVideoCompleted() {

        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private AdListener interstitialAdListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(int i) {
            setButtonState(loadInterstitialAd, ButtonState.LOAD);
            showToast("Failed to load");
        }

        @Override
        public void onAdLoaded() {
            setButtonState(loadInterstitialAd, ButtonState.SHOW);
        }

        @Override
        public void onAdClosed() {

        }

        @Override
        public void onAdOpened() {

        }

        @Override
        public void onAdClicked() {

        }

        @Override
        public void onAdImpression() {
        }
    };

    private AdListener interstitialVideoListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(int i) {
            setButtonState(loadInterstitialVideo, ButtonState.LOAD);
            showToast("Failed to load");
        }

        @Override
        public void onAdLoaded() {
            setButtonState(loadInterstitialVideo, ButtonState.SHOW);
        }

        @Override
        public void onAdClosed() {

        }

        @Override
        public void onAdOpened() {

        }

        @Override
        public void onAdClicked() {

        }

        @Override
        public void onAdImpression() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRewardedVideo = findViewById(R.id.rewarded_btn);
        loadInterstitialVideo = findViewById(R.id.interstitial_video_btn);
        loadInterstitialAd = findViewById(R.id.interstitial_ad_btn);
        loadRewardedVideo.setOnClickListener(this);
        loadInterstitialVideo.setOnClickListener(this);
        loadInterstitialAd.setOnClickListener(this);
        progressBarRewardedVideo = findViewById(R.id.rewarded_spinner);
        progressBarInterstitialVideo = findViewById(R.id.interstitial_video_spinner);
        progressBarInterstitialAd = findViewById(R.id.interstitial_ad_spinner);

        MobileAds.initialize(this, ADMOB_APP_ID);
        rewardedVideo = MobileAds.getRewardedVideoAdInstance(this);

        setButtonState(loadRewardedVideo, ButtonState.LOAD);
        setButtonState(loadInterstitialVideo, ButtonState.LOAD);
        setButtonState(loadInterstitialAd, ButtonState.LOAD);

        TextView versionStr = findViewById(R.id.versionStr);
        versionStr.setText(String.format("SDK version: %s" , YouAPPi.getVersionStr()));

        TextView moatState = findViewById(R.id.moat_state);
        if (YouAppiAdmob.isMoat()) {
            moatState.setText(String.format(Locale.US,"%s: %s",getString(R.string.trackers),getString(R.string.moat)));
        } else {
            moatState.setText(String.format(Locale.US,"%s: %s",getString(R.string.trackers),getString(R.string.none)));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rewarded_btn:
                if (rewardedVideo.isLoaded()) {
                    setButtonState(loadRewardedVideo, ButtonState.LOAD);
                    rewardedVideo.show();
                } else {
                    rewardedVideo.loadAd(ADMOB_REWARDED_VIDEO_ADUNIT_ID, new AdRequest.Builder().build());
                    rewardedVideo.setRewardedVideoAdListener(rewardedVideoAdListener);
                    setButtonState(loadRewardedVideo, ButtonState.LOADING);
                }
                break;
            case R.id.interstitial_video_btn:
                if (interstitialVideo != null && interstitialVideo.isLoaded()) {
                    setButtonState(loadInterstitialVideo, ButtonState.LOAD);
                    interstitialVideo.show();
                    interstitialVideo = null;
                } else {
                    interstitialVideo = new InterstitialAd(this);
                    interstitialVideo.setAdListener(interstitialVideoListener);
                    interstitialVideo.setAdUnitId(ADMOB_UNIT_ID_INTERSTITIAL_VIDEO);
                    Bundle videoBundle = new Bundle();
                    interstitialVideo.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(YouAppiInterstitialVideo.class, videoBundle).build());
                    setButtonState(loadInterstitialVideo, ButtonState.LOADING);
                }
                break;
            case R.id.interstitial_ad_btn:
                if (interstitialAd != null && interstitialAd.isLoaded()) {
                    setButtonState(loadInterstitialAd, ButtonState.LOAD);
                    interstitialAd.show();
                    interstitialAd = null;
                } else {
                    interstitialAd = new InterstitialAd(this);
                    interstitialAd.setAdListener(interstitialAdListener);
                    interstitialAd.setAdUnitId(ADMOB_UNIT_ID_INTERSTITIAL_AD);
                    Bundle bundle = new Bundle();
                    interstitialAd.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(YouAppiInterstitialAd.class, bundle).build());
                    setButtonState(loadInterstitialAd, ButtonState.LOADING);
                }
                break;
        }
    }

    enum ButtonState {
        LOAD, LOADING, SHOW, ERROR
    }

    void setButtonState(Button button, ButtonState buttonState) {
        String buttonText = null;
        switch (button.getId()) {
            case R.id.rewarded_btn:
                buttonText = getString(R.string.rewarded_video);
                progressBarRewardedVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.interstitial_video_btn:
                buttonText = getString(R.string.interstitial_video);
                progressBarInterstitialVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.interstitial_ad_btn:
                buttonText = getString(R.string.interstitial_ad);
                progressBarInterstitialAd.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
        }

        switch (buttonState) {
            case LOAD:
                button.setText(String.format(Locale.getDefault(),"%s %s",getString(R.string.load),buttonText));
                break;
            case LOADING:
                button.setText(String.format(Locale.getDefault(),"%s %s",getString(R.string.loading),buttonText));
                break;
            case SHOW:
                button.setText(String.format(Locale.getDefault(),"%s %s",getString(R.string.show),buttonText));
                break;
        }
    }
}
