package com.MQ.music.conti_maker_demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GUI_conti_view_share_control extends Activity {
	
	//-----------------------------------------------------------------------------------------------------------------------
	//GCM
	private static final String TAG = "GCM";
	
	//�ڽ��� Project ID �� �־��ּ���
	private static final String SENDER_ID = "1095851346823";	
	
	private Sender 				gcmSender;				//GCM Sender
	private Message 			gcmMessage;			//GCM Message
	private Result 				gcmResult;				//GCM Result(���� ����)
	private MulticastResult 	gcmMultiResult;		//GCM Multi Result(�ϰ� ����)
	
	//�ϰ����ۿ� �ʿ��� List ����
	private List<String> registrationIds = new ArrayList<String>();
	private ArrayList<AL_friend> get_registrationIds;
	
	
	private String s_registrationId = "";
	private String s_name = "";
	
	//������ �ֿܼ��� �߱޹��� API Key
	private static String 		API_KEY = "AIzaSyDGjUj0DZq8XCkD8eP8NEdgKttEbpKr_GI";
	
	//�޼����� ���� ID(?)������ �����ϸ� �˴ϴ�. �޼����� �ߺ������� ���� ���� �������� �����մϴ�
	private static String 		COLLAPSE_KEY ;
	
	//��Ⱑ Ȱ��ȭ ������ �� ������ ������. 
	private static boolean 	DELAY_WHILE_IDLE = false;
	
	//��Ⱑ ��Ȱ��ȭ ������ �� GCM Storage�� �����Ǵ� �ð�
	private static int			TIME_TO_LIVE = 600;
	
	//�޼��� ���� ���н� ��õ��� Ƚ��
	private static int 			RETRY = 3;
	//-----------------------------------------------------------------------------------------------------------------------
	
	
	private static ImageView conti_IV_m_img;
	
	private String c_name;				//��Ƽ�� ����
	private int c_name_total;
	private int c_name_m_id[];			//��Ƽ�� m_id
	private int c_name_ms_total[];		//��Ƽ�� m_id�� ��ü ������
	private String c_name_m_code[];		//��Ƽ�� m_id�� �ڵ�
	private String c_name_m_name[];		//��Ƽ�� m_id�� m_name
	
	private int now;
	private int now_num;
	private int now_page;
	
	
	private ListView LV_music_view;
	private static ArrayList<AL_music_view> ListItem_music_view;
	private static LMA_music_view LMA_music_view;

	private ListView LV_m_name;
	private ArrayList<AL_music> ListItem_m_name;
	private LMA_music_name LMA_m_name;
	

	// Thread
	private Thread T;// thread
	private Handler H;// handler
	private Boolean T_check = true;
	private int T_count = 0;
	private Boolean T_send_check = false;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    setContentView(R.layout.gui_music_view_share_control);
		overridePendingTransition(0, 0);
	    
		
		
		
	    GCMRegistrar.checkDevice(this);
	    GCMRegistrar.checkManifest(this);
	    final String regId = GCMRegistrar.getRegistrationId(this);
	    if("".equals(regId)){
	    	GCMRegistrar.register(this, SENDER_ID);
	    	Log.d("==============================adfasdfasdfasdf===",regId);
	    }else{
	    	Log.d("=================================",regId);
	    }
	    
	    Intent_set();
	    NetWork_set();
	    IV_set();
	    LV_set();
	    T_Start();
	    
	    //����
		GUI_conti_view_share_control.this.setMessage(c_name_m_id[now_num] , 1);
		GUI_conti_view_share_control.this.sendMessage();
		ListItem_music_view.clear();
		for(int i = 1 ; i <c_name_ms_total[now_num] ; i++){
			if(now_page == i){
				ListItem_music_view.add(new AL_music_view(1, c_name_m_id[now_num],i));
			}else{
				ListItem_music_view.add(new AL_music_view(0, c_name_m_id[now_num],i));
			}
		}
		LMA_music_view.notifyDataSetChanged();
	    
	    
	}
	
    @Override
    protected void onPause(){
    	super.onPause();
    	
		T_check = false;
		T = null;
    }
    
    @Override
    protected void onResume(){
    	super.onResume();

		Log.e("================================================", "Resume");
		T_check = true;
		T_Start();
    }
    
	
	
	public void Intent_set(){

		Intent intent = getIntent();
		c_name = intent.getExtras().getString("c_name");
		get_registrationIds = intent.getParcelableArrayListExtra("registrationIds");
		for(int i = 0 ; i < get_registrationIds.size(); i++){
			registrationIds.add(get_registrationIds.get(i).registrationId);
		}
		
		
		DB_handler D_test = DB_handler.open(this);
		
		//system
		Cursor C_get_System =D_test.get_System();
		s_registrationId =  C_get_System.getString(C_get_System.getColumnIndex("s_registrationId"));
		s_name =  C_get_System.getString(C_get_System.getColumnIndex("s_name"));
		C_get_System.close();
		
		//���������������
		Cursor C_get_conti_music =D_test.get_conti_music(c_name);
		c_name_total = C_get_conti_music.getCount()+1;
		c_name_m_id = new int [c_name_total];
		c_name_ms_total = new int [c_name_total];
		c_name_m_name = new String [c_name_total];
		c_name_m_code = new String [c_name_total];
		
		int i = 1;
		while(true){
			
			c_name_m_id[i] = C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("m_id"));
			c_name_ms_total[i] = getMusicPage(c_name_m_id[i]) + 1;
			c_name_m_name[i] = C_get_conti_music.getString(C_get_conti_music.getColumnIndex("m_name"));
			c_name_m_code[i] = C_get_conti_music.getString(C_get_conti_music.getColumnIndex("m_code"));
			i++;
			if(C_get_conti_music.moveToNext()== false){break;};
		}
		C_get_conti_music.close();
		
 		
		D_test.close();
 		
		registrationIds.add(s_registrationId);
		
		now = 1;
		now_num = 1;
		now_page = 1;
		
		
		Log.e("error", Integer.toString(registrationIds.size()));
		
	}
	
	
	public int getMusicPage(int _m_id){
		int ms_total = 0;
		
		DB_handler D_test = DB_handler.open(this);
		Cursor C_get_music_sub = D_test.get_music_sub(_m_id);
		ms_total = C_get_music_sub.getCount();
		C_get_music_sub.close();
 		D_test.close();
 		
 		return ms_total;
	}
	
	public void NetWork_set(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	public void IV_set(){
		conti_IV_m_img = (ImageView) findViewById(R.id.IV_m_img);
	}
	
	public void setMessage(int _m_id, int _page){
		
		
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
	    .addData("type","��Ƽ���̺���" )
	    .addData("sender",s_name)
	    .addData("message", Integer.toString(_m_id)+"_"+Integer.toString(_page)+"_"+"["+s_name+"]"+c_name)
	    //.addData("message", Integer.toString(_m_id)+"_"+Integer.toString(_page))
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
	
	
	public void LV_set(){
		
		//==================================================================================================
		//�Ǻ�������
	    LV_music_view= (ListView)findViewById(R.id.LV_ms_img);
	    ListItem_music_view = new ArrayList<AL_music_view>();
	    for(int i = now_page; i < c_name_ms_total[now_num]; i++){
	    	ListItem_music_view.add(new AL_music_view(0,c_name_m_id[now_num],i));// m_img �� ��ȣ����
	    }
		LMA_music_view = new LMA_music_view(this, ListItem_music_view );
		LV_music_view.setAdapter(LMA_music_view);
		

		//==================================================================================================
		//�Ǻ����
		LV_m_name= (ListView)findViewById(R.id.LV_m_name);
	    ListItem_m_name = new ArrayList<AL_music>();

	    ListItem_m_name.add(new AL_music(0, -1,"�Ǻ� ����","",-1,""));
	    ListItem_m_name.add(new AL_music(2, -1," "+c_name_m_name[now_num],c_name_m_code[now_num],-1,""));//��
	    
	    for(int i = now_num+1; i < c_name_total ; i++){
		    ListItem_m_name.add(new AL_music(1, -1," "+c_name_m_name[i],c_name_m_code[i],-1,""));
	    }
		LMA_m_name = new LMA_music_name(this, ListItem_m_name );
		LV_m_name.setAdapter(LMA_m_name);
		
		
		//==================================================================================================
		//Click
		LV_music_view.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{	
			
			@Override
	 	    public void onItemClick(AdapterView parent, View view, final int position, long id) 
			{
	 		// TODO Auto-generated method stub
				
				T_count = 0 ;
				
				now_num= now;
				now_page = position+1;
				Log.e("*********************", Integer.toString(now_page));
				
				setMessage(c_name_m_id[now_num] , now_page);
				sendMessage();
				
				
				ListItem_m_name.clear();
			    ListItem_m_name.add(new AL_music(0, -1,"�Ǻ� ����","",-1,""));
			    for(int i = 1; i < c_name_total ; i++){
			    	if(now_num == i){
			    	    ListItem_m_name.add(new AL_music(2, -1," "+c_name_m_name[i],c_name_m_code[i],-1,""));//��
			    	}
			    	else{
					    ListItem_m_name.add(new AL_music(1, -1," "+c_name_m_name[i],c_name_m_code[i],-1,""));
			    	}
			    }
				LMA_m_name.notifyDataSetChanged();
			    

				
				ListItem_music_view.clear();
				for(int i = 1 ; i <c_name_ms_total[now] ; i++){
					
					if(now_page == i ){
						ListItem_music_view.add(new AL_music_view(1, c_name_m_id[now],i));
					}else{
						ListItem_music_view.add(new AL_music_view(0, c_name_m_id[now],i));
					}
				}
				LMA_music_view.notifyDataSetChanged();
	 		}
	 	});
		
		LV_m_name.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{	
			
			@Override
	 	    public void onItemClick(AdapterView parent, View view, final int position, long id) 
			{
	 		// TODO Auto-generated method stub
				
				if(position != 0){
					now = position;
					ListItem_music_view.clear();
					for(int i = 1 ; i <c_name_ms_total[now] ; i++){
						
						if(now_page == i && now_num == now){
							ListItem_music_view.add(new AL_music_view(1, c_name_m_id[now],i));
						}else{
							ListItem_music_view.add(new AL_music_view(0, c_name_m_id[now],i));
						}
					}
					LMA_music_view.notifyDataSetChanged();
					LV_music_view.setSelectionFromTop(	0	,	0	);
					
				}
	 		}
	 	});
		
		
	}
	
	static void setImageConti(int _m_id,int _page){
		
		String url_m_img = Environment.getExternalStorageDirectory()+"/contimaker/score/wc"+Integer.toString(_m_id)+"_"+Integer.toString(_page)+".png";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap src = BitmapFactory.decodeFile(url_m_img, options);
		Bitmap resized = Bitmap.createScaledBitmap(src, src.getWidth(),src.getHeight(), true);   
		conti_IV_m_img.setImageBitmap(resized);
		
	}
	
	
	public void onClickButton(View v){
		
	}
	
	
	

	// ==========================================================================================
	// Thread_Start
	public void T_Start() {

		T = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (T_check) {

					try {
						H.sendMessage(H.obtainMessage());
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					T_count++;

				}
			}
		});

		H = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {


				//Log.e("GUI_music_view_share_control", Integer.toString(T_count));
				
				if(T_count > 99){
					Log.e("GUI_setting_help_1", "m_id: "+Integer.toString(c_name_m_id[now_num] )+" page: "+Integer.toString(now_page));
					
					
					
					GUI_conti_view_share_control.this.setMessage(c_name_m_id[now_num] , now_page);
					GUI_conti_view_share_control.this.sendMessage();
					
					T_count = 0;
				}
				
			}
		};

		T.start();
	}
	
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, 0);
	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {

		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�

			if (KeyCode == KeyEvent.KEYCODE_BACK) {


				T_check = false;
				T = null;
				finish();
				
				return false;
			}

		}

		return super.onKeyDown(KeyCode, event);

	}

    
	
}
