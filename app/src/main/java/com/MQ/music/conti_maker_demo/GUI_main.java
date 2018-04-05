package com.MQ.music.conti_maker_demo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GUI_main extends Activity implements OnClickListener {
	/**
     *  Manual
     *  
     *  1) ����ڰ� �ڽ��� ���ϴ� �۾��� �Ҽ� �ִ� ���� ȭ���Դϴ�.
     *  
     *  Check Date
     *  2013-03-11 (��) 
     */
	private String TAG = "GUI_main";

    
	/** [1] ACTIVITY */
	private String TAG_ACTIVITY = "---- [1] ACTIVITY";

	/** [2] GUI*/
	private String TAG_GUI = "---- [2] GUI";
	
	// ImageButton
	private ImageButton IB_profile;
	private ImageButton IB_score_search;// �Ǻ��˻� ��ư.
	private ImageButton IB_conti;// ��Ƽ�� ���� �ϴ� ��ư.
	private ImageButton IB_remoteview;// ��Ƽ����Ŀ�� ������ư.
	private ImageButton IB_friends;// ��Ƽ����Ŀ�� ������ư.
	private ImageButton IB_setting;// ��Ƽ����Ŀ�� ��ü���� �����ϴ� ��ư.
	private ImageButton IB_quit;// ��Ƽ����Ŀ�� ��ü���� �����ϴ� ��ư.

	// Textview
	private TextView TV_version;
	private TextView TV_profile;

	//Relative_Layout
	private RelativeLayout RL_menu;
	
	// Animation			
	private Animation AM_menu;// �Ǻ��˻��� ���� ��ư


	/** [3] GCM*/
	private String TAG_GCM = "---- [3] GCM";
	
	/** [4] SYSTEM */
	private String TAG_SYSTEM = "---- [4] SYSTEM";
	
	//Setting
	private String s_id;
	private String s_version = "sss";

	//Intent
	private String getType =""; //�ٸ������� �°�� -> a
	private String final_history = "";
	
	// NetWork
	private Client NW_client = new Client();
	
	// ADM Check
	private Boolean DEVELOPER_MODE = true;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (DEVELOPER_MODE) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork() 
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
					.build());
		}
		super.onCreate(savedInstanceState);
        
        //[1] ACTIVITY
		setContentView(R.layout.gui_main);
		overridePendingTransition(0, 0);
        
        //[2] GUI
        
        //[3] GCM 
		TV_set();
		IB_set();
		AM_set();
		RL_set();
        
        //[4] SYSTEM 
   	 	Intent_set();
	    DB_set();
		
	} 
	//---------------------------------------------------------------------------------------------------------------------------------
	//[1] ACTIVITY
	

	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[2] GUI 

	
	public void TV_set() {
		TV_version = (TextView) findViewById(R.id.TV_version);
		
		TV_profile = (TextView) findViewById(R.id.TV_profile);
	}
	
	public void IB_set() {

		IB_conti = (ImageButton) findViewById(R.id.IB_conti);
		IB_score_search = (ImageButton) findViewById(R.id.IB_score_search);
		IB_remoteview = (ImageButton) findViewById(R.id.IB_remoteview);
		IB_friends = (ImageButton) findViewById(R.id.IB_friends);
		IB_setting = (ImageButton) findViewById(R.id.IB_setting);
		IB_quit = (ImageButton) findViewById(R.id.IB_quit);
		IB_profile =(ImageButton) findViewById(R.id.IB_profile);

		
		IB_conti.setOnClickListener(this);
		IB_score_search.setOnClickListener(this);
		IB_friends.setOnClickListener(this);
		IB_remoteview.setOnClickListener(this);
		IB_setting.setOnClickListener(this);
		IB_quit.setOnClickListener(this);
		IB_profile.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.IB_profile) {

			
			Intent intent = new Intent();
			intent.setClass( GUI_main.this, GUI_setting_user.class);
			intent.putExtra("getFrom", "GUI_main");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_main.this.startActivity(intent);	
			GUI_main.this.finish();

		} else if (v.getId() == R.id.IB_score_search) {

			//WriteHistory("- �Ǻ�");
			Intent intent = new Intent();
			intent.setClass(GUI_main.this, GUI_music.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_main.this.startActivity(intent);
			GUI_main.this.finish();

		} else if (v.getId() == R.id.IB_conti) {

			WriteHistory("- ��Ƽ");
			Intent intent = new Intent();
			intent.setClass(GUI_main.this, GUI_conti.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_main.this.startActivity(intent);
			GUI_main.this.finish();


		} else if (v.getId() == R.id.IB_remoteview) {

			WriteHistory("- ���� ����");
			Intent intent = new Intent();
			intent.setClass(GUI_main.this, GUI_remote_view.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_main.this.startActivity(intent);
			GUI_main.this.finish();

		} else if (v.getId() == R.id.IB_friends) {

			WriteHistory("- ģ�� ����");
			Intent intent = new Intent();
			intent.setClass(GUI_main.this, GUI_friend.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        GUI_main.this.startActivity(intent);
	        GUI_main.this.finish();

		} else if (v.getId() == R.id.IB_setting) {

			WriteHistory("- ����");
			Intent intent = new Intent();
			intent.setClass(GUI_main.this, GUI_setting.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_main.this.startActivity(intent);
			GUI_main.this.finish();
			
		} else if (v.getId() == R.id.IB_quit) {

			
			try {

				DeleteDir(Environment.getExternalStorageDirectory()+"/contimaker/score"); 
					
			} catch (Exception e) {
				// TODO: handle exception
			}

			DB_handler DB_s_id = DB_handler.open(this);
			Cursor C_get_s_id = DB_s_id.get_System();
			String s_id= C_get_s_id.getString(C_get_s_id.getColumnIndex("s_id"));
			C_get_s_id.close();
			DB_s_id.close();
			
			final_history = "----------------------------------------------------------\n"
					+ "[ "
					+ s_version
					+ " ]\n"
					+ "- id ID: "
					+ s_id
					+ "\n"
					+ "- ���� ���ӳ�¥: "
					+ getToday()
					+ "\n"
					+ "- ���� ���ӳ�¥: "
					+ ReadHistory() + "\n";


			mainProcessing();
			FinishHistory();
			finish();
			
		}

	}
    
    void DeleteDir(String path) 
    { 
        File file = new File(path);
        File[] childFileList = file.listFiles();
        for(File childFile : childFileList)
        {
            if(childFile.isDirectory()) {
                DeleteDir(childFile.getAbsolutePath());     //���� ���丮 ���� 
            }
            else {
                childFile.delete();    //���� ���ϻ���
            }
        }      
        file.delete();    //root ���� 
    }
	
	public void RL_set() {
		RL_menu = (RelativeLayout) findViewById(R.id.RL_menu);
		RL_menu.setVisibility(View.VISIBLE);
	}
	
	public void AM_set() {
		AM_menu = new TranslateAnimation(200 , 0,0, 0);// �������� Device ID�� Ȯ���Ѵ��� ���v�� ���ϼ� �ְ� TI�� �̵���ŵ�ϴ�.
		AM_menu.setDuration(300);
		AM_menu.setInterpolator(new DecelerateInterpolator());
	}
	private void mainProcessing(){
     Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
     thread.start();
    }

	private Runnable doBackgroundThreadProcessing = new Runnable(){
		@Override
		public void run() {

			NW_client.ClientAccessHistory(final_history);// ������� ���� ������ �����Ѵ�.
		}
	};

	//---------------------------------------------------------------------------------------------------------------------------------
	//[4] SYSTEM 
	public void Intent_set(){
    	Intent intent = getIntent();
    	getType = intent.getExtras().getString("getType");
    	s_version = intent.getExtras().getString("s_version");
    	
		TV_version.setText(s_version);
		
		if(getType.equals("a") == true){	
			
		}if(getType.equals("b") == true){
			RL_menu.setAnimation(AM_menu);
		}
    }
	
	
	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor c_get_id = D_test.get_System();
		s_id = "Gen 1.0";//c_get_id.getString(c_get_id.getColumnIndex("s_id"));
		c_get_id.close();
		D_test.close();
		
		TV_profile.setText(s_id);
		 
		
        String filePath = Environment.getExternalStorageDirectory()+"/contimaker/ready-"+s_id+".png";
		File f1 = new File(filePath);
        if(f1.exists() == false){
			IB_profile.setImageResource(R.drawable.b_profile);
        }else{
        	try {

            	BitmapFactory.Options options = new BitmapFactory.Options();
        		options.inSampleSize = 2;
        		Bitmap src = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/contimaker/ready-"+s_id+".png", options);
        		Bitmap resized = Bitmap.createScaledBitmap(src, 100, 100, true); 
    	        IB_profile.setImageBitmap(resized);
	    	        
			} catch (Exception e) {
				// TODO: handle exception
				IB_profile.setImageResource(R.drawable.b_profile);
			}
        }
	}
	public void WriteHistory(String _history) {
			
			DB_handler D_history = DB_handler.open(this);
	
			// ������ ��� ������ ������ �´�.
			Cursor C_get_history = D_history.get_System();
			String s_id= C_get_history.getString(C_get_history.getColumnIndex("s_id"));
			String before_history = C_get_history.getString(C_get_history.getColumnIndex("s_history"));
			C_get_history.close();
	
			// ������ ��� ������ �Բ� �����Ѵ�.
			D_history.Update_System_history(s_id, before_history + "\n" + _history);
			D_history.close();

	}

	public String ReadHistory() {

		String s_history = "";
		
			DB_handler D_history = DB_handler.open(this);
	
			// ������ ��� ������ ������ �´�.
			Cursor C_get_history = D_history.get_System();
			s_history = C_get_history.getString(C_get_history
					.getColumnIndex("s_history"));
			C_get_history.close();
	
			// ������ ��� ������ �Բ� �����Ѵ�.
			D_history.close();
			
		return s_history;
	}

	public void FinishHistory() {

			DB_handler D_history = DB_handler.open(this);
			
			Cursor C_get_s_id = D_history.get_System();
			String s_id= C_get_s_id.getString(C_get_s_id.getColumnIndex("s_id"));
			C_get_s_id.close();
			
			D_history.Update_System_history(s_id, getToday());
			D_history.close();
			
	}

	public String getToday() {

		String out = "";
		GregorianCalendar today = new GregorianCalendar();

		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int yoil = today.get(Calendar.DAY_OF_MONTH);

		out = Integer.toString(year) + "�� " + Integer.toString(month) + "�� "
				+ Integer.toString(yoil) + "�� ";
		return out;
	}



	@Override
	public boolean onKeyDown(int KeyCode, KeyEvent event) {

		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�

			if (KeyCode == KeyEvent.KEYCODE_BACK) {

				return false;
			} 
		}

		return super.onKeyDown(KeyCode, event);

	}

}