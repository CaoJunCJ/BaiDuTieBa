package com.cerosoft.tieba;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.http.AndroidHttpClient;
import android.util.Log;

@SuppressWarnings("serial")
public class User implements Serializable{

	/**
	 * @param args
	 */
	String captcha_vcode_str;
	
	public User(String userName,String passWord){
		setInfo(userName,passWord);
	}
	
	private void setInfo(String userName,String passWord){
		TieBaUtilTool.setUserInfo(userName,passWord);
	}
	
	public static void main(String[] args) {
		

	}
	
	public boolean loginToBaidu() throws Exception{
		TieBaUtilTool.doGet("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=false",null);
		InputStream in = TieBaUtilTool.doGet("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=false",TieBaUtilTool.getCookie(TieBaUtilTool.list));
		String Login_Token = TieBaUtilTool.getToken(TieBaUtilTool.inToString(in));
		String data = TieBaUtilTool.getLoginData(Login_Token);
		InputStream in1 = TieBaUtilTool.doPost("https://passport.baidu.com/v2/api/?login",TieBaUtilTool.getCookie(TieBaUtilTool.list),data,true);
		String loginCheckString = TieBaUtilTool.inToString(in1);
		boolean login = TieBaUtilTool.checkLogin(loginCheckString);
		return login;
	}
	
	//Post a Topic
	public Bitmap postTopic(String TieBaName,String title,String content,String fid) throws Exception{
		InputStream in2 = TieBaUtilTool.doPost("http://tieba.baidu.com/f/commit/thread/add",TieBaUtilTool.getCookie(TieBaUtilTool.list),TieBaUtilTool.getPostTopicData(TieBaName,title,content,fid),false);
		String YZMJson = TieBaUtilTool.inToString(in2);
		System.out.println(YZMJson);
		JSONObject jsonObj = new JSONObject(YZMJson);
		String no = jsonObj.getString("no");
		if(no.equals("0")){
			return null;
		}
		JSONObject data = (JSONObject) jsonObj.get("data");
		JSONObject vcode = (JSONObject) data.get("vcode");
		captcha_vcode_str = vcode.getString("captcha_vcode_str");
		System.out.println("captcha_vcode_str = " + captcha_vcode_str);
		//InputStream in3 = TieBaUtilTool.doGet("http://tieba.baidu.com/cgi-bin/genimg?"+captcha_vcode_str+"&tag=pc&t=0.5583130894228816",TieBaUtilTool.getCookie(TieBaUtilTool.list));
		Bitmap mBitmap = downloadBitmap("http://tieba.baidu.com/cgi-bin/genimg?"+captcha_vcode_str+"&tag=pc&t=0.5583130894228816");
		return mBitmap;
		/*
		int BUFFER_SIZE = 4096;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		BufferedInputStream bis = new BufferedInputStream(in3);
		FileOutputStream fos = new FileOutputStream("/sdcard/tieba/YNM.jpg");
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);
		fos.close();
		bis.close();
		*/
		
		
		//验证码的url :http://tieba.baidu.com/cgi-bin/genimg? + vcodestr + &tag=pc&t=0.5583130894228816
		
		//检查验证码 的 url:http://tieba.baidu.com/f/commit/commonapi/checkVcode
		//request data :
		//captcha_vcode_str:0013704840300276EEAD395F0227AB82D39150BEDB2DDE3C09AB73ABA7CA9CAFBCDF6F339EDE03580F0541800CCF1BA7AAA4557D1299D52FAFA9653CFBD89EE00484A4F29CA671558D7E3FE7B85140DD81E00A454DC107067DE71536AC69481B151640F434EF08871148944E34948C0E854A59B391E1E42B84FD64A7DBAB8ABD555F1874CE7FBE61BE496DA42353E56AE2C2905F2DF58F78B93196A8D6F94794DE0BBD24DBB7E7A731A12FA16D6E172CB75C2ED66FCB5D836BBDE7EC1DE6972C486745DCA4D1D1AD1B24A838CFF8ADEA426A7FF92182F1841751238D3133E4AEC35A1338379A94469C29669D3F1BE6C6F19FA9352009713476C71BA49AA7B525A12A7728BE4E7B4716F3F81DF6F202B343B7B401D2A4408B56206A9B7C90B27041EADCA7E4C67C03
		//captcha_code_type:4
		//captcha_input_str:00010000000200000000000100020001
		//fid:693735
		//成功 data:{"anti_valve_err_no":0}
		//再次发送帖子的url:http://tieba.baidu.com/f/commit/thread/add
		//request data 多出vcode:00010000000200000000000100020001和vcode_md5,vcode_md5就是vcodestr
		
		//{"no":40,"error":"","data":{"autoMsg":"","fid":693735,"fname":"java","tid":0,"is_login":1,"content":"","vcode":{"need_vcode":1,"str_reason":"\u60a8\u8fd8\u6ca1\u6709\u559c\u6b22\u672c\u5427\uff0c\u8bf7\u8f93\u5165\u9a8c\u8bc1\u7801\u5b8c\u6210\u53d1\u8d34","captcha_vcode_str":"00137059072402763BB568B7C9E7B5BFF522ECCE44FAC8E8D008FAD2D0963905214CB7AD2D08272E480917261631FE0EF1A5041303776E04C880F379882A6C45B1253FD32F6B7AED98C5279DA8C696C550035EC75D0F2C62FF6CC77C1CD1591D2F6B8C710FFF68E9CB944DEC341A252934B255DCBEF2A252A716FECB6839364C1E2460FB0EC7510DDE8C890849CE5EA11F3593F79598020EAAE585EDD66086DAD1992274ABFB7AC8742A960ED7B81D726DEF074BBD577E298BDF24F1E83D6C0C320433CDD255C354E38A5F7BC910E5000807F3CC97EF47CD45B70E8E8F9602702A555147F49F3C59FCE0FA0B170C338F6E7284AC6AA10A3B42E1419A280546F0E61A82FC8A584EFBF03A6641A96CFCA5F5E045625F56DCDBF52E094C2901E273E1715A85FB265EF4","captcha_code_type":4}}}
	}
	
	
	public boolean haveYZM(String captcha_input_str,String title,String content,String TieBaName,String fid) throws Exception{
		return TieBaUtilTool.checkYZM(captcha_vcode_str,captcha_input_str,title,content,TieBaName,fid);
	}
	
	public static void saveFile(Bitmap bm, String fileName) throws IOException {  
        File myCaptureFile = new File(fileName);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
        bos.flush();  
        bos.close();  
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
	
	
	
	public Bitmap sendReport(String TieBaName,String content,String fid,String tid) throws Exception{
		InputStream in2 = TieBaUtilTool.doPost("http://tieba.baidu.com/f/commit/post/add",TieBaUtilTool.getCookie(TieBaUtilTool.list),TieBaUtilTool.getReportData(TieBaName,content,fid,tid),false);
		String YZMJson = TieBaUtilTool.inToString(in2);
		System.out.println(YZMJson);
		JSONObject jsonObj = new JSONObject(YZMJson);
		String no = jsonObj.getString("no");
		if(no.equals("0")){
			return null;
		}
		JSONObject data = (JSONObject) jsonObj.get("data");
		JSONObject vcode = (JSONObject) data.get("vcode");
		captcha_vcode_str = vcode.getString("captcha_vcode_str");
		System.out.println("captcha_vcode_str = " + captcha_vcode_str);
		//InputStream in3 = TieBaUtilTool.doGet("http://tieba.baidu.com/cgi-bin/genimg?"+captcha_vcode_str+"&tag=pc&t=0.5583130894228816",TieBaUtilTool.getCookie(TieBaUtilTool.list));
		Bitmap mBitmap = downloadBitmap("http://tieba.baidu.com/cgi-bin/genimg?"+captcha_vcode_str+"&tag=pc&t=0.5583130894228816");
		return mBitmap;
	}
	
	public boolean send_report_haveYZM(String captcha_input_str,String content,String TieBaName,String fid,String tid) throws Exception{
		return TieBaUtilTool.send_report_checkYZM(captcha_vcode_str,captcha_input_str,content,TieBaName,fid,tid);
	}
	
	

}
