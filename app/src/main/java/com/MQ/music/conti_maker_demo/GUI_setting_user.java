package com.MQ.music.conti_maker_demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class GUI_setting_user extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	
	private Boolean DEVELOPER_MODE = true;
	
	//TextView
	private TextView TV_s_id;
	private TextView TV_my_s_id;
	
	//EditText
	private EditText ET_name;
	private EditText ET_etc;
	
	//ImageButton
	private ImageButton IB_ok;
	private ImageButton IB_profile;
	
	//Info
	private String getFrom;
	private String s_id;
	private String s_registrationId;
	private String s_name;
	private String s_etc;

	//NetworkCheck
	private Client NW_client = new Client();
	private String BackGroundThreadSet = ""; // Finish
	
	// Process Dialog
	private ProgressDialog PD_loading;

	// Thread
	private Thread T;// thread
	private Handler H;// handler
	private Boolean T_check = true;
	private int T_count = 0;

	//URL
	private String TEMP_PHOTO_FILE;       // �ӽ� ��������
	private final int REQ_CODE_PICK_IMAGE = 0;
	
	//menu
	private String[] arrage_menu = new String [] {"���� �ٹ�","����"};
	 
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
        setContentView(R.layout.gui_setting_user);
        
		//SetView
		DB_set();
        TV_set();
        ET_set();
        IB_set();
        
        Intent_set();
        
        //ReadyInfo
    	T_Start();
		
    }
	
	private void mainProcessing(){
     Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
     thread.start();
    }

	private Runnable doBackgroundThreadProcessing = new Runnable(){
		@Override
		public void run() {
			
			Log.e("GUI_setting_user", "����� ���� ������");
			Back_finish();
			BackGroundThreadSet = "Finish";
		
		}
	};

	
	public void DB_set() {
		DB_handler D_test = DB_handler.open(this);
		Cursor c_get_id = D_test.get_System();
		s_registrationId = c_get_id.getString(c_get_id.getColumnIndex("s_registrationId"));
		s_id = c_get_id.getString(c_get_id.getColumnIndex("s_id"));
		s_name = c_get_id.getString(c_get_id.getColumnIndex("s_name"));
		s_etc = c_get_id.getString(c_get_id.getColumnIndex("s_etc"));
		c_get_id.close();
		D_test.close();
	}
	
	
	//================================================================================================================================
	//View
	
	
    public void TV_set(){
		TV_s_id = (TextView) findViewById(R.id.TV_s_id);
		TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
	}
	
    public void ET_set(){
    	ET_name = (EditText) findViewById(R.id.ET_c_name);
    	ET_etc = (EditText) findViewById(R.id.ET_c_etc);
    	
	}
    
    public void IB_set(){
    	
    	IB_ok = (ImageButton) findViewById(R.id.button_ok);
    	IB_ok.setOnClickListener(this);
    	
    	IB_profile = (ImageButton) findViewById(R.id.IB_profile);
    	IB_profile.setOnClickListener(this);

    	String filePath = Environment.getExternalStorageDirectory()+"/contimaker/"+s_id+".png";
		File f1 = new File(filePath);
        if(f1.exists() == false){
			IB_profile.setImageResource(R.drawable.b_profile);
        }else{
            
            BitmapFactory.Options options = new BitmapFactory.Options();
     		options.inSampleSize = 2;
     		Bitmap src = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/contimaker/ready-"+s_id+".png", options);
     		Bitmap resized = Bitmap.createScaledBitmap(src, 100, 100, true); 
 	        IB_profile.setImageBitmap(resized);
 	        
        }
		
    }

	//================================================================================================================================
	//Setting
	
    public void Intent_set(){

    	Intent intent = getIntent();
    	getFrom = intent.getExtras().getString("getFrom");
    	
    	TEMP_PHOTO_FILE ="ready-"+s_id+".png";
        
    	TV_s_id.setText(s_id);
		TV_my_s_id.setText("ID: "+ s_id);
		ET_name.setText(s_name);
		ET_etc.setText(s_etc);
	}
	

    
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
			if(s.charAt(i) != '$'){
				out = out+s.charAt(i);
			}
		}

		return out;
	}
	
	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.button_ok){
    		//Making Dialog	
			
			if( RemoveSpace(ET_name.getText().toString()).equals("")){
				
				 new AlertDialog.Builder(GUI_setting_user.this)
				 .setTitle("����!")
				 .setMessage("�̸��� �Է����ּ���!")
				 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				
				 }).show();
				
			}else{
				PD_loading = ProgressDialog.show(this, "ContiMaker", "please wait....",true, true);
				PD_loading.setCancelable(false);
				mainProcessing();
			}
			 
			
		}else if ( v.getId() == R.id.IB_profile ) {

			
			//Making Dialog
			new AlertDialog.Builder(GUI_setting_user.this)
			.setTitle("��������")
			.setItems(arrage_menu, new DialogInterface.OnClickListener() 
			{		
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{// TODO Auto-generated method stub
					if(which == 0)
    				{
						Intent intent = new Intent( Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						intent.setType("image/*");
						intent.putExtra("crop", "true");
						intent.putExtra("aspectX", 1);
						intent.putExtra("aspectY", 1); 
						intent.putExtra("scale", true);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // ���� ����
			            intent.putExtra("outputFormat",  Bitmap.CompressFormat.PNG.toString());
			            startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
    				}
					else if(which == 1)
        			{
						String FilePath = Environment.getExternalStorageDirectory()+"/contimaker/"+s_id+".png";
						String ReadyFilePath = Environment.getExternalStorageDirectory()+"/contimaker/ready-"+s_id+".png";
						File File = new File(FilePath);
						File ReaydFile = new File(ReadyFilePath);
						if(File.exists()){
							File.delete();
							ReaydFile.delete();
						}
						IB_profile.setImageResource(R.drawable.b_profile);
        			}	
        		}
        	}).show();
            
            
		}
		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	/** �ӽ� ���� ������ ��θ� ��ȯ */
    private Uri getTempUri() {
        return Uri.fromFile(getProfileFile());
    }
    /** ����޸𸮿� �ӽ� �̹��� ������ �����Ͽ� �� ������ ��θ� ��ȯ  */
    private File getProfileFile() {

        File f1 = new File(Environment.getExternalStorageDirectory()+"/contimaker");
        if(f1.exists() == false){f1.mkdir();}
        
        if (isSDCARDMOUNTED()) {
            File f = new File(Environment.getExternalStorageDirectory()+"/contimaker", TEMP_PHOTO_FILE);
            try {
                f.createNewFile();      // ����޸𸮿� .png ���� ����
            } catch (IOException e) {
            }
 
            return f;
        } else
            return null;
    }
	
	
    /** SDī�尡 ����Ʈ �Ǿ� �ִ��� Ȯ�� */
    private boolean isSDCARDMOUNTED() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
 
        return false;
    }
    /** �ٽ� ��Ƽ��Ƽ�� �����Ͽ����� �̹����� ���� */
    protected void onActivityResult(int requestCode, int resultCode,
            Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
 
        switch (requestCode) {
        case REQ_CODE_PICK_IMAGE:
            if (resultCode == RESULT_OK) {
                if (imageData != null) {
                	
                	try {
                		
                        String filePath = Environment.getExternalStorageDirectory()+"/contimaker/"+s_id+".png";
                        SaveBitmapToFileCache(Environment.getExternalStorageDirectory()+"/contimaker/"+s_id+".png");

                        
     	               	BitmapFactory.Options options = new BitmapFactory.Options();
     	        		options.inSampleSize = 2;
     	        		Bitmap src = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/contimaker/ready-"+s_id+".png", options);
     	        		Bitmap resized = Bitmap.createScaledBitmap(src, 100, 100, true); 
     	    	        IB_profile.setImageBitmap(resized);
                       // temp.jpg������ �̹����信 �����.
     	    	        
					} catch (Exception e) {
						// TODO: handle exception
						IB_profile.setImageResource(R.drawable.b_profile);
					}
                }
            }
            break;
        }
    }
    
    private void SaveBitmapToFileCache( String strFilePath)
    {
            File fileCacheItem = new File(strFilePath);
        	OutputStream out = null;

        	try
        	{
        		fileCacheItem.createNewFile();
        		out = new FileOutputStream(fileCacheItem);
        		
        		BitmapFactory.Options options = new BitmapFactory.Options();
        		options.inSampleSize = 2;
        		Bitmap src = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/contimaker/ready-"+s_id+".png", options);
        		Bitmap resized = Bitmap.createScaledBitmap(src, 50, 50, true);   
        		
        		resized.compress(CompressFormat.PNG, 100, out);
        	}
        	catch (Exception e) 
        	{
        		e.printStackTrace();
        	}
        	finally
        	{
        		try
        		{
        			out.close();
        		}
        		catch (IOException e)
        		{
        			e.printStackTrace();
        		}
        	}
    }
    
    
	public void Back_finish(){

		
		NW_client.SendMyId(s_id, s_registrationId+"$"+s_id+"$"+RemoveSpace(ET_name.getText().toString())+"$"+RemoveSpace(ET_etc.getText().toString()));
		NW_client.SendMyPicture(s_id);
		
		
		
		if(getFrom.equals("GUI_main")){

			DB_handler D_test = DB_handler.open(this);
			D_test.Update_System_name_etc(s_id, RemoveSpace(ET_name.getText().toString()), RemoveSpace(ET_etc.getText().toString()));
			D_test.close();

			Intent intent = new Intent();
			intent.setClass(GUI_setting_user.this, GUI_main.class);
			intent.putExtra("getType","a");
	        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	        GUI_setting_user.this.startActivity(intent);
	        
		}else if(getFrom.equals("GUI_setting")){

			DB_handler D_test = DB_handler.open(this);
			D_test.Update_System_name_etc(s_id, RemoveSpace(ET_name.getText().toString()), RemoveSpace(ET_etc.getText().toString()));
			D_test.close();
		}
		

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
			public void handleMessage(Message msg) {

				Log.e("GUI_setting_user", Integer.toString(T_count));
				
				
				if(BackGroundThreadSet.equals("Finish")){
					BackGroundThreadSet = "";
					ThreadFinishSet();
				}
			}
		};

		T.start();
	}
	
	public void ThreadFinishSet(){

		if (PD_loading.isShowing()) {
			PD_loading.dismiss();
		}
		T_count = 0;

		// Thread ����.
		T_check = false;
		T = null;
		GUI_setting_user.this.finish();	

		Log.e("GUI_setting_user", "ThreadFinish");
    }
	
    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			
				
			if( KeyCode == KeyEvent.KEYCODE_BACK ){

				
				 
				return false;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    
}
