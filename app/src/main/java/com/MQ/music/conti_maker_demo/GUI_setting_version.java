package com.MQ.music.conti_maker_demo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GUI_setting_version extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	



	private Boolean DEVELOPER_MODE = true;
	
	private String s_version="";
	private TextView TV_s_version;
	
	//Button
	private Button B_next;
	
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
        setContentView(R.layout.gui_setting_version);
		
        TV_set();
        B_set();
        DB_set();
    }

    public void DB_set(){

		DB_handler DB = DB_handler.open(this);
		Cursor C_get_system_registration = DB.get_System();
		s_version = C_get_system_registration.getString(C_get_system_registration.getColumnIndex("s_version"));
		C_get_system_registration.close();
		DB.close();
		
		TV_s_version.setText(s_version);
    	
    }
    
    public void TV_set(){
		TV_s_version = (TextView) findViewById(R.id.TV_s_version);
    }

    public void B_set(){
    	B_next = (Button)findViewById(R.id.B_next);
    	B_next.setOnClickListener(this);
    	
    }
    
	@Override
	public void onClick(View v) {
		
		if ( v.getId() == R.id.B_next) 
		{
				finish();
		}
		
	}
    
}
