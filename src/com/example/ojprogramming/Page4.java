package com.example.ojprogramming;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Page4 extends Activity{
	private TextView loginTextView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page4);
        loginTextView = (TextView)findViewById(R.id.denglu);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();  
        String str=bundle.getString("str");  
        loginTextView.setText(str);
	}

}
