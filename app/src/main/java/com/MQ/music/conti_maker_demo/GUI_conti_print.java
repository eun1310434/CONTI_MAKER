package com.MQ.music.conti_maker_demo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GUI_conti_print extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	//Intent 
	private String c_name; 
	private String print_type;

	//Print 
	private String title = "";
	private String writer = "";
	private String subject = "";
	private int date = 0;
	
	
	//Thread_1
	private  Thread T_time_1;
	private Handler H_time_1;
	private Boolean T_time_check_1 = true;
	
	private int count_1 = 0;
	private int final_count_1 = 0;
	
	//private String Ready[] = {"��","��","��","�ؤ�","�غ�","�غ�","�غ���","�غ���","�غ��ߤ�","�غ�����","�غ�����","�غ����Ԥ�","�غ����Դ�","�غ����Ԉ�","�غ����Դϴ�","�غ����Դϴ�.","�غ����Դϴ�..","�غ����Դϴ�..."};
	
	//Making Paper
	private int[] mimg;
	private Boolean print_start = false;
	private Boolean print_finish = false;
    int TotalImg =0;
    int NowImg = 0;
    
	//�ʱ� ��Ÿ��
	private Paint P_l;
	private Paint P_m_1;
	private Paint P_m_2;
	private Paint P_s;
    
	private Boolean DEVELOPER_MODE = true;

	private TextView TV_contents_0 ;
	
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
        setContentView(R.layout.gui_conti_print);
		
        Intent_set();
        TV_set();
        DB_set();
        Start();
    }
    
    public void Intent_set(){
    	
    	Intent intent = getIntent();
        c_name = intent.getExtras().get("c_name").toString();
        print_type = intent.getExtras().get("print_type").toString();
        
    }
   
    public void DB_set(){
    	
   	 	DB_handler D_test = DB_handler.open(this);
   	 
        Cursor C_get_conti = D_test.get_continuity(c_name);
        title = C_get_conti.getString(C_get_conti.getColumnIndex("c_name"));
        date = C_get_conti.getInt(C_get_conti.getColumnIndex("c_mdate"));
        writer = C_get_conti.getString(C_get_conti.getColumnIndex("c_writer"));
        subject = C_get_conti.getString(C_get_conti.getColumnIndex("c_subject"));
        C_get_conti.close();
        
        
        Cursor C_get_conti_music = D_test.get_conti_music(c_name);
        TotalImg = C_get_conti_music.getCount();
        
        mimg = new int [TotalImg];
        for(int i = 0 ; i <TotalImg ; i++)
        {
        	mimg[i] = C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("m_img"));
        	 if(C_get_conti_music.moveToNext()== false){break;};
        }
        
        C_get_conti_music.close();
        
        D_test.close();
   }
   
    public void TV_set(){
    	
    	TV_contents_0 = (TextView)findViewById(R.id.contents_0);
    	TV_contents_0.setText(String.valueOf("�غ��� �Դϴ�."));
    	
    }
  //==========================================================================================
    //Thread_Start
    public void Start(){

        T_time_1 = new Thread(new Runnable() {
    		
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			while(T_time_check_1){
        			try {
        				H_time_1.sendMessage(H_time_1.obtainMessage());
        				Thread.sleep(5);
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			count_1++;
    			}
    		}
    	});
        
        H_time_1 = new Handler(){
        	@Override
			public void handleMessage(Message msg){
        		
        		
        		if(count_1 > 400 && print_start == false)
        		{
        			try {
        				
            			Print();
            			
					} catch (Exception e) {
						// TODO: handle exception
						
						
					}
        			
        			
     				print_start= true;
        			 
        		}else if(count_1 > 380 && print_start == false)
        		{
        			TV_contents_0.setText("��Ƽ ���� �� �Դϴ�...");
        			 
        		}else if(print_finish == true ){
        			
        			print_finish = false;
        			TV_contents_0.setText(String.valueOf("���� �� �Դϴ�."));
        			
    				final_count_1 = count_1+400;
    				
        		}else if(final_count_1 < count_1 && T_time_check_1 == true && print_start == true ){
        			
        			T_time_check_1 = false;
        			T_time_1 = null;
        			

        			try {
                    	Uri targetUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    	String targetDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/conti/"+c_name+"/"+c_name+"_"+print_type;   // Ư�� ���!!
                		targetUri = targetUri.buildUpon().appendQueryParameter("bucketId", String.valueOf(targetDir.toLowerCase().hashCode())).build();
                		Intent intent = new Intent(Intent.ACTION_VIEW, targetUri);
     	    			GUI_conti_print.this.startActivity(intent);
     	    			GUI_conti_print.this.finish();
            			
						
					} catch (Exception e) {
						// TODO: handle exception
						Intent intent = new Intent();
	    	        	intent.setClass(GUI_conti_print.this, GUI_conti.class);
	    				GUI_conti_print.this.startActivity(intent);
	 	    			GUI_conti_print.this.finish();
					}
        			
        		}
        			
    			
        	}
        };

        T_time_1.start();
    }
    //==========================================================================================
    //Thread_Start
    public void Print(){

        //�ʱ�ä
    	wtype();
    	
        if(print_type.equals("A4_one")){
            	
           	 MakeConti_one(title, writer, subject, date, mimg);
           	 
        }else if(print_type.equals("A4_many")){
            	
           	 MakeConti_many(title, writer, subject, date, mimg);
           	 
        }
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
    public void MakeConti_one(String _title, String _writer , String _subject , int _date , int [] _mimg){
    	
    	//------------------------------------------------------------------------------------------------------------------------
    	//Mapping
    	String in_title = _title; 
		String in_writer = _writer;
		String in_subject = _subject;
		String in_date = String.valueOf(_date/10000)+"�� "+ String.valueOf((_date%10000)/100)+"�� "+String.valueOf(_date%100)+"��";
		

    	//------------------------------------------------------------------------------------------------------------------------
		// ���� ���� Ȯ��
        File f = new File(Environment.getExternalStorageDirectory()+"/conti");
        if(f.exists() == false){
        	f.mkdir();
        }
        
        File conti = new File(Environment.getExternalStorageDirectory()+"/conti/"+in_title);
        if(conti.exists() == false){
        	conti.mkdir();
        }
        

        File conti_type = new File(Environment.getExternalStorageDirectory()+"/conti/"+in_title+"/"+title+"_"+"A4_one");
        if(conti_type.exists() == false){
        	conti_type.mkdir();
        }

    	//------------------------------------------------------------------------------------------------------------------------
        //Print
        int width = 1050;
        int height = 1485;
		
        int top_gap = 170;
        int left_gap_1 = 25;
        
    	// Page �����
        int m;
		int s;
		int gap;
		
		Bitmap bmOverlay;
		BitmapDrawable bd;
		Canvas canvas;
		File file;
		OutputStream outStream;
		for(NowImg = TotalImg-1 ; -1 < NowImg  ; NowImg--)
		{
			
			//���
			bmOverlay = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
			canvas = new Canvas(bmOverlay);
			canvas.drawColor(Color.WHITE);
			
			//Text 
			if(NowImg == 0)
			{//1�������� ���
				canvas.drawText(in_title							, left_gap_1, 70	,P_l);
				canvas.drawText(in_date+" | "+in_writer+" | "+in_subject	, left_gap_1, 110	,P_s);
				canvas.drawText("�̸�: "							, left_gap_1, 140	,P_s);
					
			}
			canvas.drawText("-"+String.valueOf(NowImg+1)+"-", 500, 1435,P_m_1);
			canvas.drawText(" | "+in_title, 700, 1410,P_s);
			canvas.drawText(" | "+"ContiMaker Lite", 700, 1435,P_s);
			
			//�Ǻ�
			bd = (BitmapDrawable) getResources().getDrawable(mimg[NowImg]);
			
			s = 850;
			m = bd.getBitmap().getWidth();
			gap = (width - bd.getBitmap().getWidth()*s/m )/2;//��� ��ġ�� ���߱� ���ؼ� ������ �������� ��ҵ� ���� ��ü �ʺ��� ���� ���ϴ�. �׷����� ���� ���̵��� ���� ���� �� �ǹǷ� ���� ���� 2�� �����ϴ�.

			if(bd.getBitmap().getHeight()*s/m > 1200)
			{
				s = 1200;
				m = bd.getBitmap().getHeight();
				gap = (width- bd.getBitmap().getWidth() *s/m)/2;//��� ��ġ�� ���߱� ���ؼ� ������ �������� ��ҵ� ���� ��ü �ʺ��� ���� ���ϴ�. �׷����� ���� ���̵��� ���� ���� �� �ǹǷ� ���� ���� 2�� �����ϴ�.

			}
			
			
			//Img
			canvas.drawBitmap(bd.getBitmap(), null		,new Rect(gap,	top_gap,	gap + bd.getBitmap().getWidth() *s/m,	top_gap + bd.getBitmap().getHeight()*s/m)	, null);	
			
			//canvas.drawText(String.valueOf(i+1)+")", gap , top_gap + 30 ,P_m_2);// ��ȣ

			
	        String filename ="[A4_one] "+in_title+" p"+String.valueOf(NowImg+1)+".png";
	        
	        file = new File(Environment.getExternalStorageDirectory()+"/conti/"+in_title+"/"+in_title+"_A4_one", filename);
	        try {
	        	file.createNewFile();
		        outStream = new FileOutputStream(file);
		        bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		        outStream.flush();
		        outStream.close();
		        

		    	Uri uri = Uri.parse("file://" + Environment.getExternalStorageDirectory()+"/conti/"+in_title+"/"+in_title+"_A4_one/"+filename);
				Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); 
				i.setData(uri);
				this.sendBroadcast(i);
				
		    	print_finish = true;
		    	
		    } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        
	        bmOverlay.recycle();
	        bmOverlay = null;
	        
		} 
		
    	//this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://" + Environment.getExternalStorageDirectory()))); 
    	
		
    	print_finish = true;
	}
    	
        
    
    
    public void MakeConti_many(String _title, String _writer , String _subject , int _date , int [] _mimg){
    	//------------------------------------------------------------------------------------------------------------------------
    	//Mapping
    	String in_title = _title; 
		String in_writer = _writer;
		String in_subject = _subject;
		String in_date = String.valueOf(_date/10000)+"�� "+ String.valueOf((_date%10000)/100)+"�� "+String.valueOf(_date%100)+"��";
		

    	//------------------------------------------------------------------------------------------------------------------------
		// ���� ���� Ȯ��
        File f = new File(Environment.getExternalStorageDirectory()+"/conti");
        if(f.exists() == false){
        	f.mkdir();
        }
        
        File conti = new File(Environment.getExternalStorageDirectory()+"/conti/"+in_title);
        if(conti.exists() == false){
        	conti.mkdir();
        }
        

        File conti_type = new File(Environment.getExternalStorageDirectory()+"/conti/"+in_title+"/"+in_title+"_"+"A4_many");
        if(conti_type.exists() == false){
        	conti_type.mkdir();
        }
        
    	// Page check
		BitmapDrawable bd;
		
		int c_img = 0;
		String l_mimg[] = new String[TotalImg];
		int page[] = new int [13];
		
		int page_num = 0;
		int TotalPage = 0;
		
		int h1 = 0;
		int w1 = 0;
		
		int h2 = 0;
		int w2 = 0;
		
		int width = 1050;
		int height = 1485;
		
		int left_gap_1 = 25;
		int top_gap = 170;

    	// Page �����
        int m1 = 0;
        int m2 = 0;
		int s = 525;
		int gap = 0;
		
		while(true)
		{
			
			if(c_img == TotalImg-1){l_mimg[c_img] = "up1"; page[page_num] = 1; TotalPage = page_num+1; break;}
			else if(c_img>TotalImg-1){ TotalPage = page_num; break;}
			//�Ǻ�
			//�̹��� ���� 
			bd = (BitmapDrawable) getResources().getDrawable(mimg[c_img]);
			h1 = bd.getBitmap().getHeight();
			w1 = bd.getBitmap().getWidth();
			m1 = w1;
			
			bd = (BitmapDrawable) getResources().getDrawable(mimg[c_img+1]);
			h2 = bd.getBitmap().getHeight();
			w2 = bd.getBitmap().getWidth();
			m2 = w2;
			

			if((h1*s/m1 < 635 ) && (h2*s/m2 < 635))
			{
				page[page_num] = 2; 
				l_mimg[c_img++] = "up1"; 
				l_mimg[c_img++] = "down1"; 
			}else{
				page[page_num] = 1; 
				l_mimg[c_img++] = "up1"; 
			}
			
			
			if(c_img == TotalImg-1){l_mimg[c_img] = "up2"; page[page_num] = page[page_num] + 1; TotalPage = page_num+1; break;}
			else if(c_img>TotalImg-1){ TotalPage = page_num+1; break;}
			//�Ǻ�
			//�̹��� ���� 
			bd = (BitmapDrawable) getResources().getDrawable(mimg[c_img]);
			h1 = bd.getBitmap().getHeight();
			w1 = bd.getBitmap().getWidth();
			m1 = w1;
			
			bd = (BitmapDrawable) getResources().getDrawable(mimg[c_img+1]);
			h2 = bd.getBitmap().getHeight();
			w2 = bd.getBitmap().getWidth();
			m2 = w2;
			

			if((h1*s/m1 < 635 ) && (h2*s/m2 < 635))
			{
				page[page_num] = page[page_num] + 2; 
				l_mimg[c_img++] = "up2"; 
				l_mimg[c_img++] = "down2"; 
			}else{
				page[page_num] = page[page_num] + 1; 
				l_mimg[c_img++] = "up2"; 
			}
			
			page_num++;
			
		} 
    	//------------------------------------------------------------------------------------------------------------------------
        //Print
		
		Bitmap bmOverlay;
		Canvas canvas;
		File file;
		OutputStream outStream;
        int m = 0;
		
		int i_mimg = TotalImg - 1;
		for(int NowPage = TotalPage-1 ; -1 < NowPage ; NowPage-- )
		{
			//���
			bmOverlay = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
			canvas = new Canvas(bmOverlay);
			canvas.drawColor(Color.WHITE);
			
			if(NowPage == 0)
			{
				//1�������� ���
				canvas.drawText(in_title						, left_gap_1, 70	,P_l);
				canvas.drawText(in_date+" | "+in_writer+" | "+in_subject	, left_gap_1, 110	,P_s);
				canvas.drawText("�̸�: "							, left_gap_1, 140	,P_s);
				
			}
			canvas.drawText("-"+String.valueOf(NowPage+1)+"-", 500, 1435,P_m_1);
			canvas.drawText(" | "+title, 700, 1410,P_s);
			canvas.drawText(" | "+"ContiMaker Lite", 700, 1435,P_s);

			for(int i = 0 ; i < page[NowPage] ; i++)
			{

				//-------------------------------------------------------------------------------------------------------------
				//�̹��� ���� 
				bd = (BitmapDrawable) getResources().getDrawable(mimg[i_mimg]);
				h1 = bd.getBitmap().getHeight();
				w1 = bd.getBitmap().getWidth();
				
				s = 500;
				m = w1;
				gap = (width/2 - w1*s/m )/2;//��� ��ġ�� ���߱� ���ؼ� ������ �������� ��ҵ� ���� ��ü �ʺ��� ���� ���ϴ�. �׷����� ���� ���̵��� ���� ���� �� �ǹǷ� ���� ���� 2�� �����ϴ�.

				
				if(l_mimg[i_mimg].equals("up1"))
				{
					//�̹��� �Է�
					canvas.drawBitmap(bd.getBitmap(), null		,new Rect(gap,	top_gap,	gap + w1*s/m,	top_gap + h1*s/m)	, null);	
					canvas.drawText(String.valueOf(i_mimg+1)+")", gap , top_gap + 30 ,P_m_1);
					
				}else if(l_mimg[i_mimg].equals("down1"))
				{
					//�̹��� �Է�
					canvas.drawBitmap(bd.getBitmap(), null	,new Rect(gap,	top_gap + 600 + 25 , gap + w1*s/m , top_gap + 600 + 25 + h1*s/m )	, null);	
					canvas.drawText(String.valueOf(i_mimg+1)+")", gap, top_gap + 600 + 25 +30,P_m_1);
					
				}else if(l_mimg[i_mimg].equals("up2"))
				{
					//�̹��� �Է�
					canvas.drawBitmap(bd.getBitmap(), null		,new Rect(width/2+gap,	top_gap,	width/2+gap + w1*s/m,	top_gap + h1*s/m)	, null);	
					canvas.drawText(String.valueOf(i_mimg+1)+")", width/2+gap , top_gap + 30 ,P_m_1);
					
				}else if(l_mimg[i_mimg].equals("down2"))
				{
					//�̹��� �Է�
					canvas.drawBitmap(bd.getBitmap(), null	,new Rect(width/2+gap,	top_gap + 635 + 25 , width/2+gap + w1*s/m , top_gap + 635 + 25 + h1*s/m )	, null);	
					canvas.drawText(String.valueOf(i_mimg+1)+")", width/2+gap, top_gap + 635 + 25 +30,P_m_1);
					
				}
				
				i_mimg--;
			}
			
			
	        String filename ="[A4_many] "+in_title+" p"+String.valueOf(NowPage+1)+".png";
	        file = new File(Environment.getExternalStorageDirectory()+"/conti/"+in_title+"/"+in_title+"_A4_many", filename);
	        try {
	        	file.createNewFile();
		        outStream = new FileOutputStream(file);
		        bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		        outStream.flush();
		        outStream.close();
		        
		        //�̵�� ��ĵ
		    	Uri uri = Uri.parse("file://" + Environment.getExternalStorageDirectory()+"/conti/"+in_title+"/"+in_title+"_A4_many/"+filename);
				Intent i = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); 
				i.setData(uri);
				sendBroadcast(i);

		    	print_finish = true;
		    	
		    } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        
	        
	        bmOverlay.recycle();
	        bmOverlay = null;
		}
		
		
		
    	//this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://" + Environment.getExternalStorageDirectory()))); 

    }
    

	
	public void wtype(){
		
		
		P_l = new Paint();
		P_l.setAntiAlias(true);
		P_l.setColor(Color.BLACK);
		P_l.setTextSize(60);

		P_m_1 = new Paint();
		P_m_1.setAntiAlias(true);
		P_m_1.setColor(Color.BLACK);
		P_m_1.setTextSize(30);

		P_m_2 = new Paint();
		P_m_2.setAntiAlias(true);
		P_m_2.setColor(Color.BLACK);
		P_m_2.setTextSize(45);

		P_s = new Paint();
		P_s.setAntiAlias(true);
		P_s.setColor(Color.GRAY);
		P_s.setTextSize(20);
		
	}
  
	@Override
	public boolean onKeyDown( int KeyCode, KeyEvent event )
	{
	 
    	
		if( event.getAction() == KeyEvent.ACTION_DOWN ){
			// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
			// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�
	 
			if( KeyCode == KeyEvent.KEYCODE_BACK ){
				
				return false;
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
    
	
}