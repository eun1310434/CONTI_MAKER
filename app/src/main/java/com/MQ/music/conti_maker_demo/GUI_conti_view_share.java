package com.MQ.music.conti_maker_demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GUI_conti_view_share extends Activity  implements OnClickListener {
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
	private String c_name;
	private String ItemSet = ""; //on , off
	private String screen = "";
	
	//Conti List
	private ListView LV_teams;
	private ArrayList<AL_friend> ListItem;
	private ArrayList<AL_friend> get_registrationIds = new ArrayList<AL_friend>(); 
	private LMA_friend LMA_teams;
	
	
	//TextView
	private TextView TV_my_s_id;
	
	//String
	private String s_id;
	private String s_name;
	
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
        setContentView(R.layout.gui_conti_view_share);
		overridePendingTransition(0, 0);

		IntentSet();
        DB_set();
		NetWork_set();
		TV_set();
		LV_set();
        B_set();
    }

    
    
    public void IntentSet(){

		Intent intent = getIntent();
		getFrom = intent.getExtras().getString("getFrom");
    	c_name = intent.getExtras().getString("c_name");
		ItemSet = intent.getExtras().getString("ItemSet");
    }
    
	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_System = D_test.get_System();
		s_id = C_get_System.getString(C_get_System.getColumnIndex("s_id"));
		s_name = C_get_System.getString(C_get_System.getColumnIndex("s_name"));
		C_get_System.close();
		C_get_System.close();
	}
    
	public void NetWork_set(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	
	
	
    
	
    public void TV_set(){
    	
		TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
		TV_my_s_id.setText("ID: "+ s_id);
    }
    
    public void LV_set()
	{
		//--------------------------------------------------------------------------------------------------------------------------------
		//ListView ---> ����
    	LV_teams= (ListView)findViewById(R.id.list);
	    ListItem = new ArrayList<AL_friend>();
	        
	    //--------------------------------------------------------------------------------------------------------------------------------
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
		
		
		LV_check_set();
		
		
	}
    
    public void LV_check_set()
	{    
    	
    	
		DB_handler D_test = DB_handler.open(this);
		       
		for(int i = 0 ; i <ListItem.size() ; i ++)
		{
			Cursor C_get_conti_members = D_test.get_conti_members(c_name , ListItem.get(i).id);
			
			if(C_get_conti_members.getCount() == 1 ){
				LMA_teams.setCheck(i);
			
			}
			
			C_get_conti_members.close();
		}
		
		D_test.close();

		LMA_teams.notifyDataSetChanged();
		
	}

    
    public void LV_send_set(int position)
	{
    	//---------------------------------------------------------------------
    	//GCM�� Ȱ���Ͽ� ���濡�� ������ �� �ִ� ��Ƽ�� ������ �����մϴ�.
    	registrationIds.add(ListItem.get(position).registrationId);
    	get_registrationIds.add(new AL_friend(2,ListItem.get(position).name, ListItem.get(position).id,ListItem.get(position).etc,ListItem.get(position).registrationId));
	
    	
    	//---------------------------------------------------------------------
    	//DB�� ������ ����� �����մϴ�.
        DB_handler D_test = DB_handler.open(this);
        D_test.insert_conti_members(c_name , ListItem.get(position).id);
        D_test.close();
        
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
		

	        DB_handler D_test = DB_handler.open(this);
	        D_test.delete_conti_members_c_name(c_name);
	        D_test.close();
	        
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
				Toast.makeText(this, "��Ƽ ���̺��⸦ ��û�Ͽ����ϴ�.",Toast.LENGTH_SHORT).show();
				

				Intent intent = new Intent();
				intent.setClass( GUI_conti_view_share.this, GUI_down_landscape.class);
				intent.putExtra("getFrom", getFrom);
				intent.putExtra("screen", "Landscape");
	        	intent.putParcelableArrayListExtra("registrationIds", get_registrationIds);
				intent.putExtra("c_name", c_name);
				intent.putExtra("ItemSet", ItemSet);
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				GUI_conti_view_share.this.startActivity(intent);
				
				
				registrationIds.clear();// ��å�� ����� ��� �����մϴ�.
				get_registrationIds.clear();
			
				
				
			}
			
			
		}else if ( v.getId() == R.id.B_cancle) {
	    		FinishSet();
	    	
		}
		
	}
	//------------------------------------------------------------------------------------------------------------------------
		/*
		 * Message
		 */
		public void finish_setMessage(){
		
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
		    .addData("type","��Ƽ���̺�������" )
		    .addData("sender",s_name )
		    .addData("message", "")
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
			
			Cursor C_get_continuity = D_test.get_continuity(c_name);
			String c_mdate = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_mdate"));
			String c_writer = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_writer"));
			String c_subject = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_subject"));
			C_get_continuity.close();

			String message = "["+s_name+"]"+c_name+"$"+c_mdate+"$"+c_writer+"$"+c_subject;
			
			Cursor C_get_conti_music = D_test.get_conti_music(c_name);
			
			while(true)
			{
				message = message+"$"+C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("cm_m_id"));
				if(C_get_conti_music.moveToNext()== false){break;};
			}
			C_get_conti_music.close();
			
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
		    .addData("type","��Ƽ���̺������" )
		    .addData("sender",s_name )
		    .addData("message", message)
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

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}

	
	public void FinishSet(){

		
		new AlertDialog.Builder(GUI_conti_view_share.this)
		 .setTitle("����")
		 .setMessage("\'��Ƽ ���̺���\' �� �����Ͻðڽ��ϱ�?")
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
