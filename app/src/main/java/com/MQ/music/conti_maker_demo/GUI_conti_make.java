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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;



public class GUI_conti_make extends Activity  implements OnClickListener, OnCheckedChangeListener {
    /** Called when the activity is first created. */
	/*
	 * 1. ��Ƽ�� ���Ӱ� ����ϴ�. 	----> Ptype = 0 [getFrom = GUI_conti ]
	 * 2. ��Ƽ�� �����մϴ�.		----> Ptype = 1 [getFrom = GUI_conti_reset]
	 * 3. ��Ƽ�� �� ���ϴ� �Ǻ��� �˻��մϴ�. [ ���� | ù���� | �ڵ� ]
	 * 4. ��Ƽ�� ������ �����մϴ�.
	 */
	//--------------------------------------------------------------------------------------------------------------------------------
	//Process Type
	private int Ptype = -1;
	private String c_name;
	private String c_writer;
	private String c_subject;
	private String menu_title;

	//--------------------------------------------------------------------------------------------------------------------------------
	//�߰��� �Ǻ��� �˻��մϴ�.
	private EditText ET_find_music;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//�˻��� �Ǻ��� ���� �ݴϴ�.	
	private ListView LV_music;
	private ArrayList<AL_music> ListItem_m; 
	/*
	 *  type = 0	�˻��� �Ǻ��� ������ ��Ÿ���ϴ�. 
	 *  type = 1 	�˻��� �Ǻ��� Ÿ��Ʋ�� ��Ÿ���ϴ�.
	 */
	private LMA_music LMA_music;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//������ �Ǻ��� ���� �ݴϴ�.	
	private ListView LV_my_music;
	private ArrayList<AL_music> ListItem_mm;
	private ArrayList<AL_music> ListItem_mm_copy;
	private LMA_my_music LMA_my_music;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//������ �Ǻ��� �����մϴ�.	
	private RadioGroup RG_select_type;
	private int select_type = 0; // 0 --> ����, 1 --> ����
	private int select_position = -1; //��Ƽ�� ������ �ٲٷ� �Ҷ� ����մϴ�.
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//�������� �Ѿ�� ��ư	
	private ImageButton IB_up;
	private ImageButton IB_down;
	private ImageButton IB_add;
	
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
        setContentView(R.layout.gui_conti_make);
        

        Intent_set();
        LV_music_set();
    	LV_my_music_set();
        ET_set();
        RG_select_type_set();
        IB_making_set();
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
    //================================================================================================================================
	//Intent SET
    /*
     * 1. Ptype
     * 2. ListItem
     * 3. c_name
     * 4. ListView SET
     */

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

    public void Intent_set()
    {
    	Intent intent = getIntent();
    	Ptype = intent.getExtras().getInt("Ptype");
        c_name = intent.getExtras().get("c_name").toString();	
        c_writer = intent.getExtras().get("c_writer").toString();	
        c_subject = intent.getExtras().get("c_subject").toString();	
    	menu_title = intent.getExtras().get("menu_title").toString();	
    	ListItem_mm = intent.getParcelableArrayListExtra("ListItem_mm");
    	ListItem_mm_copy = (ArrayList<AL_music>) ListItem_mm.clone();
    	
    	for(int i = 0 ; i < ListItem_mm.size() ; i++){
    		
    		int	m_id = ListItem_mm.get(i).m_id; 
    		String m_name = ListItem_mm.get(i).m_name;
    		String m_code = ListItem_mm.get(i).m_code;
    		int m_img = ListItem_mm.get(i).m_img;
    		String title = ListItem_mm.get(i).title;
    		
    		ListItem_mm.set(i, new AL_music(2,m_id, m_name, m_code, m_img, title));
    	}
    	
    	
    }
    
    
    //================================================================================================================================
  	//ListView_music
	public void LV_music_set()
	{
		//[ListItem_m ����] �Ǻ��˻�ListView
        ListItem_m = new ArrayList<AL_music>();
        
		//[Music_ListView ����] �Ǻ��˻�ListView
    	LV_music= (ListView)findViewById(R.id.music_list);
        
        //[ListItem_m ����] �Ǻ���ü �˻�
        DB_handler D_test = DB_handler.open(this);
        Cursor C_get_music_name = D_test.get_music_name();
       
        
        if(C_get_music_name.getCount() > 0)
        {
        	 ListItem_m.add(new AL_music(2,-1,"","",0,"����"));
	            while(true)
	            {
	            	
	                ListItem_m.add(new AL_music(C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_bag")) , C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_id")),C_get_music_name.getString(C_get_music_name.getColumnIndex("m_name")), C_get_music_name.getString(C_get_music_name.getColumnIndex("m_code")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_img")),""));
	                
	            	if(C_get_music_name.moveToNext()== false){break;};
	            }
        }
        
        C_get_music_name.close();
 		D_test.close();
 		
 		//[Music_ListView ����] 
        LMA_music = new LMA_music(this, ListItem_m );
        
        //[Music_ListView Mapping] 
        LV_music.setAdapter(LMA_music);
        
        //[Music_ListView Listener]
        LV_music.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
        		@Override
 	    		public void onItemClick(AdapterView parent, View view, final int position, long id) 
 	    		{	// TODO Auto-generated method stub
 	    			//�˻���Ͽ��� �Ǻ��� ������ ���
 	    					if(ListItem_m.get(position).type == 1)
 	    					{
 	    						new AlertDialog.Builder(GUI_conti_make.this)
 	 	    					 .setTitle("��Ƽ����")
 	 	    					 .setMessage(String.valueOf(ListItem_mm.size()+1)+")  "+ListItem_m.get(position).m_name+"    "+ListItem_m.get(position).m_code)
 	 	    					 .setPositiveButton("����", new DialogInterface.OnClickListener() 
 	 	    					 {
 	 	    						@Override
 	 	    						public void onClick(DialogInterface dialog, int which) 
 	 	    						{	// TODO Auto-generated method stub
 	 	    							// �˻��� �Ǻ������ �ڽ��� �Ǻ� ������� �߰�
 	 	    							if( ListItem_mm.size() < 13 )
 	 	    							{
 	 	    								LV_my_music_Gset(ListItem_m.get(position).m_id	,	ListItem_m.get(position).m_name ,	ListItem_m.get(position).m_code,	ListItem_m.get(position).m_img);
 	 	    					 		}else{
 	 	    					 			Toast.makeText(GUI_conti_make.this, "[���] 14 �� �̻��� �߰��� �ȵ˴ϴ�.", Toast.LENGTH_LONG).show();
 	 	    					 		}
 	 	    	    				}
 	 	    					})
 	 	    					.setNeutralButton("�Ǻ�����", new DialogInterface.OnClickListener() 
 	 	    					{
 	 	    						@Override
 	 	    						public void onClick(DialogInterface dialog, int which) 
 	 	    						{	// TODO Auto-generated method stub
 	 	    							// ������ �Ǻ��� �̸������մϴ�.
 	 	    							File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(ListItem_m.get(position).m_id)+".png");
 	 	    							if(f.exists()){


 	 	    								Intent intent = new Intent();
 	 	    								intent.setClass( GUI_conti_make.this, GUI_music_view_portrait.class);
 	 	    								intent.putExtra("getFrom", "GUI_music");
 	 	    								intent.putExtra("m_id", ListItem_m.get(position).m_id);
 	 	    								intent.putExtra("ItemSet", "off");
 	 	    							    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
 	 	    							  GUI_conti_make.this.startActivity(intent);
 	 	    								
 	 	    							}else{
 	 	    								Intent intent = new Intent();
 	 	    								intent.setClass( GUI_conti_make.this, GUI_down_portrait.class);
 	 	    								intent.putExtra("getFrom", "GUI_music");
 	 	    								intent.putExtra("m_id", ListItem_m.get(position).m_id);
 	 	    								intent.putExtra("screen", "Portrait");
 	 	    								intent.putExtra("ItemSet", "off");
 	 	    							    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
 	 	    							  GUI_conti_make.this.startActivity(intent);
 	 	    								
 	 	    							}	
 	 	    						}
 	 	    					})
 	 	    					.setNegativeButton("���", new DialogInterface.OnClickListener() 
 	 	    					{
 	 	    						@Override
 	 	    						public void onClick(DialogInterface dialog, int which) 
 	 	    						{	// TODO Auto-generated method stub
 	 	    						
 	 	    						}
 	 	    					}).show();
 	    						
 	    						
 	    					}else if(ListItem_m.get(position).type == 0){//�Ǻ��� �����ؾߵǴ°��

 	    						Toast.makeText(GUI_conti_make.this, "�Ǻ�����",Toast.LENGTH_SHORT).show();
 	    					}
 				}
 		});
    }


	public void LV_my_music_Gset(int m_id , String m_name ,String m_code, int m_img){
		//[ListItem_mm ����]
		ListItem_mm.add(new AL_music(2 , m_id, m_name , m_code , m_img , ""));
		
		//[Adpater �� ���Ͽ� ListView�� ���� �� ���� ǥ��]
		LMA_my_music.notifyDataSetChanged();
		
		IB_view_set();
		
		LV_my_music.setSelectionFromTop(	ListItem_mm.size()	,	0	);
	}
	
    //================================================================================================================================
  	//ListView_my_music
	public void LV_my_music_set(){
        
		//[My_ListView ����]
    	LV_my_music= (ListView)findViewById(R.id.my_music_list);
        
        //[My_ListView ����]
        LMA_my_music = new LMA_my_music(this, ListItem_mm );
        
        //[My_ListView Mapping]
        LV_my_music.setAdapter(LMA_my_music);
        
        //[My_ListView Listener]
        LV_my_music.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {	
 	    		@Override
 	    		public void onItemClick(AdapterView parent, View view, final int position, long id) 
 	    		{	// TODO Auto-generated method stub
 	    				
 	    				if(select_type == 0)//����
 	    				{
 	    					//String.valueOf(ListItem_mm.size()+1)+")  "+ListItem_m.get(position).m_name+"    "+ListItem_m.get(position).m_code
 	    	            	new AlertDialog.Builder(GUI_conti_make.this)
 	    					 .setTitle("��Ƽ����")
 	    					 .setMessage(String.valueOf(position+1)+") "+ListItem_mm.get(position).m_name+"    "+ListItem_mm.get(position).m_code)
 	    					 .setPositiveButton("����", new DialogInterface.OnClickListener() {
 	    						
 	    						@Override
 	    						public void onClick(DialogInterface dialog, int which) {
 	    							// TODO Auto-generated method stub
 	    							// �ڽ��� ��Ͽ��� ������ �Ǻ��� ����
 	    	    					LV_my_music_Dset(position);
 	    						}
 	    					})
 	    					.setNegativeButton("���", new DialogInterface.OnClickListener() {
 	    						
 	    						@Override
 	    						public void onClick(DialogInterface dialog, int which) {
 	    							// TODO Auto-generated method stub
 	    						}
 	    					}).show();
 	    				}
 	    				//������ �Ǻ��� ������ �ٲܰ��
 	    				else if(select_type == 1)
 	    				{
 	    					LV_my_music_Oset(); // ������ ������ ��찡 �ִٸ�.
 	    					LV_my_music_choice(position);// �Ǻ��� �����ߴٴ� ����� ��Ÿ���ϴ�.
 	    					select_position = position;
 	    					IB_view_set();
 	    				}
 	    						
 					}
 				});
    }

	public void LV_my_music_Dset(int position){// �ڽ��� �Ǻ� ��Ͽ��� ������ Item �� ����
		
		//[ListItem_mm Delete]������ �ڽ��� �Ǻ� Delete �� �մϴ�.
		ListItem_mm.remove(position);
		
		//[Adpater �� ���Ͽ� ListView�� ���� �� ���� ǥ��]
		LMA_my_music.notifyDataSetChanged();
		
		//LV_my_music.setSelectionFromTop(	LV_my_music.getFirstVisiblePosition()	,	0	);
        
	}
	
	public void LV_my_music_choice(int position){// �ڽ��� �Ǻ� ��Ͽ��� ������ �ٲ� Item ��  ù ����
		
		//[ListItem_mm ReSet]������ �ڽ��� �Ǻ��� ����� �ٸ��� �ϱ� ���� ReSet�� �մϴ�.
		ListItem_mm.set(position,new AL_music(3,  ListItem_mm.get(position).m_id,ListItem_mm.get(position).m_name.toString(), ListItem_mm.get(position).m_code.toString(), ListItem_mm.get(position).m_img,""));
		
		LMA_my_music.notifyDataSetChanged();
		
		//LV_my_music.setSelectionFromTop(	LV_my_music.getFirstVisiblePosition()	,	0	);
        	
	}
	
	public void LV_my_music_replace(int now_position , int move_position, String type){
		
		
		int f_m_id = ListItem_mm.get(now_position).m_id;
		String f_m_name = ListItem_mm.get(now_position).m_name.toString();
		String f_m_code = ListItem_mm.get(now_position).m_code.toString();
		int f_m_img = ListItem_mm.get(now_position).m_img;
		
		//[ListItem_mm ReSet]������ ������ ��ġ�� ���� ��ġ��  Replace �մϴ�.
		// �ι�° ������ ���� ó�� ������ ��ġ�� �̵�
		ListItem_mm.set(now_position, new AL_music(2,ListItem_mm.get(move_position).m_id,  ListItem_mm.get(move_position).m_name.toString(), ListItem_mm.get(move_position).m_code.toString(), ListItem_mm.get(move_position).m_img,""));
		
		// ó�� ������ ���� �ι�° ������ ��ġ�� �̵�
		ListItem_mm.set(move_position, new AL_music(3,  f_m_id ,f_m_name, f_m_code, f_m_img,""));
		

		LMA_my_music.notifyDataSetChanged();
	
		IB_view_set();

		/*
		 * ȭ�鿡 ����Ʈ�䰡 �ٳ����� ���� ���
		 */
		if(type.equals("up") && select_position < LV_my_music.getFirstVisiblePosition() ){
			
			LV_my_music.setSelectionFromTop(	LV_my_music.getFirstVisiblePosition()-1	,	0	);
			
		}else if(type.equals("down") && select_position >= LV_my_music.getLastVisiblePosition() ){
			
				LV_my_music.setSelectionFromTop(	LV_my_music.getFirstVisiblePosition()+1	,	0	);
			
		}else{
			
			//LV_my_music.setSelectionFromTop(	LV_my_music.getFirstVisiblePosition()	,	0	);
			
		}
		
        
	}
    
	//================================================================================================================================
  	//Edit Text Set
	public void ET_set(){
		ET_find_music = (EditText) findViewById(R.id.find_music);
		ET_find_music.addTextChangedListener(textWatcherInput);  
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
        	}else if(s.toString().equals("new")){
        		LV_music_new();
        	}
        	else
        	{
                Log.i("onTextChanged", s.toString()); 
                LV_music_set(s.toString());     
                //�Է��� ������ ������ �˻��մϴ�.
        	}  
        }  
          
        @Override  
        public void beforeTextChanged(CharSequence s, int start, int count,  
                int after) {  
            // TODO Auto-generated method stub  
            Log.i("beforeTextChanged", s.toString()); 
        }  
          
        @Override  
        public void afterTextChanged(Editable s) {  
            // TODO Auto-generated method stub  
            Log.i("afterTextChanged", s.toString()); 
        }  
    };  

    public void LV_music_all(){
        
    	//[ListItem_m �ʱ�ȭ]
        ListItem_m.clear();
        
        //[ListItem_m ����]
		DB_handler D_test = DB_handler.open(this);
        Cursor C_get_music_name = D_test.get_music_name();
        
        
        if(C_get_music_name.getCount() > 0)
        {
            ListItem_m.add(new AL_music(2,-1,"","",0,"����"));
            while(true)
            {
                ListItem_m.add(new AL_music(C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_bag")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_id")),C_get_music_name.getString(C_get_music_name.getColumnIndex("m_name")), C_get_music_name.getString(C_get_music_name.getColumnIndex("m_code")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_img")),""));
               if(C_get_music_name.moveToNext()== false){break;};
            }
        }
        
        C_get_music_name.close();
 		D_test.close();
 		
 		//[Adapater �� ���Ͽ� ListView�� ���� �� ���� ǥ��]
        LMA_music.notifyDataSetChanged();
        LV_music.setSelectionFromTop(	0	,	0	);
        
    }
 // //�˻� �ؼ� ������ ����� ��Ͽ��� �����ݴϴ�.
 	public void LV_music_new()
 	{

 		//[ListItem_m �ʱ�ȭ]
        ListItem_m.clear();
        
        //[ListItem_m ����]
 		DB_handler D_test = DB_handler.open(this);
        Cursor C_get_music_new = D_test.get_music_bag();
        
        if(C_get_music_new.getCount() > 0)
        {
            ListItem_m.add(new AL_music(2,-1,"","",0,"NEW �Ǻ�"));
            while(true)
            {
            	try {
                 ListItem_m.add(new AL_music(C_get_music_new.getInt(C_get_music_new.getColumnIndex("m_bag")) , C_get_music_new.getInt(C_get_music_new.getColumnIndex("m_id")),C_get_music_new.getString(C_get_music_new.getColumnIndex("m_name")), C_get_music_new.getString(C_get_music_new.getColumnIndex("m_code")), C_get_music_new.getInt(C_get_music_new.getColumnIndex("m_img")),""));
                 
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
 	
    //�˻� �ؼ� ������ ����� ��Ͽ��� �����ݴϴ�.
	public void LV_music_set(String insert)
	{

		//[ListItem_m �ʱ�ȭ]
	       ListItem_m.clear();
	       
	       //[ListItem_m ����]
			DB_handler D_test = DB_handler.open(this);
	       Cursor C_get_music_name = D_test.get_music_name(insert);
	       Cursor C_get_music_words = D_test.get_music_words(insert);
	       Cursor C_get_music_code = D_test.get_music_code(insert);
	       
	       if(C_get_music_name.getCount() > 0)
	       {
	           ListItem_m.add(new AL_music(2,-1,"","",0,"����"));
	           while(true)
	           {
	           	try {
	           		
	                ListItem_m.add(new AL_music(C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_bag")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_id")),C_get_music_name.getString(C_get_music_name.getColumnIndex("m_name")), C_get_music_name.getString(C_get_music_name.getColumnIndex("m_code")), C_get_music_name.getInt(C_get_music_name.getColumnIndex("m_img")),""));
	                
	   			} catch (Exception e) {
	   				// TODO: handle exception
	   			}
	           	if(C_get_music_name.moveToNext()== false){break;};
	           }
	       }
	       

	       if(C_get_music_words.getCount() > 0)
	       {
	           ListItem_m.add(new AL_music(2,-1,"","",0,"ù ����"));
	           while(true)
	           {
	           	try {
	           			
	       			ListItem_m.add(new AL_music( C_get_music_words.getInt(C_get_music_words.getColumnIndex("m_bag")), C_get_music_words.getInt(C_get_music_words.getColumnIndex("m_id")),C_get_music_words.getString(C_get_music_words.getColumnIndex("m_words")), C_get_music_words.getString(C_get_music_words.getColumnIndex("m_code")), C_get_music_words.getInt(C_get_music_words.getColumnIndex("m_img")),""));
	       			
	           	} catch (Exception e) {
	   				// TODO: handle exception
	   			}
	           	if(C_get_music_words.moveToNext()== false){break;};
	           }
	       }
	       

	       if(C_get_music_code.getCount() > 0)
	       {
	       	ListItem_m.add(new AL_music(2,-1,"","",0,"�ڵ�"));
	           while(true)
	           {
	           	try {
	           			ListItem_m.add(new AL_music(C_get_music_code.getInt(C_get_music_code.getColumnIndex("m_bag")) , C_get_music_code.getInt(C_get_music_code.getColumnIndex("m_id")),C_get_music_code.getString(C_get_music_code.getColumnIndex("m_name")), C_get_music_code.getString(C_get_music_code.getColumnIndex("m_code")), C_get_music_code.getInt(C_get_music_code.getColumnIndex("m_img")),""));
	           		
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
	
	//================================================================================================================================
  	//RadioGroup Set
    public void RG_select_type_set()
    {
    	RG_select_type = (RadioGroup) findViewById(R.id.select_type);
    	RG_select_type.setOnCheckedChangeListener(this);
    }
    @Override
	public void onCheckedChanged(RadioGroup group, int id) 
    {
    		if (group == RG_select_type) 
    		{
				LV_my_music_Oset(); 
    	
    	            if(id == R.id.delete) 
    	            {	// ���� �ʱ�ȭ
	    				
    	            	select_type = 0;
    	            	select_position = -1;
    	            	
    	            }
    	            else if(id == R.id.replace) 
    	            {
    	            	select_type = 1;
	    				
    	            }
    	
    		}
   }  

	
	public void LV_my_music_Oset()
	{

		/*
	 	* ������ ������ �� ���ȭ���� �ٲ� ���� ���� ��ư�� �ٽô��� ��� �̸� �ʱ�ȭ �ؾ� ��ϴ�.
	 	* type�� 1�� �缳���մϴ�.
	 	*/

		//[ListItem_mm ����]
		if(select_position != -1)
		{
			ListItem_mm.set(select_position,new AL_music(2,  ListItem_mm.get(select_position).m_id,ListItem_mm.get(select_position).m_name.toString(), ListItem_mm.get(select_position).m_code.toString(), ListItem_mm.get(select_position).m_img,""));
		}
		
		//[Adapater �� ���Ͽ� ListView�� ���� �� ���� ǥ��]
		LMA_my_music.notifyDataSetChanged();
		
		//LV_my_music.setSelectionFromTop(	LV_my_music.getFirstVisiblePosition()	,	0	);
		
    	IB_up.setImageResource(R.drawable.off);
		IB_down.setImageResource(R.drawable.off);
		
	}

    
	//================================================================================================================================
  	//Image Button Set
    public void IB_making_set(){

    	IB_add = (ImageButton) findViewById(R.id.IB_add);
    	IB_add.setOnClickListener(this);
    	
    	IB_up =(ImageButton) findViewById(R.id.up);
    	IB_up.setOnClickListener(this);

    	IB_down =(ImageButton) findViewById(R.id.down);
    	IB_down.setOnClickListener(this);
		
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if ( v.getId() == R.id.IB_add) 
		{

			// ���� �ʱ�ȭ
			LV_my_music_Oset(); 
			//Intent
			Intent intent = new Intent();
			intent.setClass( GUI_conti_make.this, GUI_conti_make_name.class);
			intent.putExtra("getFrom", "GUI_conti_make");
			intent.putExtra("Ptype", Ptype);
			intent.putExtra("c_writer", c_writer);
			intent.putExtra("c_subject", c_subject);
			intent.putExtra("c_name", c_name);
			intent.putExtra("menu_title", menu_title);
        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_conti_make.this.startActivity(intent);
			GUI_conti_make.this.finish();
			
			
		}else if(v.getId() == R.id.up && select_position != 0 && select_type == 1){
			
			LV_my_music_replace(select_position, --select_position, "up");
			
			
		}else if(v.getId() == R.id.down && select_position != ListItem_mm.size()-1 && select_type == 1){
			
			LV_my_music_replace(select_position, ++select_position, "down");
		}
	}
	
	public void IB_view_set(){
		//�� ������ �̰ų� �� ó���ΰ�� ��ư�� �Ⱥ��̰� �մϴ�. BUTTON SET
		if(select_type == 0 || select_position == -1){
			
		}
		else if(ListItem_mm.size() < 2){
			IB_up.setImageResource(R.drawable.off);
			IB_down.setImageResource(R.drawable.off);
			
		}
		else if(select_position == 0)
		{
			IB_up.setImageResource(R.drawable.off);
			IB_down.setImageResource(R.drawable.b_down);
		
		}
		else if(select_position == ListItem_mm.size()-1)
		{
			IB_up.setImageResource(R.drawable.b_up);
			IB_down.setImageResource(R.drawable.off);
		}
		else
		{
			IB_up.setImageResource(R.drawable.b_up);
			IB_down.setImageResource(R.drawable.b_down);
		}
	}
	//--------------------------------------------------------------------------------------------------------------------------------
    //�ڷΰ��� ��ư
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			if( KeyCode == KeyEvent.KEYCODE_BACK ){
				
				new AlertDialog.Builder(GUI_conti_make.this)
				 .setTitle("����!")
				 .setMessage("\"�Ǻ�����\"�� ��� �Ͻðڽ��ϱ�?")
				 .setPositiveButton("��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//Intent

						// ���� �ʱ�ȭ
						LV_my_music_Oset(); 
						
						
						
						Intent intent = new Intent();
						intent.setClass( GUI_conti_make.this, GUI_conti_make_name.class);
						intent.putExtra("getFrom", "GUI_conti_make");
						intent.putExtra("Ptype", Ptype);
						intent.putExtra("c_writer", c_writer);
						intent.putExtra("c_subject", c_subject);
						intent.putExtra("c_name", c_name);
						intent.putExtra("menu_title", menu_title);
			        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm_copy);
					    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
						GUI_conti_make.this.startActivity(intent);
						GUI_conti_make.this.finish();
					}
				})
				.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
				
				return true;		
				// ���⼭ ���ϰ��� �߿��ѵ�; ���ϰ��� true �̳� false �̳Ŀ� ���� �ൿ�� �޶�����.
				// true �ϰ�� back ��ư�� �⺻������ ���Ḧ �����ϰ� �ȴ�.
				// ������ false �� ��� back ��ư�� �⺻������ ���� �ʴ´�.
				// back ��ư�� �������� ������� �ʰ� �ϰ� �ʹٸ� ���⼭ false �� �����ϸ� �ȴ�.
				// back ��ư�� �⺻������ ������ ���ø����̼��� ������ ����� ���⶧����
				// ���� �����ϴ� ����� �����ؾ��Ѵ�.
				}
	 
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}


	//--------------------------------------------------------------------------------------------------------------------------------
    //FUCTION  
    
}