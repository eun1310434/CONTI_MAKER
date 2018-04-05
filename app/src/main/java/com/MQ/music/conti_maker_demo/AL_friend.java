package com.MQ.music.conti_maker_demo;

import android.os.Parcel;
import android.os.Parcelable;

public class AL_friend implements Parcelable {

	int type;
	String name;
	String id;
	String etc;
	String registrationId;
	
	AL_friend (int type ,String name ,String id ,String etc ,String registrationId ){
		
		this.type = type;
		this.name = name;
		this.id = id;
		this.etc = etc;
		this.registrationId = registrationId;
		
	}

	public AL_friend(Parcel in) {
	       readFromParcel(in);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.writeInt(type);
		arg0.writeString(name);
		arg0.writeString(id);
		arg0.writeString(etc);
		arg0.writeString(registrationId);
	}
	
	private  void readFromParcel(Parcel in){
		type= in.readInt();
		name= in.readString();
		id= in.readString();
		etc= in.readString();
		registrationId= in.readString();
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
		public AL_friend createFromParcel(Parcel in) {
             return new AL_friend(in);
       }

       @Override
	public AL_friend[] newArray(int size) {
            return new AL_friend[size];
       }
   };



}
