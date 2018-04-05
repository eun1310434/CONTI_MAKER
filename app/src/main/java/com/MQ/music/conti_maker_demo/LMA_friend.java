package com.MQ.music.conti_maker_demo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class LMA_friend extends BaseAdapter{
	
	private Context maincon;
	private LayoutInflater mInflater;
	private ArrayList<AL_friend> arSrc;
	
	private String[] check;
	private Client NW_client = new Client();
	
	public LMA_friend( Context context , ArrayList<AL_friend> arItem) {
		maincon = context;
		
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		arSrc = arItem;
		
		check = new String[arItem.size()]; 
		for(int i =0 ; i<arItem.size() ; i++)
		{
			check[i] ="";
		}
		
	}

	
	
	
	public String getCheck(int position) {
		return check[position].toString();
	}
	
	public void setCheck(int i){
		check[i] ="����";
	}

	@Override
	public int getCount() {
		return arSrc.size();
	}

	@Override
	public AL_friend getItem(int position) {
		return arSrc.get(position);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getItemViewType(int position) {
		return arSrc.get(position).type;
	}
	
	
	@Override
	public int getViewTypeCount() {
		return 4;
	}


	public String[] Info_set(String msg){

		String mb_id = "";
		String mb_registrationId = "";
		String mb_name = "";
		String mb_etc = "";
		
		int fcheck = 0;
		for(int f = 0 ; f < msg.length() ; f ++)
			
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				mb_registrationId = mb_registrationId + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				mb_id = mb_id + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				mb_name = mb_name + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			mb_etc = mb_etc + msg.charAt(f);
		}
		
		String out [] ={mb_id,mb_name,mb_registrationId,mb_etc};
		
		return out;
		
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		final int pos=position;
		
		if (convertView == null) {
			
			int res = 0;
			
			switch (arSrc.get(position).type) 
			{
			
				case 0:
				
					res = R.layout.gui_friend_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_friend_r1;
					break;
			
				case 2:
				
					res = R.layout.gui_friend_r2;
					break;
			
				case 3:
				
					res = R.layout.gui_friend_r2;
					break;
			
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				Runnable doBackgroundThreadProcessing = new Runnable(){
					@Override
					public void run() {

						DB_handler DB = DB_handler.open(maincon);
						NW_client.DownFriendPic(arSrc.get(pos).id);
						String msg = NW_client.IdSearch(arSrc.get(pos).id);
						
						String [] Info = Info_set(msg);
						DB.Update_members(Info[0], Info[1], Info[2], Info[3]);//���̵� �̸� ������ ��Ÿ
						DB.close();
						
					}
				};
			    Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
			    thread.start();
			    
			    
					
				ImageButton IB_profile = (ImageButton) convertView.findViewById(R.id.IB_profile);
		        String filePath = Environment.getExternalStorageDirectory()+"/contimaker/"+arSrc.get(position).id+".png";
				File f1 = new File(filePath);
		        if(f1.exists() == false){
		        	IB_profile.setImageResource(R.drawable.b_profile_gray);
		        }else{
			        Bitmap selectedImage_1 = BitmapFactory.decodeFile(filePath);	
			        IB_profile.setImageBitmap(selectedImage_1);
		        }
		        
				TextView TV_name = (TextView) convertView.findViewById(R.id.name);
				TV_name.setText(arSrc.get(position).name);
				
				TextView TV_position = (TextView) convertView.findViewById(R.id.position);
				TV_position.setText(arSrc.get(position).id);

				TextView TV_etc = (TextView) convertView.findViewById(R.id.etc);
				TV_etc.setText(arSrc.get(position).etc);
				
				
				break;
				
				
			case 1:
				
				Runnable doBackgroundThreadProcessing_1 = new Runnable(){
					@Override
					public void run() {

						DB_handler DB = DB_handler.open(maincon);
						NW_client.DownFriendPic(arSrc.get(pos).id);
						String msg = NW_client.IdSearch(arSrc.get(pos).id);
						
						String [] Info = Info_set(msg);
						DB.Update_members(Info[0], Info[1], Info[2], Info[3]);//���̵� �̸� ������ ��Ÿ
						DB.close();
						
						
					}
				};
			    Thread thread_1 = new Thread(null, doBackgroundThreadProcessing_1, "Background");
			    thread_1.start();

				ImageButton IB_profile_1 = (ImageButton) convertView.findViewById(R.id.IB_profile);
		        String filePath_1 = Environment.getExternalStorageDirectory()+"/contimaker/"+arSrc.get(position).id+".png";
				File f1_1 = new File(filePath_1);
		        if(f1_1.exists() == false){
		        	IB_profile_1.setImageResource(R.drawable.b_profile_gray);
		        }else{
			        Bitmap selectedImage_1 = BitmapFactory.decodeFile(filePath_1);	
			        IB_profile_1.setImageBitmap(selectedImage_1);
		        }
				
				TextView TV_name_1 = (TextView) convertView.findViewById(R.id.name);
				TV_name_1.setText(arSrc.get(position).name);
				
				TextView TV_position_1 = (TextView) convertView.findViewById(R.id.position);
				TV_position_1.setText(arSrc.get(position).id);

				TextView TV_etc_1 = (TextView) convertView.findViewById(R.id.etc);
				TV_etc_1.setText(arSrc.get(position).etc);
				
				
				break;
				
				
			case 2:
				
				Runnable doBackgroundThreadProcessing_2 = new Runnable(){
					@Override
					public void run() {

						DB_handler DB = DB_handler.open(maincon);
						NW_client.DownFriendPic(arSrc.get(pos).id);
						String msg = NW_client.IdSearch(arSrc.get(pos).id);
						
						String [] Info = Info_set(msg);
						DB.Update_members(Info[0], Info[1], Info[2], Info[3]);//���̵� �̸� ������ ��Ÿ
						DB.close();
						
						
					}
				};
			    Thread thread_2 = new Thread(null, doBackgroundThreadProcessing_2, "Background");
			    thread_2.start();

				ImageButton IB_profile_2 = (ImageButton) convertView.findViewById(R.id.IB_profile);
		        String filePath_2 = Environment.getExternalStorageDirectory()+"/contimaker/"+arSrc.get(position).id+".png";
				File f1_2 = new File(filePath_2);
		        if(f1_2.exists() == false){
		        	IB_profile_2.setImageResource(R.drawable.b_profile_gray);
		        }else{
			        Bitmap selectedImage_2 = BitmapFactory.decodeFile(filePath_2);	
			        IB_profile_2.setImageBitmap(selectedImage_2);
		        }
		        
				TextView TV_name_2 = (TextView) convertView.findViewById(R.id.name);
				TV_name_2.setText(arSrc.get(position).name);
				
				TextView TV_position_2 = (TextView) convertView.findViewById(R.id.position);
				TV_position_2.setText(arSrc.get(position).id);

				TextView TV_etc_2 = (TextView) convertView.findViewById(R.id.etc);
				TV_etc_2.setText(arSrc.get(position).etc);
				
				final CheckBox CB_check_2 = (CheckBox) convertView.findViewById(R.id.CB_check);
				
				if(check[pos].equals("")){
					CB_check_2.setChecked(false);
				}else{
					CB_check_2.setChecked(true);
				}
				
				CB_check_2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(CB_check_2.isChecked() == false)
						{
							check[pos] = "";
						}
						else if(CB_check_2.isChecked() == true)
						{
							check[pos] = "����";
						}
					}
				});
				
				break;
				
				
			case 3:
				
				Runnable doBackgroundThreadProcessing_3 = new Runnable(){
					@Override
					public void run() {

						DB_handler DB = DB_handler.open(maincon);
						NW_client.DownFriendPic(arSrc.get(pos).id);
						String msg = NW_client.IdSearch(arSrc.get(pos).id);
						
						String [] Info = Info_set(msg);
						DB.Update_members(Info[0], Info[1], Info[2], Info[3]);//���̵� �̸� ������ ��Ÿ
						DB.close();
						
						
					}
				};
			    Thread thread_3 = new Thread(null, doBackgroundThreadProcessing_3, "Background");
			    thread_3.start();

				ImageButton IB_profile_3 = (ImageButton) convertView.findViewById(R.id.IB_profile);
		        String filePath_3 = Environment.getExternalStorageDirectory()+"/contimaker/"+arSrc.get(position).id+".png";
				File f1_3 = new File(filePath_3);
		        if(f1_3.exists() == false){
		        	IB_profile_3.setImageResource(R.drawable.b_profile_gray);
		        }else{
			        Bitmap selectedImage_3 = BitmapFactory.decodeFile(filePath_3);	
			        IB_profile_3.setImageBitmap(selectedImage_3);
		        }
				
				TextView TV_name_3 = (TextView) convertView.findViewById(R.id.name);
				TV_name_3.setText(arSrc.get(position).name);
				
				TextView TV_position_3 = (TextView) convertView.findViewById(R.id.position);
				TV_position_3.setText(arSrc.get(position).id);

				TextView TV_etc_3 = (TextView) convertView.findViewById(R.id.etc);
				TV_etc_3.setText(arSrc.get(position).etc);
				
				final CheckBox CB_check_3 = (CheckBox) convertView.findViewById(R.id.CB_check);
				CB_check_3.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(CB_check_3.isChecked() == false)
						{
							check[pos] = "";
						}
						else if(CB_check_3.isChecked() == true)
						{
							check[pos] = "����";
						}
					}
				});
				
				break;
				
	
		}

		return convertView;
	}

	
}
