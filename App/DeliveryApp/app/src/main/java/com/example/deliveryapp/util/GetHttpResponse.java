package com.example.deliveryapp.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by AnirudhVishal on 04/02/16.
 */
public class GetHttpResponse {

    public JSONArray getJSONArrayResponse(String url, List<NameValuePair> nameValuePairs ){
        JSONArray array= null;
        String responseText;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            responseText = EntityUtils.toString(httpEntity);
            array = new JSONArray(responseText);
        }catch (Exception ee){
            Log.e("GetHttpResponse",ee.toString());
        }
        return array;
    }

    public String getStringResponse(String url, List<NameValuePair> nameValuePairs){

        String responseText = null;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            responseText = EntityUtils.toString(httpEntity);
        }catch (Exception ee){
            Log.e("GetHttpResponse str",ee.toString());
        }
        return responseText;
    }
    
    public JSONObject getJSONObjectResponse(String url, List<NameValuePair> nameValuePairs){
        
        JSONObject object = null;
        String responseText = null;
        try{
            DefaultHttpClient httpClient = new DefaultHttpClient();
            Log.e("TAG","GetHttpResponse: url: "+url);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            responseText = EntityUtils.toString(httpEntity);
            Log.e("TAG","GetHttpResponse: resp: "+responseText);
            object = new JSONObject(responseText.trim());
        }catch (Exception ee){
            Log.e("GetHttpResponse str",ee.toString());
        }
        return object;
    }

    public interface OperationPages {
        String delivaryLogin = "delivaryLogin.jsp";
        String getOrderByArea = "getOrderByArea.jsp";
        String updateOrderStatus = "updateOrderStatus.jsp";
    }

}