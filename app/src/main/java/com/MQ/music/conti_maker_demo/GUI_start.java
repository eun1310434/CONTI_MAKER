package com.MQ.music.conti_maker_demo;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gcm.GCMRegistrar;

public class GUI_start extends Activity implements OnClickListener {
	/**
     *  Manual
     *  
     *  [Noti] -> ���ϸ��� �ý��۵�� �����Ǿ��ִ��� Ȯ���Ѵ��� ������ 1�� ������ 2���� �����մϴ�.
     *  
     *  1) ������Ʈ�� �Ȱ��
     *  	- registrationId�� �޶������ �������� ������ �մϴ�.
     *  	- ���� �ִ��� Ȯ���� �մϴ�.
     *  2) ����ڰ� ó�� ������ ��ġ�ϴ°��
     *  	- GUI_setting_help1�� ����Ʈ
     *  
     *  Check Date
     *  2013-03-11 (��) 
     */

	private String TAG = "GUI_start";

    
	/** [1] ACTIVITY */
	private String TAG_ACTIVITY = "---- [1] ACTIVITY";

	/** [2] GUI*/
	private String TAG_GUI = "---- [2] GUI";


	/** [3] GCM*/
	private String TAG_GCM = "---- [3] GCM";
	
	//GCM
	private String SENDER_ID = "1095851346823";
	
	/** [4] SYSTEM */
	private String TAG_SYSTEM = "---- [4] SYSTEM";
	
	//Setting
	private String s_version = "Gen 1.0";//�̺κ��� ������Ʈ�� �����Ϸ� �Ҷ� �ſ��߿��ϴ�~!!!!!
	
	private boolean AD_check = false;
	private String AD_date = "";
	private String AD_title = "";
	private String AD_context = "";
	private String AD_button = "";
	
	private Boolean DEVELOPER_MODE = true;

	// Thread
	private Thread T;// thread
	private Handler H;// handler
	private boolean T_check = true;
	private int T_count = 0;
	
	// NetWork
	private Client NW_client = new Client();
	private String BackGroundThreadSet = "";  // Finish


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
		setContentView(R.layout.gui_start);
        
        //[2] GUI
        
        //[3] GCM 
        
        //[4] SYSTEM 
		mainProcessing();
		T_Start();
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	//[1] ACTIVITY
	@Override
    protected void onPause(){
    	super.onPause();
	    //������ �ߴܵǴ� ���. ���� ��� ī�z�̳� �˶� ��ȭ�� ���°�� Thread�� �����մϴ�.
    	
		T_check = false;
		T = null;
		//Log.e(TAG+TAG_ACTIVITY, "onPause");
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	 //������ �ߴܵǴ� ��쿡�� �ٽ� ������ ���. ���� ��� ī�z�̳� �˶� ��ȭ�� ���°�� Thread�� ������մϴ�.
		
		T_check = true;
		T_Start();
		//Log.e(TAG+TAG_ACTIVITY, "onResume");
    }
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[2] GUI 



	//---------------------------------------------------------------------------------------------------------------------------------
	//[3] GCM 
	
	public String RegId_set(){
		//RegistrationId�� Ȯ���մϴ�.

		//GCMRegistrar.checkDevice(this);
		//GCMRegistrar.checkManifest(this);

		//GCM ��Ͽ���
		String regId = "asdfasdf"; //GCMRegistrar.getRegistrationId(this); //- 180406 GCM 오류로 아무글씨 넣음
	    
      	//��ϵ� ID�� ������ ID���� ���ɴϴ�
      	if (regId.equals("") || regId == null) {
      		GCMRegistrar.register(this, SENDER_ID);
      		return "N";
      	}else{
      		//Log.e(TAG+TAG_GCM, "- RegId_set -\n"+"1) Already Registered: " + regId);
      		Log.e("Registered", "Already Registered : " + regId);
      		return regId;
      	}
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	//[4] SYSTEM 
	private void mainProcessing(){
     Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
     thread.start();
    }

	private Runnable doBackgroundThreadProcessing = new Runnable(){
		@Override
		public void run() {

			if(System_Check()){
				Sever_Check();	
			}
			BackGroundThreadSet ="Finish";
		}
	};
	
	public boolean System_Check(){
		boolean out = false;
		
		// DB�� ������ �ȵɰ�� �����մϴ�. -> ���ʽ����
		DB_handler DB_system = DB_handler.open(this);
		Cursor C_get_system = DB_system.get_System();
		
		if (C_get_system.getCount() == 0) {
			out = false;
			
		} else if(C_get_system.getString(C_get_system.getColumnIndex("s_version")).equals(s_version) == false){
			out = false;
			
		} else {
			//ó�� ����ڰ� �ƴѰ��
			out = true;
		}
		C_get_system.close();
		DB_system.close();
		
		return out;
	}

	public void Sever_Check() {
		
		//�̹� �ý����� �����̵� ���
		DB_handler DB = DB_handler.open(this);
		

		//registraionId�� �޶������ �������� �����ϱ�.
		
		//�ڽ��� registrationId�޾ƿ���
		String registrationId ="";
		while(true){
			registrationId = RegId_set();
			if(registrationId.equals("N") == false){break;};
		}
		
		//��� ����� �ڽ��� ���� ������ ����
		Cursor C_get_system_registration = DB.get_System();
		String My_s_id = C_get_system_registration.getString(C_get_system_registration.getColumnIndex("s_id"));
		String My_s_registraionId = C_get_system_registration.getString(C_get_system_registration.getColumnIndex("s_registrationId"));
		String My_s_name = C_get_system_registration.getString(C_get_system_registration.getColumnIndex("s_name"));
		String My_s_etc = C_get_system_registration.getString(C_get_system_registration.getColumnIndex("s_etc"));
		C_get_system_registration.close();

		//���� ����
		if(My_s_registraionId.equals(registrationId) == false){
			My_s_registraionId = registrationId;
			DB.Update_System_registrationId(My_s_id, My_s_registraionId);
			NW_client.SendMyId(My_s_id, My_s_registraionId+"$"+My_s_id+"$"+My_s_name+"$"+My_s_etc);
		}
    	//Log.e(TAG+TAG_SYSTEM,"- RegistrationIdCheck - \n");

		
		//���� Ȯ��
		if (NW_client.Advertise()) { // 1) ���� �ִ��� üũ

			// DB�� ������ �� ���� ��� ��¥ Ȯ��
			Cursor C_get_ad = DB.get_System();
			String s_ad = C_get_ad.getString(C_get_ad.getColumnIndex("s_ad"));
			String s_id = C_get_ad.getString(C_get_ad.getColumnIndex("s_id"));
			C_get_ad.close();

			AD_title = NW_client.Ad_title;
			AD_context = NW_client.Ad_context;
			AD_button = NW_client.Ad_button;
			AD_date =  NW_client.Ad_date;

			// ������ �� ���� ��� ��¥�� �������� ������ ����
			if (AD_date.equals(s_ad) == false) {
				DB.Update_System_ad(s_id, NW_client.Ad_date);
				AD_check = true;
			}
		}
    	//Log.e(TAG+TAG_SYSTEM,"- ADCheck - \n");
		
		DB.close();
		
	}
	
	public void T_Start() {

		T = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (T_check) {

					try {
						H.sendMessage(H.obtainMessage());
						Thread.sleep(100);
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
				
				//Log.e("GUI_start", Integer.toString(T_count));
				if (BackGroundThreadSet.equals("Finish")) {
					BackGroundThreadSet = "";
					ThreadFinishSet();
				}

			}
		};

		T.start();
	}
	
	public void ThreadFinishSet(){

		
		T_check = false;
		T = null;

		if(NetworkCheck() == false){
			
			
			
		}else if(System_Check() == false){ //180405 - GCM Error로 일단 주석처리
			
			Intent intent = new Intent();
			intent.setClass(GUI_start.this, GUI_setting_help_1.class);
			intent.putExtra("s_version", s_version);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_start.this.startActivity(intent);
			GUI_start.this.finish();
			
		}else if(AD_check == true){
			
				new AlertDialog.Builder(GUI_start.this)
				.setTitle(AD_title)
				.setMessage(AD_context)
				.setPositiveButton(AD_button,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) { // TODO
								Intent intent = new Intent();
								intent.setClass(GUI_start.this, GUI_main.class);
								intent.putExtra("getType","b");
								intent.putExtra("s_version",s_version);
						        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
								GUI_start.this.startActivity(intent);
								GUI_start.this.finish();
								
							}
						})
				.setCancelable(false).show();
				
		}else{
			
			Intent intent = new Intent();
			intent.setClass(GUI_start.this, GUI_main.class);
			intent.putExtra("getType","b");
			intent.putExtra("s_version",s_version);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_start.this.startActivity(intent);
			GUI_start.this.finish();
			
		}
	}

	
	public Boolean NetworkCheck(){
		//�������� Ȱ��ȭ �Ǿ� �ִ��� Ȯ���մϴ�.
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();

			            		  
		if(isWifiConn==false && isMobileConn==false)
		{
			
			new AlertDialog.Builder(GUI_start.this)
			.setTitle("����!")
			.setMessage("���ͳ� ������ Ȯ���� �ּ���.")
			.setPositiveButton("��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) { // TODO
							finish();
						}
					}).setCancelable(false).show();
	    		//Log.e(TAG+TAG_SYSTEM,"- NetworkCheck - \n"+"1) check: false");
			   return false;
		}
    	//Log.e(TAG+TAG_SYSTEM,"- NetworkCheck - \n"+"check: true");
		return true;
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}