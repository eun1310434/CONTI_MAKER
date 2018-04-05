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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class GUI_setting_help_2  extends Activity  implements OnClickListener {
	/**
     *  Manual
     *  1) ������Ʈ�� �Ȱ��
     *  	- ������ ������ ������ �ٸ��� ����� ������Ʈ �Ǵ°�� �����մϴ�. �̺κ��� ������ s_id�� ������ �ٽ� update�� �����մϴ�.
     *  2) ����ڰ� ó�� ������ ��ġ�ϴ°��
     *  	- ����ڰ� ó�� ������ �����ϴ°�� s_id�� �������� �޾ƿ��� ��Ȱ�� �մϴ�.
     *  
     *  Check Date
     *  2013-03-09 (��) 
     */

	private String TAG = "GUI_setting_help_2";
    
	/** [1] ACTIVITY */
	private String TAG_ACTIVITY = "---- [1] ACTIVITY";

	/** [2] GUI*/
	private String TAG_GUI = "---- [2] GUI";

	//ImageButton
	private ImageButton IB_next;

	//TextView
	private TextView TV_s_id;
	private TextView TV_my_s_id;
	private TextView TV_info_content;

	// Process Dialog
	private ProgressDialog PD_loading;


	/** [3] GCM*/
	private String TAG_GCM = "---- [3] GCM";
	
	//GCM
	private String SENDER_ID = "1095851346823";
	
	/** [4] SYSTEM */
	private String TAG_SYSTEM = "---- [4] SYSTEM";

	//Intent
	private String s_id;
	private String s_registrationId;
	private String s_name;
	private String s_etc;
	private String s_version = "";
	
	//Setting
	private Boolean DEVELOPER_MODE = true;
	
	// Thread
	private Thread T;// thread
	private Handler H;// handler
	private Boolean T_check = true;
	private int T_count = 0;

	//Network
	private Client NW_client = new Client();
	private String GetInfo = "";
	private String BackGroundThreadSet = "";  // Finish
	
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
        setContentView(R.layout.gui_setting_help_2);
        
        //[1] ACTIVITY
		overridePendingTransition(0, 0);
        
        //[2] GUI
        PD_set();
        IB_set();
        TV_set();
        
        //[3] GCM 
        RegId_set();
        
        //[4] SYSTEM 
        Intent_set();
        T_Start();
        mainProcessing();
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
	
    public void TV_set(){
    	TV_s_id = (TextView)findViewById(R.id.TV_s_id);
    	TV_info_content = (TextView)findViewById(R.id.TV_info_content);
    	TV_my_s_id  = (TextView)findViewById(R.id.TV_my_s_id);
		//Log.e(TAG+TAG_GUI,"- TV_set -");
    }
	
	public void PD_set(){
		PD_loading = new ProgressDialog(this);
		PD_loading.setTitle("ContiMaker");
		PD_loading.setMessage("please wait....");
		PD_loading.setCancelable(false);
		PD_loading.setIndeterminate(true);
		PD_loading.show();
		//Log.e(TAG+TAG_GUI, "- PD_set -");
	}
	
    public void IB_set(){
    	IB_next = (ImageButton)findViewById(R.id.IB_next);
    	IB_next.setVisibility(View.INVISIBLE);
    	IB_next.setOnClickListener(this);
		//Log.e(TAG+TAG_GUI, "- IB_set -");
    }
	@Override
	public void onClick(View v) {
		
		if ( v.getId() == R.id.IB_next && NetworkCheck()) 
		{
			//Log.e(TAG+TAG_GUI, "- IB_click -\n"+"Intent: GUI_setting_help_2");
			
			Intent intent = new Intent();
			intent.setClass(GUI_setting_help_2.this, GUI_setting_help_3.class);
	        intent.putExtra("s_registrationId", s_registrationId);
			intent.putExtra("s_id", s_id);
			intent.putExtra("s_name", s_name);
			intent.putExtra("s_etc", s_etc);
			intent.putExtra("s_version", s_version);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        GUI_setting_help_2.this.startActivity(intent);
	        GUI_setting_help_2.this.finish();
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
			
			new AlertDialog.Builder(GUI_setting_help_2.this)
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


	//---------------------------------------------------------------------------------------------------------------------------------
	//[3] GCM 
	public boolean RegId_set(){
		//RegistrationId�� Ȯ���մϴ�.

		//GCMRegistrar.checkDevice(this);
		//GCMRegistrar.checkManifest(this);

		//GCM ��Ͽ���
		String regId = "asdfasdf"; //GCMRegistrar.getRegistrationId(this); //- 180406 GCM 오류로 아무글씨 넣음
	    
      	//��ϵ� ID�� ������ ID���� ���ɴϴ�
      	if (regId.equals("") || regId == null) {
      		GCMRegistrar.register(this, SENDER_ID);
      		return false;
      	}else{
      		//Log.e(TAG+TAG_GCM, "- RegId_set -\n"+"1) Already Registered: " + regId);
      		s_registrationId = regId;
      		return true;
      	}
      	
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[4] SYSTEM 
	public void Intent_set(){
    	Intent intent = getIntent();
    	s_version = intent.getExtras().getString("s_version");
    	
    	//Log.e(TAG+TAG_SYSTEM,"- Intent_set - \n"+"1) s_version: "+s_version);
    }
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
				
				//Log.e(TAG, TAG_SYSTEM+"- T_Start -\n"+"1) Count: "+Integer.toString(T_count));
				
				if(BackGroundThreadSet.equals("Finish")){
					BackGroundThreadSet = "";
					ThreadFinishSet();
					
				}
			}
		};

		T.start();
	}
    public void ThreadFinishSet(){
    	
    	
    	// Thread ����.
		T_count = 0;
		T_check = false;
		T = null;

    	
		Info_set(GetInfo); // ���޹��� ������ ����
		
		//UI_setting
		IB_next.setVisibility(View.VISIBLE);
    	TV_s_id.setText(s_id);
    	TV_my_s_id.setText("ID: "+s_id);
    	TV_info_content.setText("���̵� �߱� �����̽��ϴ�.");
    	
    	
		if (PD_loading.isShowing()) {
			PD_loading.dismiss();
		}

		//Log.e(TAG+TAG_SYSTEM,  "- ThreadFinishSet -\n");
    }
	private void mainProcessing(){
     Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
     thread.start();
    }

	private Runnable doBackgroundThreadProcessing = new Runnable(){
		@Override
		public void run() {
			//Log.e(TAG+TAG_SYSTEM,  "- BackGroundThreadFinish -\n");
			
			if(SystemCheck()){
				//������ ����� �����ڶ�� �������� ���̵� ��߱� ���� �ʿ䰡 ���⿡ ��񿡼� �ڽ��� ������ ������ �´�.
				DB_handler DB = DB_handler.open(GUI_setting_help_2.this);
				Cursor C_get_system_registration = DB.get_System();
				String My_s_id = C_get_system_registration.getString(C_get_system_registration.getColumnIndex("s_id"));
				C_get_system_registration.close();
				DB.close();
				
				GetInfo = NW_client.IdSearch(My_s_id);//�ڽ��� ���̵� ������ ������ ���޵� ������ ������ �ɴϴ�.
			}else{
				//������ ���������� ���� ����ڴ� �������� ���̵� �߱� �޽��ϴ�.
				GetInfo = NW_client.NewRegistration(s_registrationId);
				if(GetInfo.equals("ServerCheck")){
					GetInfo = "���ͳ� ������ �ٽ� ������ �ּ���.$$$";
				}
			}
			
			BackGroundThreadSet = "Finish";
		
		}
	};
	
	public void Info_set(String msg){
		s_id = "";
		s_registrationId = "";
		s_name = "";
		s_etc = "";
		
		int fcheck = 0;
		for(int f = 0 ; f < msg.length() ; f ++)
			
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				s_registrationId = s_registrationId + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				s_id = s_id + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			if(msg.charAt(f) == '$')
			{
				fcheck = f+1;
				break;
			}else{
				s_name = s_name + msg.charAt(f);
			}
		}
		
		for(int f = fcheck ; f < msg.length() ; f ++)
		{
			s_etc = s_etc + msg.charAt(f);
		}
	}
	public boolean SystemCheck(){
    	boolean out = false;
		DB_handler DB = DB_handler.open(this);
		Cursor C_get_system = DB.get_System();
		int count = C_get_system.getCount();
		C_get_system.close();
		
		if(count > 0){
			out = true;
		}
		
		return out;
    }
    
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			
				
			if( KeyCode == KeyEvent.KEYCODE_BACK ){
				
				 new AlertDialog.Builder(GUI_setting_help_2.this)
				 .setTitle("����!")
				 .setMessage("���� �Ͻðڽ��ϱ�?")
				 .setPositiveButton("��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

			      		//Log.e(TAG+TAG_SYSTEM, "- FINISH -\n");
						T_check = false;
						T = null;
						
						finish();
					}
				})
				.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
				return false;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    
}
