package com.example.deliveryapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.deliveryapp.R;
import com.example.deliveryapp.adapters.OrdersAdapter;
import com.example.deliveryapp.model.Orders;
import com.example.deliveryapp.util.AlertUtil;
import com.example.deliveryapp.util.AppConfig;
import com.example.deliveryapp.util.GPSTracker;
import com.example.deliveryapp.util.GetHttpResponse;
import com.example.deliveryapp.util.StartLocationAlert;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    Activity mContext = HomeActivity.this;


    RecyclerView orderRecycleView;
    String personId;
    ProgressDialog progress;
    List<Orders> ordersList;
    OrdersAdapter adapter;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        adapter = new OrdersAdapter(HomeActivity.this, ordersList, new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, Orders order) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("OrderObject", order);
                Intent ii = new Intent(HomeActivity.this,OrderDetailsActivity.class);
                ii.putExtras(bundle);
                startActivity(ii);
             //   Toast.makeText(HomeActivity.this, "Product Name: "+order.getProductName()+" Order id: "+order.getOrderId(), Toast.LENGTH_SHORT).show();
            }
        });
        orderRecycleView.setAdapter(adapter);

    }

    protected void init() {
        //this.location = null;
        gps = new GPSTracker(mContext);
        orderRecycleView = (RecyclerView) findViewById(R.id.orderRecycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HomeActivity.this);
        orderRecycleView.setLayoutManager(mLayoutManager);
        orderRecycleView.setItemAnimator(new DefaultItemAnimator());
        Bundle bundle = getIntent().getExtras();
        personId = bundle.getString("PersonId");
        progress = AlertUtil.getProgressDialogWith(HomeActivity.this,"Getting Your location..");
        ordersList = new ArrayList<Orders>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gps = new GPSTracker(HomeActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            //Toast.makeText(HomeActivity.this, "Lat: " + HomeActivity.this.location.getLatitude() + " Lon: " + HomeActivity.this.location.getLongitude(), Toast.LENGTH_SHORT).show();
            String lat = Double.toString(latitude);
            String lon = Double.toString(longitude);

            new GetAllOrders().execute(personId, lat, lon);
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            //gps.showSettingsAlert();
            Log.e("onResume", "ifWorked");
            StartLocationAlert startLocationAlert = new StartLocationAlert(mContext);
        }
       /* if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


        } else {


        }*/
    }

    class GetAllOrders extends AsyncTask<String, Void, Void> {
        ProgressDialog progress;
        String url = AppConfig.url.BASE_URL + GetHttpResponse.OperationPages.getOrderByArea;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(HomeActivity.this);
            progress.setMessage("Loading Orders...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
            ordersList.clear();
            //progress = AlertUtil.getProgressDialogWith(HomeActivity.this,"Loading Orders...");
        }

        @Override
        protected Void doInBackground(String... s) {
            try {
                String person = s[0];
                String lat = s[1];
                String lon = s[2];
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("PersonId", person));
                nameValuePairs.add(new BasicNameValuePair("Lat", lat));
                nameValuePairs.add(new BasicNameValuePair("Lng", lon));
                Log.e(TAG, "nameValuePairs "+nameValuePairs.toString());
                JSONObject responObject = new GetHttpResponse().getJSONObjectResponse(url, nameValuePairs);
                Log.e(TAG, responObject.toString());
                String status = responObject.getString("Status");
                String message = responObject.getString("Message");
                if(status.equals("Success") && message.equals("Record Found..")) {
                    JSONArray array = responObject.getJSONArray("Result");
                    for (int i=0; i< array.length();i++) {
                        Orders order = new Orders();
                        JSONObject obj = array.getJSONObject(i);
                        order.setCustMobile(obj.getString("CustMobile"));
                        order.setOrderId(obj.getString("OrderId"));
                        order.setTotalCost(obj.getString("TotalCost"));
                        order.setCustName(obj.getString("CustName"));
                        order.setProductName(obj.getString("ProductName"));
                        ordersList.add(order);
                    }
                    publishProgress();
                }
            } catch (Exception ee) {
                Log.e(TAG, ee.toString());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress.dismiss();
        }
    }
}
