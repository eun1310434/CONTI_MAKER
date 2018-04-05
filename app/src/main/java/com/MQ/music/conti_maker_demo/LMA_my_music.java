package com.MQ.music.conti_maker_demo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_my_music extends BaseAdapter{
	
	private Context maincon;
	private LayoutInflater mInflater;
	private ArrayList<AL_music> arSrc;
	
	
	
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_my_music( Context context , ArrayList<AL_music> arItem) {
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
		return 4;
		
	}
	
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		final int pos=position;
		
		if (convertView == null) {
			
			int res = 0;
			
			switch (arSrc.get(position).type) 
			{
			
				case 0:
				
					res = R.layout.gui_my_music_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_my_music_r2;
					break;
			
				case 2:
				
					res = R.layout.gui_my_music_r3;
					break;
			
				case 3:
				
					res = R.layout.gui_my_music_r4;
					break;
			
				
				
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:

				TextView TV_m_num_0 = (TextView) convertView.findViewById(R.id.m_num);
				TV_m_num_0.setText(String.valueOf(position+1)+")");
				
				TextView TV_m_name_0 = (TextView) convertView.findViewById(R.id.m_name);
				TV_m_name_0.setText(arSrc.get(position).m_name);
				
				TextView TV_m_code_0 = (TextView) convertView.findViewById(R.id.m_code);
				TV_m_code_0.setText(arSrc.get(position).m_code);
				
				
				break;

			case 1:

				TextView TV_m_num_1 = (TextView) convertView.findViewById(R.id.m_num);
				TV_m_num_1.setText(String.valueOf(position+1)+")");
				
				TextView TV_m_name_1 = (TextView) convertView.findViewById(R.id.m_name);
				TV_m_name_1.setText(arSrc.get(position).m_name);
				
				TextView TV_m_code_1 = (TextView) convertView.findViewById(R.id.m_code);
				TV_m_code_1.setText(arSrc.get(position).m_code);
				
				
				break;

			case 2:

				TextView TV_m_num_2 = (TextView) convertView.findViewById(R.id.m_num);
				TV_m_num_2.setText(String.valueOf(position+1)+")");
				
				TextView TV_m_name_2 = (TextView) convertView.findViewById(R.id.m_name);
				TV_m_name_2.setText(arSrc.get(position).m_name);
				
				TextView TV_m_code_2 = (TextView) convertView.findViewById(R.id.m_code);
				TV_m_code_2.setText(arSrc.get(position).m_code);
				
				
				break;

			case 3:

				TextView TV_m_num_3 = (TextView) convertView.findViewById(R.id.m_num);
				TV_m_num_3.setText(String.valueOf(position+1)+")");
				
				TextView TV_m_name_3 = (TextView) convertView.findViewById(R.id.m_name);
				TV_m_name_3.setText(arSrc.get(position).m_name);
				
				TextView TV_m_code_3 = (TextView) convertView.findViewById(R.id.m_code);
				TV_m_code_3.setText(arSrc.get(position).m_code);
				
				
				break;
			
	
		}

		return convertView;
	}

	
}