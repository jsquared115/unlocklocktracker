package database;

import java.util.ArrayList;
import java.util.List;

import models.UnlockDates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UnlocksDataSource {
	
	//Database fields
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.UNLOCKS_ID,
									SQLiteHelper.UNLOCKS_DATE,
									SQLiteHelper.UNLOCKS_TIME};
		
	public UnlocksDataSource(Context context)
	{
		dbHelper = new SQLiteHelper(context);
	}
		
	public void open() throws SQLException 
	{
		database = dbHelper.getWritableDatabase();
	}
		
	public void close() 
	{
		dbHelper.close();
	}
		
	public long insertUnlockDate (String date, String time) 
	{
		ContentValues initialValues = new ContentValues();

		initialValues.put(SQLiteHelper.UNLOCKS_DATE, date);
		initialValues.put(SQLiteHelper.UNLOCKS_TIME, time);
		
		return database.insert(SQLiteHelper.UNLOCKS_TABLE, null, initialValues);
	}
	
	public Cursor getRow() throws SQLException
	{
		Cursor cursor = database.query(true, SQLiteHelper.UNLOCKS_TABLE, new String[] {SQLiteHelper.UNLOCKS_ID, SQLiteHelper.UNLOCKS_DATE, SQLiteHelper.UNLOCKS_TIME}, SQLiteHelper.UNLOCKS_ID, null, null, null, null , null, null);
		
		cursor.moveToLast();
	
		return cursor;
	}
	
	public Cursor getAllDates()
	{
		return database.query(SQLiteHelper.UNLOCKS_TABLE, new String[] {SQLiteHelper.UNLOCKS_ID, SQLiteHelper.UNLOCKS_DATE, SQLiteHelper.UNLOCKS_TIME}, null, null, null, null, null);
	}
		
	public boolean deleteUnlockDate (long rowId)
	{
		return database.delete(SQLiteHelper.UNLOCKS_TABLE, SQLiteHelper.UNLOCKS_ID + " = " + rowId, null) > 0;
	}
		
	public List<UnlockDates> getThreeUnlockDates() {
		List<UnlockDates> dates = new ArrayList<UnlockDates>();
			
		Cursor cursor = database.query(SQLiteHelper.UNLOCKS_TABLE, allColumns, null, null, null, null, null);
			
		cursor.moveToLast();
			
		
		for (int i = 0; i < 3; i++) 
		{
			UnlockDates date = cursorToDates(cursor);
			dates.add(date);
			cursor.moveToPrevious();
		}
			
			cursor.close();
			return dates;
		}
	
		
	private UnlockDates cursorToDates(Cursor cursor)
		{
			UnlockDates date = new UnlockDates();
			date.setId(cursor.getLong(0));
			date.setUnlockDate(cursor.getString(1));
			date.setUnlockTime(cursor.getString(2));
			return date;
		}
	}	

