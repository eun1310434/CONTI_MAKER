package com.MQ.music.conti_maker_demo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GUI_music_view_share extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */

	//-------------------------------------------------------------------------------------------------------------------------------
	/*
	 * GCM
	 */
	private static final String TAG = "GCM";
	//�ڽ��� Project ID �� �־��ּ���
	private static final String SENDER_ID = "1095851346823";	
	
	private Sender 				gcmSender;				//GCM Sender
	private Message 			gcmMessage;			//GCM Message
	private Result 				gcmResult;				//GCM Result(���� ����)
	private MulticastResult 	gcmMultiResult;		//GCM Multi Result(�ϰ� ����)
	
	//�ϰ����ۿ� �ʿ��� List ����
	private List<String> registrationIds = new ArrayList<String>();
	
	//�������ۿ� �ʿ��� ����
	//private String		   registrationId = "APA91bF3ftR3XyfReUSMjrHfxkhA0qxZXC9blEjFmutdgw-rdqyuk55_T1A5Pvdnwk_jv-EHGXiKY2_k4cRTl7Kibn_4XKLFcUS0U4qcf8qDlAypk8iWCbqIdtJDY6uQAldQY1viySPY3RdsPSZvT6-5J4aGSFEMlA";
	
	//������ �ֿܼ��� �߱޹��� API Key
	private static String 		API_KEY = "AIzaSyDGjUj0DZq8XCkD8eP8NEdgKttEbpKr_GI";
	
	//�޼����� ���� ID(?)������ �����ϸ� �˴ϴ�. �޼����� �ߺ������� ���� ���� �������� �����մϴ�
	private static String 		COLLAPSE_KEY ;
	
	//��Ⱑ Ȱ��ȭ ������ �� ������ ������. 
	private static boolean 	DELAY_WHILE_IDLE = false;
	
	//��Ⱑ ��Ȱ��ȭ ������ �� GCM Storage�� �����Ǵ� �ð�
	private static int			TIME_TO_LIVE = 6000;
	
	//�޼��� ���� ���н� ��õ��� Ƚ��
	private static int 			RETRY = 3;

	//-------------------------------------------------------------------------------------------------------------------------------
	
	
	//Button
	private Button B_send;
	private Button B_cancle;

	//Intent
	private String getFrom = ""; // GUI_msuci  ,  GUI_conti
	private int m_id;
	private String ItemSet = "on"; //on , off
	private String screen = "";
	
	//Conti List
	private ListView LV_teams;
	private ArrayList<AL_friend> ListItem;
	private ArrayList<AL_friend> get_registrationIds;
	private LMA_friend LMA_teams;
	
	
	//TextView
	private TextView TV_my_s_id;
	
	//String
	private String s_id;
	
	private Boolean DEVELOPER_MODE = true;


	
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
        setContentView(R.layout.gui_music_view_share);

        DB_set();
		IntentSet();
		NetWork_set();
		get_ListItem_set();
		TV_set();
		LV_set();
        B_set();
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
    public void get_ListItem_set(){
    	get_registrationIds = new ArrayList<AL_friend>(); 
    }
    public void IntentSet(){

		Intent intent = getIntent();
		getFrom = intent.getExtras().getString("getFrom");
		screen = intent.getExtras().getString("screen");
		m_id = intent.getExtras().getInt("m_id");
		ItemSet = intent.getExtras().getString("ItemSet");
    }

    //================================================================================================================================
	//GCM
	
	public void NetWork_set(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	public void finish_setMessage(){
	    
		
		
			DB_handler D_test = DB_handler.open(this);
			
			Cursor C_get_System = D_test.get_System();
			String s_name = C_get_System.getString(C_get_System.getColumnIndex("s_name"));
			C_get_System.close();
			
			D_test.close();
		
		
		
		
		COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
		/*
		 * �޼����� 20���� �������� �ٸ� �������� ������ �޼����� �������� �������� �ִµ� �׷����� collapse_key�� �ֱ������� �ٲ��ָ� ������ �ذ�ȴ�.
		 * http://blog.daum.net/_blog/ArticleCateList.do?blogid=0HOSO&CATEGORYID=228114&dispkind=B2201#ajax_history_home
		 */
		
		gcmSender = new Sender(API_KEY);
		gcmMessage = new Message.Builder()
	    .collapseKey(COLLAPSE_KEY)
	    .delayWhileIdle(DELAY_WHILE_IDLE)
	    .timeToLive(TIME_TO_LIVE)
	    .addData("type","�Ǻ����̺�������" )
	    .addData("sender",s_name )
	    .addData("message", Integer.toString(m_id)+"_1")
	    .build();
		
		
		
	}
	
	public void finish_sendMessage(){
		
		//�ϰ����۽ÿ� ���
		try {
			
			gcmMultiResult = gcmSender.send(gcmMessage, registrationIds, RETRY);
			
		} catch (IOException e) {
			Log.w(TAG, "IOException " + e.getMessage());
		}
		
		Log.w(TAG, "getCanonicalIds : " + gcmMultiResult.getCanonicalIds() + "\n" + 
				"getSuccess : " + gcmMultiResult.getSuccess() + "\n" + 
				"getTotal : " + gcmMultiResult.getTotal() + "\n" + 
				"getMulticastId : " + gcmMultiResult.getMulticastId());
	}
	
	public void setMessage(){
	    
		
		
			DB_handler D_test = DB_handler.open(this);
			
			Cursor C_get_System = D_test.get_System();
			String s_name = C_get_System.getString(C_get_System.getColumnIndex("s_name"));
			C_get_System.close();
			
			D_test.close();
		
		
		
		
		COLLAPSE_KEY = String.valueOf(Math.random() % 100 + 1);
		/*
		 * �޼����� 20���� �������� �ٸ� �������� ������ �޼����� �������� �������� �ִµ� �׷����� collapse_key�� �ֱ������� �ٲ��ָ� ������ �ذ�ȴ�.
		 * http://blog.daum.net/_blog/ArticleCateList.do?blogid=0HOSO&CATEGORYID=228114&dispkind=B2201#ajax_history_home
		 */
		
		gcmSender = new Sender(API_KEY);
		gcmMessage = new Message.Builder()
	    .collapseKey(COLLAPSE_KEY)
	    .delayWhileIdle(DELAY_WHILE_IDLE)
	    .timeToLive(TIME_TO_LIVE)
	    .addData("type","�Ǻ����̺������" )
	    .addData("sender",s_name )
	    .addData("message", Integer.toString(m_id)+"_1")
	    .build();
		
		
		
	}
	
	public void sendMessage(){
		
		//�ϰ����۽ÿ� ���
		try {
			
			gcmMultiResult = gcmSender.send(gcmMessage, registrationIds, RETRY);
			
		} catch (IOException e) {
			Log.w(TAG, "IOException " + e.getMessage());
		}
		
		Log.w(TAG, "getCanonicalIds : " + gcmMultiResult.getCanonicalIds() + "\n" + 
				"getSuccess : " + gcmMultiResult.getSuccess() + "\n" + 
				"getTotal : " + gcmMultiResult.getTotal() + "\n" + 
				"getMulticastId : " + gcmMultiResult.getMulticastId());
	}
    
	
    public void TV_set(){
    	
		TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
		TV_my_s_id.setText("ID: "+ s_id);
    }

    //================================================================================================================================
	// ListView
    public void LV_set()
	{
		//--------------------------------------------------------------------------------------------------------------------------------
		//ListView ---> ����
    	LV_teams= (ListView)findViewById(R.id.list);
	    ListItem = new ArrayList<AL_friend>();
	        
	    //--------------------------------------------------------------------------------------------------------------------------------
	    //Insert ListItem --->DB�� ���� ��ü ��Ƽ ����� ���� ���ϴ�.	    
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_members_name = D_test.get_members_name();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_friend(2, C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_name")), C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_id")),C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_etc")),C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_registrationId"))));        	
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
			}
		    
			if(C_get_members_name.moveToNext()== false){break;};
		}
		
		C_get_members_name.close();
		D_test.close();
		
		//--------------------------------------------------------------------------------------------------------------------------------        
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_teams = new LMA_friend(this, ListItem );
		LV_teams.setAdapter(LMA_teams);
	}
    
    public void LV_send_set(int position)
	{
    	//---------------------------------------------------------------------
    	//GCM�� Ȱ���Ͽ� ���濡�� ������ �� �ִ� ��Ƽ�� ������ �����մϴ�.
    	registrationIds.add(ListItem.get(position).registrationId);
    	get_registrationIds.add(new AL_friend(2,ListItem.get(position).name, ListItem.get(position).id,ListItem.get(position).etc,ListItem.get(position).registrationId));
	}
    
    public void B_set()
    {
    	//���� ��ư�Դϴ�.
    	B_send =(Button) findViewById(R.id.B_send);
    	B_send.setOnClickListener(this);
    	B_send.setText("���̺���");
    	
    	//��� ��ư �Դϴ�.
    	B_cancle =(Button) findViewById(R.id.B_cancle);
    	B_cancle.setOnClickListener(this);
    	B_cancle.setText("�� ��");
    }

	@Override
	public void onClick(View v) {

		
		if ( v.getId() == R.id.B_send) {
			

			int count = 0;
			
			//[1] �����ϱ�
			for(int i = 0 ; i <ListItem.size() ; i++)
			{
				//��ü ����Ʈ���� "����"�Ǿ��� ����Ʈ�� ���� �մϴ�.
				if(LMA_teams.getCheck(i).equals("����"))
				{
					LV_send_set(i);
					count++;
				}
				
			}
			
			
			if(count == 0){
				
				//������ ģ���� ���� ���
				Toast.makeText(this, "ģ���� ������ �ּ���.",Toast.LENGTH_SHORT).show();
				
			}else{
				
				
				//������ ģ���� ���� �� ���
				setMessage();
				sendMessage();
				Toast.makeText(this, "�Ǻ� ���̺��⸦ ��û�Ͽ����ϴ�.",Toast.LENGTH_SHORT).show();
				
				
				File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(m_id)+"_1.png");
				if(f.exists()){

					Intent intent = new Intent();
					intent.setClass( GUI_music_view_share.this, GUI_music_view_share_control.class);
		        	intent.putParcelableArrayListExtra("registrationIds", get_registrationIds);
					intent.putExtra("m_id", m_id);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_music_view_share.this.startActivity(intent);
					
				}else{

					Intent intent = new Intent();
					intent.setClass( GUI_music_view_share.this, GUI_down_landscape.class);
					intent.putExtra("getFrom", "GUI_music_view_share_control");
					intent.putExtra("screen", "Landscape");
		        	intent.putParcelableArrayListExtra("registrationIds", get_registrationIds);
					intent.putExtra("m_id", m_id);
					intent.putExtra("ItemSet", ItemSet);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_music_view_share.this.startActivity(intent);
					
					
				}
				
				
				registrationIds.clear();// ��å�� ����� ��� �����մϴ�.
				get_registrationIds.clear();
			}	
			
    	}
    	else if ( v.getId() == R.id.B_cancle) {
    		FinishSet();
    	}
		
	}


	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
		
	}
	
	public void FinishSet(){

		
		new AlertDialog.Builder(GUI_music_view_share.this)
		 .setTitle("����")
		 .setMessage("\'�Ǻ� ���̺���\' �� �����Ͻðڽ��ϱ�?")
		 .setPositiveButton("��", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				int count = 0;
				
				//[1] �����ϱ�
				for(int i = 0 ; i <ListItem.size() ; i++)
				{
					//��ü ����Ʈ���� "����"�Ǿ��� ����Ʈ�� ���� �մϴ�.
					if(LMA_teams.getCheck(i).equals("����"))
					{
						LV_send_set(i);
						count++;
					}
					
				}
				
				if(count != 0){

					finish_setMessage();
					finish_sendMessage();
				}
				
				if(screen.equals("Portrait")){

					Intent intent = new Intent();
					intent.setClass( GUI_music_view_share.this, GUI_music_view_portrait.class);
					intent.putExtra("getFrom", "GUI_music");
					intent.putExtra("m_id", m_id);
					intent.putExtra("ItemSet", ItemSet);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_music_view_share.this.startActivity(intent);
					GUI_music_view_share.this.finish();
			    	
					
				}else if(screen.equals("Landscape")){

					Intent intent = new Intent();
					intent.setClass( GUI_music_view_share.this, GUI_music_view_landscape.class);
					intent.putExtra("getFrom", "GUI_music");
					intent.putExtra("m_id", m_id);
					intent.putExtra("ItemSet", ItemSet);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_music_view_share.this.startActivity(intent);
					GUI_music_view_share.this.finish();
					
				}else{
					GUI_music_view_share.this.finish();
				}
				
				finish();
			}
		})
		.setNegativeButton("���", new DialogInterface.OnClickListener() {
			
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).show();	
		
	}

	
	
	  //================================================================================================================================
	  	//�ڷ� ���� ��ư
	    @Override
		public boolean onKeyDown( int KeyCode, KeyEvent event )
		{
		 
	    	
			if( event.getAction() == KeyEvent.ACTION_DOWN ){
				// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
				// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
		 
				if( KeyCode == KeyEvent.KEYCODE_BACK ){
		    		
					FinishSet();
					
					return true;
				}
			}
			return super.onKeyDown( KeyCode, event );
		}
		
}
