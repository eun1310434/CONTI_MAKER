package com.MQ.music.conti_maker_demo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GUI_setting_request extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	
	private EditText ET_email;
	private EditText ET_request;
	private EditText ET_fight;
	
	String email;
	String request;
	String fight;
	
	private Button B_send;
	
	private Boolean SendCheck = false;
	String C_msg = "";

	//TextView
	private TextView TV_my_s_id;
	
	//String
	private String s_id;

	private Client  client = new Client();
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
        setContentView(R.layout.gui_setting_request);
        ET_set();
        B_set();
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
    public void ET_set(){
    	ET_email = (EditText) findViewById(R.id.ET_email);
    	ET_request = (EditText) findViewById(R.id.ET_request);
    	ET_fight = (EditText) findViewById(R.id.ET_fight);
    }
    public void B_set(){
    	B_send = (Button) findViewById(R.id.B_send);
    	B_send.setOnClickListener(this);
    }


	
	public String Myid(){

		DB_handler DB_system = DB_handler.open(this);
		Cursor C_get_system = DB_system.get_System();
		String s_id = C_get_system.getString(C_get_system.getColumnIndex("s_id"));
		C_get_system.close();
		DB_system.close();
		
		return s_id;
	}
    @Override
   	public void onClick(View v) {
       	if ( v.getId() == R.id.B_send) {
       		
       		email = ET_email.getText().toString();
       		request = ET_request.getText().toString();
       		fight = ET_fight.getText().toString();
       		
       		if(request.equals("")){
       			
       			Toast.makeText(this, "[ �ʼ� ���� ] ��û �Ǻ��� �ۼ��� �ּ���", Toast.LENGTH_SHORT).show();
       			
       		}else{
       		
       			SendCheck = client.SendRequest(Myid(), email, request, fight);
       			
           		if(SendCheck == true)
           		{
           			C_msg = "��� �Ǽ̽��ϴ�~^^\n��û�Ͻ� �Ǻ��� ��ٷ��ּ���~";
           			
               		ET_request.setText("");
               		ET_fight.setText("");
               		
           		}else if(SendCheck == false){
           			C_msg = "�˼��մϴ�~�Ф�\n�����������Դϴ�. ��ø� ��ٷ��ּ���~";
           		}
           		
           		new AlertDialog.Builder(GUI_setting_request.this)
				 .setTitle("�Ǻ���û")
				 .setMessage(C_msg)
				 .setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						finish();
					}
				}).show();

       		}
       		
       		
       		
       		
       	}
   	}
}