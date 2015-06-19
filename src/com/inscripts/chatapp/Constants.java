package com.inscripts.chatapp;

import android.net.Uri;

public class Constants {
	// Url
	public static final String URL = "http://demo7677878.mockable.io/getmessages";

	// Key to Parse JSON
	public static final String KEY_MESSAGES = "messages";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_ROLE = "role";
	public static final String KEY_TIMESTAMP = "timestamp";
	
	//Key for local store
	public static final String LOC_IS_API_ERROR = "is_api_error";

	// Table Name
	public static final String TABLE_CHAT_MESSAGE = "chat_message";

	// Table column name
	public static final String COL_ID = "id";
	public static final String COL_MESSAGE = "name";
	public static final String COL_ROLE = "status";
	public static final String COL_TIMESTAMP = "timestamp";
	
	
	// Related to content provider
	public static final String SCHEME = "content://";
	public static final String AUTHORITY = "com.inscripts.chatapp";

	// content://com.inscripts.chatapp
	public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

	// content://com.inscripts.chatapp/chat_message
	public static final String PATH_CHAT = "chat_message";
	public static final Uri CONTENT_URI_CHAT_MESSAGE = Uri.parse(SCHEME + AUTHORITY + "/" + PATH_CHAT);

}
