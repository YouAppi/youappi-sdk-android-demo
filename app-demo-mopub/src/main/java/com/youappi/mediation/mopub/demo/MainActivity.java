package com.youappi.mediation.mopub.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mopub.common.MoPubReward;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.youappi.sdk.mediation.mopub.YouAppiMopub;

import java.util.Set;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MoPubRewardedVideoListener, MoPubInterstitial.InterstitialAdListener {

    public static final String UNIT_ID_REWARDED_VIDEO = "5aafbe7f552842d48373f082bd585aa9";
    public static final String UNIT_ID_INTERSTITIAL_AD = "2fec087170f348e28207f0a0cb9e890b";
    public static final String UNIT_ID_INTERSTITIAL_VIDEO = "2f7234339e1949898294c0310af0b7ff";

    private MoPubInterstitial moPubInterstitial;
    private MoPubInterstitial moPubInterstitialVideo;

    private Button loadRewardedVideo;
    private Button loadInterstitialVideo;
    private Button loadInterstitialAd;
    private Button showInterstitialVideo;
    private Button showRewardedVideo;
    private Button showInterstitialAd;

    private View spinner;
    private boolean rewardedVideoReady;
    private boolean rewardedVideoShown;
    private boolean interstitialVideoShown;
    private boolean interstitialAdShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoPubRewardedVideos.initializeRewardedVideo(this);

        loadRewardedVideo = findViewById(R.id.load_rewarded);
        showRewardedVideo = findViewById(R.id.show_rewarded);
        loadInterstitialVideo = findViewById(R.id.load_interstitial_video);
        showInterstitialVideo = findViewById(R.id.show_interstitial_video);
        loadInterstitialAd = findViewById(R.id.load_interstitial_ad);
        showInterstitialAd = findViewById(R.id.show_interstitial_ad);
        loadRewardedVideo.setOnClickListener(this);
        showRewardedVideo.setOnClickListener(this);
        loadInterstitialVideo.setOnClickListener(this);
        showInterstitialVideo.setOnClickListener(this);
        loadInterstitialAd.setOnClickListener(this);
        showInterstitialAd.setOnClickListener(this);

        spinner = findViewById(R.id.spinner_layout);
        spinner.setVisibility(View.GONE);


        moPubInterstitial = new MoPubInterstitial(this, UNIT_ID_INTERSTITIAL_AD);
        moPubInterstitialVideo = new MoPubInterstitial(this, UNIT_ID_INTERSTITIAL_VIDEO);
        moPubInterstitial.setInterstitialAdListener(this);
        MoPubRewardedVideos.setRewardedVideoListener(this);

        TextView moatState = (TextView) findViewById(R.id.moat_state);
        if (YouAppiMopub.isMoat()) {
            moatState.setText("Trackers: Moat");
        } else {
            moatState.setText("Trackers: None");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_interstitial_ad:
                moPubInterstitial.load();
                interstitialAdShown = false;
                showSpinner(true);
                break;
            case R.id.show_interstitial_ad:
                moPubInterstitial.show();
                break;
            case R.id.load_rewarded:
                MoPubRewardedVideos.loadRewardedVideo(UNIT_ID_REWARDED_VIDEO);
                showSpinner(true);
                rewardedVideoShown = false;
                break;
            case R.id.show_rewarded:
                MoPubRewardedVideos.showRewardedVideo(UNIT_ID_REWARDED_VIDEO);
                break;
            case R.id.load_interstitial_video:
                moPubInterstitialVideo.load();
                interstitialVideoShown = false;
                showSpinner(true);
                break;
            case R.id.show_interstitial_video:
                moPubInterstitialVideo.show();
                break;
        }
    }

    private void updateUiState() {
        showInterstitialAd.setEnabled(!interstitialAdShown && moPubInterstitial.isReady());
        showInterstitialVideo.setEnabled(!interstitialVideoShown && moPubInterstitialVideo.isReady());
        showRewardedVideo.setEnabled(rewardedVideoReady && !rewardedVideoShown);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUiState();
    }

    private void showSpinner(boolean showSpinner) {
        if (spinner != null) {
            spinner.setVisibility(showSpinner ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
        rewardedVideoReady = true;
        updateUiState();
        showSpinner(false);
    }

    @Override
    public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
        rewardedVideoReady = false;
        updateUiState();
        showSpinner(false);
        showToast(getString(R.string.rewarded_loading_failed));
    }

    @Override
    public void onRewardedVideoStarted(@NonNull String adUnitId) {
        rewardedVideoReady = false;
        rewardedVideoShown = true;
        updateUiState();
    }

    @Override
    public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
        rewardedVideoReady = false;
        updateUiState();
    }

    @Override
    public void onRewardedVideoClosed(@NonNull String adUnitId) {
        rewardedVideoReady = false;
        rewardedVideoShown = true;
        updateUiState();
    }

    @Override
    public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
        rewardedVideoReady = false;
        rewardedVideoShown = true;
        updateUiState();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        updateUiState();
        showSpinner(false);
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        updateUiState();
        showSpinner(false);
        showToast(getString(R.string.interstitial_loading_failed));
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
        if (interstitial == moPubInterstitial) {
            interstitialAdShown = true;
        }
        if (interstitial == moPubInterstitialVideo) {
            interstitialVideoShown = true;
        }
        updateUiState();
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {
        if (interstitial == moPubInterstitial) {
            interstitialAdShown = true;
        }
        if (interstitial == moPubInterstitialVideo) {
            interstitialVideoShown = true;
        }
        updateUiState();
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
        if (interstitial == moPubInterstitial) {
            interstitialAdShown = true;
        }
        if (interstitial == moPubInterstitialVideo) {
            interstitialVideoShown = true;
        }
        updateUiState();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (spinner.getVisibility()!=View.GONE){
            showSpinner(false);
        } else {
            super.onBackPressed();
        }
    }
}
