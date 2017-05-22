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
import android.app.ListActivity;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Commit extends Activity {
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private ListView commitListView;
    public static final int PARSESUCCWSS = 0x2016;
	private static final String TAG = "MainActivity";
	ArrayList<Data> mlineList = new ArrayList<Data>();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {
				
				mlineList = (ArrayList<Data>)msg.obj;
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
        setContentView(R.layout.commit);
        tv1 = (TextView)findViewById(R.id.id_user);
        tv2 = (TextView)findViewById(R.id.id_run);
        tv3 = (TextView)findViewById(R.id.id_status);
        commitListView = (ListView)findViewById(R.id.commit_listview);
        
        Thread thread = new Thread(new Runnable(){//创建子线程进行网络访问的操作  
        	@Override
        	public void run() {
				Log.i("abc", "进入");
				String path = "http://35.189.170.28:8000/api/inline/status";
				Log.i("abc", "开始读取URL");
				String jsonString = HttpUtils.getJsonContent(path);// 从网络获取数据
				Log.i("abc", "获取到json数据");
				// 先转JsonObject

				JsonObject jsonObject = new JsonParser().parse(
						jsonString).getAsJsonObject();

				JsonArray jsonArray = jsonObject.getAsJsonObject(
						"list_submissions_response").getAsJsonArray(
						"lines");

				Gson gson = new Gson();
				ArrayList<Data> lineList = new ArrayList<Data>();
				for (JsonElement data : jsonArray) {
					Data mData = gson.fromJson(data,
							new TypeToken<Data>() {
							}.getType());
					lineList.add(mData);
				}
				Log.i("abc", "打印数组" + lineList.size());
				Iterator it = lineList.iterator();
				while (it.hasNext()) {
					Data data = (Data) it.next();
					Log.i("abc", data.toString());
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
	        for (Data news:mlineList) {
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("username", news.getUsername());
	            map.put("run_id", news.getRunId());
	            map.put("status", news.getStatus());
	            jsonlist.add(map);
	            Log.i("abc","解析json填充到listview之间的");
	        }
       SimpleAdapter adapter=new SimpleAdapter(this, jsonlist, R.layout.items2, new String[]
	                {"username","run_id","status"}, new int[]{R.id.items2_user,R.id.items2_runid,R.id.items2_status});
	       Log.i("abc","初始化");
	        commitListView.setAdapter(adapter);
	       commitListView.setOnItemClickListener(new OnItemClickListener() {
	    	   @Override
				public void onItemClick(AdapterView<?> parent, View view, int pos,
						long id) {
	    			//Map<String, Object> item = (Map<String, Object>)parent.getItemAtPosition(pos);
	    		   String string =(String)((TextView)view.findViewById(R.id.items2_runid)).getText();
	    		   Log.i("ydh","打印出item"+" "+pos+" " +id+" " +string);
	    		   Intent mComIntent = new Intent();
	    		   mComIntent.setClass(Commit.this, DetailInfoOfCommit.class);
	    		   Bundle bundle = new Bundle();
	    		   bundle.putString("str", string);
	    		   mComIntent.putExtras(bundle);
	    		   startActivity(mComIntent);
	    		  
					
					
					
	    	   }
	    	   
		});
	     
	}
  
   
    
}

  
       
        /*setListAdapter(new SimpleAdapter(this, getData(), R.layout.items2, 
				new String[]{"user", "mesg", "result"}, 
				new int[]{R.id.item2_user, R.id.item2_text, R.id.item2_result}));
        ListView lv = getListView();
      
        lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Map<String, Object> item = (Map<String, Object>)parent.getItemAtPosition(pos);
				Toast.makeText(getApplicationContext(), (String)item.get("mesg"),
						Toast.LENGTH_SHORT).show();
				Intent mComIntent = new Intent(Commit.this,DetailInfoOfCommit.class);
				startActivity(mComIntent);
				
			}        	
        });
*/

       
 

