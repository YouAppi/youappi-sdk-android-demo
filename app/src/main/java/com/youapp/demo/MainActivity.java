package com.youapp.demo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.youappi.ai.sdk.BaseAd;
import com.youappi.ai.sdk.YouAPPi;
import com.youappi.ai.sdk.ads.YAInterstitialAd;
import com.youappi.ai.sdk.ads.YAInterstitialVideoAd;
import com.youappi.ai.sdk.ads.YARewardedVideoAd;
import com.youappi.ai.sdk.logic.Logger;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements Logger.LogListener, View.OnClickListener {

    enum ButtonState {
        LOAD, LOADING, SHOW
    }

    // Please note this is YouAppi's demo access token. It should be replaced with your app access token.
    private static final String DEMO_TOKEN = "821cfa77-3127-42b5-9e6b-0afcecf77c67";
    public static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBarRewardedVideo;
    private ProgressBar progressBarInterstitialAd;
    private ProgressBar progressBarInterstitialVideo;

    Button buttonRewardedVideo;
    Button buttonInterstitialAd;
    Button buttonInterstitialVideo;

    private YARewardedVideoAd rewardedVideoAd;
    private YAInterstitialAd interstitialAd;
    private YAInterstitialVideoAd interstitialVideoAd;

    private String adUnitIdRewardedVideo = "test_rewarded_video";
    private String adUnitIdInterstitialAd = "test_interstitial_ad";
    private String adUnitIdInterstitialVideo = "test_interstitial_video";

    private EditText createEditText(String text, String hint) {
        final EditText input = new EditText(MainActivity.this);
        input.setText(text);
        input.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        return input;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouAPPi.init(MainActivity.this, DEMO_TOKEN);

        progressBarRewardedVideo = (ProgressBar) findViewById(R.id.progress_rewarded_video);
        progressBarInterstitialAd = (ProgressBar) findViewById(R.id.progress_interstitial_ad);
        progressBarInterstitialVideo = (ProgressBar) findViewById(R.id.progress_interstitial_video);

        buttonRewardedVideo = (Button) findViewById(R.id.button_rewarded_video);
        buttonRewardedVideo.setOnClickListener(this);

        buttonInterstitialAd = (Button) findViewById(R.id.button_interstitial_ad);
        buttonInterstitialAd.setOnClickListener(this);

        buttonInterstitialVideo = (Button) findViewById(R.id.button_interstitial_video);
        buttonInterstitialVideo.setOnClickListener(this);

        Button buttonAdUnitId = (Button) findViewById(R.id.button_adunitid);
        buttonAdUnitId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAdUnitIdDialog();
            }
        });

        initAdUnits();
    }

    private void initAdUnits() {
        rewardedVideoAd = YouAPPi.getInstance().rewaredVideoAd(adUnitIdRewardedVideo);
        interstitialVideoAd = YouAPPi.getInstance().interstitialVideoAd(adUnitIdInterstitialAd);
        interstitialAd = YouAPPi.getInstance().interstitialAd(adUnitIdInterstitialVideo);

        rewardedVideoAd.setRewardedVideoAdListener(new DemoRewardedVideoAdListener(this));
        interstitialVideoAd.setInterstitialVideoAdListener(new DemoInterstitialVideoAdListener(this));
        interstitialAd.setInterstitialAdListener(new DemoInterstitialAdListener(this));

        setButtonState(buttonRewardedVideo, ButtonState.LOAD);
        setButtonState(buttonInterstitialAd, ButtonState.LOAD);
        setButtonState(buttonInterstitialVideo, ButtonState.LOAD);
    }

    void setButtonState(Button button, ButtonState buttonState) {
        String buttonText = null;
        switch (button.getId()) {
            case R.id.button_rewarded_video:
                buttonText = "Rewarded Video Ad";
                progressBarRewardedVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.button_interstitial_ad:
                buttonText = "Interstitial Ad";
                progressBarInterstitialAd.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.button_interstitial_video:
                buttonText = "Interstitial Video";
                progressBarInterstitialVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
        }


        switch (buttonState) {
            case LOAD:
                button.setText("Load " + buttonText);
                break;
            case LOADING:
                button.setText("Loading " + buttonText);
                break;
            case SHOW:
                button.setText("Show " + buttonText);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        BaseAd ad = null;

        switch (v.getId()) {

            case R.id.button_rewarded_video:
                ad = rewardedVideoAd;
                break;

            case R.id.button_interstitial_ad:
                ad = interstitialAd;
                break;

            case R.id.button_interstitial_video:
                ad = interstitialVideoAd;
                break;
        }

        if (ad != null) {
            if (!ad.isAvailable()) {
                setButtonState((Button) v, ButtonState.LOADING);
                ad.load();
            } else {
                ad.show();
            }
        }
    }

    @Override
    public void log(String tag, String msg) {
        Log.i(tag, msg);
    }

    private void createAdUnitIdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.adunitid_dialog_title);

        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText editRewardedVideo = createEditText(adUnitIdRewardedVideo, "Rewarded video ad unit id");
        final EditText editInterstitialAd = createEditText(adUnitIdInterstitialAd, "Interstitial ad unit id");
        final EditText editInterstitialVideo = createEditText(adUnitIdInterstitialVideo, "Interstitial video ad unit id");

        linearLayout.addView(editRewardedVideo);
        linearLayout.addView(editInterstitialAd);
        linearLayout.addView(editInterstitialVideo);

        builder.setView(linearLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adUnitIdRewardedVideo = editRewardedVideo.getText().toString().trim();
                adUnitIdInterstitialAd = editInterstitialAd.getText().toString().trim();
                adUnitIdInterstitialVideo = editInterstitialVideo.getText().toString().trim();

                initAdUnits();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}