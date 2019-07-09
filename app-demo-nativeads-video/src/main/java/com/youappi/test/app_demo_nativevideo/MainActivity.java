package com.youappi.test.app_demo_nativevideo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.youappi.sdk.nativeads.AdRequest;
import com.youappi.sdk.nativeads.NativeAd;
import com.youappi.sdk.nativeads.NativeTypes;

import com.youappi.sdk.nativeads.listeners.NativeAdListener;
import com.youappi.sdk.nativeads.listeners.NativeAdResponseListener;
import com.youappi.sdk.nativeads.video.NativeVideoAdsRenderer;
import com.youappi.sdk.nativeads.video.VideoNativeAd;
import com.youappi.sdk.nativeads.video.VideoNativeAdLoader;
import com.youappi.sdk.nativeads.video.VideoViewMapper;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    View advView;
    View advView2;
    View advView3;
    NativeVideoAdsRenderer nativeVideoAdsRenderer;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        advView = findViewById(R.id.adv_view);
        advView2 = findViewById(R.id.adv_view2);
        advView3 = findViewById(R.id.adv_view3);

        Button envButton = findViewById(R.id.env);
        if (BuildConfig.DEBUG) {
            envButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnvironmentChanger.openEnvironment(MainActivity.this);
                }
            });
        } else {
            envButton.setVisibility(View.GONE);
        }

        final Button buttonLoadAd = findViewById(R.id.buttonLoadAd);

        buttonLoadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd();
            }
        });

        VideoViewMapper.Builder builder = new VideoViewMapper.Builder(R.layout.native_ad_layout);
        builder.setTitleViewId(R.id.nativead_title)
                .setMediaViewId(R.id.nativead_media)
                .setIconViewId(R.id.nativead_icon)
                .setCtaViewId(R.id.nativead_cta)
                .setDescriptionViewId(R.id.nativead_description)
                .setRatingViewId(R.id.nativead_rating)
                .setRatingIconViewId(R.id.nativead_rating_icon);
        nativeVideoAdsRenderer = new NativeVideoAdsRenderer(builder.build());

    }

    private void loadAd() {
        VideoNativeAdLoader videoNativeAdLoader = new VideoNativeAdLoader.Builder(MainActivity.this,
                "821cfa77-3127-42b5-9e6b-0afcecf77c67",
                "nativeAdTest")
                .setNativeAdResponseListener(new NativeAdResponseListener() {
                    @Override
                    public void onNativeAdResponse(NativeAd videoNativeAd) {
                        int reminder = counter % 3;
                        switch (reminder) {
                            case 0:
                                nativeVideoAdsRenderer.renderAd(advView, (VideoNativeAd) videoNativeAd);
                                break;
                            case 1:
                                nativeVideoAdsRenderer.renderAd(advView2, (VideoNativeAd) videoNativeAd);
                                break;
                            case 2:
                                nativeVideoAdsRenderer.renderAd(advView3, (VideoNativeAd) videoNativeAd);
                                break;
                        }

                        counter++;
                    }
                })
                .setCreativeType(NativeTypes.CreativeType.Video)
                .setVolumeMode(NativeTypes.VolumeMode.Mute)
                .setNativeAdListener(new NativeAdListener() {
                    @Override
                    public void onFailure(NativeTypes.ErrorCode errorCode, Exception e) {
                        Log.e(TAG, "ad failiure. code: " + errorCode.name() + " exception: " + e.getMessage());
                    }

                    @Override
                    public void onAdClicked() {
                        Log.e(TAG, "ad clicked");
                    }

                    @Override
                    public void onAdImpression() {
                        Log.e(TAG, "Impression");
                    }
                })
                .build();

        AdRequest adRequest = new AdRequest.Builder()
                .addCustomParam("user_id", "1234")
                .setAge(35)
                .setGender(AdRequest.Gender.Male)
                .build();

        videoNativeAdLoader.load(adRequest);
    }

}
