package com.example.ojprogramming;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;










import org.apache.http.cookie.Cookie;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	private EditText mAccount;                        //用户名编辑  
	private EditText mPwd;                            //密码编辑  
	private Button mRegisterButton;                   //注册按钮  
	private Button mLoginButton;                      //登录按钮  
	private Button mLogoutButton;                     //注销按钮  
	private CheckBox mRememberCheck;                  //记住密码
	private TextView mChangepwdText;                  //修改密码

	public int pwdresetFlag=0;   
	private SharedPreferences login_sp;
	private String userNameValue,passwordValue;

	private TextView loginSuccessShow;

	private UserDataManager mUserDataManager;         //用户数据管理类

	public static final int PARSESUCCWSS = 0x2016;
	private static final String TAG = "MainActivity";
	UserData userdata = new UserData();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {

				userdata = (UserData)msg.obj;
				loginSuccessShow.setText(userdata.toString());


			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mAccount = (EditText)findViewById(R.id.login_edit_account);
		mPwd = (EditText)findViewById(R.id.login_edit_pwd);
		mRegisterButton = (Button)findViewById(R.id.login_btn_register);
		mLoginButton = (Button)findViewById(R.id.login_btn_login);
		mLogoutButton = (Button)findViewById(R.id.login_btn_logout);
		mRememberCheck = (CheckBox)findViewById(R.id.Login_Remember);
		mChangepwdText = (TextView)findViewById(R.id.login_text_change_pwd);
		loginSuccessShow=(TextView) findViewById(R.id.login_success_show);

		login_sp = getSharedPreferences("userInfo", 0);
		String name=login_sp.getString("USER_NAME", "");
		String pwd =login_sp.getString("PASSWORD", "");
		boolean choseRemember =login_sp.getBoolean("mRememberCheck", false);
		boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);
		//如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
		if(choseRemember){
			mAccount.setText(name);
			mPwd.setText(pwd);
			mRememberCheck.setChecked(true);
		}

		mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
		mLoginButton.setOnClickListener(mListener);
		mLogoutButton.setOnClickListener(mListener);
		mChangepwdText.setOnClickListener(mListener);



		/* if (mUserDataManager == null) {
             mUserDataManager = new UserDataManager(this);
             mUserDataManager.openDataBase();                              //建立本地数据库
         }*/
	}
	OnClickListener mListener = new OnClickListener() {                  //不同按钮按下的监听事件选择
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_btn_register:                            //登录界面的注册按钮
				Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //切换Login Activity至User Activity
				startActivity(intent_Login_to_Register);
				finish();
				break;
			case R.id.login_btn_login:                              //登录界面的登录按钮
				final String usernameString = mAccount.getText().toString();
				final String passwordString = mPwd.getText().toString();
				Log.i("abc","打印出用户名和密码"+usernameString+" "+passwordString);
				if(usernameString==null||passwordString==null)
				{
					Toast.makeText(getApplicationContext(), "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
				}else{
				login();}
				break;
			case R.id.login_btn_logout:                             //登录界面的注销按钮
				logout();
				break;
			case R.id.login_text_change_pwd:                             //登录界面的注销按钮
				Intent intent_Login_to_reset = new Intent(Login.this,ResetPassword.class) ;    //切换Login Activity至User Activity
				startActivity(intent_Login_to_reset);
				finish();
				break;
			}
		}
	};
	public void login() { 
		final String usernameString = mAccount.getText().toString();
		final String passwordString = mPwd.getText().toString();//登录按钮监听事件
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					String urlpathString = "http://35.189.170.28:8000/api/inline/login/auth";
					URL url = new URL(urlpathString);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// 设置请求的方式  
					connection.setRequestMethod("POST"); 
					
					// 设置请求的超时时间  
					connection.setReadTimeout(5000);  
					connection.setConnectTimeout(5000);
					connection.setDoInput(true);//默认为true,写不写都可以
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
					connection.setRequestProperty("Accept", "application/json");

					Gson gson=new Gson();
					Map<String,String> map=new HashMap<String,String>();
					map.put("username",usernameString);
					map.put("password",passwordString);
					Map<String,Map> map2 = new HashMap<String, Map>();
					map2.put("login_auth_request", map);
					String s=gson.toJson(map2);
					Log.i("abc","转换成json"+s);
					OutputStream os=connection.getOutputStream();
			
					os.write(s.getBytes());  
					os.flush(); 
					
					if (connection.getResponseCode() == 200) {  
						// 获取响应的输入流对象  
						InputStream is = connection.getInputStream();  
						// 创建字节输出流对象  
						ByteArrayOutputStream baos = new ByteArrayOutputStream();  
						// 定义读取的长度  
						int len = 0;  
						// 定义缓冲区  
						byte buffer[] = new byte[1024];  
						// 按照缓冲区的大小，循环读取  
						while ((len = is.read(buffer)) != -1) {  
							// 根据读取的长度写入到os对象中  
							baos.write(buffer, 0, len);  
						}  
						// 释放资源  
						is.close();  
						baos.close(); 
						// 返回字符串 ，result=jsonstring,转化为json字符串
						final String result = new String(baos.toByteArray());  
						Log.i("abc","打印出结果"+result);
						//开始解析
						JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
						JsonObject jsonObject2 = jsonObject.getAsJsonObject("login_auth_response");
						Gson gson11 = new Gson();
						UserData userdata = gson11.fromJson(jsonObject2, UserData.class);
						// 先转JsonObject
						Log.i("abc","打印出用户"+userdata.toString());
						Message msg = new Message();
						msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
						msg.obj = userdata;// 将解析出的数据传递给UI线程
						mHandler.sendMessage(msg);
					} else { 
						//Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
						System.out.println("链接失败.........");  
					}  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();
		
		/*Intent miIntent = new Intent(Login.this,MainActivity.class);
		
		miIntent.putExtra("page",3);
		//miIntent.putExtra("str", usernameString);
		Bundle bundle = new Bundle();
		bundle.putString("str", usernameString);
		miIntent.putExtras(bundle);
		startActivity(miIntent);
	*/
		
		Intent mIntent2 = new Intent();
		mIntent2.setClass(Login.this, MainActivity.class);
		mIntent2.putExtra("username", usernameString);
		Toast.makeText(getBaseContext(), "登陆成功！", Toast.LENGTH_SHORT).show();
		/*Bundle bundle = new Bundle();
		bundle.putString("str", usernameString);
		mIntent2.putExtras(bundle);*/
		startActivity(mIntent2);
		finish();
		

	}
	public void logout() {           //注销
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
			String userPwd = mPwd.getText().toString().trim();
			int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
			if(result==1){                                             //返回1说明用户名和密码均正确
				Toast.makeText(this, getString(R.string.cancel_success),Toast.LENGTH_SHORT).show();//<span style="font-family: Arial;">//注销成功提示</span>
				mPwd.setText("");
				mAccount.setText("");
				mUserDataManager.deleteUserDatabyname(userName);
			}else if(result==0){
				Toast.makeText(this, getString(R.string.cancel_fail),Toast.LENGTH_SHORT).show();  //注销失败提示
			}
		}

	}
	public boolean isUserNameAndPwdValid() {
		if (mAccount.getText().toString().trim().equals("")) {
			Toast.makeText(this, getString(R.string.account_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPwd.getText().toString().trim().equals("")) {
			Toast.makeText(this, getString(R.string.pwd_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	@Override
	protected void onResume() {
		if (mUserDataManager == null) {
			mUserDataManager = new UserDataManager(this);
			mUserDataManager.openDataBase();
		}
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		if (mUserDataManager != null) {
			mUserDataManager.closeDataBase();
			mUserDataManager = null;
		}
		super.onPause();
	}

	/***
    	mRegisterButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				new AlertDialog.Builder(Login.this)
				.setTitle("提示")
				.setMessage("注册Success!")
				.setPositiveButton("确定", null)
				.show();

				Intent registerit = new Intent(Login.this,Register.class);
			    startActivity(registerit);
			}
		});
    	mLoginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Login.this)
				.setTitle("提示")
				.setMessage("登陆Success!")
				.setPositiveButton("确定", null)
				.show();


			}
		});
    	mLogoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Login.this)
				.setTitle("提示")
				.setMessage("注销Success!")
				.setPositiveButton("确定", null)
				.show();
			}
		});
    	mChangepwdText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent changepwdit = new Intent(Login.this,ResetPassword.class);
				startActivity(changepwdit);
			}
		});
	 **/




}



