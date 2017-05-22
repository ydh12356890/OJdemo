package com.example.ojprogramming;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User extends Activity {
  private Button mReturnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
       // mReturnButton = (Button)findViewById(R.id.returnback);
    }

    public void back_to_login(View view) {
        Intent backit = new Intent(User.this,Login.class) ;
        startActivity(backit);
        finish();
    }
}

