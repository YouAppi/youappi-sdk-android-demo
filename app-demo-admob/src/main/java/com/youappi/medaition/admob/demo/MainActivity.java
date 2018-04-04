package com.youappi.medaition.admob.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.youappi.ai.sdk.YouAPPi;
import com.youappi.mediation.admob.YouAppiInterstitialAd;
import com.youappi.mediation.admob.YouAppiInterstitialVideo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ADMOB_APP_ID = "ca-app-pub-9942448998483932~5444617199";

    private static final String ADMOB_REWARDED_VIDEO_ADUNIT_ID = "ca-app-pub-9942448998483932/6503382149";
    private static final String ADMOB_UNIT_ID_INTERSTITIAL_AD = "ca-app-pub-9942448998483932/8581916428";
    private static final String ADMOB_UNIT_ID_INTERSTITIAL_VIDEO = "ca-app-pub-9942448998483932/3243450527";

    private Button loadRewardedVideo;
    private Button showRewardedVideo;
    private Button loadInterstitialVideo;
    private Button showInterstitialVideo;
    private Button loadInterstitialAd;
    private Button showInterstitialAd;

    private RewardedVideoAd rewardedVideo;
    private InterstitialAd interstitialAd;
    private InterstitialAd interstitialVideo;

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
                break;

            case R.id.show_rewarded:
                rewardedVideo.show();
                break;
            case R.id.load_interstitial_video:
                interstitialVideo = new InterstitialAd(this);
                interstitialVideo.setAdUnitId(ADMOB_UNIT_ID_INTERSTITIAL_VIDEO);
                Bundle videoBundle = new Bundle();
                interstitialVideo.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(YouAppiInterstitialVideo.class, videoBundle).build());
                break;
            case R.id.show_interstitial_video:
                if (interstitialVideo != null && interstitialVideo.isLoaded()) {
                    interstitialVideo.show();
                    interstitialVideo = null;
                }
                break;
            case R.id.load_interstitial_ad:
                interstitialAd = new InterstitialAd(this);
                interstitialAd.setAdUnitId(ADMOB_UNIT_ID_INTERSTITIAL_AD);
                Bundle bundle = new Bundle();
                interstitialAd.loadAd(new AdRequest.Builder().addCustomEventExtrasBundle(YouAppiInterstitialAd.class, bundle).build());
                break;
            case R.id.show_interstitial_ad:
                if (interstitialAd != null && interstitialAd.isLoaded()) {
                    interstitialAd.show();
                    interstitialAd = null;
                }
                break;
        }
    }
}
