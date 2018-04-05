package com.MQ.music.conti_maker_demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

public class GUI_conti_make_name extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	/*
	 * 1. ��Ƽ�� ���Ӱ� ����ϴ�. 	----> Ptype = 0 [getFrom = GUI_conti ]
	 * 2. ��Ƽ�� ������ �ϴ�.		----> Ptype = 1 [getFrom = GUI_conti_reset]
	 * 3. ��Ƽ�� �� ���� , �ۼ��� , ������ �Է��մϴ�. [ ���� | �ۼ��� | ���� ]
	 */

	//--------------------------------------------------------------------------------------------------------------------------------
	//Process Type
	private int Ptype;
	private String c_name;
	private String c_writer;
	private String c_subject;
	private String getFrom;
	private String menu_title;
	
	//TextView
	private TextView TV_my_s_id;
	
	//String
	private String s_id;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//DATA�� ����Ǵ� ������ �Է��մϴ�.
	private EditText ET_c_name;
	private EditText ET_c_subject;
	private EditText ET_c_writer;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//�ڽ��� ������ �Ǻ��� ����� �����ݴϴ�.
	private ListView LV_my_music;
	private ArrayList<AL_music> ListItem_mm_backup;
	private ArrayList<AL_music> ListItem_mm;
	private LMA_my_music LMA_my_music;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//����Ҷ� ���Ǵ� ��ư�Դϴ�.
	private ImageButton IB_making;
	private ImageButton IB_add;

	//--------------------------------------------------------------------------------------------------------------------------------
	//�������� ���������� �˷��ִ� ����� ������ �ֽ��ϴ�.
	private TextView TV_c_name_check;
	private TextView TV_c_score_check;
	private int insert_check = 0;//����Ҷ� ������ �ۼ��ߴ��� Ȯ��.
	private int get_size_c_name;//����Ҷ� ������ �ߺ��Ǵ��� Ȯ��.
	
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
        setContentView(R.layout.gui_conti_make_name);
        
        Intent_set();
		DB_set();
		TV_set();
        TV_name_check();
        ET_set();
        IB_making_set();
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

    public void Intent_set()
    {
    	
    	Intent intent = getIntent();
    	
    	//[1] ���� Ŭ������ ��� ���� �Դ��� Ȯ���մϴ�.
        getFrom = intent.getExtras().get("getFrom").toString(); 
        
        //[1-1 | ��Ƽ ���θ����] GUI_conti ���� �°��
        if(getFrom.equals("GUI_conti"))
        {
        	Ptype = 0;
        	ListItem_mm = new ArrayList<AL_music>();
        	c_name = "";	
        	menu_title = "��Ƽ�����";
        }
        else
        {
        	//[1-1 | �Ǻ��߰� ] GUI_conti_make
            if(getFrom.equals("GUI_conti_make"))
            {
            	Ptype = intent.getExtras().getInt("Ptype");
            	c_writer = intent.getExtras().get("c_writer").toString();
            	c_subject = intent.getExtras().get("c_subject").toString();
            	menu_title = intent.getExtras().get("menu_title").toString();
            }
            //[1-1 | ��Ƽ ���� ] GUI_conti
            else if(getFrom.equals("GUI_conti_reset"))
            {
            	Ptype = 1;
            	menu_title = "��Ƽ����";
    	        
            }
            
            
            ListItem_mm = intent.getParcelableArrayListExtra("ListItem_mm");
            
            if(getFrom.equals("GUI_conti_make"))
            {
	            for(int i = 0 ; i < ListItem_mm.size() ; i++){
		    		
		    		int	m_id = ListItem_mm.get(i).m_id; 
		    		String m_name = ListItem_mm.get(i).m_name;
		    		String m_code = ListItem_mm.get(i).m_code;
		    		int m_img = ListItem_mm.get(i).m_img;
		    		String title = ListItem_mm.get(i).title;
		    		
		    		ListItem_mm.set(i, new AL_music(0,m_id, m_name, m_code, m_img, title));
		    	}
            }
            ListItem_mm_backup = ListItem_mm;
        	c_name = intent.getExtras().get("c_name").toString();
        	
        	
        	//DB���� T_name�� ������ �ɴϴ�.�� GUI_conti_reset�ΰ�쿡��
        	if(getFrom.equals("GUI_conti_reset"))
            {
    			DB_handler D_test = DB_handler.open(this);
    			Cursor C_get_continuity = D_test.get_continuity(c_name);
    	        C_get_continuity.close();
    	        D_test.close();
    	        
            }
        	
        	
        }
        
    	LV_my_music_set();
    	
    }
    //================================================================================================================================
	
	
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
	//View Set
    public void TV_name_check()
    {
    	TV_c_name_check = (TextView)findViewById(R.id.TV_c_name_check);
    	TV_c_name_check.setText("** ������ �Է��� �ּ���.");
		TV_c_name_check.setTextColor(R.color.silver);

    	TV_c_score_check = (TextView)findViewById(R.id.TV_c_score_check);
    	TV_c_score_check.setText(String.valueOf(ListItem_mm.size())+"�� ����");
		TV_c_score_check.setTextColor(R.color.silver);
    }
    //================================================================================================================================
	//EditText Set
    public void ET_set(){

    	//[����]
		ET_c_name = (EditText) findViewById(R.id.ET_c_name);
		ET_c_name.addTextChangedListener(textWatcherInput);  
		
		//[�ۼ���]
		ET_c_writer = (EditText) findViewById(R.id.ET_c_writer);
		
		//[����]
		ET_c_subject = (EditText) findViewById(R.id.ET_c_subject);
		
		// ���� getFrom = GUI_contu_view ���� �°�� �̹� ��ϵǾ��� ������ �����ݴϴ�.
		if(Ptype == 1)
		{
			
			DB_handler D_test = DB_handler.open(this);
			Cursor C_get_continuity = D_test.get_continuity(c_name);
			
			c_name = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_name"));
			c_writer = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_writer"));
			c_subject = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_subject"));
			
			ET_c_name.setText(c_name);
			ET_c_writer.setText(c_writer);
			ET_c_subject.setText(c_subject);
	        C_get_continuity.close();
	        D_test.close();
	        
		}else if(getFrom.equals("GUI_conti_make"))
		{
			ET_c_name.setText(c_name);
			ET_c_writer.setText(c_writer);
			ET_c_subject.setText(c_subject);
	        
		}
    }
    
    
    TextWatcher textWatcherInput = new TextWatcher() {  //ET_c_name�� ���� �ԷµǾ� ������ Ȯ���մϴ�.
        
        @Override  
        public void onTextChanged(CharSequence s, int start, int before, int count) {  
            // TODO Auto-generated method stub  

        	String c_name = RemoveSpace(s);
        	
        	if(c_name.equals("") ||  Check_Space(c_name.toString()))
        	{
        		TV_c_name_check.setText("** ������ �Է��� �ּ���.");
        		TV_c_name_check.setTextColor(R.color.silver);
            	insert_check = 0 ;
        	}
        	else if(Check_insert(c_name.toString()))
        	{
        		TV_c_name_check.setText("**  .  �� ����� �� �����ϴ�.");
        		TV_c_name_check.setTextColor(Color.RED);
            	insert_check = 0 ;
        	}
        	else
        	{
        		DB_c_name_check(c_name.toString());
            	insert_check = 1 ;
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
    
	public String RemoveSpace(CharSequence s)
	{
		// ���ڿ��� ó���κп� �Էµ� �����̽� �κ��� ��� ���� �� �ݴϴ�.
		
		//--------------------------------------------------------------------------
		// �պκк���
		int fcheck = 0;
		for(int f = 0 ; f < s.length() ; f ++)
		{
			if(s.charAt(f) != ' ')
			{
				fcheck = f;
				break;
			}
		}
		
		//--------------------------------------------------------------------------
		//�޺κк���
		int bcheck = s.length()-1;
		for(int b = s.length()-1 ; -1 < b ; b --)
		{
			if(s.charAt(b) != ' ')
			{
				bcheck = b;
				break;
			}
		}	
		
		//--------------------------------------------------------------------------
		// ���ڿ� ����� 
		// fcheck �տ��� ����  fcheck �κ� �ձ����� ������ ����
		// bcheck �ڿ��� ����  bcheck �κ� �� ������ ������ ����
		String out = "";
		for(int i = fcheck ; i <= bcheck ; i++)
		{
			out = out+s.charAt(i);
		}

		return out;
	}
	
	public Boolean Check_insert(String insert)
	{
		//���ڿ��� �̻��ϴ� �� . �� ���°�� ���� �޼����� �߰� �մϴ�.
		Boolean space_check = false;	

		for(int i = 0 ; i < insert.length() ; i++)
		{
			if(insert.charAt(i) == '.')
			{
				space_check = true;
				break;
			}
		}
	
		return space_check;
	}
	
	public Boolean Check_Space(String insert)
	{		
		//���ڿ��� ���� �� �����̽� ������ ���°��
		Boolean space_check = false;	
		
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
		return space_check;
	}

    
    public void DB_c_name_check(String insert_c_name){
    	 
    	DB_handler D_test = DB_handler.open(this);
    	
    	get_size_c_name = D_test.get_size_c_name(insert_c_name); 
    	
    	if(this.c_name.equals(insert_c_name) && Ptype == 1){
			// TODO: handle exception
			TV_c_name_check.setText("** ����� �����մϴ�.");
			TV_c_name_check.setTextColor(R.color.silver);
    		get_size_c_name = 0;
    		
    	}
    	else if( get_size_c_name == 1){
    		TV_c_name_check.setText("** �ߺ��Ǵ� ������ ��� �� �� �����ϴ�.");
    		TV_c_name_check.setTextColor(Color.RED);
    	}
    	else if(get_size_c_name == 0 ){
    		
			// TODO: handle exception
			TV_c_name_check.setText("** ����� �����մϴ�.");
			TV_c_name_check.setTextColor(R.color.silver);
    	}
    	 
    	D_test.close();
    }

    //================================================================================================================================
	//ListView Set
    public void LV_my_music_set()
    {	
    	//[My_ListView ����]
    	LV_my_music= (ListView)findViewById(R.id.my_music_list);
        
    	//[Listview_mm ����]
    	LMA_my_music = new LMA_my_music(this, ListItem_mm );
        
    	//[My_ListView Mapping]
    	LV_my_music.setAdapter(LMA_my_music);
    	
    	//[My_ListView Listener]
    	LV_my_music.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
        		@Override
 	    		public void onItemClick(AdapterView parent, View view, final int position, long id) 
 	    		{	// TODO Auto-generated method stub
 	    			//�˻���Ͽ��� �Ǻ��� ������ ���

        			if(BagCheck(ListItem_mm.get(position).m_id) == true){
        				//�����Ѱ��
        				File f = new File(Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(ListItem_mm.get(position).m_id)+".png");
    					if(f.exists()){

    						Intent intent = new Intent();
    						intent.setClass( GUI_conti_make_name.this, GUI_music_view_portrait.class);
    						intent.putExtra("getFrom", "GUI_music");
    						intent.putExtra("m_id", ListItem_mm.get(position).m_id);
    						intent.putExtra("ItemSet", "off");
          			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    						GUI_conti_make_name.this.startActivity(intent);
    						
    					}else{
    						Intent intent = new Intent();
    						intent.setClass( GUI_conti_make_name.this, GUI_down_portrait.class);
    						intent.putExtra("getFrom", "GUI_music");
    						intent.putExtra("m_id", ListItem_mm.get(position).m_id);
    						intent.putExtra("screen", "Portrait");
    						intent.putExtra("ItemSet", "off");
          			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    						GUI_conti_make_name.this.startActivity(intent);
    						
    					}
    					
        			}else if(BagCheck(ListItem_mm.get(position).m_id) == false){
        				//�������� �������
        				Toast.makeText(GUI_conti_make_name.this, "�Ǻ�����",Toast.LENGTH_SHORT).show();
				        
        			}
        			
    				
 				}
 		});
        
    }
    
    public boolean BagCheck(int m_id){

        DB_handler D_test = DB_handler.open(this);
        Cursor C_get_music = D_test.get_music(m_id);
        int check = C_get_music.getInt(C_get_music.getColumnIndex("m_bag"));
        C_get_music.close();
        D_test.close();
    	
    	if(check == 0){
        	return false;
    	}else{
        	return true;
    	}
    }
    //================================================================================================================================
	//ImageButton Set
    public void IB_making_set(){
    	
    	IB_making = (ImageButton) findViewById(R.id.making);
    	IB_making.setOnClickListener(this);
    	
    	IB_add = (ImageButton) findViewById(R.id.IB_add);
    	IB_add.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
    	if ( v.getId() == R.id.making ) {
			
			
			if(insert_check == 1 && get_size_c_name == 0)//������ �ۼ�, �ߺ�Ȯ��
			{
				if(ListItem_mm.size() > 0)
				{
					new AlertDialog.Builder(GUI_conti_make_name.this)
					 .setTitle(menu_title)
					 .setMessage("��Ƽ�� ����Ͻðڽ��ϱ�?")
					 .setPositiveButton("��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							DB_inert(RemoveSpace(ET_c_name.getText().toString()),RemoveSpace(ET_c_writer.getText().toString()),RemoveSpace(ET_c_subject.getText().toString()));// �Է��� �Ǿ��� ������ 
							

					        if(menu_title.equals("��Ƽ����")){
					        	Toast.makeText(GUI_conti_make_name.this, "��Ƽ�� �����߽��ϴ�.",Toast.LENGTH_SHORT).show();
					        }else{
					        	Toast.makeText(GUI_conti_make_name.this, "��Ƽ�� �����߽��ϴ�.",Toast.LENGTH_SHORT).show();
					        }
							
							Intent intent = new Intent();
							intent.setClass( GUI_conti_make_name.this, GUI_conti_main.class);
							intent.putExtra("c_name", ET_c_name.getText().toString());
				        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm);
          			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							GUI_conti_make_name.this.startActivity(intent);
							
							finish();
						}
					})
					.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
						
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					}).show();
					
				}else{
					
					Toast.makeText(this, "�Ǻ��� �߰� �ϼ���.",Toast.LENGTH_LONG).show();
					
				}

				
			} else {
				
				Toast.makeText(this, "������ Ȯ���� �ּ���." ,Toast.LENGTH_LONG).show();
				
			}
			
		}else if ( v.getId() == R.id.IB_add ) {
			Toast.makeText(GUI_conti_make_name.this, "�Ǻ��߰�",Toast.LENGTH_SHORT).show();
			
			Intent intent = new Intent();
			intent.setClass( GUI_conti_make_name.this, GUI_conti_make.class);
			intent.putExtra("getFrom", "GUI_conti_make_name");
			intent.putExtra("Ptype", Ptype);
			intent.putExtra("c_name", ET_c_name.getText().toString());
			intent.putExtra("c_writer", ET_c_writer.getText().toString());
			intent.putExtra("c_subject", ET_c_subject.getText().toString());
			intent.putExtra("menu_title", menu_title);
        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_conti_make_name.this.startActivity(intent);
			GUI_conti_make_name.this.finish();
			
		}
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
    
    public void DB_inert(String _c_name, String _c_writer ,String _c_subject){
    	
    	
    	//������ �ִ� File �� ����
    	
        //������ �ִ� DB ������ ����
    	DB_handler D_test = DB_handler.open(this);
    	if(Ptype == 1)
    	{
    		//File �� ���� �մϴ�.
        	Delete_file(Environment.getExternalStorageDirectory().getAbsolutePath()+"/conti/"+this.c_name);
        	
    		D_test.delete_continuity(this.c_name);
    		D_test.delete_conti_music(this.c_name);
    		D_test.delete_conti_members_c_name(this.c_name);
    	} 
    	D_test.insert_continuity(_c_name, get_date(), _c_writer, _c_subject, "N");
    	 
    	 //insert_conti_music 
    	for(int i = 0 ;i<ListItem_mm.size();i++) 
    	{
    		D_test.insert_conti_music( _c_name, i, ListItem_mm.get(i).m_id);
    	}
    	
    	D_test.close();
    }
    
    public int get_date(){
    	int date=0;
    	
    	  Calendar rightNow = Calendar.getInstance();// ��¥ �ҷ����� �Լ�
    	  int year = rightNow.get(Calendar.YEAR);
    	  int month = rightNow.get(Calendar.MONTH)+1;
    	  int day = rightNow.get(Calendar.DATE);
    	
    	  date = year*10000 + month*100 + day;
    	
    	return date;
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
				
				 new AlertDialog.Builder(GUI_conti_make_name.this)
				 .setTitle("����!")
				 .setMessage("\""+menu_title+"\""+"�� ��� �Ͻðڽ��ϱ�?")
				 .setPositiveButton("��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						

						Intent intent = new Intent();
						
						if(menu_title.equals("��Ƽ�����")){

          		        	intent.setClass(GUI_conti_make_name.this, GUI_conti.class);
          			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							
				        }else{
				        	
							intent.setClass( GUI_conti_make_name.this, GUI_conti_main.class);
          			        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							intent.putExtra("c_name", c_name);
				        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm_backup);
							
				        }
				        
						GUI_conti_make_name.this.startActivity(intent);
						GUI_conti_make_name.this.finish();
					}
				})
				.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
				
				return true;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
	
    
    
}