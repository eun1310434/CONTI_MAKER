package com.MQ.music.conti_maker_demo;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GUI_conti extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	/*
	 * 1. ��Ƽ�� ��ϵǾ��� ��ü ����� Ȯ�� �Ҽ� �ս��ϴ�.
	 * 2. ��Ƽ�� ���� �� �� �ֽ��ϴ�.
	 * 3. ��Ƽ�� ���� �ϴ� ������ �̵� ��ŵ�ϴ�.
	 */
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//Conti List
	private static ListView LV_conti;
	private static ArrayList<AL_conti> ListItem;
	private static LMA_conti LMA_conti;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//��Ƽ����.	
	private ArrayList<AL_music> ListItem_mm =  new ArrayList<AL_music>();
	
	//Dialog menu title
	private String[] menu = new String [] {"1)  ����","2)  ����","3)  ����"};
	private String[] arrage_menu = new String [] {"��¥","����","�ۼ���"};
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//ImageButton
	private ImageButton IB_make_conti;
	private ImageButton IB_delete_conti;
	private ImageButton IB_arrage_conti;

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
        setContentView(R.layout.gui_conti);
		
		
		DB_set();
		TV_set();
        IB_set();
        LV_set();
		//LV_anim();���ϸ��̼�ȿ��
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
    // [ 3 ] 
    public void IB_set()
    {
    	IB_make_conti =(ImageButton) findViewById(R.id.make_conti);
    	IB_make_conti.setOnClickListener(this);
    	IB_arrage_conti =(ImageButton) findViewById(R.id.arrage_conti);
    	IB_arrage_conti.setOnClickListener(this);
    	IB_delete_conti =(ImageButton) findViewById(R.id.delete_conti);
    	IB_delete_conti.setOnClickListener(this);
    }
    
    //Button�� ����� �����մϴ�.
    @Override
	public void onClick(View v) 
    {
		// TODO Auto-generated method stub
		
		if ( v.getId() == R.id.make_conti) 
		{


				Toast.makeText(GUI_conti.this, "��Ƽ�����",Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent();
				intent.setClass( GUI_conti.this, GUI_conti_make_name.class);
				intent.putExtra("getFrom", "GUI_conti");
			    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				GUI_conti.this.startActivity(intent);
				GUI_conti.this.finish();
				
		}else if(v.getId() == R.id.arrage_conti ){

		
			if( ListItem.size() > 0 && ListItem.get(0).type == 0){
				
				Toast.makeText(GUI_conti.this, "��Ƽ ����",Toast.LENGTH_SHORT).show();
				
				//Making Dialog
				new AlertDialog.Builder(GUI_conti.this)
				.setTitle("��Ƽ ����")
				.setItems(arrage_menu, new DialogInterface.OnClickListener() 
				{		
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{// TODO Auto-generated method stub
						if(which == 0)
	    				{
							//��¥ ������
				        	LV_set_mdate();
	    				}
						else if(which == 1)
	        			{
							//�̸� ������
				        	LV_set_name();
	        			}
						else if(which == 2)
	        			{		
							//�ۼ���
				        	LV_set_writer();
	        			}		
	        		}
	        	}).show();
			}else{
				
			}
			
		}else if(v.getId() == R.id.delete_conti ){

			Toast.makeText(GUI_conti.this, "��Ƽ����",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent();
			intent.setClass( GUI_conti.this, GUI_conti_delete.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_conti.this.startActivity(intent);
			GUI_conti.this.finish();
			
		}
	}

    public void WriteHistory(String _history){
		
    	//ID ������ ����

		DB_handler DB_system = DB_handler.open(this);
		Cursor C_get_system = DB_system.get_System();
		String s_id = C_get_system.getString(C_get_system.getColumnIndex("s_id"));
		String before_history = C_get_system.getString(C_get_system.getColumnIndex("s_history"));
		C_get_system.close();
		DB_system.close();
		
		
		//DB ���
		DB_handler DB_history = DB_handler.open(this);
		DB_history.Update_System_history(s_id, before_history+"\n"+_history);
		DB_history.close();
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
		
		String c_name = "";
		String c_mdate = "";
		String c_writer = "";
		String c_subject = "";
		String c_new = "";

		while(true)
		{
			try 
			{
				c_name = C_get_conti.getString(C_get_conti.getColumnIndex("c_name"));
				c_mdate = C_get_conti.getString(C_get_conti.getColumnIndex("c_mdate"));
				c_writer = C_get_conti.getString(C_get_conti.getColumnIndex("c_writer"));
				c_subject = C_get_conti.getString(C_get_conti.getColumnIndex("c_subject"));
				c_new = C_get_conti.getString(C_get_conti.getColumnIndex("c_new"));
				
				ListItem.add(new AL_conti(0, c_name, "�ۼ���: "+c_mdate,"�ۼ���: "+c_writer, "����: "+c_subject,c_new ));
				
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
			}
			 
			
			
			
			
		    
			if(C_get_conti.moveToNext()== false){break;};
		}
		
		C_get_conti.close();
		D_test.close();
		
		//--------------------------------------------------------------------------------------------------------------------------------        
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_conti = new LMA_conti(this, ListItem );
		LV_conti.setAdapter(LMA_conti);
		
		//---
		
		
		//--------------------------------------------------------------------------------------------------------------------------------
	    //onItemClickListener ---> ª�� ������ �Ǹ� ��Ƽ�� ���� �� �� �ֽ��ϴ�.	
		LV_conti.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{	
			
			@Override
	 	    public void onItemClick(AdapterView parent, View view, final int position, long id) 
			{
	 		// TODO Auto-generated method stub
				
				//History
				WriteHistory("		"+ListItem.get(position).c_name);
				
				if(ListItem.get(position).type == 0)
				{

					LV_R_set(ListItem.get(position).c_name);
					
					
					Intent intent = new Intent();
					intent.setClass( GUI_conti.this, GUI_conti_main.class);
					intent.putExtra("c_name", ListItem.get(position).c_name);
		        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
					GUI_conti.this.startActivity(intent);
					GUI_conti.this.finish();
					
				}else if(ListItem.get(position).type == 1){
					
				}
	 		}
	 	});
		
		
		
		//--------------------------------------------------------------------------------------------------------------------------------
	    //onItemLongClickListener ---> ���� ������ �Ǹ� �ȳ�â�� �Բ� ������ ���� �� �� �ְ� �մϴ�.
		LV_conti.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() 
		{		
			@Override
			public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) 
			{		
				
				return false;
			}
		});
	}
    
    /*
    public void LV_anim(){
    	
    	AnimationSet set = new AnimationSet(true);
 		Animation rtl = new TranslateAnimation(
 		    Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0.0f,
 		    Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f
 		);
 		rtl.setDuration(200);
 		set.addAnimation(rtl);

 		Animation alpha = new AlphaAnimation(0.0f, 1.0f);
 		alpha.setDuration(200);
 		set.addAnimation(alpha);

 		LayoutAnimationController controller =
 	        new LayoutAnimationController(set, 0.2f);
 		LV_conti.setLayoutAnimation(controller);
 		LV_conti.setVisibility(View.VISIBLE);
    }
    */
    
    
	public void LV_R_set(String c_name){


		ListItem_mm.clear();
        DB_handler D_test = DB_handler.open(this);
        Cursor C_get_conti_music = D_test.get_conti_music(c_name);
        while(true){
        	
        	ListItem_mm.add(new AL_music(0,  C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("m_id")), C_get_conti_music.getString(C_get_conti_music.getColumnIndex("m_name")), C_get_conti_music.getString(C_get_conti_music.getColumnIndex("m_code")), C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("m_img")), ""));
        	if(C_get_conti_music.moveToNext() == false){break;};
        }
        
        C_get_conti_music.close();
        D_test.close();
      	
	}
	
    public static void LV_set_mdate(ArrayList<AL_conti> insert_ListItem)
	{
	    
    	ListItem.clear();
    	ListItem.addAll(insert_ListItem);
		LMA_conti.notifyDataSetChanged();
		
		LV_conti.setSelectionFromTop(	LV_conti.getFirstVisiblePosition()	,	0	);
	}
    
    
    
    //================================================================================================================================
	// ListView
    // [ 1 , 2 ] 
    public void LV_set_mdate()
	{
	    
    	ListItem.clear();
    	
	    DB_handler D_test = DB_handler.open(this);
		Cursor C_get_conti = D_test.get_conti_music_mdate();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_conti(0, C_get_conti.getString(C_get_conti.getColumnIndex("c_name")), "�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_mdate")),"�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_writer")), "����: "+ C_get_conti.getString(C_get_conti.getColumnIndex("c_subject")),C_get_conti.getString(C_get_conti.getColumnIndex("c_new"))));        	
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
			}
		    
			if(C_get_conti.moveToNext()== false){break;};
		}
		
		C_get_conti.close();
		D_test.close();
		
		//--------------------------------------------------------------------------------------------------------------------------------        
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_conti.notifyDataSetChanged();
		
		LV_conti.setSelectionFromTop(	LV_conti.getFirstVisiblePosition()	,	0	);
	}

    //================================================================================================================================
	// ListView
    // [ 1 , 2 ] 
    public void LV_set_name()
	{
	    
    	ListItem.clear();
    	
	    DB_handler D_test = DB_handler.open(this);
		Cursor C_get_conti = D_test.get_conti_music_name();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_conti(0, C_get_conti.getString(C_get_conti.getColumnIndex("c_name")), "�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_mdate")),"�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_writer")), "����: "+ C_get_conti.getString(C_get_conti.getColumnIndex("c_subject")),C_get_conti.getString(C_get_conti.getColumnIndex("c_new"))));        	
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
		            	
			}
		    
			if(C_get_conti.moveToNext()== false){break;};
		}
		
		C_get_conti.close();
		D_test.close();
		
		//--------------------------------------------------------------------------------------------------------------------------------        
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_conti.notifyDataSetChanged();
		
		LV_conti.setSelectionFromTop(	LV_conti.getFirstVisiblePosition()	,	0	);
	}

    //================================================================================================================================
	// ListView
    // [ 1 , 2 ] 
    public void LV_set_writer()
	{
	    
    	ListItem.clear();
    	
	    DB_handler D_test = DB_handler.open(this);
		Cursor C_get_conti = D_test.get_conti_music_writer();
		        
		while(true)
		{
			try 
			{
				ListItem.add(new AL_conti(0, C_get_conti.getString(C_get_conti.getColumnIndex("c_name")), "�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_mdate")),"�ۼ���: "+C_get_conti.getString(C_get_conti.getColumnIndex("c_writer")), "����: "+ C_get_conti.getString(C_get_conti.getColumnIndex("c_subject")), C_get_conti.getString(C_get_conti.getColumnIndex("c_new"))));        	
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
		            	
			}
		    
			if(C_get_conti.moveToNext()== false){break;};
		}
		
		C_get_conti.close();
		D_test.close();
		
		//--------------------------------------------------------------------------------------------------------------------------------        
		//mapping to the ListView ---> ������ �� ������ Mapping ��ŵ�ϴ�.
		LMA_conti.notifyDataSetChanged();
		LV_conti.setSelectionFromTop(	LV_conti.getFirstVisiblePosition()	,	0	);
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
        D_test.close();
        
        //Delete ListView ListItem ---> ��Ƽ�� ListView���� ���� �մϴ�.
        ListItem.remove(position);
        
        // ListView Setting ---> ListView�� ���Ӱ� �����մϴ�.
		LMA_conti.notifyDataSetChanged();
		LV_conti.setSelectionFromTop(	LV_conti.getFirstVisiblePosition()	,	0	);
	}
    
    
    //=================================
    
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			if( KeyCode == KeyEvent.KEYCODE_BACK ){

				Intent intent = new Intent();
		        intent.setClass(GUI_conti.this, GUI_main.class);
				intent.putExtra("getType","a");
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		        GUI_conti.this.startActivity(intent);
		        GUI_conti.this.finish();
				return true;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}

	 
    
}