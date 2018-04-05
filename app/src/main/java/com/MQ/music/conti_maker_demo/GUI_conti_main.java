package com.MQ.music.conti_maker_demo;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.NotificationManager;
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

public class GUI_conti_main extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	
	
	private Boolean DEVELOPER_MODE = true;
	private String c_name;
	private String c_writer;
	private String c_subject;
	

	//--------------------------------------------------------------------------------------------------------------------------------
	//TextView
	private TextView TV_c_name;
	private TextView TV_c_writer;
	private TextView TV_c_subject;
	private TextView TV_c_score_check;
	private TextView TV_my_s_id;
	
	//String
	private String s_id;
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//ImageButton
	private ImageButton IB_sharing;
	private ImageButton IB_share;
	private ImageButton IB_edit;
	
	
	//--------------------------------------------------------------------------------------------------------------------------------
	//�ڽ��� ������ �Ǻ��� ����� �����ݴϴ�.
	private ListView LV_my_music;
	private ArrayList<AL_music> ListItem_mm;
	private LMA_my_music LMA_my_music;
	
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
        setContentView(R.layout.gui_conti_main);
		
		Intent_set();
		Update_set();
        DB_set();
		TV_set();
		IB_set();

    }
    
    @Override
    protected void onResume() {
    	  // TODO Auto-generated method stub
    	 
    	  super.onResume();
    	  //�˶�â ������
    	  NotificationManager nm =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	  nm.cancel(c_name, 0);
    	
    }
	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor c_get_id = D_test.get_System();
		s_id = c_get_id.getString(c_get_id.getColumnIndex("s_id"));
		c_get_id.close();
		D_test.close();
	}
    
    //================================================================================================================================
    public void Intent_set(){
    	Intent intent = getIntent();
    	c_name = intent.getExtras().getString("c_name");
        ListItem_mm = intent.getParcelableArrayListExtra("ListItem_mm");
        

		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_continuity = D_test.get_continuity(c_name);
		
		c_name = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_name"));
		c_writer = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_writer"));
		c_subject = C_get_continuity.getString(C_get_continuity.getColumnIndex("c_subject"));
		
		C_get_continuity.close();
        D_test.close();
        
    	LV_my_music_set();
    	
    }
    
    //================================================================================================================================
    public void Update_set(){
		DB_handler D_test = DB_handler.open(this);
		D_test.Update_CONTINUITY(c_name);
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
							intent.setClass( GUI_conti_main.this, GUI_music_view_portrait.class);
							intent.putExtra("getFrom", "GUI_music");
							intent.putExtra("m_id", ListItem_mm.get(position).m_id);
							intent.putExtra("ItemSet", "off");
						    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							GUI_conti_main.this.startActivity(intent);
							
						}else{
							Intent intent = new Intent();
							intent.setClass( GUI_conti_main.this, GUI_down_portrait.class);
							intent.putExtra("getFrom", "GUI_music");
							intent.putExtra("m_id", ListItem_mm.get(position).m_id);
							intent.putExtra("screen", "Portrait");
							intent.putExtra("ItemSet", "off");
						    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							GUI_conti_main.this.startActivity(intent);
							
						}
						
        			}else if(BagCheck(ListItem_mm.get(position).m_id) == false){
        				//�������� �������
        				Toast.makeText(GUI_conti_main.this, "�Ǻ�����",Toast.LENGTH_SHORT).show();
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
    public void TV_set()
    {
		TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
		TV_my_s_id.setText("ID: "+ s_id);
		
    	TV_c_name = (TextView) findViewById(R.id.TV_c_name);
    	TV_c_name.setText(c_name);
    	
    	TV_c_writer= (TextView) findViewById(R.id.TV_c_writer);
    	TV_c_writer.setText(c_writer);
    	
    	TV_c_subject= (TextView) findViewById(R.id.TV_c_subject);
    	TV_c_subject.setText(c_subject);
    	
    	
    	TV_c_score_check = (TextView) findViewById(R.id.TV_c_score_check);
    	TV_c_score_check.setText(ListItem_mm.size()+"��");
    }
    
    
    //================================================================================================================================
    public void IB_set()
    {
    	
    	IB_share=(ImageButton) findViewById(R.id.share_conti);
    	IB_share.setOnClickListener(this);
    	
    	IB_edit =(ImageButton) findViewById(R.id.edit_conti);
    	IB_edit.setOnClickListener(this);
    	
    	IB_sharing=(ImageButton) findViewById(R.id.sharing_conti);
    	IB_sharing.setOnClickListener(this);
    	
    	
    }
    
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
		
	}
    
	@Override
	public void onClick(View v) {
		
		
		if ( v.getId() == R.id.edit_conti) {
    		
    		
    		Toast.makeText(GUI_conti_main.this, "��Ƽ����",Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent();
    		intent.setClass( GUI_conti_main.this, GUI_conti_make_name.class);
    		intent.putExtra("getFrom", "GUI_conti_reset");
        	intent.putParcelableArrayListExtra("ListItem_mm", ListItem_mm);
    		intent.putExtra("c_name", c_name);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    		GUI_conti_main.this.startActivity(intent);
    		
    	}else if ( v.getId() == R.id.share_conti) {
    		

    		Toast.makeText(GUI_conti_main.this, "��Ƽ����",Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent();
    		intent.setClass( GUI_conti_main.this, GUI_conti_share.class);
			intent.putExtra("c_name", c_name);
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
    		GUI_conti_main.this.startActivity(intent);
    		
    		
    	}else if ( v.getId() == R.id.sharing_conti) {
    		
    		/*
    		new AlertDialog.Builder(GUI_conti_main.this)
			 .setTitle("��Ƽ���� [���̺���]")
			 .setMessage("\'���̺���\' �� ���Ͽ� ģ������ ��Ƽ�� ���̽ðڽ��ϱ�?")
			 .setPositiveButton("����", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub 
					
				}
			})
			.setNegativeButton("���", new DialogInterface.OnClickListener() {
				
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
    		 */

			Intent intent = new Intent();
			intent.setClass( GUI_conti_main.this, GUI_conti_view_share.class);
			intent.putExtra("getFrom", "GUI_gcm_conti_control");
			intent.putExtra("c_name", c_name);
			intent.putExtra("ItemSet", "off");
		    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_conti_main.this.startActivity(intent);
    		
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
		        intent.setClass(GUI_conti_main.this, GUI_conti.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		        GUI_conti_main.this.startActivity(intent);
		        GUI_conti_main.this.finish();
    			
    			
				return true;
				
			}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
	
    
}
