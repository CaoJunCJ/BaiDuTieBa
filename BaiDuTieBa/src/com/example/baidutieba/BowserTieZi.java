package com.example.baidutieba;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//import com.cerosoft.tieba.MyTool.DownLoad;
import com.cerosoft.tieba.MyTool;
import com.cerosoft.tieba.SonTieZi;
import com.cerosoft.tieba.TieBaUtilTool;
import com.cerosoft.tieba.User;
import com.example.baidutieba.BowserTieBa.downYZM;
import com.example.baidutieba.BowserTieBa.vcode;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class BowserTieZi extends Activity {
	
	 String tieBaName;
	 ArrayList<User> listUser;
	 String PID = null;
	 String title = "empty";
	 int TieZi_pageNumber = 1;
	 Document doc;
	 int maxPage = 0;
	 String[] captcha_vcode = {"","","",""};
	 boolean isNext = false;
	 int p = 0;
	 String Ftitle = "";
	 Bitmap bmp_report;
	 String fid;
	 int k;
	 TextView textv;
	 boolean send_posts_result = false;
	 AlertDialog alertDialog;
	 EditText et_report_content;
	 
	 
	@SuppressLint("NewApi")
	@SuppressWarnings({ "unchecked", "deprecation" })
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		WindowManager manage=getWindowManager();
	    Display display=manage.getDefaultDisplay();
	    int screenWidth=display.getWidth();
		
		Bundle bl;
		Intent intent;
		intent=this.getIntent();
		bl=intent.getExtras();
		ArrayList<SonTieZi> list=(ArrayList<SonTieZi>) bl.getSerializable("tiezi");
		tieBaName = bl.getString("tieBaName");
		PID = bl.getString("PID");
		fid = bl.getString("fid");
		TieZi_pageNumber = bl.getInt("pageNumber");
		listUser =(ArrayList<User>) bl.getSerializable("user");
		String test_title = bl.getString("Ftitle");
		if(test_title!=null&&!("".equals(test_title))){
			Ftitle = test_title;
		}
		
		this.setTitle(tieBaName);
		
		ScrollView sView = new ScrollView(this);
		LinearLayout layout = new LinearLayout(this);        
		layout.setOrientation(LinearLayout.VERTICAL);  
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		LinearLayout layout2 = new LinearLayout(this);        
		layout2.setOrientation(LinearLayout.VERTICAL);  
		layout2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		
		for(final SonTieZi s : list){
			if(maxPage==0){
				maxPage = s.getAllPage();
			}
			
			try{
				if(title.equals("empty"))
					title = s.getTitle();
			}catch(Exception e){
				title = Ftitle;
			}
			
			ArrayList<String> ContentImage = s.getContentImage();
			
			RelativeLayout rLayout = new RelativeLayout(this){
				protected void onDraw(Canvas canvas)
			    {
			        super.onDraw(canvas);
			        Paint paint = new Paint();
			        //paint.setColor(Color.rgb(51, 204, 255));
			        paint.setColor(Color.rgb(217, 217, 217));
			        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
			        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
			        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
			        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
			    }
			};
			RelativeLayout leftArea = new RelativeLayout(this){
				protected void onDraw(Canvas canvas)
			    {
			        super.onDraw(canvas);
			        Paint paint = new Paint();
			        //paint.setColor(Color.rgb(51, 204, 255));
			        paint.setColor(Color.rgb(217, 217, 217));
			        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
			        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
			        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
			        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
			    }
			};
			
			LinearLayout ContentLayout = new LinearLayout(this){
				/*
				protected void onDraw(Canvas canvas)
			    {
			        super.onDraw(canvas);
			        Paint paint = new Paint();
			        //paint.setColor(Color.rgb(51, 204, 255));
			        paint.setColor(Color.rgb(217, 217, 217));
			        canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
			        canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
			        canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1, this.getHeight() - 1, paint);
			        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, paint);
			    }
			    */
			};
			
			RelativeLayout.LayoutParams params_content = null;
			
			int LC_height = 0;
			
			int add = 0;
			
			if(s.replyer.size()>0){
				add = 20;
			}
			
			boolean isSgin = false;
			if(s.getSign().equals("empty")){
				
				if(s.getTieZiContent().length()<60){
					rLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,250+add));
					leftArea.setLayoutParams(new LayoutParams(160,250+add));
					params_content = new RelativeLayout.LayoutParams(screenWidth-190,180+add);
					LC_height = 180;
				}else{
					int hight = (s.getTieZiContent().length()-60)/15;
					rLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,250+30*hight+add));
					leftArea.setLayoutParams(new LayoutParams(160,250+30*hight+add));
					params_content = new RelativeLayout.LayoutParams(screenWidth-180,190+30*hight+add);
					LC_height = 190+30*hight;
				}
					
			}
			else{
				if(s.getTieZiContent().length()<60){
					params_content = new RelativeLayout.LayoutParams(screenWidth-180,190+add);	
					rLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,450+add));
					leftArea.setLayoutParams(new LayoutParams(160,470+add));
					isSgin = true;
					LC_height = 190;
				}else{
					int hight = (s.getTieZiContent().length()-60)/15;
					params_content = new RelativeLayout.LayoutParams(screenWidth-180,190+30*hight+add);	
					rLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,450+30*hight+add));
					leftArea.setLayoutParams(new LayoutParams(160,470+30*hight+add));
					LC_height = 190+30*hight;
					isSgin = true;
				}
			}
			
			
			
			params_content.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params_content.setMargins(160, 0, 0, 0);
			
			ContentLayout.setLayoutParams(params_content);
			
			RelativeLayout SignLayout = new RelativeLayout(this);
			
			RelativeLayout.LayoutParams params_sgin = new RelativeLayout.LayoutParams(screenWidth-170,250);
			//params_sgin = new RelativeLayout.LayoutParams(screenWidth-170,250);
			params_sgin.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params_sgin.setMargins(160, LC_height+20, 0, 0);
			
			
			SignLayout.setLayoutParams(params_sgin);
			
			
			
			
			leftArea.setBackgroundColor(Color.rgb(247,247,247));
			
			
			
			rLayout.setBackgroundColor(Color.rgb(255,255,255));
			TextView textView = new TextView(this);
			textView.setText(s.getAuthor());
			textView.setTextSize(9f);
			TextView textView1 = new TextView(this);
			textView1.setText(s.getTieZiContent());
			ImageView image = new ImageView(this);
			BitmapDownloaderTask bitask = new BitmapDownloaderTask(image); 
			bitask.execute(s.getAuthor_image());
			
//			Bitmap bitmap = getLoacalBitmap("/sdcard/tieba/"+PID.replaceAll("[^0-9]", "").trim()+"/"+s.getAuthor()+"_author_image.png");
//			image.setImageBitmap(bitmap);
			image.setScaleX(0.8f);
			image.setScaleY(0.8f);
			
			
			
			
			
			if(isSgin){
				ImageView sgin_image = new ImageView(this);
				
				BitmapDownloaderTask task = new BitmapDownloaderTask(sgin_image); 
	             task.execute(s.getSign());
				//Bitmap bitmap1 = getLoacalBitmap("/sdcard/tieba/"+PID.replaceAll("[^0-9]", "").trim()+"/"+s.getAuthor()+"_sign_image.png");
				//sgin_image.setImageBitmap(bitmap1);
	            sgin_image.setScaleX(0.9f);
	            sgin_image.setScaleY(0.9f);
				
				RelativeLayout N_image_layout = new RelativeLayout(this);
				RelativeLayout.LayoutParams params_image_sgin = new RelativeLayout.LayoutParams(1000,1000);
				params_image_sgin.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				params_image_sgin.setMargins(25, 50, 0, 0); 
				N_image_layout.setLayoutParams(params_image_sgin);
				N_image_layout.addView(sgin_image);
				TextView test = new TextView(this);
				test.setText("      ...................................................");
				test.setTextColor(Color.rgb(179, 179, 179));
				SignLayout.addView(test);
				SignLayout.addView(N_image_layout);
				rLayout.addView(SignLayout);
			}
			
			
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);  
			params2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);  
			params2.setMargins(0, 125, 0, 0);
			
			LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			//params3.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params3.setMargins(20, 20, 0, 0);
			
			RelativeLayout.LayoutParams params_image_author = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
	                LayoutParams.WRAP_CONTENT);
			params_image_author.addRule(RelativeLayout.ALIGN_PARENT_TOP);  
			params_image_author.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);  
			params_image_author.setMargins(00, 20, 0, 0);
			
			textView.setLayoutParams(params2);
			textView1.setLayoutParams(params3);
			
			
			image.setLayoutParams(params_image_author);
			leftArea.addView(image);
			leftArea.addView(textView);
			
			ContentLayout.addView(textView1);
			
			if(s.replyer.size()>0){
				RelativeLayout LC_layout = new RelativeLayout(this);
				RelativeLayout.LayoutParams re_param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
		                LayoutParams.WRAP_CONTENT);
				re_param.setMargins(20, 5, 0, 0);
				RelativeLayout.LayoutParams re_param1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
		                LayoutParams.WRAP_CONTENT);
				re_param1.setMargins(290, -10, 0, 0);
				
				
				RelativeLayout.LayoutParams Lc_params = new RelativeLayout.LayoutParams(screenWidth-170,50);
				//params_sgin = new RelativeLayout.LayoutParams(screenWidth-170,250);
				Lc_params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				if(isSgin)
					Lc_params.setMargins(160, LC_height-18, 0, 0);
				else
					Lc_params.setMargins(160, LC_height+20, 0, 0);
				LC_layout.setLayoutParams(Lc_params);
				Button LC_report = new Button(this);
				LC_report.setLayoutParams(re_param1);
				LC_report.setBackgroundColor(Color.rgb(51, 181, 229));
				LC_report.setTextColor(Color.WHITE);
				LC_report.setTextSize(12f);
				LC_report.setText("点击查看");
				LC_report.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						ScrollView Pop_sView = new ScrollView(BowserTieZi.this);
						
						LinearLayout Pop_layout = new LinearLayout(BowserTieZi.this);        
						Pop_layout.setOrientation(LinearLayout.VERTICAL);  
						Pop_layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
						
						for(int j=0;j<s.replyer.size();j++){
							
							ImageView report_image = new ImageView(BowserTieZi.this);
							
							LinearLayout Pop_author_image = new LinearLayout(BowserTieZi.this);  
							Pop_author_image.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
							
							
							BitmapDownloaderTask reprot_task = new BitmapDownloaderTask(report_image); 
							reprot_task.execute(s.replyAuthorImage.get(j));
							
							LinearLayout Pop_layout1 = new LinearLayout(BowserTieZi.this);  
							Pop_layout1.setOrientation(LinearLayout.VERTICAL);  
							LinearLayout.LayoutParams Pop_layout1_param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
							Pop_layout1_param.setMargins(50, 30, 50, 30);
							Pop_layout1.setLayoutParams(Pop_layout1_param);
							
							TextView tw = new TextView(BowserTieZi.this);
							TextView time = new TextView(BowserTieZi.this);
							
							
							LinearLayout.LayoutParams time_param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
							time_param.setMargins(0, 20, 0, 0);
							time_param.gravity = Gravity.RIGHT; 
							time.setLayoutParams(time_param);
							time.setGravity(Gravity.RIGHT);
							
							//tw.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
							LinearLayout.LayoutParams tw_param = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
							tw_param.setMargins(20, 0, 0, 0);
							tw.setLayoutParams(tw_param);
						    SpannableString ss = new SpannableString(s.replyer.get(j)+": "+s.replyContent.get(j));  
						  
						    ss.setSpan(new ForegroundColorSpan(Color.rgb(29, 83, 191)), 0, s.replyer.get(j).length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
							
							tw.setText(ss);
							time.setTextSize(12f);
							time.setTextColor(Color.rgb(153, 153, 153));
							time.setText(s.replyDate.get(j));
							Pop_author_image.addView(report_image);
							Pop_author_image.addView(tw);
							Pop_layout1.addView(Pop_author_image);
//							Pop_layout1.addView(report_image);
//							Pop_layout1.addView(tw);
							Pop_layout1.addView(time);
							Pop_layout.addView(Pop_layout1);
						}
						Pop_sView.addView(Pop_layout);
						
						new AlertDialog.Builder(BowserTieZi.this).setView(Pop_sView).show();
					}
					
				});
				
			
				
				TextView LC_Num = new TextView(this);
				LC_Num.setTextColor(Color.rgb(0, 153, 204));
				LC_Num.setText("共有："+s.replyDate.size()+"个回复");
				LC_Num.setLayoutParams(re_param);
				LC_layout.addView(LC_Num);
				LC_layout.addView(LC_report);
				rLayout.addView(LC_layout);
				
			}
			
			for(String contentURL : ContentImage){
				ImageView conImage = new ImageView(this);
				BitmapDownloaderTask bk = new BitmapDownloaderTask(conImage); 
				bk.execute(contentURL);
				conImage.setLayoutParams(params3);
				
				ContentLayout.addView(conImage);
			}
			
			//rLayout.addView(textView1);
			rLayout.addView(ContentLayout);
			rLayout.addView(leftArea);
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
		beforePage.setTextSize(13);
		beforePage.setTextColor(Color.WHITE);
		beforePage.setText("上一页");
		beforePage.setBackgroundColor(Color.rgb(138, 213, 240));
		beforePage.setLayoutParams(beforePage_Params);
		if(TieZi_pageNumber>0)
			beforePage.setOnClickListener(new MyButtonListener((TieZi_pageNumber-1),false));
		else
			beforePage.setOnClickListener(new MyButtonListener((TieZi_pageNumber),false));
		
		nextPage.setTextSize(13);
		nextPage.setTextColor(Color.WHITE);
		nextPage.setText("下一页");
		nextPage.setBackgroundColor(Color.rgb(138, 213, 240));
		if(maxPage>TieZi_pageNumber)
			nextPage.setOnClickListener(new MyButtonListener((TieZi_pageNumber+1),true));
		else
			nextPage.setOnClickListener(new MyButtonListener((TieZi_pageNumber),true));
		nextPage.setLayoutParams(nextPage_Params);
		TextView tvw = new TextView(this);
		tvw.setText("当前页数："+(TieZi_pageNumber)+"   ");
		LinearLayout.LayoutParams tvw_Params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,    
                LayoutParams.WRAP_CONTENT);
		tvw_Params.setMargins(130, 10, 0, 0);
		tvw.setLayoutParams(tvw_Params);
		
		bottom.addView(tvw);
		bottom.addView(beforePage);
		bottom.addView(nextPage);
		layout.addView(bottom);
		
		
		TextView tv_report_content = new TextView(this);
		tv_report_content.setWidth(screenWidth);
		et_report_content = new EditText(this);
		Button send_report = new Button(this);
		send_report.setText("发表回复");
		send_report.setTextColor(Color.WHITE);
		send_report.setBackgroundColor(Color.rgb(0, 151, 202));
		send_report.setOnClickListener(new SendPosts());
		
		tv_report_content.setText("内容：");
		
		
		
		et_report_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE|InputType.TYPE_CLASS_TEXT);
		et_report_content.setWidth(screenWidth);
		
		layout.addView(tv_report_content);
		layout.addView(et_report_content);
		layout.addView(send_report);
		
		this.setTitle(title);
		sView.addView(layout);
		layout2.addView(sView);
		setContentView(layout2);  
	}
	
	
	
	
	
	
	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
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
	
	
	public static Bitmap getLoacalBitmap(String url) {
        try {
             FileInputStream fis = new FileInputStream(url);
             return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
   }
	
	
	class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> { 
	     private String url; 
	     private final WeakReference<ImageView> imageViewReference; 
	  
	     public BitmapDownloaderTask(ImageView imageView) { 
	         imageViewReference = new WeakReference<ImageView>(imageView); 
	     } 
	  
	     @Override 
	     // Actual download method, run in the task thread 
	     protected Bitmap doInBackground(String... params) { 
	          // params comes from the execute() call: params[0] is the url. 
	          return downloadBitmap(params[0]); 
	     } 
	  
	     @Override 
	     // Once the image is downloaded, associates it to the imageView 
	     protected void onPostExecute(Bitmap bitmap) { 
	         if (isCancelled()) { 
	             bitmap = null; 
	         } 
	  
	         if (imageViewReference != null) { 
	             ImageView imageView = imageViewReference.get(); 
	             if (imageView != null) { 
	                 imageView.setImageBitmap(bitmap); 
	             } 
	         } 
	     } 
	 } 
	
	
	public static Bitmap downloadBitmap(String url) { 
	     final AndroidHttpClient client = AndroidHttpClient.newInstance("Android"); 
	     final HttpGet getRequest = new HttpGet(url); 
	  
	     try { 
	         HttpResponse response = client.execute(getRequest); 
	         final int statusCode = response.getStatusLine().getStatusCode(); 
	         if (statusCode != HttpStatus.SC_OK) {  
	             Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);  
	             return null; 
	         } 
	          
	         final HttpEntity entity = response.getEntity(); 
	         if (entity != null) { 
	             InputStream inputStream = null; 
	             try { 
	                 inputStream = entity.getContent();  
	                 final Bitmap bitmap = BitmapFactory.decodeStream(inputStream); 
	                 return bitmap; 
	             } finally { 
	                 if (inputStream != null) { 
	                     inputStream.close();   
	                 } 
	                 entity.consumeContent(); 
	             } 
	         } 
	     } catch (Exception e) { 
	         // Could provide a more explicit error message for IOException or IllegalStateException 
	         getRequest.abort(); 
	        // Log.w("ImageDownloader", "Error while retrieving bitmap from " + url, e.toString()); 
	     } finally { 
	         if (client != null) { 
	             client.close(); 
	         } 
	     } 
	     return null; 
	 } 
	
	public class MyButtonListener implements View.OnClickListener{
		int page;
		public boolean mb = false;
		public MyButtonListener(int i,boolean b){
			this.page = i;
			this.mb = b;
		}

		@Override
		public void onClick(View v) {
			isNext = mb;
			new Thread(){
				@Override
				public void run(){
					try {
						doc = Jsoup.connect("http://tieba.baidu.com"+PID+"?pn="+page)
								.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
					} catch (IOException e) {
					}
					handler.sendEmptyMessage(0);
				}
				
			}.start();
			
		}
		
	}
	
	private Handler handler =new Handler(){
		ArrayList<SonTieZi> son;
		public void handleMessage(Message msg){
		super.handleMessage(msg);
		try {
			MyTool ml = new MyTool();
			son = ml.NTieView1(doc);
			//ArrayList<DownLoad> al = ml.threadlist;
			
			
			Intent I_next = new Intent();
			I_next.setClass(BowserTieZi.this, BowserTieZi.class);
			Bundle bST = new Bundle();
			bST.putSerializable("tiezi",son);
			bST.putSerializable("user",listUser);
			bST.putString("tieBaName", tieBaName);
			bST.putString("PID", PID);
			bST.putString("fid", fid);
			bST.putString("Ftitle", title);
			if(isNext){
				if(maxPage>TieZi_pageNumber)
					bST.putInt("pageNumber", (TieZi_pageNumber+1));
				else
					bST.putInt("pageNumber", (TieZi_pageNumber));
			}else{
				if(TieZi_pageNumber>0)
					bST.putInt("pageNumber", (TieZi_pageNumber-1));
				else
					bST.putInt("pageNumber", (TieZi_pageNumber));
			}
			I_next.putExtras(bST);
			startActivityForResult(I_next, 0);
		} catch (Exception e) {
		}
		
		}
		};
	
		
		public class vcodeListener implements View.OnClickListener{

			
				int but_i;
				
				public vcodeListener(int i){
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
								
								send_posts_result = listUser.get(0).send_report_haveYZM(textv.getText().toString().substring(6, textv.getText().toString().length()), et_report_content.getText().toString(), tieBaName, fid,PID.substring(3, PID.length()));
							} catch (Exception e) {
								e.printStackTrace();
							}
								
								
							handler_send_posts.sendEmptyMessage(0);
							}
							}.start();
					}
				
			}
			
		}
		
		public class SendPosts implements View.OnClickListener{
			LinearLayout Pop_layout;
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				
				new downYZM(listUser.get(0)).start();
				
			}
			
		}
		
		public class downYZM extends Thread {
			User u_0;
			
			public downYZM(User user){
				u_0 = user;
			}
			
			public void run(){
				try {
					System.out.println("fid="+fid+"&PID="+PID);
					bmp_report = u_0.sendReport(tieBaName, et_report_content.getText().toString(), fid, PID.substring(3, PID.length()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler_0.sendEmptyMessage(0);
			}
		}
		
		private Handler handler_0 =new Handler(){
			public void handleMessage(Message msg){

				super.handleMessage(msg);
				//处理UI
				if(bmp_report==null){
					new  AlertDialog.Builder(BowserTieZi.this).setTitle("Message").setMessage("Send success").setPositiveButton("OK", null).show();
					et_report_content.setText("");
					alertDialog.dismiss();
					new Thread(){
						@Override
						public void run(){
							try {
								doc = Jsoup.connect("http://tieba.baidu.com"+PID+"?pn="+maxPage)
										.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
							} catch (IOException e) {
							}
							handler.sendEmptyMessage(0);
						}
						
					}.start();
					return;
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(BowserTieZi.this);
				
				ImageView iiw = new ImageView(BowserTieZi.this);
				iiw.setImageBitmap(bmp_report);
				RelativeLayout.LayoutParams iiw_params = new RelativeLayout.LayoutParams(bmp_report.getWidth()*2,bmp_report.getHeight()*2);
				iiw_params.setMargins((MyTool.getScreenWidth(BowserTieZi.this)-30)/2-bmp_report.getWidth(), 0, 0, 0);
				
				iiw.setLayoutParams(iiw_params);
				
//				iiw.setLayoutParams(new RelativeLayout.LayoutParams(bmp_report.getWidth()*2,bmp_report.getHeight()*2));
//				iiw.setScaleX(1.5f);
//				iiw.setScaleY(1.5f);
				
				RelativeLayout Pop_layout = new RelativeLayout(BowserTieZi.this);
				//Pop_layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
				RelativeLayout.LayoutParams pop_params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,    
		                LayoutParams.FILL_PARENT);
				pop_params.setMargins(150, 40, 40, 150);
				Pop_layout.setLayoutParams(pop_params);
				
				//Pop_layout.setGravity(Gravity.CENTER);
				Pop_layout.addView(iiw);
				textv = new TextView(BowserTieZi.this);
				textv.setText("code : ");
				int width = ((bmp_report.getWidth()*2)/3);
				int height = ((bmp_report.getHeight()*2)/4);
				
				for(k=1;k<10;k++){
					Button bn = new Button(BowserTieZi.this);
					
					
					
					bn.setOnClickListener(new vcodeListener(k));
					
					
					
					bn.setWidth(width+10);
					bn.setHeight(height+10);
					//透明
					bn.getBackground().setAlpha(100);
					//bn.setBackgroundColor(Color.RED);
					int top = 0,left = 0;
					if(k%3==1){
						left = (MyTool.getScreenWidth(BowserTieZi.this)-30)/2-(width*3)/2;
					}else if(k%3==2){
						left = (MyTool.getScreenWidth(BowserTieZi.this)-30)/2-width/2;
					}else if(k%3==0){
						left = (MyTool.getScreenWidth(BowserTieZi.this)-30)/2+width/2;
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
				LinearLayout vcode_layout = new LinearLayout(BowserTieZi.this);
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
		
		private Handler handler_send_posts =new Handler(){
			@SuppressLint({ "HandlerLeak", "NewApi" })
			@Override
			//当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg){
			super.handleMessage(msg);
			if(send_posts_result)
				new  AlertDialog.Builder(BowserTieZi.this).setTitle("Message").setMessage("Send success").setPositiveButton("OK", null).show();
			else
				new  AlertDialog.Builder(BowserTieZi.this).setTitle("Message").setMessage("Send fail").setPositiveButton("OK", null).show();
			et_report_content.setText("");
			alertDialog.dismiss();
			
			new Thread(){
				@Override
				public void run(){
					try {
						doc = Jsoup.connect("http://tieba.baidu.com"+PID+"?pn="+maxPage)
								.userAgent("Mozilla/9.0 (compatible; MSIE 10.0; Windows NT 8.1; .NET CLR 2.0.50727)").get();
					} catch (IOException e) {
					}
					handler.sendEmptyMessage(0);
				}
				
			}.start();
			
			}
		};

}




