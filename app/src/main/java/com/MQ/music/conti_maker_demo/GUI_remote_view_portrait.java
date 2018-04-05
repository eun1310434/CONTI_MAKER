package com.MQ.music.conti_maker_demo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class GUI_remote_view_portrait extends Activity implements  OnClickListener {
	/** Called when the activity is first created. */

	//Information
	private static int m_id;//�Ǻ��� ���̵�
	private static int page;//�Ǻ��� ���̵�
	private static String m_name = "";//�Ǻ��� ����
	private String m_words = "";//�Ǻ��� ù����
	private String m_code = "";//�Ǻ��� ù����
	private String c_name = "";
	
	public static String m_img;//���� ȭ���� ��� �Ǻ��� �̹���
	
	//Intent
	private String getFrom = ""; 
	public static String sender = "";
	//TextView
	public static TextView TV_title;
	
	//Relative Layout
	private RelativeLayout RL_items;
	private RelativeLayout RL_title;
	private String ItemSet = "off"; //on , off

	
	//ImageButton
	public static ImageButton IB_m_img;
	private ImageButton IB_screen;
	private ImageButton IB_youtube; 
	

	//Activity
	public static Activity GUI_remote_view_portrait_activity;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (true) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.gui_remote_view_portrait);
		overridePendingTransition(0, 0);

		
		GUI_remote_view_portrait_activity = GUI_remote_view_portrait.this;//����� ���
		
		
		
		Intent_set();
		NetWork_set();
		MUSIC_DB_set();
		RL_set();
		TV_set();
		IB_set();
	}
	
	public void Intent_set() {
		
			// ������ Activity ���� �Ǻ��� id�� ������ ���ϴ�.
			Intent intent = getIntent();
			sender = intent.getExtras().getString("sender");
			getFrom = intent.getExtras().getString("getFrom");
			m_id = intent.getExtras().getInt("m_id");
			page = intent.getExtras().getInt("page");
			ItemSet = intent.getExtras().getString("ItemSet");
			
			if(getFrom.equals("GUI_gcm_conti")){
				c_name = intent.getExtras().getString("c_name");
			}
			
		}

	public void NetWork_set(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	public void RL_set(){
		RL_items = (RelativeLayout) findViewById(R.id.RL_items);
		RL_title = (RelativeLayout) findViewById(R.id.RL_title);
		
		if(ItemSet.equals("on")){
			RL_items.setVisibility(View.VISIBLE);
			RL_title.setVisibility(View.VISIBLE);
		}else if(ItemSet.equals("off")){
			RL_items.setVisibility(View.INVISIBLE);
			RL_title.setVisibility(View.INVISIBLE);
			
		}
		
	}
	
	public void TV_set(){
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_title.setText("[���� ����]"+sender+"-"+m_name);
	}
	
	public static void score_set(int  _m_id , int _page , String _m_name){
		
		m_id = _m_id;
		page = _page;
		m_name = _m_name;
		TV_title.setText("[���� ����]"+sender+"-"+m_name);
		m_img = Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(m_id)+".png";
		IB_m_img.setImageURI(Uri.fromFile(new File(m_img)));

		/*
		m_id = _m_id;
		page = _page;
		m_name = _m_name;
		TV_title.setText("[���� ����]"+sender+"-"+m_name);
		
		m_img = Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(m_id)+".png";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap src = BitmapFactory.decodeFile(m_img, options);
		Bitmap resized = Bitmap.createScaledBitmap(src, src.getWidth(),src.getHeight(), true);   
		IB_m_img.setImageBitmap(resized);
		*/
		
		
	}
	
	
	public void IB_set(){
		IB_m_img =  (ImageButton) findViewById(R.id.IB_m_img);
		IB_m_img.setImageURI(Uri.fromFile(new File(m_img)));
		IB_m_img.setOnClickListener(this);
		
		IB_screen = (ImageButton) findViewById(R.id.IB_screen);
		IB_youtube = (ImageButton) findViewById(R.id.IB_youtube);
		
		IB_screen.setOnClickListener(this);
		IB_youtube.setOnClickListener(this);
		
	}
	
	public void MUSIC_DB_set(){
		
		DB_handler D_test = DB_handler.open(this);
	    
		//���� ȭ���� �̹����� ������ �ɴϴ�.
		Cursor C_get_music = D_test.get_music(m_id);
		m_name = C_get_music.getString(C_get_music.getColumnIndex("m_name"));
		m_words = C_get_music.getString(C_get_music.getColumnIndex("m_words"));
		m_code = C_get_music.getString(C_get_music.getColumnIndex("m_code"));
		m_img = Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(m_id)+".png";
		C_get_music.close();
 		D_test.close();
 		
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.IB_m_img){
			
			if(ItemSet.equals("on")){
				RL_items.setVisibility(View.INVISIBLE);
				RL_title.setVisibility(View.INVISIBLE);
				ItemSet = "off";
			}else if(ItemSet.equals("off")){
				RL_items.setVisibility(View.VISIBLE);
				RL_title.setVisibility(View.VISIBLE);
				ItemSet = "on";
			}
			
		}else if(v.getId() == R.id.IB_screen){

			
			if(getFrom.equals("GUI_gcm_conti")){
				

				Intent intent1 = new Intent(GUI_remote_view_portrait.this, GUI_down_landscape.class);
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent1.putExtra("getFrom", "GUI_gcm_conti");
				intent1.putExtra("sender",sender);
				intent1.putExtra("screen", "Landscape");
				intent1.putExtra("c_name",c_name);
				intent1.putExtra("ItemSet", "off");
				intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				GUI_remote_view_portrait.this.finish();
				GUI_remote_view_portrait.this.startActivity(intent1);
				
			}else{

			
				File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(m_id)+"_1.png");
				if(f.exists()){

					Intent intent = new Intent();
					intent.setClass( GUI_remote_view_portrait.this, GUI_remote_view_landscape.class);
					intent.putExtra("sender", sender);
					intent.putExtra("getFrom", getFrom);
					intent.putExtra("m_id", m_id);
					intent.putExtra("page", page);
					intent.putExtra("ItemSet", ItemSet);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_remote_view_portrait.this.startActivity(intent);
					GUI_remote_view_portrait.this.finish();
			    	
			    }else{
			    	
					Intent intent = new Intent();
					intent.setClass( GUI_remote_view_portrait.this, GUI_down_landscape.class);
					intent.putExtra("getFrom", "GUI_gcm");
					intent.putExtra("sender", sender);
					intent.putExtra("screen", "Landscape");
					intent.putExtra("m_id",m_id);
					intent.putExtra("page", page);
					intent.putExtra("ItemSet", ItemSet);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_remote_view_portrait.this.startActivity(intent);
					GUI_remote_view_portrait.this.finish();
					
			    }
			}
				
				
			
		}else if(v.getId() == R.id.IB_share){
		}else if(v.getId() == R.id.IB_youtube){
			
			Uri uri = Uri.parse("http://www.youtube.com/results?search_query="+m_name);
			Intent it  = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(it);
			
		}

	}
	
    
	public static void Finish(){
		
		GUI_remote_view_portrait aActivity = (GUI_remote_view_portrait) GUI_remote_view_portrait.GUI_remote_view_portrait_activity;
		
		Intent intent = new Intent();
		intent.setClass( aActivity, GUI_remote_view.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		aActivity.finish();
		aActivity.startActivity(intent);
	
	}
    
    
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
		
	}
	

		public boolean onKeyDown(int KeyCode, KeyEvent event) {

			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
				// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�

				if (KeyCode == KeyEvent.KEYCODE_BACK) {
					
					GUI_remote_view_portrait.Finish();
					
					return false;
				}

			}

			return super.onKeyDown(KeyCode, event);

		}



}