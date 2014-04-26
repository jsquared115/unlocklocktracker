package models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class LockDates implements Parcelable {
	private static final String TAG = "Locks Dates object";
	private long lockDateId;
	private String lockDate;
	private String lockTime;
	
	public long getId()
	{
		return lockDateId;
	}
	
	public void setId (long dateId) 
	{
		this.lockDateId = dateId;
	}
	
	public String getLockDate() 
	{
		return lockDate;
	}

	public String getLockTime()
	{
		return lockTime;
	}

	public void setLockDate(String lockDate) 
	{
		this.lockDate = lockDate;
	}

	public void setLockTime (String lockTime) 
	{
		this.lockTime = lockTime;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder(32);
		sb.append(lockDate);
		sb.append("\n \t\t      ");
		sb.append(lockTime);
		return sb.toString();
	}

	public static final Parcelable.Creator<LockDates> CREATOR = new Parcelable.Creator<LockDates>() {
		
		@Override
		public LockDates createFromParcel(Parcel in) {
			Log.v(TAG, "creating from parcel");
			LockDates newDates = new LockDates();
			newDates.lockDateId = in.readLong();
			newDates.lockDate = in.readString();
			newDates.lockTime = in.readString();
			return newDates;
		}
		
		@Override
		public LockDates[] newArray (int size) {
			return new LockDates[size];
		}
	};
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		Log.v(TAG, "writeToParcel..." + flags);
		out.writeLong(lockDateId);
		out.writeString(lockDate);
		out.writeString(lockTime);
	}
	
	public LockDates(Parcel source)
	{
		Log.v(TAG, "Dates(Dates source): lets put back the parcel data");
		lockDateId = source.readInt();
		lockDate = source.readString();
		lockTime = source.readString();
	}
	
	public LockDates()
	{
		Log.d(TAG, "new date");
	}
	
	//below logic is used for the purpose of serializing object through intent
   	@Override
   	public int describeContents() {
   		return 0;
   	}

}
