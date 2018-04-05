package com.MQ.music.conti_maker_demo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_setting extends BaseAdapter{
	
	private Context maincon;
	private LayoutInflater mInflater;
	private ArrayList<AL_setting> arSrc;
	
	
	
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_setting( Context context , ArrayList<AL_setting> arItem) {
		maincon = context;
		
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		arSrc = arItem;
		
	}

	@Override
	public int getCount() {
		return arSrc.size();
	}

	@Override
	public AL_setting getItem(int position) {
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
		return 3;
		
	}
	
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		final int pos=position;
		
		if (convertView == null) {
			
			int res = 0;
			
			switch (arSrc.get(position).type) 
			{
			
				case 0:
				
					res = R.layout.gui_setting_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_setting_r2;
					break;
			
				case 2:
				
					res = R.layout.gui_setting_r3;
					break;
			
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				TextView TV_c_name_1 = (TextView) convertView.findViewById(R.id.TV_menu);
				TV_c_name_1.setText(arSrc.get(position).TV_name);
				
				TextView TV_c_mdate_1 = (TextView) convertView.findViewById(R.id.TV_submenu);
				TV_c_mdate_1.setText(arSrc.get(position).TV_subname);
				break;
				
			case 1:
				
				TextView TV_c_name_2 = (TextView) convertView.findViewById(R.id.TV_menu);
				TV_c_name_2.setText(arSrc.get(position).TV_name);
				break;
				
			case 2:
				
				TextView TV_c_name_3 = (TextView) convertView.findViewById(R.id.TV_menu);
				TV_c_name_3.setText(arSrc.get(position).TV_name);
				
				break;
				
	
		}

		return convertView;
	}

	
}