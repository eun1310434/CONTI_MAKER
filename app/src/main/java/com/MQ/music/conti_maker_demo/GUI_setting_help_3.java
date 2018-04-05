package com.MQ.music.conti_maker_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GUI_setting_help_3  extends Activity  implements OnClickListener {
	/**
     *  Manual
     *  1) ������Ʈ�� �Ȱ��
     *  	- s_id�� ������ �ڽ��� ��񿡼� ������ ������ ���� ���Ͽ��� �̹������� ������ �ɴϴ�.
     *  	    �̸� ���� �Ǵ� �Է��� �ϰ� �˴ϴ�. �׷����� ������ �������մϴ�.
     *  2) ����ڰ� ó�� ������ ��ġ�ϴ°��
     *  	- ����ڰ� �ڽ��� �̸��� ������ ������ �Է��Ͽ� ������ �������մϴ�.
     *         
     *  Check Date
     *  2013-03-10 (��) 
     */

	private String TAG = "GUI_setting_help_3";
    
	/** [1] ACTIVITY */
	private String TAG_ACTIVITY = "---- [1] ACTIVITY";


	/** [2] GUI*/
	private String TAG_GUI = "---- [2] GUI";

	//ImageButton
	private ImageButton IB_ok;
	private ImageButton IB_profile;
	
	//TextView
	private TextView TV_my_s_id;

	//EditText
	private EditText ET_s_name;
	private EditText ET_s_etc;
	
	// Process Dialog
	private ProgressDialog PD_loading;



	/** [3] GCM*/
	
	/** [4] SYSTEM */
	private String TAG_SYSTEM = "---- [4] SYSTEM";

	//Intent
	private String s_id;
	private String s_registrationId;
	private String s_name;
	private String s_etc;
	
	//Setting
	private Boolean DEVELOPER_MODE = true;

	private String s_version = "";

	//Network
	private Client NW_client = new Client();

	//URL
	private String TEMP_PHOTO_FILE;       // �ӽ� ��������
	private final int REQ_CODE_PICK_IMAGE = 0;
	
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
        setContentView(R.layout.gui_setting_help_3);
        
        //[1] ACTIVITY
		overridePendingTransition(0, 0);
        
        //[2] GUI
        TV_set();
        PD_set();
        ET_set();
        IB_set();
        
        //[3] GCM 
        
        //[4] SYSTEM 
        Intent_set();
        MakefileNoMedia();
    }
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[1] ACTIVITY
	@Override
    protected void onPause(){
    	super.onPause();
	    //������ �ߴܵǴ� ���. ���� ��� ī�z�̳� �˶� ��ȭ�� ���°�� Thread�� �����մϴ�.
    	
		//Log.e(TAG+TAG_ACTIVITY, "onPause");
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	//������ �ߴܵǴ� ��쿡�� �ٽ� ������ ���. ���� ��� ī�z�̳� �˶� ��ȭ�� ���°�� Thread�� ������մϴ�.
		
		//Log.e(TAG+TAG_ACTIVITY, "onResume");
    }

	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[2] GUI 
    
    public void TV_set(){
    	TV_my_s_id = (TextView) findViewById(R.id.TV_my_s_id);
    	
		//Log.e(TAG+TAG_GUI,"- TV_set -");
    }
    public void PD_set(){
		PD_loading = new ProgressDialog(this);
		PD_loading.setTitle("ContiMaker");
		PD_loading.setMessage("please wait....");
		PD_loading.setIndeterminate(true);
		PD_loading.setCancelable(false);
		
		//Log.e(TAG+TAG_GUI, "- PD_set -");
    }
    public void ET_set(){
    	ET_s_name = (EditText) findViewById(R.id.ET_s_name);
    	ET_s_name.setText(s_name);
    	
    	ET_s_etc = (EditText) findViewById(R.id.ET_s_etc);
    	ET_s_etc.setText(s_etc);
		
		//Log.e(TAG+TAG_GUI, "- ET_set -");
    }
    public void IB_set(){
    	IB_ok = (ImageButton)findViewById(R.id.IB_ok);
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
		//Log.e(TAG+TAG_GUI, "- IB_set -");
    }
	
	@Override
	public void onClick(View v) {
		
		if ( v.getId() == R.id.IB_profile ) {
			//Log.e(TAG+TAG_GUI, "- IB_click -\n"+"GetPic: GUI_setting_help_3");
			
			Intent intent = new Intent( Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1); 
			intent.putExtra("scale", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());     // ���� ����
            intent.putExtra("outputFormat",  Bitmap.CompressFormat.PNG.toString());
            startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
            
            
		}else if( v.getId() == R.id.IB_ok && NetworkCheck()){
			//Log.e(TAG+TAG_GUI, "- IB_click -\n"+"Intent: GUI_setting_help_3");
			
			if(RemoveSpace(ET_s_name.getText().toString()).equals("")){
				
				 new AlertDialog.Builder(GUI_setting_help_3.this)
				 .setTitle("����!")
				 .setMessage("�̸��� �Է����ּ���!")
				 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				
				 }).show();
				 
			}else{
				
				PD_loading.show();
				
				//���� Thread
				Runnable doBackgroundThreadProcessing = new Runnable(){
					@Override
					public void run() {
						
						Log.e("GUI_setting_help_3", "����� ���� ������");
						BackGround_Thread_Finish_set();
					
					}
				};
			    Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
			    thread.start();
			}
		}
				
		
		
	}
	
	public void BackGround_Thread_Finish_set(){
		
		NW_client.SendMyId(s_id, s_registrationId+"$"+s_id+"$"+RemoveSpace(ET_s_name.getText().toString())+"$"+RemoveSpace(ET_s_etc.getText().toString()));
		NW_client.SendMyPicture(s_id);
		
		DB_handler D_test = DB_handler.open(this);
		D_test.delete_System(s_id);
		D_test.Insert_System(s_id,s_registrationId,RemoveSpace(ET_s_name.getText().toString()),RemoveSpace(ET_s_etc.getText().toString()), "����" ,s_version);	
		D_test.close();
		

		if (PD_loading.isShowing()) {
			PD_loading.dismiss();
		}
		
		Intent intent = new Intent();
		intent.setClass(GUI_setting_help_3.this, GUI_start.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
        GUI_setting_help_3.this.startActivity(intent);
		GUI_setting_help_3.this.finish();
		
	}
	
	public Boolean NetworkCheck(){
		//�������� Ȱ��ȭ �Ǿ� �ִ��� Ȯ���մϴ�.
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();

			            		  
		if(isWifiConn==false && isMobileConn==false)
		{
			
			new AlertDialog.Builder(GUI_setting_help_3.this)
			.setTitle("����!")
			.setMessage("���ͳ� ������ Ȯ���� �ּ���.")
			.setPositiveButton("��",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,int which) { // TODO
							finish();
						}
					}).setCancelable(false).show();
	    		//Log.e(TAG+TAG_SYSTEM,"- NetworkCheck - \n"+"1) check: false");
			   return false;
		}
    	//Log.e(TAG+TAG_SYSTEM,"- NetworkCheck - \n"+"check: true");
		return true;
	}


	//---------------------------------------------------------------------------------------------------------------------------------
	//[3] GCM 
	
	//---------------------------------------------------------------------------------------------------------------------------------
	//[4] SYSTEM 
	 public void Intent_set(){
		
    	
    	Intent intent = getIntent();
    	s_registrationId = intent.getExtras().getString("s_registrationId");
    	s_id = intent.getExtras().getString("s_id");
    	s_name = intent.getExtras().getString("s_name");
    	s_etc = intent.getExtras().getString("s_etc");
    	s_version = intent.getExtras().getString("s_version");

		//UI_setting
    	TV_my_s_id.setText("ID: "+s_id);
    	
    	//File
    	TEMP_PHOTO_FILE ="ready-"+s_id+".png";
    	
    	//Log.e(TAG+TAG_SYSTEM,"- Intent_set - \n"+"1) s_version: "+s_version);
	}
	 
	public void MakefileNoMedia(){
			File f1 = new File(Environment.getExternalStorageDirectory()
					+ "/contimaker");
			if (f1.exists() == false) {
				f1.mkdir();
			}
			
			File f2 = new File(Environment.getExternalStorageDirectory()
					+ "/contimaker/.nomedia");
			if (f2.exists() == false) {
				f2.mkdir();
			}
	}
	
	public String RemoveSpace(CharSequence s)
	{
		// ���ڿ��� ó���κп� �Էµ� �����̽� �κ��� ��� ���� �� �ݴϴ�.
		
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
    protected void onActivityResult(int requestCode, int resultCode,Intent imageData) {
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
	
	

    @Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			
				
			if( KeyCode == KeyEvent.KEYCODE_BACK ){
				
				 new AlertDialog.Builder(GUI_setting_help_3.this)
				 .setTitle("����!")
				 .setMessage("���� �Ͻðڽ��ϱ�?")
				 .setPositiveButton("��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

			      		//Log.e(TAG+TAG_SYSTEM, "- FINISH -\n");
						
						finish();
					}
				})
				.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
				return false;
				}
		}
		 
		return super.onKeyDown( KeyCode, event );
		
	}
    
}
