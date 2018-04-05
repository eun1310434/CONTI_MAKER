package com.MQ.music.conti_maker_demo;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GUI_friend extends Activity  implements OnClickListener {
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
	
	//�ϰ����ۿ� �ʿ��� List ����
	//private List<String> registrationIds = new ArrayList<String>();
	
	//�������ۿ� �ʿ��� ����
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
	
		
	private EditText ET_search;
	
	private String mb_id ="";
	private String mb_name="";
	private String mb_registrationId="";
	private String mb_etc="";
	
	private static Client NW_client = new Client();
	
	//Button
	private ImageButton IB_delete;
	private ImageButton IB_arrage;
	private Button B_search;
	
	//
	private String[] arrage_menu = new String [] {"�̸�","ID"};
	
	//TextView
	private TextView TV_my_s_id;
	
	//String
	private String s_id;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Conti List
	private static ListView LV_teams;
	private static ArrayList<AL_friend> ListItem;
	private static LMA_friend LMA_teams;
	
	
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
        setContentView(R.layout.gui_friend);

		NetWork_set();
		ET_set();
		DB_set();
		TV_set();
		LV_set();
        ImageButton_set();
        Button_set();
    }
    
    @Override
    protected void onResume() {
    	  // TODO Auto-generated method stub
    	 
    	  super.onResume();
    	  //�˶�â ������
    	  NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	  nm.cancel("ģ�����", 0);
    	
    }
	public void NetWork_set(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
    //================================================================================================================================
	//GCM
	public void setMessage(){
		
			
			
			String message = "";
			
			DB_handler D_test = DB_handler.open(this);
			Cursor C_get_System = D_test.get_System();
			message = C_get_System.getString(C_get_System.getColumnIndex("s_id")) + "|";
			message = message + C_get_System.getString(C_get_System.getColumnIndex("s_name")) + "|";
			String sender = C_get_System.getString(C_get_System.getColumnIndex("s_name"));
			message = message + C_get_System.getString(C_get_System.getColumnIndex("s_registrationId")) + "|";
			message = message + C_get_System.getString(C_get_System.getColumnIndex("s_etc"));
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
	    .addData("type","ģ�����" )
	    .addData("sender",sender )
	    .addData("message", message)
	    .build();
		
	}
	
	public void sendMessage(String registrationId){
		
		//�ϰ����۽ÿ� ���
		try {
			gcmResult = gcmSender.send(gcmMessage, registrationId, RETRY);
		} catch(IOException e) {
			Log.w(TAG, "IOException " + e.getMessage());
		}
		
		Log.w(TAG, "getCanonicalIds : " + gcmResult.getCanonicalRegistrationId() + "\n" + 
				"getMessageId : " + gcmResult.getMessageId());
		
	}
	//================================================================================================================================

    
    public void ET_set(){

        ET_search = (EditText) findViewById(R.id.ET_search);
        ET_search.setVisibility(View.VISIBLE);
    	
    }
    

	public void Info_set(String msg){

		mb_id = "";
		mb_registrationId = "";
		mb_name = "";
		mb_etc = "";
		
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
		
	}

	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor c_get_id = D_test.get_System();
		s_id = c_get_id.getString(c_get_id.getColumnIndex("s_id"));
		c_get_id.close();
		D_test.close();
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
    	LV_teams.setVisibility(View.VISIBLE);
	    ListItem = new ArrayList<AL_friend>();
	        
	    //--------------------------------------------------------------------------------------------------------------------------------
	    //Insert ListItem --->DB�� ���� ��ü ��Ƽ ����� ���� ���ϴ�.	    
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_members_name = D_test.get_members_name();
		        
		while(true)
		{
			
			try 
			{
				ListItem.add(new AL_friend(0, C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_name")), C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_id")),C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_etc")),C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_registrationId"))));        	
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
		
		//---
		
		
		//--------------------------------------------------------------------------------------------------------------------------------
	    //onItemClickListener ---> ª�� ������ �Ǹ� ��Ƽ�� ���� �� �� �ֽ��ϴ�.	
		LV_teams.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{	
			
			@Override
	 	    public void onItemClick(AdapterView parent, View view, final int position, long id) 
			{
	 		// TODO Auto-generated method stub
				
	 		}
	 	});
		//--------------------------------------------------------------------------------------------------------------------------------
	    //onItemLongClickListener ---> ���� ������ �Ǹ� �ȳ�â�� �Բ� ������ ���� �� �� �ְ� �մϴ�.
		LV_teams.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() 
		{		
			@Override
			public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) 
			{		
				
				return false;
			}
		});
	}
    
    public static void LV_set(ArrayList<AL_friend> insert_ListItem ,String mb_id)
	{
    	NW_client.DownFriendPic(mb_id);
    	
    	ListItem.clear();
    	ListItem.addAll(insert_ListItem);
		LMA_teams.notifyDataSetChanged();
		LV_teams.setSelectionFromTop(	LV_teams.getFirstVisiblePosition()	,	0	);
	}
    
    public void LV_set_name()
	{
	    
    	ListItem.clear();
    	
	    DB_handler D_test = DB_handler.open(this);
		Cursor C_get_members_name = D_test.get_members_name();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_friend(0, C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_name")), C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_id")),C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_etc")),C_get_members_name.getString(C_get_members_name.getColumnIndex("mb_registrationId"))));        	
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
		LMA_teams.notifyDataSetChanged();
		
		LV_teams.setSelectionFromTop(	LV_teams.getFirstVisiblePosition()	,	0	);
	}
    
    
    public void LV_set_id()
	{
	    
    	ListItem.clear();
    	
	    DB_handler D_test = DB_handler.open(this);
		Cursor C_get_members_id = D_test.get_members_id();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_friend(0, C_get_members_id.getString(C_get_members_id.getColumnIndex("mb_name")), C_get_members_id.getString(C_get_members_id.getColumnIndex("mb_id")),C_get_members_id.getString(C_get_members_id.getColumnIndex("mb_etc")),C_get_members_id.getString(C_get_members_id.getColumnIndex("mb_registrationId"))));        	
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
			}
		    
			if(C_get_members_id.moveToNext()== false){break;};
		}
		
		C_get_members_id.close();
		D_test.close();
		
		//--------------------------------------------------------------------------------------------------------------------------------        
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_teams.notifyDataSetChanged();
		
		LV_teams.setSelectionFromTop(	LV_teams.getFirstVisiblePosition()	,	0	);
	}
    
    public void ImageButton_set(){
    	
    	IB_arrage = (ImageButton) findViewById(R.id.button_arrage);
    	IB_arrage.setOnClickListener(this);

    	IB_delete = (ImageButton) findViewById(R.id.button_delete);
    	IB_delete.setOnClickListener(this);
    	
    }
    public void Button_set(){
    	B_search = (Button) findViewById(R.id.button_search);
    	B_search.setOnClickListener(this);
    	B_search.setVisibility(View.VISIBLE);
    }
	@Override
	public void onClick(View v) {

		

		
		if ( (v.getId() == R.id.button_search) && (ET_search.getText().toString().equals("") == false)) {
			
			
					String msg = NW_client.IdSearch(ET_search.getText().toString());


        			DB_handler D_system = DB_handler.open(this);
        			Cursor C_system = D_system.get_System();
        			String MyId = C_system.getString(C_system.getColumnIndex("s_id"));
        			C_system.close();
        			D_system.close();
        			
					if(MyId.equals(ET_search.getText().toString())){
						
				    				  new AlertDialog.Builder(GUI_friend.this)
				    				 .setTitle("����!!")
				    				 .setMessage("�ڽ��� ID�� ����Ҽ� �����ϴ�.")
				    				 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				    					@Override
				    					public void onClick(DialogInterface dialog, int which) {
				    						ET_search.setText("");
				    					}
				    					
				    				}).show();
						
						
					}else if(msg.equals("NoId")){
						
						
				    				  new AlertDialog.Builder(GUI_friend.this)
				    				 .setTitle("����!!")
				    				 .setMessage("��ϵ��� ���� ID �Դϴ�.")
				    				 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				    					@Override
				    					public void onClick(DialogInterface dialog, int which) {
				    						ET_search.setText("");
				    					}
				    					
				    				}).show();
				    				  
						
					}else if(msg.equals("ServerCheck")){
						
						
									Toast.makeText(GUI_friend.this,"�˼��մϴ�Ф� \n���� ���� �� �Դϴ�...", Toast.LENGTH_SHORT).show();
									mb_id ="";
									mb_name="";
									mb_etc="";
									mb_registrationId="";
						
						
					}else{
						
						
									Info_set(msg);
			
				        			DB_handler D_member_check = DB_handler.open(this);
				        			Cursor C_member_check = D_member_check.get_members(mb_id);
				        			int check = C_member_check.getCount();
				        			C_member_check.close();
				        			D_member_check.close();
				        			
									if(check == 1){
										
					    				  new AlertDialog.Builder(GUI_friend.this)
					    				 .setTitle("����!!")
					    				 .setMessage("�̹� ��� �� ID �Դϴ�.")
					    				 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					    					@Override
					    					public void onClick(DialogInterface dialog, int which) {
			
					    						ET_search.setText("");
					    					}
					    					
					    				}).show();
					    				  
									}else if(check == 0){
										
										/*
										 * ���濡�� ���� ����� �������ϴ�.
										 */
										NW_client.DownFriendPic(ET_search.getText().toString());
										setMessage();
										sendMessage(mb_registrationId);
										
						        		try {
			
											ET_search.setText("");
						        			DB_handler D_member_insert = DB_handler.open(this);
						        			D_member_insert.insert_members(mb_id, mb_name, mb_registrationId, mb_etc);
						        			D_member_insert.close();
						        	        Toast.makeText(GUI_friend.this,"ģ���� �߰��Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
						        	        LV_set_name();
						        	        
						    			} catch (Exception e) {
						    				// TODO: handle exceptionnew AlertDialog.Builder(GUI_friend_add_1.this)
						    			}
										
									}
		        		
		        		
					}
						
						
		}
    	else if ( v.getId() == R.id.button_arrage) {
    		//ģ������
    		
    		if( ListItem.size() == 0){
    		}else{
    			
				Toast.makeText(GUI_friend.this, "ģ�� ����",Toast.LENGTH_SHORT).show();
				
				new AlertDialog.Builder(GUI_friend.this)
				.setTitle("ģ�� ����")
				.setItems(arrage_menu, new DialogInterface.OnClickListener() 
				{		
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{// TODO Auto-generated method stub
						if(which == 0)
	    				{
							//�̸� ������
							LV_set_name();
	    				}
						else if(which == 1)
	        			{
							//�Ҽ� ������
							LV_set_id();
	        			}
	        		}
	        	}).show();
				
			}
    		//ģ������
    		
			
    	}
    	else if ( v.getId() == R.id.button_delete) {
    		//ģ������
    		Toast.makeText(GUI_friend.this,"ģ������", Toast.LENGTH_SHORT).show();
    		
			Intent intent = new Intent();
			intent.setClass(GUI_friend.this, GUI_friend_delete.class);
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_friend.this.startActivity(intent);
			GUI_friend.this.finish();
			
    	}
		
	}
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			if( KeyCode == KeyEvent.KEYCODE_BACK ){

				Intent intent = new Intent();
				intent.setClass(GUI_friend.this, GUI_main.class);
				intent.putExtra("getType","a");
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		        GUI_friend.this.startActivity(intent);
		        GUI_friend.this.finish();
					
					
				return true;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    
}
