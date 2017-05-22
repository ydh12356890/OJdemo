package com.example.ojprogramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Contests extends Activity{
	private ListView mcontestlListView;
	public static final int PARSESUCCWSS = 0x2016;

	// private String urlPath="http://35.189.170.28:8000/api/inline/problems";
	private static final String TAG = "MainActivity";
	ArrayList<ContestsData> mlineList = new ArrayList<ContestsData>();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {

				mlineList = (ArrayList<ContestsData>)msg.obj;
				initdata();

				/*
				Line line = (Line) msg.obj;
				textView.setText(line.toString());*/

			}
		}
	};
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contests);
        mcontestlListView = (ListView)findViewById(R.id.contest_listview);
        final String path = "http://35.189.170.28:8000/api/inline/contests";  

		Thread thread = new Thread(new Runnable(){//创建子线程进行网络访问的操作  
			@Override
			public void run() {
				Log.i("abc", "进入");
				//String path = "http://35.189.170.28:8000/api/inline/problems";
				Log.i("abc", "开始读取URL");
				String jsonString = HttpUtils.getJsonContent(path);// 从网络获取数据
				Log.i("abc", "获取到json数据");
				// 先转JsonObject

				JsonObject jsonObject = new JsonParser().parse(
						jsonString).getAsJsonObject();

				JsonArray jsonArray = jsonObject.getAsJsonObject(
						"list_contests_response").getAsJsonArray(
								"lines");

				Gson gson = new Gson();
				ArrayList<ContestsData> lineList = new ArrayList<ContestsData>();
				for (JsonElement line : jsonArray) {
					ContestsData mLine = gson.fromJson(line,
							new TypeToken<ContestsData>() {
					}.getType());
					lineList.add(mLine);
				}
				Log.i("abc", "打印数组" + lineList.size());
				Iterator it = lineList.iterator();
				while (it.hasNext()) {
					ContestsData line = (ContestsData) it.next();
					Log.i("abc", line.toString());
				}
				// mView.setText("dfghjk");
				Message msg = new Message();
				msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
				msg.obj = lineList;// 将解析出的数据传递给UI线程
				mHandler.sendMessage(msg);
			}

		});
		thread.start();

	}  
	public void initdata()
	{
		Log.i("abc","进入初始化视图");
		List<Map<String, Object>> jsonlist=new ArrayList<Map<String,Object>>();
		for (ContestsData news:mlineList) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("contests_id", news.getContest_id());
			map.put("title", news.getTitle());
			map.put("status", news.getStatus());
			jsonlist.add(map);
			Log.i("abc","解析json填充到listview之间的");
		}
		SimpleAdapter adapter=new SimpleAdapter(this, jsonlist, R.layout.items_contest, new String[]
				{"contests_id","title","status"}, new int[]{R.id.item_contestid,R.id.item_title,R.id.item_status});
		Log.i("abc","初始化");
		mcontestlListView.setAdapter(adapter);
		mcontestlListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				//Map<String, Object> item = (Map<String, Object>)parent.getItemAtPosition(pos);
				String string =(String)((TextView)view.findViewById(R.id.item_contestid)).getText();
				Log.i("ydh","打印出Problem的item"+" "+pos+" " +id+" " +string);

				Intent mComIntent = new Intent();
				mComIntent.setClass(Contests.this, DetailInfoOfContests.class);
				Bundle bundle = new Bundle();
				bundle.putString("str", string);
				mComIntent.putExtras(bundle);
				startActivity(mComIntent);




			}

		});

	}

}
