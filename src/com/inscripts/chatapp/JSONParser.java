package com.inscripts.chatapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.inscripts.chatapp.Constants.*;
import android.content.ContentValues;
import android.content.Context;

public class JSONParser {
	
	public static void parsAndInsertMessageIntoDBFromJSON(String jsonString, Context context) throws JSONException {
		JSONObject object =  new JSONObject(jsonString);
		JSONArray array = object.getJSONArray(KEY_MESSAGES);
		Chat chat;
		
//		for (int i = 0; i < array.length(); i++) {
		for (int i = array.length() - 1; i > 0; i--) {
		
			chat =  new Chat();
			JSONObject jsonObject = array.getJSONObject(i);
			chat.setMessage(jsonObject.getString(KEY_MESSAGE));
			chat.setRole(jsonObject.getString(KEY_ROLE));
			chat.setTimeStamp(jsonObject.getString(KEY_TIMESTAMP));
			insertInToChatMessageDB(chat, context);
			
		}
		
		
	}

	private static void insertInToChatMessageDB(Chat chat, Context context) {
		
		ContentValues values =  new ContentValues();
		
		values.put(COL_MESSAGE, chat.getMessage());
		values.put(COL_ROLE, chat.getRole());
		values.put(COL_TIMESTAMP, chat.getTimeStamp());
		
		context.getContentResolver().insert(CONTENT_URI_CHAT_MESSAGE, values);
		
	}

}
