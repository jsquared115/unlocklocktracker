package myLastUsage;

import java.util.List;

import models.LockDates;
import models.UnlockDates;

import com.example.mylastusage.R;

import database.LocksDataSource;
import database.UnlocksDataSource;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainScreen extends ListActivity {
	
	private LocksDataSource locksDataSource;
	private UnlocksDataSource unlocksDataSource;
	private List<UnlockDates> dates;
	private List<LockDates> dateslocks;
	private ListView locksList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		locksDataSource = new LocksDataSource(this);
		locksDataSource.open();
		
		unlocksDataSource = new UnlocksDataSource(this);
		unlocksDataSource.open();
		
		dates = unlocksDataSource.getThreeUnlockDates();
		dateslocks = locksDataSource.getThreeLockDates();

		
		displayRecentUnlock();
		displayRecentLock();
		setupUnlockListView();
		setupLockListView();
	}

	public void startTracking(View view) {
		 startService(new Intent(getBaseContext(), ScreenService.class));
	
	}
	
	public void stopTracking(View view) {
		 stopService(new Intent(getBaseContext(), ScreenService.class));
	}
	
	public void refresh(View view) {
		displayRecentUnlock();
		displayRecentLock();
		dateslocks.clear();
		dateslocks = locksDataSource.getThreeLockDates();
		dates.clear();
		dates = unlocksDataSource.getThreeUnlockDates();
		setupUnlockListView();
		setupLockListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	
	private void displayRecentUnlock() {
		
		Cursor cursor = unlocksDataSource.getRow();
		if (cursor != null) {
			DisplayUnlockDate(cursor);
		}
		else
			Toast.makeText(this, "No date found", Toast.LENGTH_SHORT).show();
	}
	
	private void displayRecentLock() {
		
		Cursor cursor = locksDataSource.getRow();
		if (cursor != null) {
			DisplayLockDate(cursor);
		}
		else
			Toast.makeText(this, "No date found", Toast.LENGTH_SHORT).show();
	}
	
	public void DisplayUnlockDate (Cursor cursor)
	{
		TextView text=(TextView)findViewById(R.id.textViewDate);
		text.setText("MOST RECENT UNLOCK: \n"
				+ "Date: " + cursor.getString(1) + "\n"
				+ "Time: " + cursor.getString(2));
	}
	
	public void DisplayLockDate (Cursor cursor)
	{
		TextView text=(TextView)findViewById(R.id.textViewDate1);
		text.setText("MOST RECENT LOCK: \n"
				+ "Date: " + cursor.getString(1) + "\n"
				+ "Time: " + cursor.getString(2));
	}
	
	public void setupUnlockListView() {
		
		getListView();
		//playerListView.setChoiceMode(playerListView.CHOICE_MODE_NONE);
		ArrayAdapter<UnlockDates> adapter = new ArrayAdapter<UnlockDates>(this, R.layout.mylist, dates);
		setListAdapter(adapter);
	}
	
	public void setupLockListView() {
		locksList = (ListView) findViewById (R.id.list1);
		locksList.setAdapter(new ArrayAdapter<LockDates> (this, R.layout.mylist, dateslocks));
	}
}
