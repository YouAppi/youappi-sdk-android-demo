package com.youappi.medaition.admob.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.youappi.ai.sdk.YouAPPi;
import com.youappi.mediation.admob.YouAppiInterstitialAd;
import com.youappi.mediation.admob.YouAppiInterstitialVideo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ADMOB_APP_ID = "ca-app-pub-9942448998483932~5444617199";

    private static final String ADMOB_REWARDED_VIDEO_ADUNIT_ID = "ca-app-pub-9942448998483932/6503382149";
    private static final String ADMOB_UNIT_ID_INTERSTITIAL_AD = "ca-app-pub-9942448998483932/8581916428";
    private static final String ADMOB_UNIT_ID_INTERSTITIAL_VIDEO = "ca-app-pub-9942448998483932/3243450527";

    private Button loadRewardedVideo;
    private Button loadInterstitialVideo;
    private Button loadInterstitialAd;
    private Button showInterstitialVideo;
    private Button showRewardedVideo;
    private Button showInterstitialAd;


    private View spinner;

    private boolean rewardedShown = false;

    private RewardedVideoAd rewardedVideo;
    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialVideo;

    private RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
        @Override
        public void onRewardedVideoAdLoaded() {
            showSpinner(false);
            updateUiState();
        }

        @Override
        public void onRewardedVideoAdOpened() {
            rewardedShown = true;
            updateUiState();
        }

        @Override
        public void onRewardedVideoStarted() {
            rewardedShown = true;
            updateUiState();
        }

        @Override
        public void onRewardedVideoAdClosed() {
            rewardedShown = true;
            updateUiState();
        }

        @Override
        public void onRewarded(RewardItem rewardItem) {

        }

        @Override
        public void onRewardedVideoAdLeftApplication() {

        }

        @Override
        public void onRewardedVideoAdFailedToLoad(int i) {
            showSpinner(false);
            updateUiState();
            showToast("Failed to load");
        }

        @Override
        public void onRewardedVideoCompleted() {
            rewardedShown = true;
            updateUiState();
        }
    };

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private AdListener adListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(int i) {
            showSpinner(false);
            updateUiState();
            showToast("Failed to load");
        }

        @Override
        public void onAdLoaded() {
            showSpinner(false);
            updateUiState();
        }

        @Override
        public void onAdClosed() {
            updateUiState();
        }

        @Override
        public void onAdOpened() {
            updateUiState();
        }

        @Override
        public void onAdClicked() {
            updateUiState();
        }

        @Override
        public void onAdImpression() {
            updateUiState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        MobileAds.initialize(this, ADMOB_APP_ID);
        rewardedVideo = MobileAds.getRewardedVideoAdInstance(this);


        TextView versionStr = (TextView) findViewById(R.id.versionStr);
        versionStr.setText("SDK version: " + YouAPPi.getInstance().getVersionStr());

        TextView moatState = (TextView) findViewById(R.id.moat_state);
//    if (YouAppiMopub.isMoat()){
//      moatState.setText("Trackers: Moat");
//    } else {
//      moatState.setText("Trackers: None");
//    }
        
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.load_rewarded:
                rewardedVideo.loadAd(ADMOB_REWARDED_VIDEO_ADUNIT_ID, new AdRequest.Builder().build());
                rewardedVideo.setRewardedVideoAdListener(rewardedVideoAdListener);
                rewardedShown = false;
                showSpinner(true);
                break;
            case R.id.show_rewarded:
                rewardedVideo.show();
                break;
            case R.id.load_interstitial_video:
                interstitialVideo = new InterstitialAd(this);
                interstitialVideo.setAdListener(adListener);
                interstitialVideo.setAdUnitId(ADMOB_UNIT_ID_INTERSTITIAL_VIDEO);
                Bundle videoBundle = new Bundle();
                interstitialVideo.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(YouAppiInterstitialVideo.class, videoBundle).build());
                showSpinner(true);
                break;
            case R.id.show_interstitial_video:
                if (interstitialVideo != null && interstitialVideo.isLoaded()) {
                    interstitialVideo.show();
                    interstitialVideo = null;
                }
                break;
            case R.id.load_interstitial_ad:
                interstitialAd = new InterstitialAd(this);
                interstitialAd.setAdListener(adListener);
                interstitialAd.setAdUnitId(ADMOB_UNIT_ID_INTERSTITIAL_AD);
                Bundle bundle = new Bundle();
                interstitialAd.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(YouAppiInterstitialAd.class, bundle).build());
                showSpinner(true);
                break;
            case R.id.show_interstitial_ad:
                if (interstitialAd != null && interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    interstitialAd = null;
                }
                break;
        }
    }

    private void showSpinner(boolean showSpinner) {
        if (spinner != null) {
            spinner.setVisibility(showSpinner ? View.VISIBLE : View.GONE);
        }
    }

    private void updateUiState() {
        showInterstitialAd.setEnabled(interstitialAd != null && interstitialAd.isLoaded());
        showInterstitialVideo.setEnabled(interstitialVideo != null && interstitialVideo.isLoaded());
        showRewardedVideo.setEnabled(rewardedVideo.isLoaded() && !rewardedShown);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUiState();
    }
}
