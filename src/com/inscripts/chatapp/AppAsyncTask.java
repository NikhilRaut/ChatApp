package com.inscripts.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import static com.inscripts.chatapp.Constants.*;

public class AppAsyncTask extends AsyncTask<Void, Void, InputStream> {
	private ProgressDialog dialog;
	private Context context;
	ResponseCollector collector;

	public AppAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.setTitle("Please wait..");
		dialog.setMessage("Please wait.. application is performing an operation");
		dialog.show();
	}

	@Override
	protected InputStream doInBackground(Void... params) {
		try {
			URL url = new URL(URL);
			Log.e("URL", url.toString());
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			return stream;
		} catch (MalformedURLException e) {
			PreferenceManager.getDefaultSharedPreferences(this.context.getApplicationContext()).edit().putBoolean(LOC_IS_API_ERROR, true).commit();
		} catch (IOException e) {
			PreferenceManager.getDefaultSharedPreferences(this.context.getApplicationContext()).edit().putBoolean(LOC_IS_API_ERROR, true).commit();
		} catch (Exception e) {
			PreferenceManager.getDefaultSharedPreferences(this.context.getApplicationContext()).edit().putBoolean(LOC_IS_API_ERROR, true).commit();
		}

		return null;
	}

	@Override
	protected void onPostExecute(InputStream result) {
		super.onPostExecute(result);
		dialog.cancel();
		try {
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(result));
			String temp = null;

			while ((temp = reader.readLine()) != null) {
				builder.append(temp);
			}
			reader.close();
			result.close();

			String response = builder.toString();
			
			Log.e("Response", "::" + response);
			collector.collectResponse(response);
		} catch (IOException e) {

		}
	}

}
