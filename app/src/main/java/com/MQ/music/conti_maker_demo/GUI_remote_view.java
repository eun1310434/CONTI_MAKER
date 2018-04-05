package com.MQ.music.conti_maker_demo;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class GUI_remote_view  extends Activity {
	/** Called when the activity is first created. */
	
	public static Activity GUI_remote_view_activity;
	
	//TextView
	private TextView TV_my_s_id;

	//String
	private String s_id;
	

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
		setContentView(R.layout.gui_remote_view);
    	

		
		
		GUI_remote_view_activity = GUI_remote_view.this;//����� ���
		
		DB_set();
        TV_set();
    	
	}
    @Override
    protected void onResume() {
    	  // TODO Auto-generated method stub
    	  super.onResume();
    	  //�˶�â ������
    	  NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	  nm.cancel("�Ǻ� ���̺���", 0);
    	
    }
    
	//------------------------------------------------------------------------------------------------------------
    //DB�� ���Ͽ� ���̵� �����մϴ�.
	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor c_get_id = D_test.get_System();
		s_id = c_get_id.getString(c_get_id.getColumnIndex("s_id"));
		c_get_id.close();
		D_test.close();
	}
	public void TV_set() {
		TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
		TV_my_s_id.setText("ID: "+ s_id);
	}

		
	    
		public static void IntentGo(int _m_id, int _page, String sender){
			
			GUI_remote_view aActivity = (GUI_remote_view) GUI_remote_view.GUI_remote_view_activity;

			
			File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(_m_id)+"_"+Integer.toString(_page)+".png");
			if(f.exists()){

				Intent intent = new Intent();
				intent.setClass( aActivity, GUI_remote_view_landscape.class);
				intent.putExtra("sender", sender);
				intent.putExtra("getFrom", "GUI_gcm");
				intent.putExtra("m_id", _m_id);
				intent.putExtra("page", _page);
				intent.putExtra("ItemSet", "off");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				aActivity.startActivity(intent);
				aActivity.finish();
				
			}else{
				Intent intent = new Intent();
				intent.setClass( aActivity, GUI_down_landscape.class);
				intent.putExtra("getFrom", "GUI_gcm");
				intent.putExtra("sender", sender);
				intent.putExtra("screen", "Landscape");
				intent.putExtra("m_id",_m_id);
				intent.putExtra("page",_page);
				intent.putExtra("ItemSet", "off");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				aActivity.finish();
				aActivity.startActivity(intent);
			}
			
		}
		
		public static void IntentGoConti(String c_name , String sender){
			
			GUI_remote_view aActivity = (GUI_remote_view) GUI_remote_view.GUI_remote_view_activity;

			Intent intent1 = new Intent(aActivity, GUI_down_landscape.class);
			intent1.putExtra("getFrom", "GUI_gcm_conti");
			intent1.putExtra("sender",sender);
			intent1.putExtra("screen", "Landscape");
			intent1.putExtra("c_name",c_name);
			intent1.putExtra("ItemSet", "off");
			intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			aActivity.finish();
			aActivity.startActivity(intent1);
		}

		

		public void FinishSet(){

			
			new AlertDialog.Builder(GUI_remote_view.this)
			 .setTitle("����")
			 .setMessage("\'REMOTE VIEW\' �� �����Ͻðڽ��ϱ�?")
			 .setPositiveButton("��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					

					Intent intent = new Intent();
					intent.setClass(GUI_remote_view.this, GUI_main.class);
					intent.putExtra("getType","a");
			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			        GUI_remote_view.this.startActivity(intent);
			        GUI_remote_view.this.finish();
			        
				}
			})
			.setNegativeButton("���", new DialogInterface.OnClickListener() {
				
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();	
			
		}
		
		public boolean onKeyDown(int KeyCode, KeyEvent event) {

			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
				// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�

				if (KeyCode == KeyEvent.KEYCODE_BACK) {
					FinishSet();
					return true;
				}

			}

			return super.onKeyDown(KeyCode, event);

		}



}