package com.MQ.music.conti_maker_demo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_conti extends BaseAdapter{
	
	private Context maincon;
	private LayoutInflater mInflater;
	private ArrayList<AL_conti> arSrc;
	
	private String[] check ;
	
	
	
	
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_conti( Context context , ArrayList<AL_conti> arItem) {
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

	@Override
	public int getCount() {
		return arSrc.size();
	}

	@Override
	public AL_conti getItem(int position) {
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
				
					res = R.layout.gui_conti_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_conti_r2;
					break;
			
				case 2:
				
					res = R.layout.gui_conti_r3;
					break;
			
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				TextView TV_c_name_1 = (TextView) convertView.findViewById(R.id.c_name);
				TV_c_name_1.setText(arSrc.get(position).c_name);
				
				TextView TV_c_mdate_1 = (TextView) convertView.findViewById(R.id.c_mdate);
				TV_c_mdate_1.setText(arSrc.get(position).c_mdate);

				TextView TV_c_writer_1 = (TextView) convertView.findViewById(R.id.c_writer);
				TV_c_writer_1.setText(arSrc.get(position).c_writer);

				TextView TV_c_subject_1 = (TextView) convertView.findViewById(R.id.c_subject);
				TV_c_subject_1.setText(arSrc.get(position).c_subject);

				ImageView IV_c_new_1 = (ImageView) convertView.findViewById(R.id.c_new);
				if(arSrc.get(position).c_new.equals("Y")){
					IV_c_new_1.setVisibility(View.VISIBLE);
				}else{
					IV_c_new_1.setVisibility(View.INVISIBLE);
				}
				
				
				
				break;
			case 1:
				
				break;
			
			
			case 2:
				
				TextView TV_c_name_3 = (TextView) convertView.findViewById(R.id.c_name);
				TV_c_name_3.setText(arSrc.get(position).c_name);
				
				TextView TV_c_mdate_3 = (TextView) convertView.findViewById(R.id.c_mdate);
				TV_c_mdate_3.setText(arSrc.get(position).c_mdate);

				TextView TV_c_writer_3 = (TextView) convertView.findViewById(R.id.c_writer);
				TV_c_writer_3.setText(arSrc.get(position).c_writer);

				TextView TV_c_subject_3 = (TextView) convertView.findViewById(R.id.c_subject);
				TV_c_subject_3.setText(arSrc.get(position).c_subject);

				ImageView IV_c_new_3 = (ImageView) convertView.findViewById(R.id.c_new);
				if(arSrc.get(position).c_new.equals("Y")){
					IV_c_new_3.setVisibility(View.VISIBLE);
				}else{
					IV_c_new_3.setVisibility(View.INVISIBLE);
				}
				
				
				final CheckBox CB_check = (CheckBox) convertView.findViewById(R.id.CB_check);
				
				if(check[pos].equals("")){
					CB_check.setChecked(false);
				}else{
					CB_check.setChecked(true);
				}
				
				CB_check.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(CB_check.isChecked() == false)
						{
							check[pos] = "";
						}
						else if(CB_check.isChecked() == true)
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