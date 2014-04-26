package database;

import java.util.ArrayList;
import java.util.List;

import models.LockDates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocksDataSource {
	
	//Database fields
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;
	private String[] allColumns = { SQLiteHelper.LOCKS_ID,
									SQLiteHelper.LOCKS_DATE,
									SQLiteHelper.LOCKS_TIME};
	
	public LocksDataSource(Context context)
	{
		dbHelper = new SQLiteHelper(context);
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}
	
	public long insertLockDate (String LockDate, String LockTime) {
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(SQLiteHelper.LOCKS_DATE, LockDate);
		initialValues.put(SQLiteHelper.LOCKS_TIME, LockTime);
		return database.insert(SQLiteHelper.LOCKS_TABLE, null, initialValues);

	}
	
	public Cursor getRow() throws SQLException
	{
		Cursor cursor = database.query(true, SQLiteHelper.LOCKS_TABLE, new String[] {SQLiteHelper.LOCKS_ID, SQLiteHelper.LOCKS_DATE, SQLiteHelper.LOCKS_TIME}, SQLiteHelper.LOCKS_ID, null, null, null, null, null, null);

		cursor.moveToLast();
	
		return cursor;
	}
	
	public boolean deleteLockDate (long rowId)
	{
		return database.delete(SQLiteHelper.LOCKS_TABLE, SQLiteHelper.LOCKS_ID + " = " + rowId, null) > 0;
	}
	
	public List<LockDates> getThreeLockDates() {
		List<LockDates> dates = new ArrayList<LockDates>();
		
		Cursor cursor = database.query(SQLiteHelper.LOCKS_TABLE, allColumns, null, null, null, null, null);
		
		cursor.moveToLast();
		
		for (int i = 0; i < 3; i++)  {
			LockDates date = cursorToDates(cursor);
			dates.add(date);
			cursor.moveToPrevious();
		}
		
		cursor.close();
		return dates;
	}
	
	private LockDates cursorToDates(Cursor cursor)
	{
		LockDates date = new LockDates();
		date.setId(cursor.getLong(0));
		date.setLockDate(cursor.getString(1));
		date.setLockTime(cursor.getString(2));
		return date;
	}
}

