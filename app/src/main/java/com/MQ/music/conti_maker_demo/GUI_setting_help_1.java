package com.MQ.music.conti_maker_demo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class GUI_setting_help_1 extends Activity  implements OnClickListener {
	/**
     *  Manual
     *  1) ������Ʈ�� �Ȱ��
     *  	- ������ ������ ������ �ٸ��� ����� ������Ʈ �Ǵ°�� �����մϴ�. �̺κ��� RegistrationId�� ������ ���� ��Ȱ�� �մϴ�.
     *  2) ����ڰ� ó�� ������ ��ġ�ϴ°��
     *  	- ����ڰ� ó�� ������ �����ϴ°�� RegistrationId�� �޾ƿ��� ��Ȱ�� �մϴ�.
     *  
     *  Check Date
     *  2013-03-09 (��) 
     */

	private String TAG = "GUI_setting_help_1";
    
	/** [1] ACTIVITY */
	private String TAG_ACTIVITY = "---- [1] ACTIVITY";

	/** [2] GUI*/
	private String TAG_GUI = "---- [2] GUI";
	
	//ProcessDialog
	private ProgressDialog PD_loading;
	public static int PD_loading_finish = -1 ; // -1[FirstSetting],  0[RegistrationId UnCheck], 1[RegistrationId Check]
	private int PD_loading_count  = 0 ;

	//Button
	private Button B_next;
	
	//TextView
	private TextView TV_code;
	private TextView TV_s_version;
	
	//EditText
	private EditText ET_code;


	/** [3] GCM*/
	private String TAG_GCM = "---- [3] GCM";
	
	//GCM
	private String SENDER_ID = "1095851346823";
	
	/** [4] SYSTEM */
	private String TAG_SYSTEM = "---- [4] SYSTEM";
	
	//Intent
	private String s_version = "";
	
	//Setting
	private Boolean DEVELOPER_MODE = true;

	// Thread
	private Thread T;
	private Handler H;
	private Boolean T_check = true;
	private int T_count = 0;

	//String
	private String code = "abcd";
	
	
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
        
        //[1] ACTIVITY
        setContentView(R.layout.gui_setting_help_1);
        
        //[2] GUI
        TV_set();
        ET_set();
        B_set();
        PD_set();
        
        //[3] GCM 
        RegId_set();
        
        //[4] SYSTEM 
        Intent_set();
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
	public void TV_set(){
		TV_code = (TextView) findViewById(R.id.TV_code);
		TV_s_version = (TextView) findViewById(R.id.TV_s_version);
		//Log.e(TAG+TAG_GUI,"- TV_set -");
	}
	
	public void ET_set(){
		ET_code = (EditText) findViewById(R.id.ET_code);
		
		//Log.e(TAG+TAG_GUI, "- ET_set -");2
	}
	
	public void PD_set(){
		PD_loading = new ProgressDialog(this);
		PD_loading.setTitle("ContiMaker");
		PD_loading.setMessage("please wait....");
		PD_loading.setIndeterminate(true);
		PD_loading.setCancelable(false);
		PD_loading.show();
        PD_loading_count = T_count + 20000;
        
		//Log.e(TAG+TAG_GUI, "- PD_set -");
	}

    public void B_set(){
    	B_next = (Button)findViewById(R.id.B_next);
    	B_next.setVisibility(View.INVISIBLE);
    	
		//Log.e(TAG+TAG_GUI, "- B_set -");
    }
    @Override
	public void onClick(View v) {
		
		if ( v.getId() == R.id.B_next && NetworkCheck()) 
		{
			
			if(ET_code.getText().toString().equals(code)){

				//Log.e(TAG+TAG_GUI, "- B_click -\n"+"Intent: GUI_setting_help_1");
				
				Intent intent = new Intent();
				intent.setClass(GUI_setting_help_1.this, GUI_setting_help_2.class);
				intent.putExtra("s_version", s_version);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			    GUI_setting_help_1.this.startActivity(intent);
		        GUI_setting_help_1.this.finish();
		        
			}else{

				//Log.e(TAG+TAG_GUI, "- B_click -\n"+"Msg: �ڵ尡 Ʋ�Ƚ��ϴ�.");
				
				Toast.makeText(this, "�ڵ尡 Ʋ�Ƚ��ϴ�.",Toast.LENGTH_SHORT).show();
				ET_code.setText("");
			}
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
			
			new AlertDialog.Builder(GUI_setting_help_1.this)
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
      		PD_loading_finish = 1;
      		return true;
      	}
      	
	    
	}
	//---------------------------------------------------------------------------------------------------------------------------------
	//[4] SYSTEM 
    public void Intent_set(){
    	Intent intent = getIntent();
    	s_version = intent.getExtras().getString("s_version");
		TV_s_version.setText(s_version);
    	
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
				
				if (PD_loading_finish == 1) {
					//RegistrationId�� ������ �°�� ������ �����մϴ�.

					if (PD_loading.isShowing()) {
						PD_loading.dismiss();
					}
					PD_loading_finish = 0;
					ThreadFinishSet();
					
				}else if(PD_loading_finish == -1 && T_count > PD_loading_count){
					//RegistrationId�� ������ �����°�� 20000�� ��ٸ��鼭 �ٽ� ������մϴ�.
					RegId_set();
					PD_loading_count = T_count + 20000;
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
		
    	B_next.setOnClickListener(this);
    	B_next.setVisibility(View.VISIBLE);
    	
    	TV_code.setVisibility(View.VISIBLE);
    	ET_code.setVisibility(View.VISIBLE);
    	
		//Log.e(TAG+TAG_SYSTEM,  "- ThreadFinishSet -\n");
	}
	
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
				
			if( KeyCode == KeyEvent.KEYCODE_BACK ){
				
				 new AlertDialog.Builder(GUI_setting_help_1.this)
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
