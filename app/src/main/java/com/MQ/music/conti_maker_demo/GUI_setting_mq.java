package com.MQ.music.conti_maker_demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GUI_setting_mq extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
	
	private Button B_dial;
	private Button B_send;
	
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
        setContentView(R.layout.gui_setting_mq);

        B_dial = (Button) findViewById(R.id.B_dial);
        B_dial.setOnClickListener(this);
        
        B_send = (Button) findViewById(R.id.B_send);
        B_send.setOnClickListener(this);

    }

	@Override
	public void onClick(View v) {
		if ( v.getId() == R.id.B_dial) {
    		
    		Intent sendIntent = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:010-7444-8705"));
    		startActivity(sendIntent);
    	}
    	else if ( v.getId() == R.id.B_send) {
    		Toast.makeText(GUI_setting_mq.this, "�غ��� �Դϴ�.", Toast.LENGTH_SHORT).show();
    	}
	}
    
}
