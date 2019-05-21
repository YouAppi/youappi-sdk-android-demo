package com.youappi.sdk.demo;

import android.app.Application;

import com.youappi.sdk.YouAPPi;

/**
 * User: Bashan
 * Date: 18/02/2018
 * Time: 13:50
 */
public class YouAppApplication extends Application {

  // Please note this is YouAppi's demo access token. It should be replaced with your app access token.
  private static final String DEMO_TOKEN = "821cfa77-3127-42b5-9e6b-0afcecf77c67";
  private static final boolean consent = true;
  private static final boolean gdpr = false;

  @Override
  public void onCreate() {
    super.onCreate();
    YouAPPi.init(this, DEMO_TOKEN, consent, gdpr);
  }
}