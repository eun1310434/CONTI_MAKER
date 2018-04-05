package com.MQ.music.conti_maker_demo;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;

public class GUI_down_portrait extends Activity {
    /** Called when the activity is first created. */

	private Boolean DEVELOPER_MODE = true;
	

	//Intent
	private String getFrom = "";
	private int m_id = -1;
	private int page = -1;
	private String c_name = "";
	private String screen = "";
	private String ItemSet = "";
	private String sender = "";
	private ArrayList<AL_friend> ListItem;
	
	// Process Dialog
	private ProgressDialog PD_loading;
	private String BackGroundThreadSet = "";  // Finish
	
	// Thread
	private Thread T;// thread
	private Handler H;// handler
	private Boolean T_check = true;
	private int T_count = 0;
	
	

	//Network
	private Client NW_client = new Client();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	if (DEVELOPER_MODE) {
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
        setContentView(R.layout.gui_down);

        Intent_set();
		//ReadyActivity
		PD_loading = new ProgressDialog(this);
		PD_loading.setTitle("ContiMaker");
		PD_loading.setMessage("please wait....");
		PD_loading.setIndeterminate(true);
		PD_loading.setCancelable(false);
		PD_loading.show();
		
        //ReadyInfo
        T_Start();
        mainProcessing();
        
    }
    
public void Intent_set(){
		
		Intent intent = getIntent();
		getFrom = intent.getExtras().getString("getFrom");
			
		if(getFrom.equals("GUI_music")){

			screen = intent.getExtras().getString("screen");
			m_id = intent.getExtras().getInt("m_id");
			ItemSet = intent.getExtras().getString("ItemSet");
			
		}else if(getFrom.equals("GUI_conti")){
			
			c_name = intent.getExtras().getString("c_name");
			
		}else if(getFrom.equals("GUI_gcm")){
			
			sender = intent.getExtras().getString("sender");
			screen = intent.getExtras().getString("screen");
			m_id = intent.getExtras().getInt("m_id");
			page = intent.getExtras().getInt("page");
			ItemSet = intent.getExtras().getString("ItemSet");
			
		}else if(getFrom.equals("GUI_gcm_conti")){
			
			sender = intent.getExtras().getString("sender");
			screen = intent.getExtras().getString("screen");
			c_name = intent.getExtras().getString("c_name");
			ItemSet = intent.getExtras().getString("ItemSet");
			c_name = intent.getExtras().getString("c_name");
			
			
		}else if(getFrom.equals("GUI_gcm_conti_control")){
			
			sender = intent.getExtras().getString("sender");
			screen = intent.getExtras().getString("screen");
			ListItem = intent.getParcelableArrayListExtra("registrationIds");
			c_name = intent.getExtras().getString("c_name");
			ItemSet = intent.getExtras().getString("ItemSet");
			
		}else if(getFrom.equals("GUI_music_view_share_control")){
			
			screen = intent.getExtras().getString("screen");
			ListItem = intent.getParcelableArrayListExtra("registrationIds");
			m_id = intent.getExtras().getInt("m_id");
			ItemSet = intent.getExtras().getString("ItemSet");
		}
	}
	
	public int GetTotalMsCount(int _m_id){
		
		int GetTotalMsCount = 0;
		
		DB_handler DB_gui_down = DB_handler.open(this);
		Cursor C_get_music_sub = DB_gui_down.get_music_sub(_m_id);
		GetTotalMsCount = C_get_music_sub.getCount();
		C_get_music_sub.close();
		DB_gui_down.close();
		
		return GetTotalMsCount;
	}
	
	public void NWGetScore(int _m_id){
		
		if(screen.equals("Portrait")){

			File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(_m_id)+".png");
			if(f.exists() == false){
				NW_client.DownMusicScore("wc"+_m_id);
			}
		    
		}else if(screen.equals("Landscape")){
			
			File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(_m_id)+"_1.png");
			if(f.exists() == false){
				int total_ms = GetTotalMsCount(_m_id);
				for(int i = 0 ; i < total_ms ; i++ ){
					NW_client.DownMusicScore("wc"+_m_id+"_"+Integer.toString(i+1));
				}
			}
			
		}
	}
	
	public void NWGetConti(String c_name){
		
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_conti_music = D_test.get_conti_music(c_name);
		
		while(true){
			int cm_m_id = C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("cm_m_id"));
			NWGetScore(cm_m_id);
			if(C_get_conti_music.moveToNext()== false){break;};
		}
		C_get_conti_music.close();
		D_test.close();
		
	}
	
	
    public Boolean ConnectionCheck(){
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();
			            		  
		if(isWifiConn==false && isMobileConn==false)
		{
			
			new AlertDialog.Builder(GUI_down_portrait.this)
			.setTitle("����!")
			.setMessage("���ͳ� ������ Ȯ���� �ּ���.")
			.setPositiveButton("��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) { // TODO
							finish();
						}
					}).setCancelable(false).show();
			   return false;
		}
		return true;
	}
    
	private void mainProcessing(){
     Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
     thread.start();
    }


	private Runnable doBackgroundThreadProcessing = new Runnable(){
		@Override
		public void run() {

			Log.e("GUI_down", ": �Ǻ��ٿ�");

	        
			if(getFrom.equals("GUI_music") || getFrom.equals("GUI_gcm") || getFrom.equals("GUI_music_view_share_control")){
				NWGetScore(m_id);
				
			}else if(getFrom.equals("GUI_gcm_conti") || getFrom.equals("GUI_gcm_conti_control")){
				NWGetConti(c_name);
			}
			BackGroundThreadSet = "Finish";
		}
	};
	// ==========================================================================================
	// Thread_Start
	public void T_Start() {

		T = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (T_check) {

					try {
						H.sendMessage(H.obtainMessage());
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					T_count++;

				}
			}
		});

		H = new Handler() {
			@Override
			public void handleMessage(Message msg) {

				Log.e("GUI_down", Integer.toString(T_count));
			

				if(BackGroundThreadSet.equals("Finish")){
					BackGroundThreadSet = "";
					ThreadFinishSet();
				}
			}
		};

		T.start();
	}
	
	public void ThreadFinishSet(){
		T_count = 0;
    	
		T_check = false;
		T = null;
		
		if (PD_loading.isShowing()) {
			PD_loading.dismiss();
		}

		
		if(getFrom.equals("GUI_music")){
			
			FinishFromGUI_music();
			
		}else if(getFrom.equals("GUI_gcm")){
			
			FinishFromGcm();
			
		}else if(getFrom.equals("GUI_gcm_conti")){
			
			FinishFromGcmConti();
			
		}else if(getFrom.equals("GUI_gcm_conti_control")){
			
			FinishFromGcmContiControl();
			
		}else if(getFrom.equals("GUI_music_view_share_control")){
			
			FinishFromGcmShare();
			
		}
			
		Log.e("GUI_down", "ThreadFinish");
		
	}
	
	public void FinishFromGcmShare(){
		
		if(screen.equals("Portrait")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_music_view_share_control.class);
        	intent.putParcelableArrayListExtra("registrationIds", ListItem);
			intent.putExtra("m_id", m_id);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}else if(screen.equals("Landscape")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_music_view_share_control.class);
        	intent.putParcelableArrayListExtra("registrationIds", ListItem);
			intent.putExtra("m_id", m_id);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}
		
	}
	
	public void FinishFromGcmContiControl(){
		

		
		if(screen.equals("Portrait")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_conti_view_share_control.class);
			intent.putExtra("c_name", c_name);
        	intent.putParcelableArrayListExtra("registrationIds", ListItem);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}else if(screen.equals("Landscape")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_conti_view_share_control.class);
			intent.putExtra("c_name", c_name);
        	intent.putParcelableArrayListExtra("registrationIds", ListItem);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}
		
	}
	
	
	public void FinishFromGcmConti(){

		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_conti_music = D_test.get_conti_music(c_name,0);
		int cm_m_id = C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("cm_m_id"));
		C_get_conti_music.close();
		D_test.close();
		
		if(screen.equals("Portrait")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_remote_view_portrait.class);
			intent.putExtra("sender", sender);
			intent.putExtra("getFrom", getFrom);
			intent.putExtra("m_id", cm_m_id);
			intent.putExtra("page", 1);
			intent.putExtra("ItemSet", ItemSet);
			intent.putExtra("c_name", c_name);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}else if(screen.equals("Landscape")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_remote_view_landscape.class);
			intent.putExtra("sender", sender);
			intent.putExtra("getFrom", getFrom);
			intent.putExtra("m_id", cm_m_id);
			intent.putExtra("page", 1);
			intent.putExtra("ItemSet", ItemSet);
			intent.putExtra("c_name", c_name);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
		}
	}
	
	
	public void FinishFromGcm(){
		
		if(screen.equals("Portrait")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_remote_view_portrait.class);
			intent.putExtra("sender", sender);
			intent.putExtra("getFrom", getFrom);
			intent.putExtra("m_id", m_id);
			intent.putExtra("page", page);
			intent.putExtra("ItemSet", ItemSet);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}else if(screen.equals("Landscape")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_remote_view_landscape.class);
			intent.putExtra("sender", sender);
			intent.putExtra("getFrom", getFrom);
			intent.putExtra("m_id", m_id);
			intent.putExtra("page", page);
			intent.putExtra("ItemSet", ItemSet);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
		}
		
	}
	
	
	public void FinishFromGUI_music(){
		
		if(screen.equals("Portrait")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_music_view_portrait.class);
			intent.putExtra("getFrom", getFrom);
			intent.putExtra("m_id", m_id);
			intent.putExtra("ItemSet", ItemSet);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
			
		}else if(screen.equals("Landscape")){

			Intent intent = new Intent();
			intent.setClass( GUI_down_portrait.this, GUI_music_view_landscape.class);
			intent.putExtra("getFrom", getFrom);
			intent.putExtra("m_id", m_id);
			intent.putExtra("ItemSet", ItemSet);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_down_portrait.this.startActivity(intent);
			GUI_down_portrait.this.finish();
		}
	}
	
	
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
				
			if( KeyCode == KeyEvent.KEYCODE_BACK ){
				
				return false;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
}
