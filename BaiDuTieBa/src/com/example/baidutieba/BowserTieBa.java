package com.example.baidutieba;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.cerosoft.tieba.MyTool;
//import com.cerosoft.tieba.MyTool.DownLoad;
import com.cerosoft.tieba.SonTieZi;
import com.cerosoft.tieba.TieBaObj;
import com.cerosoft.tieba.TieBaUtilTool;
import com.cerosoft.tieba.User;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class BowserTieBa extends Activity {
	
	String PID = null;
	Bundle bl;
	Intent intent;
	ArrayList<TieBaObj> list;
	ArrayList<View> view = new ArrayList<View>();;
	ArrayList<User> user ;
	ArrayList<SonTieZi> son;
    Document doc;
    //ArrayList<User> listUser;
    String tieBaName;
    ImageView portNumIcon;
    ImageView authorIcon;
    ImageView ReportIcon;
    int j = 5;
    int pageNumber = 0;
    boolean isNext = false;
    EditText et_report_title;
    EditText et_report_content;
    InputStream YZM_IN = null;
    Bitmap bmp_report;
    String[] captcha_vcode = {"","","",""};
    int k;
    int p = 0;
    TextView textv;
    String fid = "empty";
    boolean send_posts_result = false;
    AlertDialog alertDialog;
    
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager manage=getWindowManager();
	    Display display=manage.getDefaultDisplay();
	    int screenWidth=display.getWidth();
		this.setTitle("");
		intent=this.getIntent();
		bl=intent.getExtras();
		list=(ArrayList<TieBaObj>) bl.getSerializable("tieBa");
		tieBaName = bl.getString("tieBaName");
		user = (ArrayList<User>) bl.getSerializable("user");
		pageNumber = bl.getInt("pageNumber");
		try{
			if(tieBaName!=null)
				this.setTitle(java.net.URLDecoder.decode(tieBaName, "GB2312")+"吧欢迎您");
		}catch(Exception e){
			
		}
		if(list.size()==0)
			new  AlertDialog.Builder(BowserTieBa.this).setTitle("Message").setMessage("empty.").setPositiveButton("OK", null).show();
		//listUser =(ArrayList<User>) bl.getSerializable("selectTieba");
		
		ScrollView sView = new ScrollView(this);
		LinearLayout layout = new LinearLayout(this);        
		layout.setOrientation(LinearLayout.VERTICAL);  
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		LinearLayout layout2 = new LinearLayout(this);        
		layout2.setOrientation(LinearLayout.VERTICAL);  
		layout2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		//Button button1 = new Button(this);
		//button1.setBackgroundColor(Color.rgb(136,0,0));
		//button1.setText("贴吧");
		//layout2.addView(button1);
		
	
		/*
for(i=0;i<list.size();i++){
			
			 RelativeLayout rLayout = new RelativeLayout(this){
				 protected void onDraw(Canvas canvas)
				    {
				        super.onDraw(canvas);
				        Paint paint = new Paint();
				        paint.setColor(Color.rgb(51, 204, 255));
				        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
				        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
				        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
				        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
				    }
			};
			rLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,170));
			if((i+10)%2!=1)
				rLayout.setBackgroundColor(Color.rgb(245,245,245));
				
			rLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					//rLayout.setBackgroundColor(Color.rgb(232,232,232));
					
					new Thread(){
						@Override
						public void run(){
							try {
								doc = Jsoup.connect("http://tieba.baidu.com"+list.get(i).getpID())
										.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
							} catch (IOException e) {
							}
							handler.sendEmptyMessage(0);
						}
						
					}.start();
				}
				
			});
			
			WindowManager manage=getWindowManager();
		    Display display=manage.getDefaultDisplay();
		    int screenWidth=display.getWidth();
			
		
			TextView textView = new TextView(this);
			TextView lastReport = new TextView(this);
			TextView lastTime = new TextView(this);
			TextView reportNum = new TextView(this);
			String temp = getNum(list.get(i).getReply());
			reportNum.setText(getNum(list.get(i).getReply()));
			
			reportNum.setTextSize(11f);
			
			lastReport.setText(list.get(i).getLastReply());
			lastTime.setText(list.get(i).getTime());
			
			textView.setText(list.get(i).getTitle());
			textView.setTextColor(Color.rgb(29,83,191));
			ImageView huifu_icon = new ImageView(this);
			huifu_icon.setImageResource(R.drawable.reply);
			ImageView author_icon = new ImageView(this);
			author_icon.setImageResource(R.drawable.author);
			ImageView report_icon = new ImageView(this);
			report_icon.setImageResource(R.drawable.report);
			
			huifu_icon.setScaleX(0.7f);
			huifu_icon.setScaleY(0.8f);
			
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params2.setMargins(screenWidth-307, 90, 15, 0);
			params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params3.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
			params3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params3.setMargins(4, 2, 0, 0);
			
			RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params4.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
			params4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params4.setMargins(112, 5, 0, 0);
			
			RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params5.addRule(RelativeLayout.ALIGN_PARENT_LEFT); 
			params5.setMargins(screenWidth-350, 87, 155, 0);
			
			RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params6.addRule(RelativeLayout.ALIGN_PARENT_LEFT); 
			params6.setMargins(screenWidth-337, 132, 155, 0);
			
			RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params7.setMargins(screenWidth-307, 130, 15, 0);
			params7.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			
			RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params8.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); 
			params8.setMargins(0, 130, 15, 0);
			
			RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params9.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
			params9.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			if(temp.length()==1){
				params9.setMargins(52, 7, 0, 0);
			}else if(temp.length()==2){
				params9.setMargins(45, 7, 0, 0);
			}else if(temp.length()==3){
				params9.setMargins(35, 7, 0, 0);
			}else if(temp.length()==4){
				params9.setMargins(25, 7, 0, 0);
			}else if(temp.length()==5){
				params9.setMargins(15, 7, 0, 0);
			}else if(temp.length()==6){
				params9.setMargins(5, 7, 0, 0);
			}else{
				params9.setMargins(0, 7, 0, 0);
			}
			
			
			TextView text1 = new TextView(this);
			text1.setLayoutParams(params2);
			text1.setText(list.get(i).getAuthor());
			text1.setTextColor(Color.rgb(74,130,240));
			
			textView.setLayoutParams(params4);
			huifu_icon.setLayoutParams(params3);
			author_icon.setLayoutParams(params5);
			report_icon.setLayoutParams(params6);
			lastReport.setLayoutParams(params7);
			lastTime.setLayoutParams(params8);
			reportNum.setLayoutParams(params9);
			
			rLayout.addView(text1);
			rLayout.addView(textView);
			rLayout.addView(lastReport);
			rLayout.addView(lastTime);
			rLayout.addView(huifu_icon);
			rLayout.addView(author_icon);
			rLayout.addView(report_icon);
			rLayout.addView(reportNum);
			layout.addView(rLayout);
		}
		
		*/
		
		
		
		
		for(final TieBaObj t : list){
			if(fid.equals("empty")){
				fid = t.getFid();
			}
			
			j++;
			final RelativeLayout rLayout = new RelativeLayout(this){
				/*
				 protected void onDraw(Canvas canvas)
				    {
				        super.onDraw(canvas);
				        Paint paint = new Paint();
				        //paint.setColor(Color.rgb(51, 204, 255));
				        paint.setColor(Color.rgb(255, 255, 255));
				        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
				        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
				        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
				        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
				    }
				    */
			};
			rLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,170));
			if((j+10)%2!=1)
				rLayout.setBackgroundColor(Color.rgb(245,245,245));
				
			rLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					//rLayout.setBackgroundColor(Color.rgb(232,232,232));
					
					new Thread(){
						@Override
						public void run(){
							try {
								PID = t.getpID();
								doc = Jsoup.connect("http://tieba.baidu.com"+t.getpID())
										.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
							} catch (IOException e) {
							}
							handler.sendEmptyMessage(0);
						}
						
					}.start();
				}
				
			});
		
			
			
		
			TextView textView = new TextView(this);
			TextView lastReport = new TextView(this);
			TextView lastTime = new TextView(this);
			TextView reportNum = new TextView(this);
			String temp = getNum(t.getReply());
			reportNum.setText(getNum(t.getReply()));
			
			reportNum.setTextSize(11f);
			if(t.getLastReply()==null||"".equals(t.getLastReply()))
				lastReport.setText(t.getAuthor());
			else
				lastReport.setText(t.getLastReply());
			lastTime.setText(t.getTime());
			
			textView.setText(t.getTitle());
			textView.setTextColor(Color.rgb(29,83,191));
			ImageView huifu_icon = new ImageView(this);
			huifu_icon.setImageResource(R.drawable.reply);
			ImageView author_icon = new ImageView(this);
			author_icon.setImageResource(R.drawable.author);
			ImageView report_icon = new ImageView(this);
			report_icon.setImageResource(R.drawable.report);
			
			huifu_icon.setScaleX(0.7f);
			huifu_icon.setScaleY(0.8f);
			
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params2.setMargins(screenWidth-307, 90, 15, 0);
			params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params3.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
			params3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params3.setMargins(4, 2, 0, 0);
			
			RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params4.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
			params4.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params4.setMargins(112, 5, 0, 0);
			
			RelativeLayout.LayoutParams params5 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params5.addRule(RelativeLayout.ALIGN_PARENT_LEFT); 
			params5.setMargins(screenWidth-350, 87, 155, 0);
			
			RelativeLayout.LayoutParams params6 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params6.addRule(RelativeLayout.ALIGN_PARENT_LEFT); 
			params6.setMargins(screenWidth-337, 132, 155, 0);
			
			RelativeLayout.LayoutParams params7 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params7.setMargins(screenWidth-307, 130, 15, 0);
			params7.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			
			RelativeLayout.LayoutParams params8 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params8.addRule(RelativeLayout.ALIGN_PARENT_RIGHT); 
			params8.setMargins(0, 130, 15, 0);
			
			RelativeLayout.LayoutParams params9 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params9.addRule(RelativeLayout.ALIGN_PARENT_TOP); 
			params9.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			if(temp.length()==1){
				params9.setMargins(52, 7, 0, 0);
			}else if(temp.length()==2){
				params9.setMargins(45, 7, 0, 0);
			}else if(temp.length()==3){
				params9.setMargins(35, 7, 0, 0);
			}else if(temp.length()==4){
				params9.setMargins(25, 7, 0, 0);
			}else if(temp.length()==5){
				params9.setMargins(15, 7, 0, 0);
			}else if(temp.length()==6){
				params9.setMargins(5, 7, 0, 0);
			}else{
				params9.setMargins(0, 7, 0, 0);
			}
			
			
			TextView text1 = new TextView(this);
			text1.setLayoutParams(params2);
			text1.setText(t.getAuthor());
			text1.setTextColor(Color.rgb(74,130,240));
			
			textView.setLayoutParams(params4);
			huifu_icon.setLayoutParams(params3);
			author_icon.setLayoutParams(params5);
			report_icon.setLayoutParams(params6);
			lastReport.setLayoutParams(params7);
			lastTime.setLayoutParams(params8);
			reportNum.setLayoutParams(params9);
			
			rLayout.addView(text1);
			rLayout.addView(textView);
			rLayout.addView(lastReport);
			rLayout.addView(lastTime);
			rLayout.addView(huifu_icon);
			rLayout.addView(author_icon);
			rLayout.addView(report_icon);
			rLayout.addView(reportNum);
			layout.addView(rLayout);
			
		}
		LinearLayout bottom = new LinearLayout(this);
		LinearLayout.LayoutParams bottom_Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                90);
		bottom_Params.setMargins(0, 10, 0, 0);
		bottom.setLayoutParams(bottom_Params);
		bottom.setBackgroundColor(Color.rgb(226, 244, 251));
		//bottom.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,120));
		
		Button nextPage = new Button(this);
		Button beforePage = new Button(this);
		
		LinearLayout.LayoutParams nextPage_Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                65);
		nextPage_Params.setMargins(50, 10, 0, 0);
		
		LinearLayout.LayoutParams beforePage_Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                65);
		beforePage_Params.setMargins(0, 10, 0, 0);
		//beforePage.setHeight(10);
		beforePage.setTextSize(13);
		beforePage.setTextColor(Color.WHITE);
		beforePage.setText("上一页");
		beforePage.setBackgroundColor(Color.rgb(138, 213, 240));
		beforePage.setLayoutParams(beforePage_Params);
		if(pageNumber>0)
			beforePage.setOnClickListener(new MyButtonListener((pageNumber-1),false));
		else
			beforePage.setOnClickListener(new MyButtonListener((pageNumber),false));
		
		//nextPage.setHeight(10);
		nextPage.setTextSize(13);
		nextPage.setTextColor(Color.WHITE);
		nextPage.setText("下一页");
		nextPage.setBackgroundColor(Color.rgb(138, 213, 240));
		nextPage.setOnClickListener(new MyButtonListener((pageNumber+1),true));
		nextPage.setLayoutParams(nextPage_Params);
		TextView tvw = new TextView(this);
		tvw.setText("当前页数："+(pageNumber+1)+"   ");
		LinearLayout.LayoutParams tvw_Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                LayoutParams.WRAP_CONTENT);
		tvw_Params.setMargins(130, 10, 0, 0);
		tvw.setLayoutParams(tvw_Params);
		
		bottom.addView(tvw);
		bottom.addView(beforePage);
		bottom.addView(nextPage);
		layout.addView(bottom);
		
		TextView tv_report_title = new TextView(this);
		tv_report_title.setWidth(screenWidth);
		TextView tv_report_content = new TextView(this);
		tv_report_content.setWidth(screenWidth);
		et_report_title = new EditText(this);
		et_report_title.setWidth(screenWidth);
		et_report_content = new EditText(this);
		Button send_report = new Button(this);
		
		send_report.setTextColor(Color.WHITE);
		send_report.setText("发表帖子");
		send_report.setOnClickListener(new SendPosts());
		send_report.setBackgroundColor(Color.rgb(0, 151, 202));
		
		tv_report_title.setText("标题：");
		tv_report_content.setText("内容：");
		
		
		
		et_report_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_CLASS_TEXT);
		et_report_content.setWidth(screenWidth);
		
		layout.addView(tv_report_title);
		layout.addView(et_report_title);
		layout.addView(tv_report_content);
		layout.addView(et_report_content);
		layout.addView(send_report);
		
		
		
		
		sView.addView(layout);
		layout2.addView(sView);
		setContentView(layout2);  
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
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
	
	private Handler h_true_page =new Handler(){
		ArrayList<TieBaObj> lTBO_trun_page;
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			try {
				lTBO_trun_page = (ArrayList<TieBaObj>) MyTool.resolveHtml(doc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//处理UI
			if(lTBO_trun_page.size()==0){
				new  AlertDialog.Builder(BowserTieBa.this).setTitle("Message").setMessage("kong.").setPositiveButton("OK", null).show();
			}else{
				Intent I_next = new Intent();
				I_next.setClass(BowserTieBa.this, BowserTieBa.class);
				Bundle bST = new Bundle();
				bST.putSerializable("tieBa",lTBO_trun_page);
				bST.putSerializable("user",list);
				bST.putString("tieBaName", tieBaName);
				
				if(isNext){
					bST.putInt("pageNumber", (pageNumber+1));
				}else{
					if(pageNumber>0)
						bST.putInt("pageNumber", (pageNumber-1));
					else
						bST.putInt("pageNumber", (pageNumber));
				}
					
				I_next.putExtras(bST);
				
				startActivityForResult(I_next, 0);
			}
			
			}
	};
	
	
	private Handler handler =new Handler(){
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		//son = new ArrayList<SonTieZi>();
		try {
			MyTool ml = new MyTool();
			son = ml.NTieView1(doc);
			//ArrayList<DownLoad> al = ml.threadlist;
			
			
			Intent I_next = new Intent();
			I_next.setClass(BowserTieBa.this, BowserTieZi.class);
			Bundle bST = new Bundle();
			bST.putSerializable("tiezi",son);
			bST.putSerializable("user",user);
			bST.putString("tieBaName", tieBaName);
			bST.putString("PID", PID);
			bST.putString("fid", fid);
			bST.putInt("pageNumber", 1);
			I_next.putExtras(bST);
			startActivityForResult(I_next, 0);
		} catch (Exception e) {
		}
		
		}
		};
		
		public static String getNum(String src) {
			if(src==null||"".equals(src)){
				return "";
			}
			String regex = "\\d+";
			Pattern p= Pattern.compile(regex);
			Matcher m = p.matcher( src);
			String s = null;
			while(m.find()){
			s = m.group();
//			System.out.println(s);
			}
			return s;
		}
		
	public class MyButtonListener implements View.OnClickListener{
		public int pNumber = 0;
		public boolean mb = false;
		
		public MyButtonListener(int i,boolean b){
			this.pNumber = i;
			this.mb = b;
		}

		@Override
		public void onClick(View v) {
			isNext = mb;
			new Thread(){
	    		
				@Override
				public void run(){
	    	
					
			try {
				
				doc = Jsoup.connect("http://tieba.baidu.com.cn/f?kw="+java.net.URLEncoder.encode(tieBaName,"GB2312")+"&tp=0&pn="+(pNumber*50))
						.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
				h_true_page.sendEmptyMessage(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				}
			}.start();
			
		}
		
	}
	
	public class SendPosts implements View.OnClickListener{
		LinearLayout Pop_layout;
		@SuppressLint("NewApi")
		@Override
		public void onClick(View arg0) {
			
			new downYZM(user.get(0)).start();
			
		}
		
	}
	
	public class downYZM extends Thread {
		User u_0;
		
		public downYZM(User user){
			u_0 = user;
		}
		
		public void run(){
			try {
				bmp_report = u_0.postTopic(tieBaName,et_report_title.getText().toString(), et_report_content.getText().toString(),fid);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler_0.sendEmptyMessage(0);
		}
	}
	
	private Handler handler_0 =new Handler(){
		@SuppressLint({ "HandlerLeak", "NewApi" })
		@Override
		//当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		//处理UI
		if(bmp_report==null){
			new  AlertDialog.Builder(BowserTieBa.this).setTitle("Message").setMessage("Send success").setPositiveButton("OK", null).show();
			new Thread(){
				@Override
				public void run(){
			try {
				doc = Jsoup.connect("http://tieba.baidu.com.cn/f?kw="+java.net.URLEncoder.encode(tieBaName,"GB2312"))
						.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
				h_true_page.sendEmptyMessage(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
			}.start();
			
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(BowserTieBa.this);
		
		ImageView iiw = new ImageView(BowserTieBa.this);
		iiw.setImageBitmap(bmp_report);
		RelativeLayout.LayoutParams iiw_params = new RelativeLayout.LayoutParams(bmp_report.getWidth()*2,bmp_report.getHeight()*2);
		iiw_params.setMargins((MyTool.getScreenWidth(BowserTieBa.this)-30)/2-bmp_report.getWidth(), 0, 0, 0);
		
		iiw.setLayoutParams(iiw_params);
		
//		iiw.setLayoutParams(new RelativeLayout.LayoutParams(bmp_report.getWidth()*2,bmp_report.getHeight()*2));
//		iiw.setScaleX(1.5f);
//		iiw.setScaleY(1.5f);
		
		RelativeLayout Pop_layout = new RelativeLayout(BowserTieBa.this);
		//Pop_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		RelativeLayout.LayoutParams pop_params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,    
                LayoutParams.FILL_PARENT);
		pop_params.setMargins(150, 40, 40, 150);
		Pop_layout.setLayoutParams(pop_params);
		
		//Pop_layout.setGravity(Gravity.CENTER);
		Pop_layout.addView(iiw);
		textv = new TextView(BowserTieBa.this);
		textv.setText("code : ");
		int width = ((bmp_report.getWidth()*2)/3);
		int height = ((bmp_report.getHeight()*2)/4);
		
		for(k=1;k<10;k++){
			Button bn = new Button(BowserTieBa.this);
			
			
			
			bn.setOnClickListener(new vcode(k));
			
			
			
			bn.setWidth(width+10);
			bn.setHeight(height+10);
			//透明
			bn.getBackground().setAlpha(100);
			//bn.setBackgroundColor(Color.RED);
			int top = 0,left = 0;
			if(k%3==1){
				left = (MyTool.getScreenWidth(BowserTieBa.this)-30)/2-(width*3)/2;
			}else if(k%3==2){
				left = (MyTool.getScreenWidth(BowserTieBa.this)-30)/2-width/2;
			}else if(k%3==0){
				left = (MyTool.getScreenWidth(BowserTieBa.this)-30)/2+width/2;
			}
			
			if(k<4){
				top = height-10;
			}else if(k<7){
				top = 2*height-10;
			}else if(k<10){
				top = 3*height-10;
			}
			
			
			
			RelativeLayout.LayoutParams bn_params = new RelativeLayout.LayoutParams(width,height);
			bn_params.setMargins(left, top, 0, 0);
			bn.setLayoutParams(bn_params);
			Pop_layout.addView(bn);
			
			
			
			
			
		}
		
		RelativeLayout.LayoutParams textv_params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		textv_params.setMargins(0, bmp_report.getHeight()*2+30, 0, 0);
		textv.setLayoutParams(textv_params);
		LinearLayout vcode_layout = new LinearLayout(BowserTieBa.this);
		vcode_layout.setLayoutParams(textv_params);
		vcode_layout.addView(textv);
		Pop_layout.addView(vcode_layout);
		/*
		BitmapDrawable be = new BitmapDrawable(YZM_IN);
		Pop_layout = new LinearLayout(BowserTieBa.this);
		Pop_layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		Pop_layout.setBackground(be);
		
		//BitmapDrawable bmp = new BitmapDrawable(bp);
		
		//Pop_layout.setBackground(bmp);
		 */
		
		
		builder.setView(Pop_layout);
		builder.setTitle("请点击输入验证码：");
		alertDialog = builder.create();
		alertDialog.show();
		
		
		
		}
		};
		
	
		
		public class vcode implements View.OnClickListener{
			int but_i;
			
			public vcode(int i){
				but_i = i;
			}

			@Override
			public void onClick(View v) {
				captcha_vcode[p] = TieBaUtilTool.getCaptchaString(but_i-1);
				p++;
				String sum = "";
				for(String str: captcha_vcode){
					if(str.equals("")||str.equals("null")||str==null)
						continue;
					else
						sum = sum+str;
				}
				textv.setText("code : "+sum);
				textv.postInvalidate();
				if(captcha_vcode[3].equals("")||captcha_vcode[3].equals("null")||captcha_vcode[3]==null){
					
				}else{

					new Thread(){
						@Override
						public void run(){
						//你要执行的方法
						try {
							
							send_posts_result = user.get(0).haveYZM(textv.getText().toString().substring(6, textv.getText().toString().length()), et_report_title.getText().toString(), et_report_content.getText().toString(), tieBaName, fid);
						} catch (Exception e) {
							e.printStackTrace();
						}
							
							
						handler_send_posts.sendEmptyMessage(0);
						}
						}.start();
					
					
					
				}
				
				
			}
			
		}
		
		
		private Handler handler_send_posts =new Handler(){
			@SuppressLint({ "HandlerLeak", "NewApi" })
			@Override
			//当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(send_posts_result)
				new  AlertDialog.Builder(BowserTieBa.this).setTitle("Message").setMessage("Send success").setPositiveButton("OK", null).show();
			else
				new  AlertDialog.Builder(BowserTieBa.this).setTitle("Message").setMessage("Send fail").setPositiveButton("OK", null).show();
			et_report_title.setText("");
			et_report_content.setText("");
			alertDialog.dismiss();
			new Thread(){
				    		
							@Override
							public void run(){
				    	
								
						try {
							
							doc = Jsoup.connect("http://tieba.baidu.com.cn/f?kw="+java.net.URLEncoder.encode(tieBaName,"GB2312"))
									.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
							h_true_page.sendEmptyMessage(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
							}
						}.start();
			}
		};
	
}
