package com.example.ojprogramming;

import java.util.ArrayList;

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
import android.widget.TextView;

public class DetailInfoOfCommit extends Activity {
	private TextView mTextView;
	public static final int PARSESUCCWSS = 0x2016;
	private static final String TAG = "MainActivity";
	DetailCommitData dcd = new DetailCommitData();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {
				
				dcd = (DetailCommitData)msg.obj;
				mTextView.setText(dcd.toString());
				

			}
		}
	};

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info_of_commit);
        mTextView = (TextView)findViewById(R.id.id_detail_commit);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();  
        final String str=bundle.getString("str");
        Log.i("ydh","���runid"+str);
        Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i("abc", "����");
				String path = "http://35.189.170.28:8000/api/inline/status";
				Log.i("abc", "��ʼ��ȡURL");
				String jsonString = HttpUtils.getJsonContent(path);// �������ȡ����
				//Log.i("abc","����"+jsonString);
				JsonObject jsonObject = new JsonParser().parse(
						jsonString).getAsJsonObject();

				JsonArray jsonArray = jsonObject.getAsJsonObject(
						"list_submissions_response").getAsJsonArray(
						"lines");

				Log.i("abc", "��ӡ"+jsonArray.toString());
				Gson gson = new Gson();
				DetailCommitData lastdata = new DetailCommitData();
				ArrayList<DetailCommitData> lineList = new ArrayList<DetailCommitData>();
				for (JsonElement data : jsonArray) {
					DetailCommitData mdcData = gson.fromJson(data,
							new TypeToken<DetailCommitData>() {}.getType());
					//������������
					if((mdcData.getrun_id()).equals(str)){
					lastdata = mdcData;
				}
				}
				Message msg = new Message();
				msg.what = PARSESUCCWSS;// ֪ͨUI�߳�Json�������
				msg.obj = lastdata;// �������������ݴ��ݸ�UI�߳�
				mHandler.sendMessage(msg);
				
			}

		});
		thread.start();

	}
	

}
