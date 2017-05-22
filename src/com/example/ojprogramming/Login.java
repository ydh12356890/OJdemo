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

	private EditText mAccount;                        //�û����༭  
	private EditText mPwd;                            //����༭  
	private Button mRegisterButton;                   //ע�ᰴť  
	private Button mLoginButton;                      //��¼��ť  
	private Button mLogoutButton;                     //ע����ť  
	private CheckBox mRememberCheck;                  //��ס����
	private TextView mChangepwdText;                  //�޸�����

	public int pwdresetFlag=0;   
	private SharedPreferences login_sp;
	private String userNameValue,passwordValue;

	private TextView loginSuccessShow;

	private UserDataManager mUserDataManager;         //�û����ݹ�����

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
		//����ϴ�ѡ�˼�ס���룬�ǽ����¼ҳ��Ҳ�Զ���ѡ��ס���룬�������û���������
		if(choseRemember){
			mAccount.setText(name);
			mPwd.setText(pwd);
			mRememberCheck.setChecked(true);
		}

		mRegisterButton.setOnClickListener(mListener);                      //����OnClickListener�������ò�ͬ��ť����֮��ļ����¼�
		mLoginButton.setOnClickListener(mListener);
		mLogoutButton.setOnClickListener(mListener);
		mChangepwdText.setOnClickListener(mListener);



		/* if (mUserDataManager == null) {
             mUserDataManager = new UserDataManager(this);
             mUserDataManager.openDataBase();                              //�����������ݿ�
         }*/
	}
	OnClickListener mListener = new OnClickListener() {                  //��ͬ��ť���µļ����¼�ѡ��
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_btn_register:                            //��¼�����ע�ᰴť
				Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //�л�Login Activity��User Activity
				startActivity(intent_Login_to_Register);
				finish();
				break;
			case R.id.login_btn_login:                              //��¼����ĵ�¼��ť
				final String usernameString = mAccount.getText().toString();
				final String passwordString = mPwd.getText().toString();
				Log.i("abc","��ӡ���û���������"+usernameString+" "+passwordString);
				if(usernameString==null||passwordString==null)
				{
					Toast.makeText(getApplicationContext(), "�û��������벻��Ϊ�գ�", Toast.LENGTH_SHORT).show();
				}else{
				login();}
				break;
			case R.id.login_btn_logout:                             //��¼�����ע����ť
				logout();
				break;
			case R.id.login_text_change_pwd:                             //��¼�����ע����ť
				Intent intent_Login_to_reset = new Intent(Login.this,ResetPassword.class) ;    //�л�Login Activity��User Activity
				startActivity(intent_Login_to_reset);
				finish();
				break;
			}
		}
	};
	public void login() { 
		final String usernameString = mAccount.getText().toString();
		final String passwordString = mPwd.getText().toString();//��¼��ť�����¼�
		
		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					String urlpathString = "http://35.189.170.28:8000/api/inline/login/auth";
					URL url = new URL(urlpathString);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// ��������ķ�ʽ  
					connection.setRequestMethod("POST"); 
					
					// ��������ĳ�ʱʱ��  
					connection.setReadTimeout(5000);  
					connection.setConnectTimeout(5000);
					connection.setDoInput(true);//Ĭ��Ϊtrue,д��д������
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
					Log.i("abc","ת����json"+s);
					OutputStream os=connection.getOutputStream();
			
					os.write(s.getBytes());  
					os.flush(); 
					
					if (connection.getResponseCode() == 200) {  
						// ��ȡ��Ӧ������������  
						InputStream is = connection.getInputStream();  
						// �����ֽ����������  
						ByteArrayOutputStream baos = new ByteArrayOutputStream();  
						// �����ȡ�ĳ���  
						int len = 0;  
						// ���建����  
						byte buffer[] = new byte[1024];  
						// ���ջ������Ĵ�С��ѭ����ȡ  
						while ((len = is.read(buffer)) != -1) {  
							// ���ݶ�ȡ�ĳ���д�뵽os������  
							baos.write(buffer, 0, len);  
						}  
						// �ͷ���Դ  
						is.close();  
						baos.close(); 
						// �����ַ��� ��result=jsonstring,ת��Ϊjson�ַ���
						final String result = new String(baos.toByteArray());  
						Log.i("abc","��ӡ�����"+result);
						//��ʼ����
						JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
						JsonObject jsonObject2 = jsonObject.getAsJsonObject("login_auth_response");
						Gson gson11 = new Gson();
						UserData userdata = gson11.fromJson(jsonObject2, UserData.class);
						// ��תJsonObject
						Log.i("abc","��ӡ���û�"+userdata.toString());
						Message msg = new Message();
						msg.what = PARSESUCCWSS;// ֪ͨUI�߳�Json�������
						msg.obj = userdata;// �������������ݴ��ݸ�UI�߳�
						mHandler.sendMessage(msg);
					} else { 
						//Toast.makeText(this, "�������", Toast.LENGTH_SHORT).show();
						System.out.println("����ʧ��.........");  
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
		Toast.makeText(getBaseContext(), "��½�ɹ���", Toast.LENGTH_SHORT).show();
		/*Bundle bundle = new Bundle();
		bundle.putString("str", usernameString);
		mIntent2.putExtras(bundle);*/
		startActivity(mIntent2);
		finish();
		

	}
	public void logout() {           //ע��
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();    //��ȡ��ǰ������û�����������Ϣ
			String userPwd = mPwd.getText().toString().trim();
			int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
			if(result==1){                                             //����1˵���û������������ȷ
				Toast.makeText(this, getString(R.string.cancel_success),Toast.LENGTH_SHORT).show();//<span style="font-family: Arial;">//ע���ɹ���ʾ</span>
				mPwd.setText("");
				mAccount.setText("");
				mUserDataManager.deleteUserDatabyname(userName);
			}else if(result==0){
				Toast.makeText(this, getString(R.string.cancel_fail),Toast.LENGTH_SHORT).show();  //ע��ʧ����ʾ
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
				.setTitle("��ʾ")
				.setMessage("ע��Success!")
				.setPositiveButton("ȷ��", null)
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
				.setTitle("��ʾ")
				.setMessage("��½Success!")
				.setPositiveButton("ȷ��", null)
				.show();


			}
		});
    	mLogoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Login.this)
				.setTitle("��ʾ")
				.setMessage("ע��Success!")
				.setPositiveButton("ȷ��", null)
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



