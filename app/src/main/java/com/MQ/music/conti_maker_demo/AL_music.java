package com.MQ.music.conti_maker_demo;

import android.os.Parcel;
import android.os.Parcelable;

public class AL_music implements Parcelable {
	
	int type;
	int m_id;
	String m_name;
	String m_code;
	int m_img;
	String title;
	
	AL_music (int type ,int m_id	, String m_name , String m_code , int m_img, String title){
		
		this.type = type;
		this.m_id = m_id;
		this.m_name = m_name;
		this.m_code = m_code;
		this.m_img = m_img;
		this.title = title;
		
	}

	public AL_music(Parcel in) {
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
		arg0.writeInt(m_id);
		arg0.writeString(m_name);
		arg0.writeString(m_code);
		arg0.writeInt(m_img);
		arg0.writeString(title);
	}
	
	private  void readFromParcel(Parcel in){
		type= in.readInt();
		m_id= in.readInt();
		m_name= in.readString();
		m_code= in.readString();
		m_img= in.readInt();
		title= in.readString();
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
		public AL_music createFromParcel(Parcel in) {
             return new AL_music(in);
       }

       @Override
	public AL_music[] newArray(int size) {
            return new AL_music[size];
       }
   };



}
