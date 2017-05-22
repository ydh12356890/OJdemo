package com.example.ojprogramming;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.R.anim;
import android.R.string;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShouyeList extends Activity{
	private TextView num;
	private TextView question;
	private TextView accuracy;
	private ListView mlistview;
	public static final int PARSESUCCWSS = 0x2016;

	// private String urlPath="http://35.189.170.28:8000/api/inline/problems";
	private static final String TAG = "MainActivity";
	ArrayList<Line> mlineList = new ArrayList<Line>();
	final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == PARSESUCCWSS) {

				mlineList = (ArrayList<Line>)msg.obj;
				initdata();

			}
		}
	};
	@Override  
	protected void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);     
		num = (TextView)findViewById(R.id.number);
		question = (TextView)findViewById(R.id.text);
		accuracy = (TextView)findViewById(R.id.rate);
		mlistview= (ListView)findViewById(R.id.listview);
		final String path = "http://35.189.170.28:8000/api/inline/problems";  

		Thread thread = new Thread(new Runnable(){//创建子线程进行网络访问的操作  
			@Override
			public void run() {
				Log.i("abc", "进入");
				String path = "http://35.189.170.28:8000/api/inline/problems";
				Log.i("abc", "开始读取URL");
				String jsonString = HttpUtils.getJsonContent(path);// 从网络获取数据
				Log.i("abc", "获取到json数据");
				// 先转JsonObject

				JsonObject jsonObject = new JsonParser().parse(
						jsonString).getAsJsonObject();

				JsonArray jsonArray = jsonObject.getAsJsonObject(
						"list_problems_response").getAsJsonArray(
								"lines");

				Gson gson = new Gson();
				ArrayList<Line> lineList = new ArrayList<Line>();
				for (JsonElement line : jsonArray) {
					Line mLine = gson.fromJson(line,
							new TypeToken<Line>() {
					}.getType());
					lineList.add(mLine);
				}
				Log.i("abc", "打印数组" + lineList.size());
				/*Iterator it = lineList.iterator();
				while (it.hasNext()) {
					Line line = (Line) it.next();
					Log.i("abc", line.toString());
				}*/
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
		for (Line news:mlineList) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("sid", news.getSid());
			map.put("title", news.getTitle());
			map.put("source", news.getSource());
			jsonlist.add(map);
			Log.i("abc","解析json填充到listview之间的");
		}
		SimpleAdapter adapter=new SimpleAdapter(this, jsonlist, R.layout.items, new String[]
				{"sid","title","source"}, new int[]{R.id.items_number,R.id.items_text,R.id.items_rate});
		Log.i("abc","初始化");
		mlistview.setAdapter(adapter);
		mlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				//Map<String, Object> item = (Map<String, Object>)parent.getItemAtPosition(pos);
				String string =(String)((TextView)view.findViewById(R.id.items_number)).getText();
				Log.i("ydh","打印出Problem的item"+" "+pos+" " +id+" " +string);

				Intent mComIntent = new Intent();
				mComIntent.setClass(ShouyeList.this, DetailInfoOfQuestion.class);
				Bundle bundle = new Bundle();
				bundle.putString("str", string);
				mComIntent.putExtras(bundle);
				startActivity(mComIntent);




			}

		});

	}

}
/*//DataBaseManagementHelper继承自SQLiteOpenHelper
	    public static class DataBaseManagementHelper extends SQLiteOpenHelper {
	    	private Context context;
	       public DataBaseManagementHelper(Context context) {
	            super(context, "mySQLite", null, 2);
	            this.context = context;
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) {

	        	 String sql1 = "create table if not exists problem_info(problem_sid varchar(10) primary key,"
	        	 		+ "title varchar(50),description varchar(200),input varchar(20),"
	        	 		+ "output varchar(20),sample_input varchar(20),sample_output varchar(20),"
	        	 		+ "source varchar(20),hint varchar(20),hide varchar(20))";
	             db.execSQL(sql1);

	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            Log.i(TAG, "DataBaseManagementHelper onUpgrade");
	            onCreate(db);
	        }
	    }*/




