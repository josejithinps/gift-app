package com.Catalina.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

/*
 * This class is used to get the response from the remote server using the provided url
 */

public class ServiceConnector {
	
	public ServiceConnector(){
		
	}
	
	/*
	 * This method is used to get the response of the provided url
	 */
	public String getResponse(String urlString) throws Exception
	{
		return urlString;

	}

	/*
	 * This method is used to connect to the remote server. An input stream is returned.
	 */
	public void downloadUrl(String data) throws URISyntaxException {
		HttpURLConnection con = null;
		Log.v("HTTP Response", "Download Reached------------------------");
		String urlString = "";
		InputStream is=null;
		try {
			

			URL url = new URL("http://101.63.196.40/DataConnector/index.php");

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url.toURI());
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		      nameValuePairs.add(new BasicNameValuePair("data",data));
		      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpClient.execute(httpPost);
			int responseCode = response.getStatusLine().getStatusCode();
			Log.v("HTTP Response", "HTTP Response Code==="+responseCode);
			if(responseCode == 200) {
			
			HttpEntity entity = response.getEntity();
			    if(entity != null) {
			        String responseBody = EntityUtils.toString(entity);
			        Log.v("HTTP Response", "HTTP Response==="+responseBody);
			    }
			}

		}catch (IOException e) {
                        //handle the exception !
			Log.e("Catalina", "An exception occured while getting the response " + e.getMessage(), e);
			e.printStackTrace();
		}

	}
}
