package models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class UnlockDates implements Parcelable {

	private static final String TAG = "Unlock Dates object";
	private long unlockDateId;
	private String unlockDate;
	private String unlockTime;
		
	public long getId()
	{
		return unlockDateId;
	}
		
	public void setId (long dateId) 
	{
		this.unlockDateId = dateId;
	}
		
	public String getUnlockDate() 
	{
		return unlockDate;
	}

	public String getUnlockTime()
	{
		return unlockTime;
	}
	
	public void setUnlockDate (String unlockDate) 
	{
		this.unlockDate = unlockDate;
	}

	public void setUnlockTime (String unlockTime)
	{
		this.unlockTime = unlockTime;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder(32);
		sb.append(unlockDate);
		sb.append("\n \t\t     ");
		sb.append(unlockTime);
		return sb.toString();		
	}
	
		
	public static final Parcelable.Creator<UnlockDates> CREATOR = new Parcelable.Creator<UnlockDates>() {
		
		@Override
		public UnlockDates createFromParcel(Parcel in) {
			Log.v(TAG, "creating from parcel");
			UnlockDates newDates = new UnlockDates();
			newDates.unlockDateId = in.readLong();
			newDates.unlockDate = in.readString();
			newDates.unlockTime = in.readString();
			return newDates;
		}
			
		@Override
		public UnlockDates[] newArray (int size) {
			return new UnlockDates[size];
		}
	};
		
	@Override
	public void writeToParcel(Parcel out, int flags) {
		Log.v(TAG, "writeToParcel..." + flags);
		out.writeLong(unlockDateId);
		out.writeString(unlockDate);
		out.writeString(unlockTime);
	}
		
	public UnlockDates(Parcel source)
	{
		Log.v(TAG, "Dates(Dates source): lets put back the parcel data");
		unlockDateId = source.readInt();
		unlockDate = source.readString();
		unlockTime = source.readString();
	}
		
	public UnlockDates()
	{
		Log.d(TAG, "new date");
	}
		
	//below logic is used for the purpose of serializing object through intent
   	@Override
   	public int describeContents() {
   		return 0;
   	}
}

