package com.example.deliveryapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Anirudh on 21/02/18.
 */

public class AlertUtil {

    private MyAlertListener listener = null;
    private Activity context = null;

    public enum AlertType {
        DEFAULT,
        CANCEL,
        CONFIRM
    }

    public enum AlertButtonType {
        POSITIVE,
        NEGATIVE
    }

    public AlertUtil(Activity context) {
        this.context = context;
    }

    public AlertDialog buildAlertDialogue(String title, String message, AlertType type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setTitle(title);
        switch (type) {
            case CANCEL:
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        if(listener != null) {
                            listener.onButtonClicked(d,i, AlertButtonType.NEGATIVE);
                        } else {
                            Log.e("TAG", "Add Listener to the alert util");
                            d.dismiss();
                        }
                    }
                });
                break;

            case DEFAULT:
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        if(listener != null) {
                            listener.onButtonClicked(d,i, AlertButtonType.POSITIVE);
                        } else {
                            Log.e("TAG", "Add Listener to the alert util");
                            d.dismiss();
                        }
                    }
                });
                break;

            case CONFIRM:
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        if(listener != null) {
                            listener.onButtonClicked(d,i, AlertButtonType.POSITIVE);
                        } else {
                            Log.e("TAG", "Add Listener to the alert util");
                            d.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        if(listener != null) {
                            listener.onButtonClicked(d,i, AlertButtonType.NEGATIVE);
                        } else {
                            Log.e("TAG", "Add Listener to the alert util");
                            d.dismiss();
                        }
                    }
                });
                break;

            default:
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        if(listener != null) {
                            listener.onButtonClicked(d,i, AlertButtonType.POSITIVE);
                        } else {
                            Log.e("TAG", "Add Listener to the alert util");
                            d.dismiss();
                        }
                    }
                });
                break;

        }
        return builder.create();
    }

    public void setButtonClickListener(MyAlertListener listener) {
        this.listener = listener;
    }

    public interface MyAlertListener {
        void onButtonClicked(DialogInterface d, int i, AlertUtil.AlertButtonType typeOfButton);
    }


    public static ProgressDialog getProgressDialogWith(Activity context, String message){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}