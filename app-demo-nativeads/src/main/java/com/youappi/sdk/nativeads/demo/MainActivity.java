package com.youappi.sdk.nativeads.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.youappi.sdk.nativeads.AdRequest;
import com.youappi.sdk.nativeads.ErrorCode;
import com.youappi.sdk.nativeads.NativeAd;
import com.youappi.sdk.nativeads.NativeAdLoader;
import com.youappi.sdk.nativeads.listeners.NativeAdListener;
import com.youappi.sdk.nativeads.listeners.NativeAdResponseListener;
import com.youappi.sdk.nativeads.views.NativeAdView;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView textState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textState = findViewById(R.id.textState);
        textState.setText("");

        final Button buttonLoadAd = findViewById(R.id.buttonLoadAd);

        final View nativeAdViewContainer = findViewById(R.id.nativeAdViewContainer);
        nativeAdViewContainer.setVisibility(View.GONE);

        buttonLoadAd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                textState.setText("Loading Native Ad");
                nativeAdViewContainer.setVisibility(View.GONE);

                NativeAdLoader nativeAdLoader = new NativeAdLoader.Builder(
                        MainActivity.this,
                        "821cfa77-3127-42b5-9e6b-0afcecf77c67",
                        "nativeAdTest")
                        .setNativeAdResponseListener(new NativeAdResponseListener() {
                            @Override
                            public void onNativeAdResponse(NativeAd nativeAd) {
                                final NativeAdView nativeAdView = findViewById(R.id.includedLayout);
                                nativeAdView.bind(nativeAd);

                                textState.setText("Ad was loaded");
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNativeAdListener(new NativeAdListener() {
                            @Override
                            public void onFailure(ErrorCode errorCode, Exception e) {
                                Log.e(TAG, "Failed in native request: " + errorCode, e);
                                textState.setText("Failed loading Ad");
                            }

                            @Override
                            public void onAdClicked() {
                                textState.setText("Ad click");
                            }

                            @Override
                            public void onAdImpression() {
                                textState.setText("Ad Impression");
                            }
                        })
                        .setUserConsent(true)
                        .setAgeRestrictedUser(false)
                        .build();

                nativeAdLoader.load(new AdRequest.Builder().build());

            }
        });
    }

}