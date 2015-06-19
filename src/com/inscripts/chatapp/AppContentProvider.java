package com.inscripts.chatapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import static com.inscripts.chatapp.Constants.*;

public class AppContentProvider extends ContentProvider {

	private DBHelper helper;
	

	private static final int CODE_CHAT = 1;
	
	static UriMatcher uriMatcher;
	
	static {
		
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH_CHAT, CODE_CHAT);
	}

	
	@Override
	public boolean onCreate() {
		
		helper = new DBHelper(getContext());
		return false;
	}
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		int code = uriMatcher.match(uri);
		int rowAffected = 0;
		if(code == CODE_CHAT) {
			rowAffected = db.delete(TABLE_CHAT_MESSAGE, selection, selectionArgs);
		}
		return rowAffected;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		SQLiteDatabase db = helper.getWritableDatabase();
		int code = uriMatcher.match(uri);
		if(code == CODE_CHAT){
			db.insert(TABLE_CHAT_MESSAGE, null, values);
		}
		return null;
	}

	

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		SQLiteDatabase db = helper.getReadableDatabase();
		int code = uriMatcher.match(uri);
		Cursor cursor = null;
		if(code == CODE_CHAT) {
			cursor = db.query(TABLE_CHAT_MESSAGE, projection, selection, selectionArgs, null, null, sortOrder);
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		
		return 0;
	}

}

