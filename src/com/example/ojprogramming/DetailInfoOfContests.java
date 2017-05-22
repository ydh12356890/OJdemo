package com.example.ojprogramming;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class DetailInfoOfContests extends Activity {
	private TextView mTextView;
	public static final int PARSESUCCWSS = 0x2016;
	private static final String TAG = "MainActivity";
	ContestsData dcd = new ContestsData();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {
				
				dcd = (ContestsData)msg.obj;
				mTextView.setText(dcd.toString());
				

			}
		}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_of_contests);
        mTextView = (TextView)findViewById(R.id.id_detail_contests);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();  
        final String str=bundle.getString("str");
        Log.i("ydh","输出runid"+str);
        Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i("abc", "进入");
				String path = "http://35.189.170.28:8000/api/inline/contests";
				Log.i("abc", "开始读取URL");
				String jsonString = HttpUtils.getJsonContent(path);// 从网络获取数据
				//Log.i("abc","连接"+jsonString);
				JsonObject jsonObject = new JsonParser().parse(
						jsonString).getAsJsonObject();

				JsonArray jsonArray = jsonObject.getAsJsonObject(
						"list_contests_response").getAsJsonArray(
						"lines");

				Log.i("abc", "打印"+jsonArray.toString());
				Gson gson = new Gson();
				ContestsData lastdata = new ContestsData();
				ArrayList<ContestsData> lineList = new ArrayList<ContestsData>();
				for (JsonElement data : jsonArray) {
					ContestsData mdcData = gson.fromJson(data,
							new TypeToken<ContestsData>() {}.getType());
					//根据条件过滤
					if((mdcData.getContest_id()).equals(str)){
					lastdata = mdcData;
				}
				}
				Message msg = new Message();
				msg.what = PARSESUCCWSS;// 通知UI线程Json解析完成
				msg.obj = lastdata;// 将解析出的数据传递给UI线程
				mHandler.sendMessage(msg);
				
			}

		});
		thread.start();

	}
	

}


