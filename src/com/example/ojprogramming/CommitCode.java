package com.example.ojprogramming;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CommitCode extends Activity {
	private EditText mchooselanguageEditText;
	private EditText mcommitcodeEditText;
	private Button mcommitButton;
	private TextView mshowcommitinfo;
	public static final int PARSESUCCWSS = 0x2016;
	private static final String TAG = "MainActivity";
	CommitCodeReturnInfo commitdata = new CommitCodeReturnInfo();
	final Handler mHandler3 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {
				commitdata = (CommitCodeReturnInfo)msg.obj;
				mshowcommitinfo.setText(commitdata.toString());


			}
		}
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commitcode);
		mchooselanguageEditText =(EditText)findViewById(R.id.id_choose_language);
		mcommitcodeEditText = (EditText)findViewById(R.id.id_commitcode);
		mcommitButton = (Button)findViewById(R.id.id_btn_commit);
		mshowcommitinfo = (TextView)findViewById(R.id.id_showcommitinfo);

		/*Intent intent = getIntent(); 
		final String str= intent.getStringExtra("str");*/
		
		final String commitcode = mcommitcodeEditText.getText().toString();
		final String chooselanguageid = mchooselanguageEditText.getText().toString();

		Thread thread = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					String urlpathString = "http://35.189.170.28:8000/api/inline/submit";
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
					map.put("problem_sid","local-1000");
					map.put("code",commitcode);
					map.put("language_id",chooselanguageid);
					Map<String,Map> map2 = new HashMap<String, Map>();
					map2.put("submit_request", map);
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
						JsonObject jsonObject2 = jsonObject.getAsJsonObject("submit_response");
						Gson gson11 = new Gson();
						CommitCodeReturnInfo commitdata = gson11.fromJson(jsonObject2, CommitCodeReturnInfo.class);
						// 先转JsonObject
						Log.i("abc","打印出用户"+commitdata.toString());
						Message msg = new Message();
						msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
						msg.obj = commitdata;// 将解析出的数据传递给UI线程
						mHandler3.sendMessage(msg);
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

	}

	}

