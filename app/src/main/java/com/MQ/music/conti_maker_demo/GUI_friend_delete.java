package com.MQ.music.conti_maker_demo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GUI_friend_delete extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */

	
	
	//Button
	private Button B_delete;
	private Button B_cancle;
	
	//Conti List
	private ListView LV_teams;
	private ArrayList<AL_friend> ListItem;
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
        setContentView(R.layout.gui_friend_delete);
		overridePendingTransition(0, 0);


		DB_set();
		TV_set();
		LV_set();
        B_set();
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

    
    public void LV_D_set(int position)
	{
    	//Delete DB ---> ��Ƽ�� DB���� �����մϴ�.
        DB_handler D_test = DB_handler.open(this);
        D_test.delete_members(ListItem.get(position).id);
        D_test.delete_conti_members_mb_id(ListItem.get(position).id);
        D_test.close();
	}
    
    public void B_set()
    {
    	//���� ��ư�Դϴ�.
    	B_delete =(Button) findViewById(R.id.B_delete);
    	B_delete.setOnClickListener(this);
    	
    	//��� ��ư �Դϴ�.
    	B_cancle =(Button) findViewById(R.id.B_cancle);
    	B_cancle.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {

		
		if ( v.getId() == R.id.B_delete) {
			int count = 0;
			
			//[1] �����ϱ�
			for(int i = 0 ; i <ListItem.size() ; i++)
			{
				//��ü ����Ʈ���� "����"�Ǿ��� ����Ʈ�� ���� �մϴ�.
				if(LMA_teams.getCheck(i).equals("����"))
				{
					LV_D_set(i);
					count++;
				}
				
			}
			
			//[2] Ȯ���ϱ�
			if(count == 0){
				
				//������ ģ���� ���� ���
				Toast.makeText(this, "ģ���� ������ �ּ���.",Toast.LENGTH_SHORT).show();
				
			}else{
				
				//������ ģ���� ���� �� ���
				Toast.makeText(this, "ģ���� ���� �߽��ϴ�.",Toast.LENGTH_SHORT).show();
	        	Intent intent = new Intent();
	        	intent.setClass(this, GUI_friend.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	    		startActivity(intent);
				finish();	
			}	
			
    	}
    	else if ( v.getId() == R.id.B_cancle) {
    		
        	Intent intent = new Intent();
        	intent.setClass(this, GUI_friend.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    		startActivity(intent);
			finish();
    	}
		
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
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

		        	Intent intent = new Intent();
		        	intent.setClass(this, GUI_friend.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		    		startActivity(intent);
					finish();			
					
					return true;
				}
			}
			return super.onKeyDown( KeyCode, event );
		}
}
