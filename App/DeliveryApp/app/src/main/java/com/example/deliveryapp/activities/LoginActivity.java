package com.example.deliveryapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.deliveryapp.R;
import com.example.deliveryapp.util.AlertUtil;
import com.example.deliveryapp.util.AppConfig;
import com.example.deliveryapp.util.GetHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUserName, editTextUserPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName = editTextUserName.getText().toString();
                String strPassword = editTextUserPassword.getText().toString();
                if (strPassword.isEmpty() || strUserName.isEmpty()) {
                    new AlertUtil(LoginActivity.this).buildAlertDialogue("Warning", "Please your login credentials", AlertUtil.AlertType.DEFAULT).show();
                } else {
                    new VerifyLogin().execute(strUserName, strPassword);
                }
            }
        });
    }

    void init() {
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextUserPassword = (EditText) findViewById(R.id.editTextUserPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }

    class VerifyLogin extends AsyncTask<String, String, String> {
        String url = AppConfig.url.BASE_URL + GetHttpResponse.OperationPages.delivaryLogin;
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Verifying credentials");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);

            progress.show();
        }

        @Override
        protected String doInBackground(String... params) {
           try {
               String userName = params[0];
               String password = params[1];
               List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
               nameValuePairs.add(new BasicNameValuePair("UserName",userName));
               nameValuePairs.add(new BasicNameValuePair("Password",password));
               JSONObject response = new GetHttpResponse().getJSONObjectResponse(url,nameValuePairs);
               String status = response.getString("Status");
               String personId = response.getString("PersonId");
               String message = response.getString("Message");
               if(status.equals("Success")) {
                   return status+"~"+personId+"~"+message;
               } else {
                   return status+"~"+personId+"~"+message;
               }
           } catch (Exception ee) {
               return "Exception~0~"+ee.toString();
           }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progress.dismiss();
            String[] data = s.split("~");
            if(!data[0].equals("Fail") || !data[0].equals("Exception")) {
                Intent ii = new Intent(LoginActivity.this, HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PersonId",data[1]);
                ii.putExtras(bundle);
                startActivity(ii);
            } else {
                new AlertUtil(LoginActivity.this).buildAlertDialogue(data[0],data[2], AlertUtil.AlertType.DEFAULT).show();
            }
        }
    }
}
