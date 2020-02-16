package com.example.deliveryapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.deliveryapp.model.Orders;
import com.example.deliveryapp.util.AlertUtil;
import com.example.deliveryapp.util.AppConfig;
import com.example.deliveryapp.util.GetHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {
    FloatingActionButton fab;
    TextView textViewOrderId,textViewCustName,textViewCustMobile,textViewProductName,textViewTotalCost;
    Button buttonMarkDelivared;
    Orders order;
    public static final int SIGNATURE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        textViewOrderId.setText("ORDER ID: "+order.getOrderId());
        textViewCustName.setText("CUSTOMER NAME: "+order.getCustName());
        textViewCustMobile.setText("CUSTOMER MOBILE: "+order.getCustMobile());
        textViewProductName.setText("PRODUCT NAME: "+order.getProductName());
        textViewTotalCost.setText("TOTAL COST: "+order.getTotalCost());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailsActivity.this,QRCodeActivity.class));
            }
        });

        buttonMarkDelivared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String orderId = order.getOrderId();
//                new UpdateDeliveryStatus().execute(orderId);
                Intent intent = new Intent(OrderDetailsActivity.this, CaptureSignature.class);
                startActivityForResult(intent, SIGNATURE_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SIGNATURE_ACTIVITY:
                if (resultCode == RESULT_OK) {

                    Bundle bundle = data.getExtras();
                    String status = bundle.getString("status");
                    if (status.equalsIgnoreCase("done")) {
                        Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 105, 50);
                        toast.show();
                        String orderId = order.getOrderId();
                        new UpdateDeliveryStatus().execute(orderId);
                    }
                }
                break;
        }
    }

    void init() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle bundle = getIntent().getExtras();
        order = (Orders) bundle.getSerializable("OrderObject");
        textViewOrderId = (TextView) findViewById(R.id.textViewOrderId);
        textViewCustName = (TextView) findViewById(R.id.textViewCustName);
        textViewCustMobile = (TextView) findViewById(R.id.textViewCustMobile);
        textViewProductName = (TextView) findViewById(R.id.textViewProductName);
        textViewTotalCost = (TextView) findViewById(R.id.textViewTotalCost);
        buttonMarkDelivared = (Button) findViewById(R.id.buttonMarkDelivared);
    }





    class UpdateDeliveryStatus extends AsyncTask<String, String, String> {
        ProgressDialog progress;
        String url = AppConfig.url.BASE_URL+ GetHttpResponse.OperationPages.updateOrderStatus;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = AlertUtil.getProgressDialogWith(OrderDetailsActivity.this, "Updating Delivery Status...");
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {
           try {
               String orderId = strings[0];
               List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
               nameValuePairs.add(new BasicNameValuePair("OrderId",orderId));
               JSONObject responseObject = new GetHttpResponse().getJSONObjectResponse(url,nameValuePairs);
               String status = responseObject.getString("Status");
               if(status.equals("Success")) {
                   return "Success";
               } else {
                   return "Fail";
               }
           } catch (Exception ee) {
               Log.e("TAG", ee.toString());
               return "Exception: "+ee.toString();
           }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            if(!(s.equals("Fail")) || !(s.contains("Exception:"))) {
                AlertUtil alertUtil= new AlertUtil(OrderDetailsActivity.this);
                alertUtil.setButtonClickListener(new AlertUtil.MyAlertListener() {
                    @Override
                    public void onButtonClicked(DialogInterface d, int i, AlertUtil.AlertButtonType typeOfButton) {
                        switch (typeOfButton){
                            case NEGATIVE:
                                Log.e("TAG", "NEGATIVE CLICKED");
                                break;
                            case POSITIVE:
                                Log.e("TAG", "POSITIVE CLICKED..");
                                d.dismiss();
                                onBackPressed();
                                break;
                        }
                    }
                });
                AlertDialog dialog = alertUtil.buildAlertDialogue("Success","Delivery Status Updated success", AlertUtil.AlertType.DEFAULT);
                dialog.show();
            } else {
                new AlertUtil(OrderDetailsActivity.this).buildAlertDialogue("Fail","An Error ocured While Updating status", AlertUtil.AlertType.DEFAULT).show();
            }
        }
    }

}
