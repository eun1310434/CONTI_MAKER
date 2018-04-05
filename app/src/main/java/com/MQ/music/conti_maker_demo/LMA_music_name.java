package com.MQ.music.conti_maker_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_music_name extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<AL_music> arSrc;
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_music_name( Context context , ArrayList<AL_music> arItem) {
		
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		arSrc = arItem;
		
		
	}

	@Override
	public int getCount() {
		return arSrc.size();
	}

	@Override
	public AL_music getItem(int position) {
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
	
		
		if (convertView == null) {
			
			int res = 0;
			
			switch (arSrc.get(position).type) 
			{
			
				case 0:
				
					res = R.layout.gui_music_name_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_music_name_r2;
					break;
			
				case 2:
				
					res = R.layout.gui_music_name_r3;
					break;
				
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				break;
				
			case 1:
				
				TextView TV_name = (TextView) convertView.findViewById(R.id.name);
				TV_name.setText(arSrc.get(position).m_name);
				
				TextView TV_code = (TextView) convertView.findViewById(R.id.code);
				TV_code.setText(arSrc.get(position).m_code);
				
				break;
				
			case 2:
				
				TextView TV_name_2 = (TextView) convertView.findViewById(R.id.name);
				TV_name_2.setText(arSrc.get(position).m_name);
				
				TextView TV_code_2 = (TextView) convertView.findViewById(R.id.code);
				TV_code_2.setText(arSrc.get(position).m_code);
				
				break;
				
	
		}

		return convertView;
	}

	
}