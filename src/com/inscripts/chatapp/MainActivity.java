package com.inscripts.chatapp;

import java.util.ArrayList;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static com.inscripts.chatapp.Constants.*;

public class MainActivity extends Activity implements ResponseCollector {

	private ListView list;
	private ArrayList<Chat> array;
	private ArrayAdapter<Chat> adapter;
	private String tag = "MainActivity";
	private EditText message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		message =  (EditText)findViewById(R.id.messageEdit);
		setActionBar();
		list =  (ListView)findViewById(R.id.listView);
		array =  new ArrayList<Chat>();
		adapter =  new CustomListView(this, array);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				hideKeyboard();
			}
		});
		
		if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean(LOC_IS_API_ERROR, false)) {
			getMessagesFromApi();
		} else {
			getMessageFromDatabase();
		}
	}
	
	@SuppressLint("InflateParams")
	private void setActionBar() {

		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setHomeButtonEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setDisplayShowCustomEnabled(true);

		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.home_page_action_bar, null);
		TextView textView = (TextView)v.findViewById(R.id.title);
		textView.setText("Nikhil Raut");

		bar.setCustomView(v);

	}
	
	public void send(View view) {

		if (message.getText().toString().length() > 0) {
			
			message.setError(null);
			Long tsLong = System.currentTimeMillis()/1000;

			Chat chat =  new Chat();
			chat.setMessage(message.getText().toString());
			chat.setRole("sender");
			chat.setTimeStamp(tsLong.toString());

			array.add(chat);
			adapter.notifyDataSetChanged();
			insetInToDatabase(chat);
			message.setText(null);
			
		} else {
			message.setError("Please enter some message...");
		}
	}
	
	private void hideKeyboard() {   
	    // Check if no view has focus:
	    View view = this.getCurrentFocus();
	    if (view != null) {
	        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
	


	private void insetInToDatabase( Chat chat) {
		ContentValues contentValues =  new ContentValues();
		contentValues.put(COL_MESSAGE, chat.getMessage());
		contentValues.put(COL_ROLE, chat.getRole());
		contentValues.put(COL_TIMESTAMP, chat.getTimeStamp());
		getContentResolver().insert(CONTENT_URI_CHAT_MESSAGE, contentValues);
	}

	private void getMessagesFromApi() {
		if( Utility.isOnline(this)) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
			AppAsyncTask asyncTask = new AppAsyncTask(this);
			asyncTask.collector = (ResponseCollector) this;
			asyncTask.execute();
		} else {
			Toast.makeText(this, "Please check Your Network Connection", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private void getMessageFromDatabase() {
		
		String [] column = { COL_MESSAGE, COL_ROLE, COL_TIMESTAMP };
		
		Cursor cursor = null;
		
		cursor =  getContentResolver().query(CONTENT_URI_CHAT_MESSAGE, column, null, null, null);
		
		if (cursor != null && cursor.moveToFirst()) {
			Chat chat;
			do {
				
				chat =  new Chat();
				chat.setMessage(cursor.getString(cursor.getColumnIndex(COL_MESSAGE)));
				chat.setRole(cursor.getString(cursor.getColumnIndex(COL_ROLE)));
				chat.setTimeStamp(cursor.getString(cursor.getColumnIndex(COL_TIMESTAMP)));

				array.add(chat);
			} while (cursor.moveToNext());
			
			adapter.notifyDataSetChanged();
			cursor.close();
			
		} else {
			getMessagesFromApi();
		}
		
	}
	

	@Override
	public void collectResponse(String response) {
		Log.e(tag , response);
		try {
			getContentResolver().delete(CONTENT_URI_CHAT_MESSAGE, null, null);
			JSONParser.parsAndInsertMessageIntoDBFromJSON(response, this);
			getMessageFromDatabase();
		} catch (JSONException e) {
			//If failed 
			PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean(LOC_IS_API_ERROR, true).commit();
			Log.e(tag, e.toString());
			e.printStackTrace();
		}
	}

	
}
