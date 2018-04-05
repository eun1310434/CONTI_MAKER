package com.MQ.music.conti_maker_demo;
import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GUI_conti_delete extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	/*
	 * 1. ��Ƽ�� ��ϵǾ��� ��ü ����� Ȯ�� �Ҽ� �ս��ϴ�.
	 * 2. ��Ƽ�� ���� �� �� �ֽ��ϴ�.
	 * 3. ��Ƽ�� ���� �ϴ� ������ �̵� ��ŵ�ϴ�.
	 */
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Conti List : ��ü ��Ƽ��� �Դϴ�.
	private ListView LV_conti;
	private ArrayList<AL_conti> ListItem;
	private LMA_conti LMA_conti;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Button : ���� , ��� ��ư �Դϴ�. 
	private Button B_delete;
	private Button B_cancle;

	//TextView
	private TextView TV_my_s_id;
	
	//String
	private String s_id;
	
	
	private Boolean DEVELOPER_MODE = true;
	//================================================================================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
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
        setContentView(R.layout.gui_conti_delete);
		overridePendingTransition(0, 0);
		
        B_set();
        LV_set();
		DB_set();
		TV_set();
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

    
    //================================================================================================================================
    // Button�� �����մϴ�.
    
    public void B_set()
    {
    	//���� ��ư�Դϴ�.
    	B_delete =(Button) findViewById(R.id.B_delete);
    	B_delete.setOnClickListener(this);
    	
    	//��� ��ư �Դϴ�.
    	B_cancle =(Button) findViewById(R.id.B_cancle);
    	B_cancle.setOnClickListener(this);
    }
    
    //Button�� ����� �����մϴ�.
    @Override
	public void onClick(View v) 
    {
		// TODO Auto-generated method stub
		
		if ( v.getId() == R.id.B_delete) 
		{//������ư
			int count = 0;
			
			//[1] �����ϱ�
			for(int i = 0 ; i <ListItem.size() ; i++)
			{
				//��ü ����Ʈ���� "����"�Ǿ��� ����Ʈ�� ���� �մϴ�.
				if(LMA_conti.getCheck(i).equals("����"))
				{
					LV_D_set(i);
					count++;
				}
				
			}
			
			//[2] Ȯ���ϱ�
			if(count == 0){
				
				//������ �Ǻ��� ���� ���
				Toast.makeText(this, "��Ƽ�� ������ �ּ���.",Toast.LENGTH_SHORT).show();
				
			}else{
				
				//������ �Ǻ��� ���� �� ���
				Toast.makeText(this, "��Ƽ�� ���� �߽��ϴ�.",Toast.LENGTH_SHORT).show();
	        	Intent intent = new Intent();
	        	intent.setClass(this, GUI_conti.class);
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	    		startActivity(intent);
				finish();	
			}		
		}
		else if ( v.getId() == R.id.B_cancle) 
		{// ��ҹ�ư
        	Intent intent = new Intent();
        	intent.setClass(this, GUI_conti.class);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    		startActivity(intent);
			finish();
		}
		
	}

    //================================================================================================================================
	// ListView
    // [ 1 , 2 ] 
    public void LV_set()
	{
		//--------------------------------------------------------------------------------------------------------------------------------
		//ListView ---> ����
	    LV_conti= (ListView)findViewById(R.id.conti_list);
	    ListItem = new ArrayList<AL_conti>();
	        
	    //--------------------------------------------------------------------------------------------------------------------------------
	    //Insert ListItem --->DB�� ���� ��ü ��Ƽ ����� ���� ���ϴ�.	    
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_conti = D_test.get_conti_music_mdate();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_conti(2, C_get_conti.getString(C_get_conti.getColumnIndex("c_name")), "�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_mdate")),"�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_writer")), "����: "+ C_get_conti.getString(C_get_conti.getColumnIndex("c_subject")), C_get_conti.getString(C_get_conti.getColumnIndex("c_new"))));        	
			} 
			catch (Exception e) 
			{
				
			}
		    
			if(C_get_conti.moveToNext()== false){break;};
		}
		
		C_get_conti.close();
		D_test.close();
		
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_conti = new LMA_conti(this, ListItem );
		LV_conti.setAdapter(LMA_conti);
		        
	}



    public void Delete_file(String path){

    	File conti = new File(path);
        if(conti.exists() == true){

        	File[] childFileList = conti.listFiles();
        	for(File childFile : childFileList)
        	{
        		if(childFile.isDirectory()) 
        		{
        			Delete_file(childFile.getAbsolutePath());
        		}
        		else 
        		{
        			childFile.delete();	
        		}
        		
        	}
        	conti.delete();    //root ����
        }    	
    }
    
    public void LV_D_set(int position)
	{
    	//File �� ���� �մϴ�.
    	Delete_file(Environment.getExternalStorageDirectory().getAbsolutePath()+"/conti/"+ListItem.get(position).c_name);
    	
    	//Delete DB ---> ��Ƽ�� DB���� �����մϴ�.
        DB_handler D_test = DB_handler.open(this);
        D_test.delete_conti_music(ListItem.get(position).c_name);
        D_test.delete_continuity(ListItem.get(position).c_name);
        D_test.delete_conti_members_c_name(ListItem.get(position).c_name);
        D_test.close();
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
	        	intent.setClass(this, GUI_conti.class);
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	    		startActivity(intent);
				finish();			
				
				return true;
			}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    

}
