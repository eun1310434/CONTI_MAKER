package com.MQ.music.conti_maker_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_ms_page extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<AL_ms_page> arSrc;
	
	
	
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_ms_page( Context context , ArrayList<AL_ms_page> arItem) {
		
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		arSrc = arItem;
		
		
	}

	@Override
	public int getCount() {
		return arSrc.size();
	}

	@Override
	public AL_ms_page getItem(int position) {
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
		return 2;
		
	}
	
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		
		if (convertView == null) {
			
			int res = 0;
			
			switch (arSrc.get(position).type) 
			{
			
				case 0:
				
					res = R.layout.gui_ms_page_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_ms_page_r2;
					break;
			
				
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				TextView TV_ms_name = (TextView) convertView.findViewById(R.id.TV_ms_name);
				TV_ms_name.setText(arSrc.get(position).ms_name);
				
				break;
				
			case 1:
				
				TextView TV_ms_name_2 = (TextView) convertView.findViewById(R.id.TV_ms_name);
				TV_ms_name_2.setText(arSrc.get(position).ms_name);
				
				break;
		}

		return convertView;
	}

	
}