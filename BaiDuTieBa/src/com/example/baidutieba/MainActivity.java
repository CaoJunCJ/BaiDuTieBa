package com.example.baidutieba;


import java.util.ArrayList;

import com.cerosoft.tieba.User;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	
	private EditText userName;
	private EditText passWord;
	private Button Login;
	private boolean isLogin;
	private ArrayList<User> list = new ArrayList<User>();
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);            
        //全屏            
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,                
                                    WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_login);
		this.setTitle("百度贴吧");
		userName = (EditText) findViewById(R.id.et_qqNum);
		passWord = (EditText) findViewById(R.id.et_qqPwd);
		userName.setText("airgetbook");
		passWord.setText("11223344ll");
		Login = (Button) findViewById(R.id.btn_login);
		Login.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		 
		 final User user = new User(userName.getText().toString(),passWord.getText().toString());
			
			new Thread(){
				@Override
				public void run(){
				//你要执行的方法
				//执行完毕后给handler发送一个空消息
					try {
						isLogin = user.loginToBaidu();
						list.add(user);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				handler.sendEmptyMessage(0);
				}
				}.start();
		 
		    }
		});
		
		
	
	}
	
	//定义Handler对象
	private Handler handler =new Handler(){
	@SuppressLint("HandlerLeak")
	@Override
	//当有消息发送出来的时候就执行Handler的这个方法
	public void handleMessage(Message msg){
	super.handleMessage(msg);
	//处理UI
	if(isLogin){
		//new  AlertDialog.Builder(MainActivity.this).setTitle("Message").setMessage("Success.").setPositiveButton("OK", null).show();
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, SelectTieBa.class);
		Bundle bl = new Bundle();
		bl.putSerializable("user_0",list);
		
		intent.putExtras(bl);
		
		startActivityForResult(intent, 0);
	}else{
		new  AlertDialog.Builder(MainActivity.this).setTitle("Message").setMessage("Error username or password.").setPositiveButton("OK", null).show();
	}
	}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
