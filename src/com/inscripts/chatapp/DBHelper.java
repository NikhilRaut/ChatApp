package com.inscripts.chatapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.inscripts.chatapp.Constants.*;
public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "chat.db";
	private static int VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE " + TABLE_CHAT_MESSAGE + " ( " 
				   + COL_MESSAGE + " TEXT, "
				   + COL_ROLE + " TEXT, "
				   + COL_TIMESTAMP + " TEXT);" );
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
