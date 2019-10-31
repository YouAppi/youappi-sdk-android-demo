package com.youappi.sdk.demo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.youappi.sdk.BaseAd;
import com.youappi.sdk.YouAPPi;
import com.youappi.sdk.ads.YAAdRequest;
import com.youappi.sdk.ads.YAInterstitialAd;
import com.youappi.sdk.ads.YARewardedVideoAd;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonRewardedVideo;
    Button buttonInterstitialVideo;
    Button buttonInterstitialAd;
    private ProgressBar progressBarRewardedVideo;
    private ProgressBar progressBarInterstitialVideo;
    private ProgressBar progressBarInterstitialAd;
    private YARewardedVideoAd rewardedVideoAd;
    private YAInterstitialAd interstitialVideoAd;
    private YAInterstitialAd interstitialAd;
    private String adUnitIdRewardedVideo = "test_rewarded_video_android";
    private String adUnitIdInterstitialVideo = "test_interstitial_video_android";
    private String adUnitIdInterstitialAd = "test_interstitial_ad_android";

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

        progressBarRewardedVideo = findViewById(R.id.progress_rewarded_video);
        progressBarInterstitialVideo = findViewById(R.id.progress_interstitial_video);
        progressBarInterstitialAd = findViewById(R.id.progress_interstitial_ad);

        buttonRewardedVideo = findViewById(R.id.button_rewarded_video);
        buttonRewardedVideo.setOnClickListener(this);

        buttonInterstitialVideo = findViewById(R.id.button_interstitial_video);
        buttonInterstitialVideo.setOnClickListener(this);

        buttonInterstitialAd = findViewById(R.id.button_interstitial_ad);
        buttonInterstitialAd.setOnClickListener(this);

        Button buttonAdUnitId = findViewById(R.id.button_adunitid);
        buttonAdUnitId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAdUnitIdDialog();
            }
        });

        initAdUnits();
    }

    private void initAdUnits() {
        rewardedVideoAd = YouAPPi.getInstance().rewardedVideoAd(adUnitIdRewardedVideo);
        interstitialVideoAd = YouAPPi.getInstance().interstitialAd(adUnitIdInterstitialVideo);
        interstitialAd = YouAPPi.getInstance().interstitialAd(adUnitIdInterstitialAd);

        interstitialAd.setAdRequest(new YAAdRequest().setCreativeType(YAAdRequest.CreativeType.Video));
        interstitialAd.setAdRequest(new YAAdRequest().setCreativeType(YAAdRequest.CreativeType.Static));

        rewardedVideoAd.setRewardedVideoAdListener(new DemoRewardedVideoAdListener(this));
        interstitialVideoAd.setInterstitialAdListener(new DemoInterstitialAdListener(this));
        interstitialAd.setInterstitialAdListener(new DemoInterstitialAdListener(this));

        setButtonState(buttonRewardedVideo, ButtonState.LOAD);
        setButtonState(buttonInterstitialVideo, ButtonState.LOAD);
        setButtonState(buttonInterstitialAd, ButtonState.LOAD);
    }

    @SuppressLint("SetTextI18n")
    void setButtonState(Button button, ButtonState buttonState) {
        String buttonText = null;
        switch (button.getId()) {
            case R.id.button_rewarded_video:
                buttonText = "Rewarded Video Ad";
                progressBarRewardedVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.button_interstitial_video:
                buttonText = "Interstitial Video";
                progressBarInterstitialVideo.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
                break;
            case R.id.button_interstitial_ad:
                buttonText = "Interstitial Ad";
                progressBarInterstitialAd.setVisibility(buttonState == ButtonState.LOADING ? View.VISIBLE : View.INVISIBLE);
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

            case R.id.button_interstitial_video:
                ad = interstitialVideoAd;
                break;

            case R.id.button_interstitial_ad:
                ad = interstitialAd;
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

    private void createAdUnitIdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.adunitid_dialog_title);

        LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText editRewardedVideo = createEditText(adUnitIdRewardedVideo, "Rewarded video ad unit id");
        final EditText editInterstitialVideo = createEditText(adUnitIdInterstitialVideo, "Interstitial video ad unit id");
        final EditText editInterstitialAd = createEditText(adUnitIdInterstitialAd, "Interstitial ad unit id");

        linearLayout.addView(editRewardedVideo);
        linearLayout.addView(editInterstitialVideo);
        linearLayout.addView(editInterstitialAd);

        builder.setView(linearLayout);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adUnitIdRewardedVideo = editRewardedVideo.getText().toString().trim();
                adUnitIdInterstitialVideo = editInterstitialVideo.getText().toString().trim();
                adUnitIdInterstitialAd = editInterstitialAd.getText().toString().trim();

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

    enum ButtonState {
        LOAD, LOADING, SHOW, ERROR
    }
}