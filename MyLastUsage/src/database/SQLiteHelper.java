package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	
	public static final String LOCKS_ID = "_LocksId";
	public static final String UNLOCKS_ID = "_UnlocksId";
	public static final String DATABASE_NAME = "UsageTimeDB.db";
	public static final String LOCKS_DATE = "LocksDate";
	public static final String UNLOCKS_DATE = "UnlocksDate";
	public static final String LOCKS_TABLE = "Locks";
	public static final String UNLOCKS_TABLE = "Unlocks";
	public static final String UNLOCKS_TIME = "UnlocksTime";
	public static final String LOCKS_TIME = "locksTime";
	private static final int DATABASE_VERSION = 9;
	
	public static final String DATABASE_CREATE_UNLOCKS = "CREATE TABLE Unlocks (_UnlocksId INTEGER PRIMARY KEY autoincrement, UnlocksDate string, UnlocksTime string)";
	public static final String DATABASE_CREATE_LOCKS = "CREATE TABLE Locks (_LocksId INTEGER PRIMARY KEY autoincrement, LocksDate string, LocksTime string)";
			//"create table " + UNLOCKS_TABLE + " (" + UNLOCKS_ID 
			//+ " integer primary key autoincrement, " + UNLOCKS_DATE + " string, " + UNLOCKS_TIME + " string);";
/*	public static final String DATABASE_CREATE_LOCKS = "create table " + LOCKS_TABLE + " (" + LOCKS_ID 
			+ " integer primary key autoincrement, " + LOCKS_DATE + " string, " + LOCKS_TIME + " string);";*/
	
	public SQLiteHelper (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_UNLOCKS);
		db.execSQL(DATABASE_CREATE_LOCKS);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(SQLiteHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		
		//create new tables
		db.execSQL("DROP TABLE IF EXISTS " + UNLOCKS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + LOCKS_TABLE);
		onCreate(db);		
	}
}
