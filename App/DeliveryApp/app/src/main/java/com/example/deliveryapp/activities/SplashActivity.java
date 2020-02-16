package com.example.deliveryapp.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.deliveryapp.R;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {
    String[] mPermission = {"android.permission.CAMERA", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION","android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_CODE_PERMISSION = 2;
    GifImageView imageViewSplash;
    //VideoView splashImg;
    Animation animationFadeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageViewSplash = (GifImageView) findViewById(R.id.imageViewSplash);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission[0]) != MockPackageManager.PERMISSION_GRANTED     ||
                    ActivityCompat.checkSelfPermission(this, mPermission[1]) != MockPackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[2]) != MockPackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[3]) != MockPackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[4]) != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, mPermission, REQUEST_CODE_PERMISSION);

            } else {
                animateImage();
            }
        } catch (Exception ee) {
            Log.e("TAG", "Activityplash, onCreate, Exception " + ee.toString());
        }
    }

    public void goNext() {

        Thread mainSplashThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("mainSplashThread", "mainSplashThread Started");
                    Thread.sleep(100);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity();
                        }
                    });
                } catch (Exception ee) {
                    Log.e("ResultException", ee.toString());
                }
            }
        });
        mainSplashThread.start();
    }

    void startActivity() {
        Intent ii = new Intent(SplashActivity.this,LoginActivity.class);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> pair1 = new Pair<View, String>(imageViewSplash, "sharedLogoSplsahLogin");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pair1);
            startActivity(ii, options.toBundle());
            SplashActivity.this.finish();
        } else {
            startActivity(ii);
            SplashActivity.this.finish();
        }
    }

    public void animateImage() {
        imageViewSplash.startAnimation(animationFadeIn);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length == mPermission.length &&
                    grantResults[0] == MockPackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == MockPackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == MockPackageManager.PERMISSION_GRANTED &&
                    grantResults[3] == MockPackageManager.PERMISSION_GRANTED &&
                    grantResults[4] == MockPackageManager.PERMISSION_GRANTED) {
                animateImage();
            }
        }
    }
}
