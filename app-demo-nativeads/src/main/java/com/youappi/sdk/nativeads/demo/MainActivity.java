package com.youappi.sdk.nativeads.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.youappi.sdk.nativeads.AdRequest;
import com.youappi.sdk.nativeads.NativeAd;
import com.youappi.sdk.nativeads.NativeAdView;
import com.youappi.sdk.nativeads.NativeStaticAdsRenderer;
import com.youappi.sdk.nativeads.NativeTypes;
import com.youappi.sdk.nativeads.StaticNativeAd;
import com.youappi.sdk.nativeads.StaticNativeAdLoader;
import com.youappi.sdk.nativeads.ViewMapper;
import com.youappi.sdk.nativeads.listeners.NativeAdListener;
import com.youappi.sdk.nativeads.listeners.NativeAdResponseListener;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TextView textState;

    private NativeStaticAdsRenderer nativeStaticAdsRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textState = findViewById(R.id.textState);
        textState.setText("");

        final Button buttonLoadAd = findViewById(R.id.buttonLoadAd);

        final View nativeAdViewContainer = findViewById(R.id.nativeAdViewContainer);
        nativeAdViewContainer.setVisibility(View.GONE);

        nativeStaticAdsRenderer = new NativeStaticAdsRenderer(new ViewMapper.Builder()
                .setMediaView(findViewById(R.id.nativead_media))
                .setIconView(findViewById(R.id.nativead_icon))
                .setCtaButtonView(findViewById(R.id.nativead_cta))
                .setTitleView(findViewById(R.id.nativead_title))
                .setPrivacyView(findViewById(R.id.nativead_opt)).build());

        buttonLoadAd.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                textState.setText("Loading Native Ad");
                nativeAdViewContainer.setVisibility(View.GONE);

                StaticNativeAdLoader nativeAdLoader = new StaticNativeAdLoader.Builder(
                        MainActivity.this,
                        "821cfa77-3127-42b5-9e6b-0afcecf77c67",
                        "nativeAdTest")
                        .setNativeAdResponseListener(new NativeAdResponseListener() {
                            @Override
                            public void onNativeAdResponse(NativeAd nativeAd) {
                                final NativeAdView nativeAdView = findViewById(R.id.includedLayout);
                                nativeStaticAdsRenderer.renderAd(nativeAdView, (StaticNativeAd) nativeAd);
                                textState.setText("Ad was loaded");
                                nativeAdViewContainer.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNativeAdListener(new NativeAdListener() {
                            @Override
                            public void onFailure(NativeTypes.ErrorCode errorCode, Exception e) {
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