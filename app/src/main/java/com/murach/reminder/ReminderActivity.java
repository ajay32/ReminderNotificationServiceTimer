package com.murach.reminder;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

public class ReminderActivity extends Activity implements OnClickListener {

    private Button startServiceButton;
    private Button stopServiceButton;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);
		
        startServiceButton = (Button) findViewById(R.id.startServiceButton);
        stopServiceButton = (Button) findViewById(R.id.stopServiceButton);
        
        startServiceButton.setOnClickListener(this);
        stopServiceButton.setOnClickListener(this);		
	}

    @Override
    public void onClick(View v) {
        Intent serviceIntent = new Intent(this, ReminderService.class);
        switch (v.getId()) {
        	case R.id.startServiceButton:
        		startService(serviceIntent);
                Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();
        		break;
        	case R.id.stopServiceButton:
        		stopService(serviceIntent);
                Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
        		break;
        }
    }
}