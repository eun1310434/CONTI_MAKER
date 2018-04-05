package com.MQ.music.conti_maker_demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class GUI_music_view_portrait extends Activity implements OnTouchListener , OnClickListener {
	/** Called when the activity is first created. */

	//Information
	private int m_id;//�Ǻ��� ���̵�
	private String m_name = "";//�Ǻ��� ����
	private String m_words = "";//�Ǻ��� ù����
	private String m_code = "";
	
	private String m_img;//���� ȭ���� ��� �Ǻ��� �̹���
	
	private int ms_num = 0;//���� ȭ���� ��� �Ǻ��� ��ȣ
	private int ms_total;
	private String [] ms_img;//���� ȭ���� ��� �Ǻ��� �̹���
	
	private int MoveToNext = -1 ; // 0 --> �����Ǻ��� �̵�, 1 --> �����Ǻ��� �̵�
	private int MoveToPage = -1 ; // 0 --> �����Ǻ��� �̵�, 1 --> �����Ǻ��� �̵�
	
	//Intent
	private String getFrom = ""; // GUI_msuci  ,  GUI_conti

	//TextView
	private TextView TV_title;
	
	//Relative Layout
	private RelativeLayout RL_items;
	private RelativeLayout RL_title;
	private String ItemSet = "on"; //on , off

	//ImageButton
	private ImageButton IB_screen;
	private ImageButton IB_share;
	private ImageButton IB_youtube; 
	
	//music List
	private ListView LV_ms_page;
	private ArrayList<AL_ms_page> ms_ListItem;
	private LMA_ms_page LMA_ms_page;
		
	// Thread_1
	private Thread T_time;
	private Handler H_time;
	private Boolean T_time_check = true;

	private int count = 0;

	private int click_time_count = 0;
	private int click = 0;
	
	
	
	private String cm_name;// ��Ƽ�� ����
	private int cm_num;// ��Ƽ�� �Ǻ� ����
	private int cm_total;// ��ü ��Ƽ�� �Ǻ� ��
	private String[] cm_m_name ;// ��Ƽ�� ������ �Ǻ� ����
	
	//[ IMAGE SET ]
	private static final String TAG = "Touch";

	// These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	private Matrix savedMatrix2 = new Matrix();
	
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	// Image Setting Scale
	float SET_IMAGE_SCALE = 0.0f;
	float SET_IMAGE_SCALE_H = 0.0f;
	float SET_IMAGE_SCALE_W = 0.0f;

	// Display_mode
	private int DISPLAY_MODE = 0;// [ 0 --> ���� ], [ 1 --> �ʺ� ]
	private float DISPLY_W = 0.0f;// ȭ���� ����
	private float DISPLY_H = 0.0f;// ȭ���� ����
	private Boolean ImageSize_in= false;

	//
	private Boolean FIRST_IMAGE_SET = true;
	private int IMAGE_SET = 0; // ���� �߽� -> 0 , �ʺ� �߽� -> 1 ,
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (true) {
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
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.gui_music_view_portrait);
		overridePendingTransition(0, 0); 
		

    	
		Intent_set();
		MUSIC_DB_set();
		RL_set();
		TV_set();
		IB_set();
		IV_score_set();
		LV_ms_page_set();
		Start();
		
		
	}
    
    @Override
    protected void onPause(){
    	super.onPause();

		//Log.e("================================================", "PAUSE");
		T_time_check = false;
		T_time = null;
    }
    
    @Override
    protected void onResume(){
    	super.onResume();

		//Log.e("================================================", "Resume");
		T_time_check = true;
		Start();
    }
	
	public void Intent_set() {
		
			// ������ Activity ���� �Ǻ��� id�� ������ ���ϴ�.
			Intent intent = getIntent();
			getFrom = intent.getExtras().getString("getFrom");
			
			if(getFrom.equals("GUI_music")){//�Ǻ��˻����� �� ���
				
				m_id = intent.getExtras().getInt("m_id");
				ItemSet = intent.getExtras().getString("ItemSet");
				
			}else if(getFrom.equals("GUI_conti")){//��Ƽ���ۿ��� �� ���
				
				cm_name = intent.getExtras().getString("cm_name");
				cm_num = intent.getExtras().getInt("cm_num");
				cm_total = intent.getExtras().getInt("cm_total");
				
				DB_handler D_test = DB_handler.open(this);
				Cursor C_get_conti_music = D_test.get_conti_music(cm_name, cm_num);
				m_id = C_get_conti_music.getInt(C_get_conti_music.getColumnIndex("m_id"));
				C_get_conti_music.close();
				D_test.close();
				
				
			}
			

		}
	
	
	public void RL_set(){
		RL_items = (RelativeLayout) findViewById(R.id.RL_items);
		RL_title = (RelativeLayout) findViewById(R.id.RL_title);
		
		if(ItemSet.equals("on")){
			RL_items.setVisibility(View.VISIBLE);
			RL_title.setVisibility(View.VISIBLE);
		}else if(ItemSet.equals("off")){
			RL_items.setVisibility(View.INVISIBLE);
			RL_title.setVisibility(View.INVISIBLE);
			
		}
		
	}
	
	public void TV_set(){
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_title.setText(m_name);
	}
	
	public void IB_set(){
		
		IB_screen = (ImageButton) findViewById(R.id.IB_screen);
		IB_share = (ImageButton) findViewById(R.id.IB_share);
		IB_youtube = (ImageButton) findViewById(R.id.IB_youtube);
		
		IB_screen.setOnClickListener(this);
		IB_share.setOnClickListener(this);
		IB_youtube.setOnClickListener(this);
		
	}
	//====================================================================================================================================
    /*
	 * Data Base
	 */
	public void MUSIC_DB_set(){
		
		DB_handler D_test = DB_handler.open(this);
	    
		//���� ȭ���� �̹����� ������ �ɴϴ�.
		Cursor C_get_music = D_test.get_music(m_id);
		m_name = C_get_music.getString(C_get_music.getColumnIndex("m_name"));
		m_words = C_get_music.getString(C_get_music.getColumnIndex("m_words"));
		m_code = C_get_music.getString(C_get_music.getColumnIndex("m_code"));
		m_img = Environment.getExternalStorageDirectory().getAbsoluteFile()+"/contimaker/score/wc"+Integer.toString(m_id)+".png";
		C_get_music.close();
		
		
		//���� ȭ���� �̹����� ������ �ɴϴ�.
		Cursor C_get_music_sub = D_test.get_music_sub(m_id);
		ms_total = C_get_music_sub.getCount();
		Log.e("page total", Integer.toString(ms_total));
		ms_img = new String [ms_total];
		int i = 0;
		while(true){
			ms_img[i] = Environment.getExternalStorageDirectory().getAbsoluteFile()+"/contimaker/score/wc"+Integer.toString(m_id)+"_"+(i+1)+".png";
			if(C_get_music_sub.moveToNext() == false){ break;}
			i++;
		}
		C_get_music_sub.close();
		
		
 		D_test.close();
 		
	}
	
	//====================================================================================================================================
    /*
	 * �޴��ɼ�
	 */
	/*
	public boolean onCreateOptionsMenu(Menu menu){
		
		super.onCreateOptionsMenu(menu);
		
		menu.add(0,1,0,"YouTube").setIcon(android.R.drawable.ic_menu_search);
		menu.add(0,2,0,"Help").setIcon(android.R.drawable.ic_menu_help);
		
		
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case 1:

			

			
			return true;
		case 2:
			
			new AlertDialog.Builder(GUI_music_view_portrait.this)
			 .setTitle("����")
			 .setMessage("- MP3�� �ȳ����� ���\n\n\"�Ǻ� ����\"�� ������ \"MP3\" �� ã�� �ִ� ���� �Դϴ�. ����� MP3 ������ �Ǻ��� ����� �����ϰ� �ٽ� ������ �ּ���.")
			 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
			
			return true;
		}
		return false;
	}
	 */
	
	//====================================================================================================================================
	//ListView page
	
	public void LV_ms_page_set(){
		
		
		LV_ms_page = (ListView) findViewById(R.id.LV_ms_page);
		ms_ListItem = new ArrayList<AL_ms_page>();
	    

		for(int i = 0 ; i < ms_total ; i++){
			
			if(i == ms_num){
				ms_ListItem.add(new AL_ms_page(1,Integer.toString(i+1)));	
			}else{
				ms_ListItem.add(new AL_ms_page(0,Integer.toString(i+1)));	
			}
		}
		
	    LMA_ms_page = new LMA_ms_page (this, ms_ListItem );
	    
		LV_ms_page.setAdapter(LMA_ms_page);
		LV_ms_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			
 	    	@Override
 	    		public void onItemClick(AdapterView parent, View view, int position, long id) {
 					// TODO Auto-generated method stub
			
			 	   		ms_num = position;
			 	   		FIRST_IMAGE_SET = true;
			 	   		IMAGE_SET = 0;
			 	   		IV_score_set();
			 	   		LV_ms_page_check();
 					}
 				});

		if(DISPLAY_MODE == 0){
			LV_ms_page.setVisibility(View.INVISIBLE);
		}else{
			LV_ms_page.setVisibility(View.VISIBLE);
		}
		
	}
	
	public void LV_ms_page_check(){
		
		ms_ListItem.clear();
		for(int i = 0 ; i < ms_total ; i++){
			
			if(i == ms_num){
				ms_ListItem.add(new AL_ms_page(1,Integer.toString(i+1)));	
			}else{
				ms_ListItem.add(new AL_ms_page(0,Integer.toString(i+1)));	
			}
		}
		LMA_ms_page.notifyDataSetChanged();
		
	}

	
	//====================================================================================================================================
	//IMAGE
	@SuppressLint("WrongConstant")
	public void IV_score_set() {
		//�Ǻ��� ImageButton���� ����ϴ�.
		
		ImageView IV_m_img = (ImageView) findViewById(R.id.IV_m_img);
		IV_m_img.setOnTouchListener(this);
		
		// 1. ȭ���� ���¸� Ȯ���մϴ�.
		WindowManager wm = getWindowManager();
		if (wm == null)
			return;
		DISPLAY_MODE = wm.getDefaultDisplay().getOrientation();
		
		if (DISPLAY_MODE == 0 || DISPLAY_MODE == 2) { // �����߽�
			IV_m_img.setImageURI(Uri.fromFile(new File(m_img)));
			DISPLAY_MODE = 0 ;
			
		} else {// �ʺ��߽�
			IV_m_img.setImageURI(Uri.fromFile(new File(ms_img[ms_num])));
			DISPLAY_MODE = 1; 
		}
		
		First_set_img(IV_m_img);
		
	}
	
	
	public void First_set_img(ImageView view) {
		// �̹����� ȭ���� ũ�⿡ �°� ����ϴ�.

		// 2. ȭ���� ũ�⸦ Ȯ�� �մϴ�.
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();
		DISPLY_W = display.getWidth();
		DISPLY_H = display.getHeight();

		// 3. �̹����� ũ�⸦ Ȯ���մϴ�.
		Drawable d = view.getDrawable();
		if (d == null)
			return;
		float IMAGE_W = d.getIntrinsicWidth();
		float IMAGE_H = d.getIntrinsicHeight();

		SET_IMAGE_SCALE_W = DISPLY_W / IMAGE_W;
		SET_IMAGE_SCALE_H = DISPLY_H / IMAGE_H;

		// 4. ũ�⿡ �°� ȭ�鿡 ����ϴ�.
		String out = "";

		if (DISPLAY_MODE == 0 ) { // �����߽�
			out = "DISPLAY_MODE: �����߽�";

			// ���� �߽����� ������ �ٿ����� ---> �ڵ�����
			if (SET_IMAGE_SCALE_H * IMAGE_W > DISPLY_W) {
				// ���� �߽����� ������ ��Ȥ �̹����� ȭ�鿡 ����� ��찡 �ִ�. �̷���� �ڵ����� ���� �߽��� �ƴ� ����
				// �߽����� �ǵ��� image_set �� 1 ���Ѵ�.
				IMAGE_SET = 1;
				out = out + " , IMAGE_SET: �ʺ� �߽�";
			}

			// ������� ������ ������
			if (IMAGE_SET == 0)// ���̿� ���߱�
			{
				SET_IMAGE_SCALE = SET_IMAGE_SCALE_H;
				out = out + " , IMAGE_SET: ���� �߽�";
				
			} else if (IMAGE_SET == 1) {// ���ο� ���߱�
				SET_IMAGE_SCALE = SET_IMAGE_SCALE_W;
				out = out + " , IMAGE_SET: �ʺ� �߽�";
			}

		} else {// ����
			
			out = "DISPLAY_MODE: �����߽�";

			// ���� �߽����� ������ �ٿ����� ---> �ڵ�����
			if (SET_IMAGE_SCALE_H * IMAGE_W > DISPLY_W) {
				// ���� �߽����� ������ ��Ȥ �̹����� ȭ�鿡 ����� ��찡 �ִ�. �̷���� �ڵ����� ���� �߽��� �ƴ� ����
				// �߽����� �ǵ��� image_set �� 1 ���Ѵ�.
				IMAGE_SET = 1;
				out = out + " , IMAGE_SET: �ʺ� �߽�";
			}

			// ������� ������ ������
			if (IMAGE_SET == 0)// ���̿� ���߱�
			{
				SET_IMAGE_SCALE = SET_IMAGE_SCALE_H;
				out = out + " , IMAGE_SET: ���� �߽�";
				
			} else if (IMAGE_SET == 1) {// ���ο� ���߱�
				SET_IMAGE_SCALE = SET_IMAGE_SCALE_W;
				out = out + " , IMAGE_SET: �ʺ� �߽�";
			}

		}
		
		matrix.set(savedMatrix);
		matrixTurning(matrix, view, 1);
		view.setImageMatrix(matrix);

		//Log.e("================================================", out);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if (v.getId() == R.id.IV_m_img) {
			
			int MAKE_IMAGE = 0;
			ImageView view = (ImageView) v;

			// Dump touch event to log
			dumpEvent(event);
			
			//
			int size_x = (int) (start.x - event.getX());
			int size_y = (int) (start.y - event.getY());
			
			
			// Handle touch events here...
			switch (event.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN:// ù��° ��ġ ����
				
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());
				mode = DRAG;
				
				break;

			case MotionEvent.ACTION_POINTER_DOWN:// �ι�° ��ġ ����
				
				oldDist = spacing(event);
				Log.d(TAG, "oldDist=" + oldDist);
				if (oldDist > 10f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;
					Log.d(TAG, "mode=ZOOM");
				}
				break;

			case MotionEvent.ACTION_UP:// �R��° ��ġ ����
				
				if(getFrom.equals("GUI_conti")){// ���� �Ǻ��� �̵�
					MoveToNext_Music_set();	
				}
				
				if (DISPLAY_MODE == 1){// �ʺ� ����� ���
					MoveToNext_Page_set();
				}

				MoveToNext = -1;
				MoveToPage = -1;
				
				if (size_x * size_x < 50 && size_y * size_y < 50 ) {
					
					click++;
					
					if (click == 1 ) { //ù��° Ŭ���� ��� Ŭ���� �� count �� �����մϴ�.
						click_time_count = count + 3;
					}
					
					if(click == 1)// �ѹ� Ŭ��
					{
						//Log.e("================================================","Ŭ��: 1 ");

						
						if(ItemSet.equals("on")){
							RL_items.setVisibility(View.INVISIBLE);
							RL_title.setVisibility(View.INVISIBLE);
							
							ItemSet = "off";
							
						}else if(ItemSet.equals("off")){
							RL_items.setVisibility(View.VISIBLE);
							RL_title.setVisibility(View.VISIBLE);
							
							ItemSet = "on";
							
						}
						
						
					}else if(click == 2 &&  count <= click_time_count && DISPLAY_MODE == 0){//�ι� Ŭ��
						
						if(FIRST_IMAGE_SET == true){

							//Log.e("================================================","���� Ŭ��: 2 Ȯ��");

							matrix.set(savedMatrix);
							FIRST_IMAGE_SET = false;
							MAKE_IMAGE = 2;
							
						}else if(FIRST_IMAGE_SET == false){
							

							//Log.e("================================================","���� Ŭ��: 2 �ʱ�ȭ");

							matrix.set(savedMatrix);
							FIRST_IMAGE_SET = true;
							MAKE_IMAGE = 1;
						}
						

						RL_items.setVisibility(View.INVISIBLE);
						RL_title.setVisibility(View.INVISIBLE);
						
						ItemSet = "off";
						
					}
				}
				
				mode = NONE;
				break;
			case MotionEvent.ACTION_POINTER_UP:// �ι�° ��ġ ����
				mode = NONE;
				break;

			case MotionEvent.ACTION_MOVE:// �巡�� �Ǵ� ��
				if (mode == DRAG) {
					// ...
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - start.x, event.getY()- start.y );
					
					MoveToNext = -1;
					MoveToPage = -1;
					
					if(ImageSize_in == true && event.getX() - start.x >250  && (event.getY() - start.y) * (event.getY() - start.y) < 250 * 250 ){//���� �Ǻ�
						MoveToNext = 0;
					}else if(ImageSize_in == true &&  start.x - event.getX() >250  && (event.getY() - start.y) * (event.getY() - start.y) < 250 * 250 ){//���� �Ǻ�
						MoveToNext = 1;
					}else if(DISPLAY_MODE == 1  && event.getY() - start.y >250  && (event.getX() - start.x) * (event.getX() - start.x) < 250 * 250 ){//���� page
						MoveToPage = 0;
					}else if(DISPLAY_MODE == 1  &&  start.y - event.getY() >250  && (event.getX() - start.x) * (event.getX() - start.x) < 250 * 250 ){//���� page
						MoveToPage = 1;
					}
					
					
				} else if (mode == ZOOM && DISPLAY_MODE == 0) {
					float newDist = spacing(event);
					Log.d(TAG, "newDist=" + newDist);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / oldDist;
						matrix.postScale(scale, scale, mid.x, mid.y);
						FIRST_IMAGE_SET = false;
					}
				}
				break;

			}

			matrixTurning(matrix, view, MAKE_IMAGE);
			view.setImageMatrix(matrix);
		} 
		
		return true; // indicate event was handled
	}
	

	
	public void MoveToNext_Music_set(){
	
		Boolean check = false;
		
		if(MoveToNext == 0){
			Log.e("MOVE_MUSIC", "����");
			
			if(cm_num > 0)
			{
				cm_num = cm_num - 1;
				check = true;
			}else{
				check = false;
			}
			
		}else if(MoveToNext == 1){
			Log.e("MOVE_MUSIC", "����");
			
			if(cm_num < cm_total-1)
			{
				cm_num = cm_num + 1;
				check = true;
			}else{
				check = false;
			}
			
		}
		
		if((MoveToNext == 0 || MoveToNext == 1) && check == true){

			T_time_check = false;
			T_time = null;
			
			Intent intent = new Intent();
			intent.setClass( GUI_music_view_portrait.this, GUI_music_view_portrait.class);
			intent.putExtra("Intent_Type", 1);
			intent.putExtra("cm_name", cm_name);
			intent.putExtra("cm_num", cm_num);
			intent.putExtra("cm_total", cm_total);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			startActivity(intent);
			finish();
			
		}
	}
	
	public void MoveToNext_Page_set(){
	
		Boolean check = false;
		
		if(MoveToPage == 0){
			Log.e("MOVE_PAGE", "����");
			
			if(ms_num > 0)
			{
				ms_num = ms_num - 1;
				check = true;
			}else{
				check = false;
			}
			
		}else if(MoveToPage == 1){
			Log.e("MOVE_PAGE", "����");
			
			if(ms_num < ms_total-1)
			{
				ms_num = ms_num + 1;
				check = true;
			}else{
				check = false;
			}
			
		}
		
		if((MoveToPage == 0 || MoveToPage == 1) && check == true){

			
			FIRST_IMAGE_SET = true;
			IMAGE_SET = 0;
			IV_score_set();
			LV_ms_page_check();
			LV_ms_page.setSelectionFromTop(	ms_num,	0	);
			
		}
		
	}
	
	/** Show an event in the LogCat view, for debugging */
	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((int) event.getX(i));
			sb.append(",").append((int) event.getY(i));
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
		Log.d(TAG, sb.toString());
	}

	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	private void matrixTurning(Matrix matrix, ImageView view,int MAKE_IMAGE) {

		// ��Ʈ���� ��
		float[] value = new float[9];
		matrix.getValues(value);
		float[] savedValue = new float[9];
		savedMatrix2.getValues(savedValue);

		// �̹��� ũ��
		Drawable d = view.getDrawable();
		if (d == null)
			return;
		int imageWidth = d.getIntrinsicWidth();
		int imageHeight = d.getIntrinsicHeight();
		

		
		if (MAKE_IMAGE == 1) {// Matrix �ʱ�ȭ

			if (IMAGE_SET == 0) // ���� �߽�����
			{
				value[0] = value[4] = SET_IMAGE_SCALE_H;
				value[5] = savedValue[5]/savedValue[0]+savedValue[5]%savedValue[0];// �׸����� y���� ��ġ�� �ǹ��մϴ�.
			} else { // ���� �߽�����
				value[0] = value[4] = SET_IMAGE_SCALE_W;
				value[5] = savedValue[5]/savedValue[0]+savedValue[5]%savedValue[0];// �׸����� y���� ��ġ�� �ǹ��մϴ�.
				
			}
		
		}else if(MAKE_IMAGE == 2 && DISPLAY_MODE == 0){// Matrix Ȯ��

			if(IMAGE_SET == 0){
				value[0] = value[4] = SET_IMAGE_SCALE_W;
				value[5] = savedValue[5]/savedValue[0]+savedValue[5]%savedValue[0];
				
			}else if(IMAGE_SET == 1){
				//�����߽����� �����.
				value[0] = value[4] = SET_IMAGE_SCALE_H;
				value[5] = savedValue[5]/savedValue[0]+savedValue[5]%savedValue[0];
				
				//�ʹ� ū��� 8��ŭ�� Ű���.
				if(value[0] / SET_IMAGE_SCALE > 8 || value[4] / SET_IMAGE_SCALE > 8){
					value[0] = value[4] = SET_IMAGE_SCALE*8.0f;
					value[2] = savedValue[2]*value[0];
					value[5] = savedValue[5]*value[0];
				}
				
			}
			
		}
		
		int scaleWidth = (int) (imageWidth * value[0]);
		int scaleHeight = (int) (imageHeight * value[4]);

		// �̹����� �ٱ����� ������ �ʵ���.
		if (value[2] < DISPLY_W - scaleWidth) {
			value[2] = DISPLY_W - scaleWidth;
		}
		if (value[5] < DISPLY_H - scaleHeight) {
			value[5] = DISPLY_H - scaleHeight;
		}
		if (value[2] > 0) {
			value[2] = 0;
		}
		if (value[5] > 0) {
			value[5] = 0;
		}

		// ȭ�� �̹����� 8�� �̻� Ȯ�� ���� �ʵ���
		if (value[0] / SET_IMAGE_SCALE > 8 || value[4] / SET_IMAGE_SCALE > 8) {
			value[0] = savedValue[0];
			value[4] = savedValue[4];
			value[2] = savedValue[2];
			value[5] = savedValue[5];
		}

		
		if (imageWidth > DISPLY_W || imageHeight > DISPLY_H) {

			if (IMAGE_SET == 0 && scaleHeight < DISPLY_H) // ���� �߽�
			{
				value[0] = SET_IMAGE_SCALE;
				value[4] = SET_IMAGE_SCALE;
				//Log.e("================================================","���̰� ȭ�麸�� �۾���: " + String.valueOf(SET_IMAGE_SCALE)+ " , " + String.valueOf(value[4]));
				FIRST_IMAGE_SET = true;
			} else if (IMAGE_SET == 1 && scaleWidth < DISPLY_W) {
				value[0] = SET_IMAGE_SCALE;
				value[4] = SET_IMAGE_SCALE;
				//Log.e("================================================","�ʺ� ȭ�麸�� �۾���: " + String.valueOf(SET_IMAGE_SCALE)+ " , " + String.valueOf(value[4]));
				FIRST_IMAGE_SET = true;
			}

		}else {
			if (value[0] < SET_IMAGE_SCALE){
				value[0] = SET_IMAGE_SCALE;
				FIRST_IMAGE_SET = true;
			}
			if (value[4] < SET_IMAGE_SCALE){
				value[4] = SET_IMAGE_SCALE;
				FIRST_IMAGE_SET = true;
			}
				
			
		}

		// �׸��� ��� ��ġ�ϵ��� �Ѵ�.
		scaleWidth = (int) (imageWidth * value[0]);// �̹����� ���� ũ��
		scaleHeight = (int) (imageHeight * value[4]);// �̹����� ���� ũ��

		if (scaleWidth < DISPLY_W) {
			value[2] = (float) DISPLY_W / 2 - (float) scaleWidth / 2;
		}
		if (scaleHeight < DISPLY_H) {
			value[5] = (float) DISPLY_H / 2 - (float) scaleHeight / 2;
		}
		
		if(MAKE_IMAGE == 2){
			
			if(IMAGE_SET == 1){

				value[5] = (float) DISPLY_H / 2 - (float) scaleHeight / 2;
			}
			value[2] = (float) DISPLY_W / 2 - (float) scaleWidth / 2;
		}

		
		
		

		//�̹����� ������ ũ�Ⱑ ȭ���� ���κ��� �۰ų� ������ ���� ȭ������ ��ȯ ����
		ImageSize_in = false;
		if(scaleWidth <= DISPLY_W ){
			ImageSize_in = true;
		}
		
		
		matrix.setValues(value);
		savedMatrix2.set(matrix);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId() == R.id.IB_screen){

			
			File f = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/contimaker/score/wc"+Integer.toString(m_id)+"_1.png");
			if(f.exists()){
				
				T_time_check = false;
				T_time = null;

				Intent intent = new Intent();
				intent.setClass( GUI_music_view_portrait.this, GUI_music_view_landscape.class);
				intent.putExtra("getFrom", "GUI_music");
				intent.putExtra("m_id", m_id);
				intent.putExtra("ItemSet", ItemSet);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				GUI_music_view_portrait.this.startActivity(intent);
				GUI_music_view_portrait.this.finish();
		    	
		    }else{
				
				T_time_check = false;
				T_time = null;

				Intent intent = new Intent();
				intent.setClass( GUI_music_view_portrait.this, GUI_down_landscape.class);
				intent.putExtra("getFrom", "GUI_music");
				intent.putExtra("screen", "Landscape");
				intent.putExtra("m_id", m_id);
				intent.putExtra("ItemSet", ItemSet);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				GUI_music_view_portrait.this.startActivity(intent);
				GUI_music_view_portrait.this.finish();
				
		    }
			
		}else if(v.getId() == R.id.IB_share){
			
			T_time_check = false;
			T_time = null;

			Intent intent = new Intent();
			intent.setClass( GUI_music_view_portrait.this, GUI_music_view_share.class);
			intent.putExtra("getFrom", "GUI_music_view_portrait");
			intent.putExtra("screen", "Portrait");
			intent.putExtra("m_id", m_id);
			intent.putExtra("ItemSet", ItemSet);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			GUI_music_view_portrait.this.startActivity(intent);
			GUI_music_view_portrait.this.finish();
			
		}else if(v.getId() == R.id.IB_youtube){
			
			Uri uri = Uri.parse("http://www.youtube.com/results?search_query="+m_name);
			Intent it  = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(it);
			
		}

	}
	
	  //==========================================================================================
	  //Thread_Start
	    public void Start(){

	        T_time = new Thread(new Runnable() {
	    		
	    		@Override
	    		public void run() {
	    			// TODO Auto-generated method stub
	    			while(T_time_check){
	    				
	        			try {
	        				H_time.sendMessage(H_time.obtainMessage());
	        				Thread.sleep(100);
	        			} catch (InterruptedException e) {
	        				// TODO Auto-generated catch block
	        				e.printStackTrace();
	        			}
	        			count++;
	        			
	    			}
	    		}
	    	});
	        
	        H_time = new Handler(){
	        	@Override
	        	
				public void handleMessage(Message msg){
					Log.e("==",Integer.toString(count));

	        		//�ʱ�ȭ
					if(count > click_time_count && click != 0){
						click = 0;
						//Log.e("================================================","�ʱ�ȭ ");
					}
	        	}
	        };

	        T_time.start();
	    }
	    
		public boolean onKeyDown(int KeyCode, KeyEvent event) {

			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				// �� �κ��� Ư�� Ű�� �������� ���� �ȴ�.
				// ���� �ڷ� ��ư�� �������� �� �ൿ�� �����ϰ� �ʹٸ�

				if (KeyCode == KeyEvent.KEYCODE_BACK) {
					
					T_time_check = false;
					T_time = null;
					finish();
					
					return false;
				}

			}

			return super.onKeyDown(KeyCode, event);

		}


}