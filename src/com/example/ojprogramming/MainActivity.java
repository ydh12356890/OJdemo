package com.example.ojprogramming;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.app.Activity;
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class MainActivity extends Activity implements
android.view.View.OnClickListener {
	private ViewPager mViewPager;// 用来放置界面切换
	private PagerAdapter mPagerAdapter;// 初始化View适配器
	private List<View> mViews = new ArrayList<View>();// 用来存放Tab01-04
	// 四个Tab，每个Tab包含一个按钮
	private LinearLayout ll_weixin;
	private LinearLayout ll_tongxunlu;
	private LinearLayout ll_faxian;
	private LinearLayout ll_wode;
	// 四个按钮
	private ImageButton btn_weixin;
	private ImageButton btn_tongxunlu;
	private ImageButton btn_faxian;
	private ImageButton btn_wode;
	private TextView musenameTextView;
	public static final int PARSESUCCWSS = 0x2016;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initViewPage();
		initEvent();



		/*Intent intent = getIntent();
		int page = intent.getIntExtra("page",3);
		//String str = intent.getStringExtra("str");
		 Bundle bundle=intent.getExtras();  
    		    String str=bundle.getString("str");
		mViewPager.setCurrentItem(page);*/
		//View view = mViewPager.findViewById(page);
		// musenameTextView = (TextView)view.findViewById(R.id.denglu);



	}

	/* Intent intent2 = getIntent();
    Bundle bundle=intent2.getExtras();  
    String str=bundle.getString("str");*/





	private void initEvent()
	{
		ll_weixin.setOnClickListener(this);
		ll_tongxunlu.setOnClickListener(this);
		ll_faxian.setOnClickListener(this);
		ll_wode.setOnClickListener(this);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			//ViewPage左右滑动时

			@Override
			public void onPageSelected(int arg0) {
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					resetImg();
					btn_weixin.setImageResource(R.drawable.onclick_weixin);
					break;
				case 1:
					resetImg();
					btn_tongxunlu.setImageResource(R.drawable.onclick_tongxunlu);
					break;
				case 2:
					resetImg();
					btn_faxian.setImageResource(R.drawable.onclick_faxian);
					break;
				case 3:
					resetImg();
					btn_wode.setImageResource(R.drawable.onclick_wode);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	//初始化设置

	private void initView() 
	{
		mViewPager = (ViewPager) findViewById(R.id.vp_content);
		// 初始化四个LinearLayout
		ll_weixin = (LinearLayout) findViewById(R.id.weixin);
		ll_tongxunlu = (LinearLayout) findViewById(R.id.tongxunlu);
		ll_faxian = (LinearLayout) findViewById(R.id.faxian);
		ll_wode = (LinearLayout) findViewById(R.id.wode);
		// 初始化四个按钮
		btn_weixin = (ImageButton) findViewById(R.id.id_btn_weixin);
		btn_tongxunlu = (ImageButton) findViewById(R.id.id_btn_tongxunlu);
		btn_faxian = (ImageButton) findViewById(R.id.id_btn_faxian);
		btn_wode = (ImageButton) findViewById(R.id.id_btn_wode);

	}

	//初始化ViewPage

	private void initViewPage() {

		// 初始化四个布局
		LayoutInflater mLayoutInflater = LayoutInflater.from(this);
		View tab01 = mLayoutInflater.inflate(R.layout.page1, null);
		View tab02 = mLayoutInflater.inflate(R.layout.page2, null);
		View tab03 = mLayoutInflater.inflate(R.layout.page3, null);
		View tab04 = mLayoutInflater.inflate(R.layout.page4, null);
		musenameTextView = (TextView)tab04.findViewById(R.id.denglu);
	
		Intent intent = getIntent();
		// Bundle bundle=intent.getExtras();  
		  //  String str=bundle.getString("str");
		String str = intent.getStringExtra("username");
		    musenameTextView.setText(str);
		

		mViews.add(tab01);
		mViews.add(tab02);
		mViews.add(tab03);
		mViews.add(tab04);
		// 适配器初始化并设置
		mPagerAdapter = new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(mViews.get(position));

			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				//container.setId(position);
				View view = mViews.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return mViews.size();
			}

		};
		mViewPager.setAdapter(mPagerAdapter);
	}

	//判断哪个要显示，及设置按钮图片

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.weixin:
			mViewPager.setCurrentItem(0);
			resetImg();
			btn_weixin.setImageResource(R.drawable.onclick_weixin);
			break;
		case R.id.tongxunlu:
			mViewPager.setCurrentItem(1);
			resetImg();
			btn_tongxunlu.setImageResource(R.drawable.onclick_tongxunlu);
			break;
		case R.id.faxian:
			mViewPager.setCurrentItem(2);
			resetImg();
			btn_faxian.setImageResource(R.drawable.onclick_faxian);
			break;
		case R.id.wode:
			mViewPager.setCurrentItem(3);
			resetImg();
			btn_wode.setImageResource(R.drawable.onclick_wode);
			break;
		default:
			break;
		}
	}

	//把所有图片变暗
	private void resetImg() 
	{
		btn_weixin.setImageResource(R.drawable.notclick_weixin);
		btn_tongxunlu.setImageResource(R.drawable.notclick_tongxunlu);
		btn_faxian.setImageResource(R.drawable.notclick_faxian);
		btn_wode.setImageResource(R.drawable.notclick_wode);
	}

	public void IntoLogin(View source)
	{
		Intent intologinit = new Intent(MainActivity.this,Login.class);
		startActivity(intologinit);
		finish();

	}
	public void IntoQuestion(View source)
	{
		Intent intoquestionit = new Intent (MainActivity.this,ShouyeList.class);
		startActivity(intoquestionit);
	}
	public void IntoCommit(View source)
	{
		Intent intocommit = new Intent (MainActivity.this,Commit.class);
		startActivity(intocommit);
	}
	public void IntoContests(View source)
	{
		Intent intoconIntent = new Intent(MainActivity.this,Contests.class);
		startActivity(intoconIntent);
	}
}