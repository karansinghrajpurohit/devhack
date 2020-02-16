package com.example.deliveryapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.deliveryapp.R;
import com.example.deliveryapp.util.AlertUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity {
    ZXingScannerView mScannerView;
    List<BarcodeFormat> formats;
    boolean flashOn = false;
    FloatingActionButton fab;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        mScannerView.setFormats(formats);
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                address = result.getText().replace(" ","+");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertUtil alertUtil = new AlertUtil(QRCodeActivity.this);
                        AlertDialog dialog = alertUtil.buildAlertDialogue("CONFIRM","Are You sure you want to navigate to this address", AlertUtil.AlertType.CONFIRM);
                        alertUtil.setButtonClickListener(new AlertUtil.MyAlertListener() {
                            @Override
                            public void onButtonClicked(DialogInterface d, int i, AlertUtil.AlertButtonType typeOfButton) {

                                switch (typeOfButton) {
                                    case NEGATIVE:
                                        d.dismiss();
                                        onBackPressed();
                                        break;
                                    case POSITIVE:
                                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+address+"&mode=d");
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);
                                        QRCodeActivity.this.finish();
                                        break;
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flashOn){
                    mScannerView.setFlash(true);
                    fab.setImageResource(R.drawable.ic_flash_off_black_24dp);
                }else {
                    mScannerView.setFlash(false);
                    fab.setImageResource(R.drawable.ic_flash_on_black_24dp);
                }
                flashOn = !flashOn;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    void init() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_flash_on_black_24dp);
        mScannerView = (ZXingScannerView) findViewById(R.id.mScannerView);
        formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.QR_CODE);

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
}
