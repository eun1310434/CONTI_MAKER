package com.MQ.music.conti_maker_demo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LMA_music_view extends BaseAdapter{

	private LayoutInflater mInflater;
	private ArrayList<AL_music_view> arSrc;
	
	
	
	/*
	 * ������ ��ȣ�� �������� 1������ ��� 0������ ���� �����ϸ� ������ 20���� 19������ �ȴ�.
	 * �̴� ������ ���� ��ȣ���� 1�� �۰� ǥ�õȴ�.
	 */
	
	
	public LMA_music_view( Context context , ArrayList<AL_music_view> arItem) {
		
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		arSrc = arItem;
		
		
	}

	@Override
	public int getCount() {
		return arSrc.size();
	}

	@Override
	public AL_music_view getItem(int position) {
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
				
					res = R.layout.gui_music_view_r1;
					break;
			
				case 1:
				
					res = R.layout.gui_music_view_r2;
					break;
				
			}
			
			convertView = mInflater.inflate(res, parent, false);
		}
		
		
		
		switch (arSrc.get(position).type) 
		{
			case 0:
				
				TextView TV_num_1 = (TextView) convertView.findViewById(R.id.TV_page);
				TV_num_1.setText(Integer.toString(arSrc.get(position).page));
				
				ImageView IV_music_1 = (ImageView) convertView.findViewById(R.id.IV_m_img);
				String m_img_1 = Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(arSrc.get(position).m_id)+"_"+Integer.toString(arSrc.get(position).page)+".png";
				
				
				BitmapFactory.Options options_1 = new BitmapFactory.Options();
        		options_1.inSampleSize = 4;
        		Bitmap src_1 = BitmapFactory.decodeFile(m_img_1, options_1);
        		Bitmap resized_1 = Bitmap.createScaledBitmap(src_1, src_1.getWidth(),src_1.getHeight(), true);   
        		
        		IV_music_1.setImageBitmap(resized_1);
				//IV_music_1.setImageURI(Uri.fromFile(new File(m_img_1)));
				
				break;
				
			case 1:
				
				TextView TV_num_2 = (TextView) convertView.findViewById(R.id.TV_page);
				TV_num_2.setText(Integer.toString(arSrc.get(position).page));
				
				ImageView IV_music_2 = (ImageView) convertView.findViewById(R.id.IV_m_img);
				String m_img_2 = Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(arSrc.get(position).m_id)+"_"+Integer.toString(arSrc.get(position).page)+".png";

				
				BitmapFactory.Options options_2 = new BitmapFactory.Options();
        		options_2.inSampleSize = 4;
        		Bitmap src_2 = BitmapFactory.decodeFile(m_img_2, options_2);
        		Bitmap resized_2 = Bitmap.createScaledBitmap(src_2, src_2.getWidth(),src_2.getHeight(), true);   
        		
        		IV_music_2.setImageBitmap(resized_2);
				
				break;
				
	
		}

		return convertView;
	}

	
}