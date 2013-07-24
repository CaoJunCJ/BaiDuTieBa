package com.example.baidutieba;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cerosoft.tieba.MyTool;
import com.cerosoft.tieba.TieBaObj;
import com.cerosoft.tieba.User;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("HandlerLeak")
public class SelectTieBa extends Activity {
	
	private EditText keyWord;
	private Button gButton;
	private Bundle bl;
	private Intent intent;
	//private String tiebaName;
	private ArrayList<User> list;
	private ArrayList<TieBaObj> lTBO = new ArrayList<TieBaObj>();
	private Document doc;
	private String tempName;
	
	
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input_tieba_name);
		this.setTitle("百度贴吧");
		intent=this.getIntent();
		bl=intent.getExtras();
	    list=(ArrayList<User>) bl.getSerializable("user_0");
		keyWord = (EditText) findViewById(R.id.str_tieba_name);
		gButton = (Button) findViewById(R.id.ser_tieba);
		try {
			 
				
				gButton.setOnClickListener(new View.OnClickListener() {
				    public void onClick(View v) {
				    	String tiebaName = keyWord.getText().toString();
				    	if(tiebaName==null||"".equals(tiebaName)){
				    		new  AlertDialog.Builder(SelectTieBa.this).setTitle("Message").setMessage("The keyword can not empty.").setPositiveButton("OK", null).show();
				    	}else{
				    		try {
								tiebaName = java.net.URLEncoder.encode(keyWord.getText().toString(),"GB2312");
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				    		tempName = tiebaName;
				    		new Thread(){
					    		
								@Override
								public void run(){
					    	
									
							try {
								doc = Jsoup.connect("http://tieba.baidu.com.cn/f?kw="+tempName)
										.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
								handler.sendEmptyMessage(0);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								}
							}.start();
				    	}
				    	
				    	
						
				    }
				}
				);
				
			
		} catch (Exception e2) {
		}
		
	}
	
	@SuppressWarnings("unused")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private Handler handler =new Handler(){
		@Override
		
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		try {
			lTBO = (ArrayList<TieBaObj>) MyTool.resolveHtml(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//处理UI
		if(lTBO.size()==0){
			new  AlertDialog.Builder(SelectTieBa.this).setTitle("Message").setMessage("kong.").setPositiveButton("OK", null).show();
		}else{
			Intent I_next = new Intent();
			I_next.setClass(SelectTieBa.this, BowserTieBa.class);
			Bundle bST = new Bundle();
			bST.putSerializable("tieBa",lTBO);
			bST.putSerializable("user",list);
			bST.putString("tieBaName", tempName);
			bST.putInt("pageNumber", 0);
			I_next.putExtras(bST);
			
			startActivityForResult(I_next, 0);
		}
		
		}
		};

}
