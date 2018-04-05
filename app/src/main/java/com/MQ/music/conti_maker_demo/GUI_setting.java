package com.MQ.music.conti_maker_demo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class GUI_setting extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	

	private ListView LV_setting;
	private ArrayList<AL_setting> ListItem;
	private LMA_setting LMA_setting;


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
        setContentView(R.layout.gui_setting);
        
        LV_set();
		DB_set();
		TV_set();
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
    
    


	//------------------------------------------------------------------------------------------------------------
    /*
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,1,0,"Help").setIcon(android.R.drawable.ic_menu_help);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case 1:
			return true;
		}
		return false;
	}
	*/
	//------------------------------------------------------------------------------------------------------------
	

	

	
	public String Myid(){

		DB_handler DB_system = DB_handler.open(this);
		Cursor C_get_system = DB_system.get_System();
		String s_id = C_get_system.getString(C_get_system.getColumnIndex("s_id"));
		C_get_system.close();
		DB_system.close();
		
		return s_id;
	}
	
	public String getVersion(){

		DB_handler DB_system = DB_handler.open(this);
		Cursor C_get_system = DB_system.get_System();
		String s_version = C_get_system.getString(C_get_system.getColumnIndex("s_version"));
		C_get_system.close();
		DB_system.close();
		
		return s_version;
	}
  
   public void LV_set(){
	   LV_setting = (ListView)findViewById(R.id.LV_setting);
	   
       ListItem = new ArrayList<AL_setting>();
       
       ListItem.add(new AL_setting(2,"�⺻����",""));
       ListItem.add(new AL_setting(0,"��������",getVersion()));//Ŭ�� ���� 1
       ListItem.add(new AL_setting(1,"������Ʈ"	,""));//Ŭ�� ���� 2

       ListItem.add(new AL_setting(2,"��������",""));
       ListItem.add(new AL_setting(0,"��Ƽ����Ŀ ID",""));//Ŭ�� ���� 4

       ListItem.add(new AL_setting(2,"MQ",""));
       ListItem.add(new AL_setting(1,"������ ����",""));//Ŭ�� ���� 6

       LMA_setting = new LMA_setting(this, ListItem );
       
       LV_setting.setAdapter(LMA_setting);
       LV_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
	    	@Override
	    		public void onItemClick(AdapterView parent, View view, int position, long id) {
					// TODO Auto-generated method stub
	    					
	    						if(position == 1){
	    							
	    							
		 	    					Intent intent = new Intent();
		 							intent.setClass( GUI_setting.this, GUI_setting_version.class);
		 							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		 							GUI_setting.this.startActivity(intent);	
	    							
	    							
	    						}else if(position == 2){
	    							
	    							Uri uri = Uri.parse("market://details?id=com.MQ.music.conti_maker_demo");

	    					          //--- ��) market://details?id=com.jopenbusiness.android.smartsearch

	    					        Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
	    					        startActivity(intent);
		 							
	    						}else if(position == 4){
	    							
		 	    					Intent intent = new Intent();
		 							intent.setClass( GUI_setting.this, GUI_setting_user.class);
		 							intent.putExtra("getFrom", "GUI_setting");
		 							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		 							GUI_setting.this.startActivity(intent);	
		 							
	    						}else if(position == 6){

	    				    		
	    				    		Intent sendIntent = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:010-7444-8705"));
	    				    		startActivity(sendIntent);
	    							/*
		 	    					Intent intent = new Intent();
		 							intent.setClass( GUI_setting.this, GUI_setting_mq.class);
		 							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		 							GUI_setting.this.startActivity(intent);	
		 							*/
	    						}
	    			}
				});
   }
   
	@Override
	public void onClick(View v) {
		
	}
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			if( KeyCode == KeyEvent.KEYCODE_BACK ){

				Intent intent = new Intent();
				intent.setClass(GUI_setting.this, GUI_main.class);
				intent.putExtra("getType","a");
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		        GUI_setting.this.startActivity(intent);
		        GUI_setting.this.finish();
					
					
				return true;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    
}