package com.youappi.demo.mopub;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.youappi.sdk.mediation.mopub.YouAppiMopub;

import java.util.Locale;
import java.util.Set;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements View.OnClickListener, SdkInitializationListener {

    public static final String UNIT_ID_REWARDED_VIDEO = "5aafbe7f552842d48373f082bd585aa9";
    public static final String UNIT_ID_INTERSTITIAL_AD = "2fec087170f348e28207f0a0cb9e890b";
    public static final String UNIT_ID_INTERSTITIAL_VIDEO = "2f7234339e1949898294c0310af0b7ff";

    private MoPubInterstitial moPubInterstitial;
    private MoPubInterstitial moPubInterstitialVideo;

    private Button loadRewardedVideo;
    private Button loadInterstitialVideo;
    private Button loadInterstitialAd;
    private ProgressBar progressBarRewardedVideo;
    private ProgressBar progressBarInterstitialVideo;
    private ProgressBar progressBarInterstitialAd;

    private MoPubRewardedVideoListener rewardedVideoListener = new MoPubRewardedVideoListener() {
        @Override
        public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
            setButtonState(loadRewardedVideo, ButtonState.SHOW);
        }

        @Override
        public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
            setButtonState(loadRewardedVideo, ButtonState.LOAD);
            showToast(getString(R.string.rewarded_loading_failed));
        }

        @Override
        public void onRewardedVideoStarted(@NonNull String adUnitId) {

        }

        @Override
        public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {

        }

        @Override
        public void onRewardedVideoClicked(@NonNull String adUnitId) {

        }

        @Override
        public void onRewardedVideoClosed(@NonNull String adUnitId) {

        }

        @Override
        public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {

        }
    };

    private MoPubInterstitial.InterstitialAdListener InterstitialVideoListener = new MoPubInterstitial.InterstitialAdListener() {
        @Override
        public void onInterstitialLoaded(MoPubInterstitial interstitial) {
            setButtonState(loadInterstitialVideo, ButtonState.SHOW);
        }

        @Override
        public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
            setButtonState(loadInterstitialVideo, ButtonState.LOAD);
            showToast(getString(R.string.interstitial_loading_failed));
        }

        @Override
        public void onInterstitialShown(MoPubInterstitial interstitial) {

        }

        @Override
        public void onInterstitialClicked(MoPubInterstitial interstitial) {

        }

        @Override
        public void onInterstitialDismissed(MoPubInterstitial interstitial) {

        }
    };

    private MoPubInterstitial.InterstitialAdListener interstitialAdListener = new MoPubInterstitial.InterstitialAdListener() {
        @Override
        public void onInterstitialLoaded(MoPubInterstitial interstitial) {
            setButtonState(loadInterstitialAd, ButtonState.SHOW);
        }

        @Override
        public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
            setButtonState(loadInterstitialAd, ButtonState.LOAD);
            showToast(getString(R.string.interstitial_loading_failed));
        }

        @Override
        public void onInterstitialShown(MoPubInterstitial interstitial) {

        }

        @Override
        public void onInterstitialClicked(MoPubInterstitial interstitial) {

        }

        @Override
        public void onInterstitialDismissed(MoPubInterstitial interstitial) {

        }
    };

    @Override
    public void onInitializationFinished() {
        Log.i(MainActivity.class.getSimpleName(),"Mopub initialized");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MoPubRewardedVideos.initializeRewardedVideo(this);

        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder(UNIT_ID_REWARDED_VIDEO).build();
        MoPub.initializeSdk(this, sdkConfiguration, this);

        loadRewardedVideo = findViewById(R.id.rewarded_btn);
        loadInterstitialVideo = findViewById(R.id.interstitial_video_btn);
        loadInterstitialAd = findViewById(R.id.interstitial_ad_btn);
        loadRewardedVideo.setOnClickListener(this);
        loadInterstitialVideo.setOnClickListener(this);
        loadInterstitialAd.setOnClickListener(this);
        progressBarRewardedVideo = findViewById(R.id.rewarded_spinner);
        progressBarInterstitialVideo = findViewById(R.id.interstitial_video_spinner);
        progressBarInterstitialAd = findViewById(R.id.interstitial_ad_spinner);

        setButtonState(loadRewardedVideo, ButtonState.LOAD);
        setButtonState(loadInterstitialVideo, ButtonState.LOAD);
        setButtonState(loadInterstitialAd, ButtonState.LOAD);

        moPubInterstitial = new MoPubInterstitial(this, UNIT_ID_INTERSTITIAL_AD);
        moPubInterstitialVideo = new MoPubInterstitial(this, UNIT_ID_INTERSTITIAL_VIDEO);
        moPubInterstitial.setInterstitialAdListener(interstitialAdListener);
        moPubInterstitialVideo.setInterstitialAdListener(InterstitialVideoListener);
        MoPubRewardedVideos.setRewardedVideoListener(rewardedVideoListener);

        TextView moatState = findViewById(R.id.moat_state);

        if (YouAppiMopub.isMoat()) {
            moatState.setText(String.format(Locale.US, "%s: %s", getString(R.string.trackers), getString(R.string.moat)));
        } else {
            moatState.setText(String.format(Locale.US, "%s: %s", getString(R.string.trackers), getString(R.string.none)));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.interstitial_ad_btn:
                if (moPubInterstitial.isReady()) {
                    moPubInterstitial.show();
                } else {
                    moPubInterstitial.load();
                    setButtonState(loadInterstitialAd, ButtonState.LOADING);
                }
                break;
            case R.id.rewarded_btn:
                if (MoPubRewardedVideos.hasRewardedVideo(UNIT_ID_REWARDED_VIDEO)) {
                    MoPubRewardedVideos.showRewardedVideo(UNIT_ID_REWARDED_VIDEO);
                } else {
                    MoPubRewardedVideos.loadRewardedVideo(UNIT_ID_REWARDED_VIDEO);
                    setButtonState(loadRewardedVideo, ButtonState.LOADING);
                }
                break;
            case R.id.interstitial_video_btn:
                if (moPubInterstitialVideo.isReady()) {
                    moPubInterstitialVideo.show();
                } else {
                    moPubInterstitialVideo.load();
                    setButtonState(loadInterstitialVideo, ButtonState.LOADING);
                }
                break;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
                button.setText(String.format(Locale.getDefault(), "%s %s", getString(R.string.load), buttonText));
                break;
            case LOADING:
                button.setText(String.format(Locale.getDefault(), "%s %s", getString(R.string.loading), buttonText));
                break;
            case SHOW:
                button.setText(String.format(Locale.getDefault(), "%s %s", getString(R.string.show), buttonText));
                break;
        }
    }

}
