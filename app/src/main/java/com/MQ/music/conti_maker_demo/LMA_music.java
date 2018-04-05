package com.MQ.music.conti_maker_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_music extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<AL_music> arSrc;
	
	
	
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_music( Context context , ArrayList<AL_music> arItem) {
		
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
				
					res = R.layout.gui_music_r1_1;
					break;
			
				case 1:
				
					res = R.layout.gui_music_r1_2;
					break;
			
				case 2:
				
					res = R.layout.gui_music_r2;
					break;
				
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				TextView TV_m_name_1_1 = (TextView) convertView.findViewById(R.id.m_name);
				TV_m_name_1_1.setText(arSrc.get(position).m_name);
				
				TextView TV_m_code_1_1 = (TextView) convertView.findViewById(R.id.m_code);
				TV_m_code_1_1.setText(arSrc.get(position).m_code);
				
				break;
				
			case 1:
				
				TextView TV_m_name_1_2 = (TextView) convertView.findViewById(R.id.m_name);
				TV_m_name_1_2.setText(arSrc.get(position).m_name);
				
				TextView TV_m_code_1_2 = (TextView) convertView.findViewById(R.id.m_code);
				TV_m_code_1_2.setText(arSrc.get(position).m_code);
				
				
				break;
				
			case 2:
				
				TextView TV_title = (TextView) convertView.findViewById(R.id.title);
				TV_title.setText(arSrc.get(position).title);
				
				break;
	
		}

		return convertView;
	}

	
}