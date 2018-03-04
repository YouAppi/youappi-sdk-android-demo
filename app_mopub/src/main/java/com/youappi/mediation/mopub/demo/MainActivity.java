package com.youappi.mediation.mopub.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mopub.mobileads.MoPubInterstitial;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  public static final String UNIT_ID_REWARDED_VIDEO = "5aafbe7f552842d48373f082bd585aa9";
  public static final String UNIT_ID_INTERSTITIAL_AD = "2fec087170f348e28207f0a0cb9e890b";
  public static final String UNIT_ID_INTERSTITIAL_VIDEO = "2f7234339e1949898294c0310af0b7ff";

  private MoPubInterstitial moPubInterstitial;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.load_interstitial_ad).setOnClickListener(this);
    findViewById(R.id.show_interstitial_ad).setOnClickListener(this);

    moPubInterstitial = new MoPubInterstitial(this, UNIT_ID_INTERSTITIAL_AD);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.load_interstitial_ad:
        moPubInterstitial.load();
        break;
      case R.id.show_interstitial_ad:
        moPubInterstitial.show();
        break;
    }
  }
}
