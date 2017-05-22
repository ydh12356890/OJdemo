package com.example.ojprogramming;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Text;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DetailInfoOfQuestion extends Activity {
	private TextView mtitleTextView;
	private TextView minfoTextView;
	private Button mButton;
	private ListView mListView;
	private Button mcommitbtn;
	public static final int PARSESUCCWSS = 0x2016;
	private static final String TAG = "MainActivity";
	Problem problem = new Problem();

	ArrayList<Language> lineList = new ArrayList<Language>();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {

				problem = (Problem)msg.obj;
				//lineList = (ArrayList<Language>)msg.obj;
				minfoTextView.setText(problem.toString());
				//mlanguageTextView.setText(lineList.toString());


			}
		}
	};
	final Handler mHandler2 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {

				//problem = (Problem)msg.obj;
				lineList = (ArrayList<Language>)msg.obj;
				//minfoTextView.setText(problem.toString());
				//mlanguageTextView.setText(lineList.toString());
				initlanguagedata();


			}
		}
	};
	final Handler mHandler3 = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {
				/*Intent intent = getIntent();
				Bundle bundle=intent.getExtras();  
				 String str=bundle.getString("str");  
				Log.i("ydh","打印出str"+str);*/
				Intent mComIntent = new Intent(DetailInfoOfQuestion.this, CommitCode.class);
				/*mComIntent.setClass(DetailInfoOfQuestion.this, CommitCode.class);

				mComIntent.putExtra("str",str);*/
				startActivity(mComIntent);




			}
		}
	};



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_info_of_question);
		mtitleTextView = (TextView)findViewById(R.id.detailinfo_question);
		minfoTextView = (TextView)findViewById(R.id.replay_question);
		mButton = (Button)findViewById(R.id.id_btn_language);
		mListView = (ListView)findViewById(R.id.language_listview);
		mcommitbtn = (Button)findViewById(R.id.id_btn_commitcode);

		Intent intent = getIntent();
		Bundle bundle=intent.getExtras();  
		final String str=bundle.getString("str");  



		final String urlpathString  = "http://35.189.170.28:8000/api/inline/problem?problem_sid="+str;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i("abc", "进入");
				//String path = "http://35.189.170.28:8000/api/inline/problem?problem_sid=zoj-1000";
				Log.i("abc", "开始读取URL");
				String jsonString = HttpUtils.getJsonContent(urlpathString);// 从网络获取数据
				//Log.i("abc","连接"+jsonString);
				JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
				JsonObject jsonObject2 = jsonObject.getAsJsonObject("show_problem_response").getAsJsonObject("problem");
				Log.i("abc", "打印"+jsonObject2.toString());
				Gson gson = new Gson();
				Problem problem = gson.fromJson(jsonObject2, Problem.class);
				Log.i("abc","转换"+problem.toString());

				/*JsonArray jsonArray = jsonObject.getAsJsonObject("show_problem_response").getAsJsonArray("languages");
				Gson gson1 = new Gson();
				ArrayList<Language> lineList = new ArrayList<Language>();
				for (JsonElement data : jsonArray) {
					Language mData = gson1.fromJson(data,
							new TypeToken<Language>() {
							}.getType());
					lineList.add(mData);
				}*/
				//mlanguageTextView.setText(lineList.toString());
				Message msg = new Message();
				msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
				msg.obj = problem;// 将解析出的数据传递给UI线程
				//msg.obj = lineList;
				mHandler.sendMessage(msg);

			}

		});
		thread.start();
		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						Log.i("abc", "进入");
						//String path = "http://35.189.170.28:8000/api/inline/problem?problem_sid=zoj-1000";
						Log.i("abc", "开始读取URL");
						String jsonString = HttpUtils.getJsonContent(urlpathString);// 从网络获取数据
						//Log.i("abc","连接"+jsonString);
						JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();


						JsonArray jsonArray = jsonObject.getAsJsonObject("show_problem_response").getAsJsonArray("languages");
						Gson gson1 = new Gson();
						ArrayList<Language> lineList = new ArrayList<Language>();
						for (JsonElement data : jsonArray) {
							Language mData = gson1.fromJson(data,
									new TypeToken<Language>() {
							}.getType());
							lineList.add(mData);
						}
						//mlanguageTextView.setText(lineList.toString());
						Message msg = new Message();
						msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
						//msg.obj = problem;// 将解析出的数据传递给UI线程
						msg.obj = lineList;
						mHandler2.sendMessage(msg);

					}

				});
				thread.start();


			}
		});
		mcommitbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						Message msg = new Message();
						msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
						//msg.obj = problem;// 将解析出的数据传递给UI线程
						//msg.obj = lineList;
						mHandler3.sendMessage(msg);
					}
				});
				thread.start();
			}
		});

	}

	public void initlanguagedata()
	{
		List<Map<String, Object>> jsonlist=new ArrayList<Map<String,Object>>();
		for (Language news:lineList) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("compiler", news.getCompiler());
			map.put("language", news.getLanguage());
			map.put("language_id", news.getLanguage_id());
			map.put("oj_name", news.getOj_name());
			jsonlist.add(map);
			Log.i("abc","解析json填充到listview之间的");
		}
		SimpleAdapter adapter=new SimpleAdapter(this, jsonlist, R.layout.detail_info_language, new String[]
				{"compiler","language","language_id","oj_name"}, new int[]{R.id.id_txt_compiler,R.id.id_txt_language,R.id.id_txt_languageid,R.id.id_txt_ojname});
		Log.i("abc","初始化");
		mListView.setAdapter(adapter);
	}
}




