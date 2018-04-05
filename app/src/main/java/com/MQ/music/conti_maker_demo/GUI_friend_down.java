package com.MQ.music.conti_maker_demo;

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
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;

public class GUI_friend_down extends Activity {
    /** Called when the activity is first created. */

	private Boolean DEVELOPER_MODE = true;
	

	//Intent
	private String getFrom = "";
	
	
	// Process Dialog
	private ProgressDialog PD_loading_down;
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
        setContentView(R.layout.gui_friend_down);

        Intent_set();
        
		//ReadyActivity
        PD_loading_down = ProgressDialog.show(GUI_friend_down.this, "ContiMaker", "please wait....",true, false);

        //ReadyInfo
        T_Start();
        mainProcessing();
        
    }
    
	public void Intent_set(){
		
		Intent intent = getIntent();
		getFrom = intent.getExtras().getString("getFrom");
			
		if(getFrom.equals("GUI_start")){
			
		}else if(getFrom.equals("GUI_main")){
			
		}
	}
	
    public Boolean ConnectionCheck(){
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();
			            		  
		if(isWifiConn==false && isMobileConn==false)
		{
			
			new AlertDialog.Builder(GUI_friend_down.this)
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

			Log.e("GUI_friend_getinfo ", ": ģ�������ٿ�");
			NWGetFriendInfo();
			BackGroundThreadSet = "Finish";
		}
	};
	
	public void NWGetFriendInfo(){
		
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_members_id = D_test.get_members_id();
		
		while(true){
			String mb_id = C_get_members_id.getString(C_get_members_id.getColumnIndex("mb_id"));
			
			NW_client.DownFriendPic(mb_id);
			String msg = NW_client.IdSearch(mb_id);
			
			String [] Info = Info_set(msg);
			
			D_test.Update_members(Info[0], Info[1], Info[2], Info[3]);//���̵� �̸� ������ ��Ÿ
			
			if(C_get_members_id.moveToNext()== false){break;};
		}
		
		C_get_members_id.close();
		D_test.close();
		
	}
	
	public String[] Info_set(String msg){

		String mb_id = "";
		String mb_registrationId = "";
		String mb_name = "";
		String mb_etc = "";
		
		int fcheck = 0;
		for(int f = 0 ; f < msg.length() ; f ++)
			
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				mb_registrationId = mb_registrationId + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				mb_id = mb_id + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				mb_name = mb_name + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			mb_etc = mb_etc + msg.charAt(f);
		}
		
		String out [] ={mb_id,mb_name,mb_registrationId,mb_etc};
		
		return out;
		
	}
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
		
		if (PD_loading_down.isShowing()) {
			PD_loading_down.dismiss();
		}
		T_count = 0;
    	
		T_check = false;
		T = null;
		
		if(getFrom.equals("GUI_start")){
			
		}else if(getFrom.equals("GUI_main")){
			
			Intent intent = new Intent();
			intent.setClass(GUI_friend_down.this, GUI_friend.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        GUI_friend_down.this.startActivity(intent);
	        GUI_friend_down.this.finish();
		}
			
		Log.e("GUI_friend_getinfo", "ThreadFinish");
		
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

