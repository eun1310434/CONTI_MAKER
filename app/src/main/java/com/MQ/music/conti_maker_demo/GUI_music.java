package com.MQ.music.conti_maker_demo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class GUI_music extends Activity  implements OnClickListener {
	/**
     *  Manual
     *  
     *  1) ����ڰ� �Ǻ��� �˻��Ҽ� �ִ°�
     *  
     *  Check Date
     *  2013-03-11 (��) 
     */


	private String TAG = "GUI_music";
    
	/** [1] ACTIVITY */
	private String TAG_ACTIVITY = "---- [1] ACTIVITY";


	/** [2] GUI*/
	private String TAG_GUI = "---- [2] GUI";
	
	//EditText
	private EditText ET_find_music;

	//ListView
	private ListView LV_music;
	private ArrayList<AL_music> ListItem;
	private LMA_music LMA_music;
	private Animation anim_LV_music = new AlphaAnimation(0, 1);
	
	
	//ImageButton
	private ImageButton IB_letter;
	private ImageButton IB_bag;
	
	//TextView
	private TextView TV_my_s_id;


	/** [3] GCM*/
	
	/** [4] SYSTEM */
	
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
        
        //[1] ACTIVITY
        setContentView(R.layout.gui_music);
        
        //[2] GUI
        TV_set();
        ET_set();
        LV_set();
        IB_set();
        AM_set();
        
        //[3] GCM 

        //[4] SYSTEM 
        DB_set();
    }
	//---------------------------------------------------------------------------------------------------------------------------------
	//[1] ACTIVITY
	

	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[2] GUI 
	public void TV_set() {
		TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
	}
	
	 public void ET_set(){
		ET_find_music = (EditText) findViewById(R.id.ET_find_music);
		ET_find_music.addTextChangedListener(textWatcherInput);  
	}
	 public void LV_set(){
	    	
	    	LV_music= (ListView)findViewById(R.id.LV_music);
	        ListItem = new ArrayList<AL_music>();
	        
	        //[ListItem_m ����] �Ǻ���ü �˻�
	        DB_handler D_test = DB_handler.open(this);
	        Cursor C_get_music_name = D_test.get_music_name();
	        
	        if(C_get_music_name.getCount() > 0)
	        {
	            ListItem.add(new AL_music(2,-1,"","",0,"����"));
	            while(true)
	            {
	            	String m_name = C_get_music_name.getString(C_get_music_name.getColumnIndex("m_name"));
	                ListItem.add(new AL_music( C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_bag")) , C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_id")),m_name, C_get_music_name.getString(C_get_music_name.getColumnIndex("m_code")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_img")),""));
	            	if(C_get_music_name.moveToNext()== false){break;};
	            }
	        }
	        
	        C_get_music_name.close();
	 		D_test.close();
	 		
	        LMA_music = new LMA_music(this, ListItem );
	        
	        LV_music.setAdapter(LMA_music);
	        LV_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				
	 	    	@Override
	 	    		public void onItemClick(AdapterView parent, View view, int position, long id) {
	 					// TODO Auto-generated method stub
	 	    					

 	    						WriteHistory("		"+ListItem.get(position).m_name);
 	    						if( ListItem.get(position).type == 1){
		 							
 	    							final int pos = position;
 	    							
		 							//Making Dialog
		 							new AlertDialog.Builder(GUI_music.this)
		 							.setTitle(ListItem.get(pos).m_name)
		 							.setItems(new String[] {"���� ��� ","���� ���","���� ����","YouTube"}, new DialogInterface.OnClickListener() 
		 							{		
		 								@Override
		 								public void onClick(DialogInterface dialog, int which) 
		 								{// TODO Auto-generated method stub
		 									
		 									if(which == 0){

		 										File f = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/contimaker/score/wc"+Integer.toString(ListItem.get(pos).m_id)+".png");

												Log.e("test", String.valueOf(f.exists()));

												if(f.exists()){
		 											Intent intent = new Intent();
		 											intent.setClass( GUI_music.this, GUI_music_view_portrait.class);
		 											intent.putExtra("getFrom", "GUI_music");
		 											intent.putExtra("m_id", ListItem.get(pos).m_id);
		 											intent.putExtra("ItemSet", "off");
		 											intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		 											GUI_music.this.startActivity(intent);
		 											
		 										}else{
				 	    							Intent intent = new Intent();
						 							intent.setClass( GUI_music.this, GUI_down_portrait.class);
						 							intent.putExtra("getFrom", "GUI_music");
						 							intent.putExtra("m_id", ListItem.get(pos).m_id);
						 							intent.putExtra("screen", "Portrait");
						 							intent.putExtra("ItemSet", "off");
						 							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
						 							GUI_music.this.startActivity(intent);
		 											
		 										}
		 										
		 									}else if(which == 1){

		 										File f = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/contimaker/score/wc"+Integer.toString(ListItem.get(pos).m_id)+"_1.png");

												Log.e("test", String.valueOf(f.exists()));
												if(f.exists()){
		 											Intent intent = new Intent();
		 											intent.setClass( GUI_music.this, GUI_music_view_landscape.class);
		 											intent.putExtra("getFrom", "GUI_music");
		 											intent.putExtra("m_id", ListItem.get(pos).m_id);
		 											intent.putExtra("ItemSet", "off");
		 											intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		 											GUI_music.this.startActivity(intent);
		 											
		 										}else{

				 	    							Intent intent = new Intent();
						 							intent.setClass( GUI_music.this, GUI_down_landscape.class);
						 							intent.putExtra("getFrom", "GUI_music");
						 							intent.putExtra("m_id", ListItem.get(pos).m_id);
						 							intent.putExtra("screen", "Landscape");
						 							intent.putExtra("ItemSet", "off");
						 							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
						 							GUI_music.this.startActivity(intent);
		 										}
		 										
		 									}else if(which == 2){
		 										//���� ����

		 										Intent intent = new Intent();
		 										intent.setClass( GUI_music.this, GUI_music_view_share.class);
		 										intent.putExtra("getFrom", "GUI_music_view_landscape");
		 										intent.putExtra("screen", "");
		 										intent.putExtra("m_id", ListItem.get(pos).m_id);
		 										intent.putExtra("ItemSet", "on");
		 										intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		 										GUI_music.this.startActivity(intent);
		 										
		 										
		 									}else if(which == 3){
		 										//YouTube
		 										Uri uri = Uri.parse("http://www.youtube.com/results?search_query="+ListItem.get(pos).m_name);
		 										Intent it  = new Intent(Intent.ACTION_VIEW,uri);
		 										startActivity(it);
		 										
		 									}
		 									
		 				        		}
		 				        	}).show();
		 							
 	    						}else if(ListItem.get(position).type == 0){
 	    							Toast.makeText(GUI_music.this, "�����ϴ� ����Ʈ �̵�", Toast.LENGTH_SHORT).show();
 	    						}
	 					}
	 				});
	        
			LV_music.setVisibility(View.VISIBLE);
			LV_music.setAnimation(anim_LV_music);
	    }
	
      
	TextWatcher textWatcherInput = new TextWatcher() {  
        // �ԷµǴ� ������ Ȯ���Ͽ� �ڵ����� �˻��մϴ�.
    	
        @Override  
        public void onTextChanged(CharSequence s, int start, int before, int count) {  
            // TODO Auto-generated method stub  
        	if(Check_Space(s.toString()))
        	{
        		LV_music_all();
        		//��ü�� �˻��մϴ�.
        	}else if(s.toString().equals("bag")){
        		LV_music_bag();
        	}
        	else
        	{
                LV_music_set(s.toString());     
                //Log.i("onTextChanged", s.toString()); 
                //�Է��� ������ ������ �˻��մϴ�.
        	}   
        }  
          
        @Override  
        public void beforeTextChanged(CharSequence s, int start, int count,  
                int after) {  
            // TODO Auto-generated method stub  
            //Log.i("beforeTextChanged", s.toString()); 
        }  
          
        @Override  
        public void afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
            //Log.i("afterTextChanged", s.toString()); 
        }  
    };  

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
    
    public void LV_music_all(){
        
    	//[ListItem_m �ʱ�ȭ]
        ListItem.clear();
        
        //[ListItem_m ����]
		DB_handler D_test = DB_handler.open(this);
        Cursor C_get_music_name = D_test.get_music_name();
        
        if(C_get_music_name.getCount() > 0)
        {
            ListItem.add(new AL_music(2,-1,"","",0,"����"));
            while(true)
            {
                ListItem.add(new AL_music(C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_bag")) , C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_id")),C_get_music_name.getString(C_get_music_name.getColumnIndex("m_name")), C_get_music_name.getString(C_get_music_name.getColumnIndex("m_code")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_img")),""));
                if(C_get_music_name.moveToNext()== false){break;};
            }
        }
        
        C_get_music_name.close();
 		D_test.close();

 		//[Music_ListView ����]
        LMA_music.notifyDataSetChanged();
        LV_music.setSelectionFromTop(	0	,	0	);
         
    }
 
    //�˻� �ؼ� ������ ����� ��Ͽ��� �����ݴϴ�.
	public void LV_music_set(String insert)
	{

		//[ListItem_m �ʱ�ȭ]
       ListItem.clear();
       
       //[ListItem_m ����]
		DB_handler D_test = DB_handler.open(this);
       Cursor C_get_music_name = D_test.get_music_name(insert);
       Cursor C_get_music_words = D_test.get_music_words(insert);
       Cursor C_get_music_code = D_test.get_music_code(insert);
       
       if(C_get_music_name.getCount() > 0)
       {
           ListItem.add(new AL_music(2,-1,"","",0,"����"));
           while(true)
           {
           	try {
                ListItem.add(new AL_music(C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_bag")) , C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_id")),C_get_music_name.getString(C_get_music_name.getColumnIndex("m_name")), C_get_music_name.getString(C_get_music_name.getColumnIndex("m_code")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_img")),""));
                
   			} catch (Exception e) {
   				// TODO: handle exception
   			}
           	if(C_get_music_name.moveToNext()== false){break;};
           }
       }
       

       if(C_get_music_words.getCount() > 0)
       {
           ListItem.add(new AL_music(2,-1,"","",0,"ù ����"));
           while(true)
           {
           	try {
       			ListItem.add(new AL_music(C_get_music_words.getInt(C_get_music_words.getColumnIndex("m_bag")) , C_get_music_words.getInt(C_get_music_words.getColumnIndex("m_id")),C_get_music_words.getString(C_get_music_words.getColumnIndex("m_words")), C_get_music_words.getString(C_get_music_words.getColumnIndex("m_code")), C_get_music_words.getInt(C_get_music_words.getColumnIndex("m_img")),""));
       			
           	} catch (Exception e) {
   				// TODO: handle exception
   			}
           	if(C_get_music_words.moveToNext()== false){break;};
           }
       }
       

       if(C_get_music_code.getCount() > 0)
       {
       	ListItem.add(new AL_music(2,-1,"","",0,"�ڵ�"));
           while(true)
           {
           	try {	
           			ListItem.add(new AL_music(C_get_music_code.getInt(C_get_music_code.getColumnIndex("m_bag")) , C_get_music_code.getInt(C_get_music_code.getColumnIndex("m_id")),C_get_music_code.getString(C_get_music_code.getColumnIndex("m_name")), C_get_music_code.getString(C_get_music_code.getColumnIndex("m_code")), C_get_music_code.getInt(C_get_music_code.getColumnIndex("m_img")),""));
           			

           	} catch (Exception e) {
   				// TODO: handle exception
   			}
           	if(C_get_music_code.moveToNext()== false){break;};
           }
       }

       
       C_get_music_code.close();
       C_get_music_words.close();
       C_get_music_name.close();
		D_test.close();

 		//[Music_ListView ����]
        LMA_music.notifyDataSetChanged();
        LV_music.setSelectionFromTop(	0	,	0	);
        
	}

	// //�˻� �ؼ� ������ ����� ��Ͽ��� �����ݴϴ�.
	public void LV_music_bag()
	{

		//[ListItem_m �ʱ�ȭ]
       ListItem.clear();
       
       //[ListItem_m ����]
		DB_handler D_test = DB_handler.open(this);
       Cursor C_get_music_new = D_test.get_music_bag();
       
       if(C_get_music_new.getCount() > 0)
       {
           ListItem.add(new AL_music(2,-1,"","",0,"������ �Ǻ�"));
           while(true)
           {
           	try {
                ListItem.add(new AL_music(C_get_music_new.getInt(C_get_music_new.getColumnIndex("m_bag")) , C_get_music_new.getInt(C_get_music_new.getColumnIndex("m_id")),C_get_music_new.getString(C_get_music_new.getColumnIndex("m_name")), C_get_music_new.getString(C_get_music_new.getColumnIndex("m_code")), C_get_music_new.getInt(C_get_music_new.getColumnIndex("m_img")),""));
                
   			} catch (Exception e) {
   				// TODO: handle exception
   			}
           	if(C_get_music_new.moveToNext()== false){break;};
           }
       }

       
       C_get_music_new.close();
		D_test.close();

 		//[Music_ListView ����]
        LMA_music.notifyDataSetChanged();
        LV_music.setSelectionFromTop(	0	,	0	);
        
	}
	
	public Boolean Check_Space(String insert)
	{
		Boolean space_check = false;	

		if(insert.equals(""))
		{
			space_check = true;
		}
		else
		{
			for(int i = 0 ; i < insert.length() ; i++)
			{
				if(insert.charAt(i) == ' ')
				{
					space_check = true;
				}
				else
				{
					space_check = false;
					break;
				}
			}
		}
		
		return space_check;
	}
    public void LV_null(){
        
        ListItem.clear();
        ListItem.add(new AL_music(3,0,"","",0,""));
        LMA_music = new LMA_music(this, ListItem );
        LV_music.setAdapter(LMA_music);
         
    }
    public void IB_set(){
    	IB_letter = (ImageButton) findViewById(R.id.IB_letter);
    	IB_letter.setOnClickListener(this);
		IB_letter.setImageResource(R.drawable.b_letter);
    	
		IB_bag = (ImageButton) findViewById(R.id.IB_bag);
		IB_bag.setOnClickListener(this);
		IB_bag.setImageResource(R.drawable.b_bag);
    }
    //Button�� ����� �����մϴ�.
    @Override
	public void onClick(View v) 
    {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.IB_letter){
			
			
			Toast.makeText(GUI_music.this, "�Ǻ���û",Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent();
			intent.setClass( GUI_music.this, GUI_setting_request.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_music.this.startActivity(intent);	

		}else if(v.getId() == R.id.IB_bag){
			ET_find_music.setText("bag");
		}
	}
    public void AM_set(){
	  	anim_LV_music.setDuration(400);
    }
    
	//---------------------------------------------------------------------------------------------------------------------------------
	//[4] SYSTEM 
	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor c_get_id = D_test.get_System();
		s_id = c_get_id.getString(c_get_id.getColumnIndex("s_id"));
		c_get_id.close();
		D_test.close();
		
		TV_my_s_id.setText("ID: "+ s_id);
	}
	@Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			if( KeyCode == KeyEvent.KEYCODE_BACK ){

				WriteHistory("- ����");
				Intent intent = new Intent();
				intent.setClass(GUI_music.this, GUI_main.class);
				intent.putExtra("getType","a");
		        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		        GUI_music.this.startActivity(intent);
		        GUI_music.this.finish();
					
				return true;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    
}