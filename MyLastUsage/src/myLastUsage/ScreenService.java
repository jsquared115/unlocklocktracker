package myLastUsage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.LockDates;
import models.UnlockDates;

import database.LocksDataSource;
import database.UnlocksDataSource;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Time;
import android.widget.Toast;

public class ScreenService extends Service {
	
	private LocksDataSource locksDataSource;
	private UnlocksDataSource unlocksDataSource;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(Intent.ACTION_USER_PRESENT)) 
			{
				showUnlockToast();
			} else if(action.equals(Intent.ACTION_SCREEN_OFF))
			{
				showLockToast();
			}
		}
	};
	
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Screen lock/unlock tracking initializing...", Toast.LENGTH_SHORT).show();
		
		final IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_USER_PRESENT);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		
		
		registerReceiver(receiver, filter);
		
		locksDataSource = new LocksDataSource(this);
		locksDataSource.open();
		
		unlocksDataSource = new UnlocksDataSource(this);
		unlocksDataSource.open();
	}
	
	@Override
	public int onStartCommand (Intent intent, int flags, int startId) {
		
		Toast.makeText(this, "Screen lock/unlock tracking has started!", Toast.LENGTH_LONG).show();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "Tracking has been disabled.", Toast.LENGTH_LONG).show();
		if (receiver != null) {
			this.unregisterReceiver(this.receiver);
		}
	}
	
	private void showUnlockToast() 
	{
		Toast.makeText(this, "Unlocked screen detected", Toast.LENGTH_LONG).show();
		getLocalDate();
		getLocalTime();
		
		unlocksDataSource.insertUnlockDate(getLocalDate(), getLocalTime());
	}
	
	private void showLockToast()
	{
		Toast.makeText(this, "Locked screen detected", Toast.LENGTH_LONG).show();
		getLocalDate();
		getLocalTime();
		
		locksDataSource.insertLockDate(getLocalDate(), getLocalTime());

	}
	
	private String getLocalDate() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		UnlockDates newUnlockDate = new UnlockDates();
		newUnlockDate.setUnlockDate(date);
		return date;
	}
	
	private String getLocalTime()
	{
		Date now = new Date();
		DateFormat shortDf = DateFormat.getTimeInstance(DateFormat.SHORT); 
		return shortDf.format(now);
	}
	
}
