package com.youappi.medaition.admob.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.youappi.ai.sdk.YouAPPi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String ADMOB_APP_ID = "ca-app-pub-9942448998483932~5444617199";

  private static final String ADMOB_REWARDED_VIDEO_ADUNIT_ID = "ca-app-pub-9942448998483932/6503382149";

  private RewardedVideoAd rewardedVideo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.load_rewarded).setOnClickListener(this);
    findViewById(R.id.show_rewarded).setOnClickListener(this);

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
    }
  }
}
