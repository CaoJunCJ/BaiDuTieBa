package com.cerosoft.tieba;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


import org.json.JSONObject;

import android.graphics.Bitmap;

public class TieBaUtilTool {
	
	public static String userName = "airgetbook";
	public static String passWord = "11223344ll";
	public static String Login_Token = null;
	public static ArrayList<String> list = new ArrayList<String>();
	public final static int BUFFER_SIZE = 4080;  

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if(loginToBaidu()){
			//postTopic();
		}

	}
	
	public static void setUserInfo(String username,String password){
		userName = username;
		passWord = password;
	}
	
	/**
	 * get
	 * u : url
	 * cookie : cookie
	 * @return InputStream
	 */
	public static InputStream doGet(String u,String cookie) throws Exception{
		HttpURLConnection con = null;
		URL url = new URL(u);
		con = (HttpURLConnection) url.openConnection();
		if(cookie!=null)
			con.setRequestProperty("Cookie", cookie);
		
		
		Map<String, List<String>> map = con.getHeaderFields();
		
		if(map!=null){
				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					   Object key = entry.getKey();
					   if(key!=null&&((String)key).equals("Set-Cookie")){
						   List<String> l1 = entry.getValue();
						   for(String s : l1){
							   list.add(s);
						   }
					   
					  }
				}
			}
			
		
		
		//con.connect();
		InputStream in = con.getInputStream(); 
		//con.disconnect();
		return in;
		
		
	}
	
	
	/**
	 * post
	 * u : url
	 * cookie : cookie
	 * isHttps : http/https
	 * @return InputStream
	 */
	public static InputStream doPost(String u,String cookie,String date,boolean isHttps) throws Exception{
		URL url = new URL(u);
		if(isHttps){
			HttpsURLConnection conn = null;
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			if(cookie!=null)
				conn.setRequestProperty("Cookie", cookie);
			OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());
			out.write(date); 
			out.flush();  
		    out.close();
		    Map<String, List<String>> map = conn.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				   Object key = entry.getKey();
				   if(key!=null&&((String)key).equals("Set-Cookie")){
					   List<String> l1 = entry.getValue();
					   for(String s : l1){
						   list.add(s);
					   }
				   
				  }
			}
		    InputStream in = conn.getInputStream(); 
		    //conn.disconnect();
			return in;
		}else{
			HttpURLConnection conn = null;
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			if(cookie!=null)
				conn.setRequestProperty("Cookie", cookie);
			OutputStreamWriter out=new OutputStreamWriter(conn.getOutputStream());
			out.write(date); 
			out.flush();  
		    out.close();
		    Map<String, List<String>> map = conn.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				   Object key = entry.getKey();
				   if(key!=null&&((String)key).equals("Set-Cookie")){
					   List<String> l1 = entry.getValue();
					   for(String s : l1){
						   list.add(s);
					   }
				   
				  }
			}
		    InputStream in = conn.getInputStream(); 
		    //conn.disconnect();
			return in;
		}
		
		
	}
	
	public static boolean checkLogin(String str){
		String checkString = "99";
		String[] check = str.split("&");
		for(String s : check){
			String[] error = s.split("=");
				if(error[0].equals("error")){
					checkString = error[1];
					checkString = checkString.substring(0, 1);
					break;
				}
		}
		if(checkString.equals("0")){
			System.out.println("Success!");
			return true;
		}else{
			
			System.out.println("The userName or passWord has error!");
			return false;
		}
	}
	
	public static String getToken(String s){
		String[] temp =  s.split(";");
		String[] token = new String[5];
		for(int i=0;i<5;i++){
			token[i] = temp[i];
		}
		for(String str : token){
			String[] name = str.split("=");
			for(String str1 : name){
				if(str1.trim().equals("bdPass.api.params.login_token")){
					String newStr = name[1].replaceAll("'","");
					return newStr;
				}
			}
		}
		return null;
	}
	
	public static String getCookie(ArrayList<String> lt){
		StringBuffer sb = new StringBuffer();
		for(String s : lt){
			sb.append(s).append(";");
		}
		return sb.toString();
	}
	
	//login to baidu
	public static boolean loginToBaidu() throws Exception{
		doGet("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=false",null).close();
		InputStream in = doGet("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=false",getCookie(list));
		Login_Token = getToken(inToString(in));
		String data = getLoginData(Login_Token);
		InputStream in1 = doPost("https://passport.baidu.com/v2/api/?login",getCookie(list),data,true);
		String loginCheckString = inToString(in1);
		boolean login = checkLogin(loginCheckString);
		return login;
	}
	
	//Post a Topic
	public static void postTopic(String title,String content,String TieBaName,String fid) throws Exception{
		InputStream in2 = doPost("http://tieba.baidu.com/f/commit/thread/add",getCookie(list),getPostTopicData(TieBaName,title,content,fid),false);
		String YZMJson = inToString(in2);
		System.out.println(YZMJson);
		JSONObject jsonObj = new JSONObject(YZMJson);
		JSONObject data = (JSONObject) jsonObj.get("data");
		JSONObject vcode = (JSONObject) data.get("vcode");
		String captcha_vcode_str = vcode.getString("captcha_vcode_str");
		System.out.println("captcha_vcode_str = " + captcha_vcode_str);
		InputStream in3 = doGet("http://tieba.baidu.com/cgi-bin/genimg?"+captcha_vcode_str+"&tag=pc&t=0.5583130894228816",getCookie(list));
		int BUFFER_SIZE = 4096;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		BufferedInputStream bis = new BufferedInputStream(in3);
		FileOutputStream fos = new FileOutputStream("c:/yzm.jpg");
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);
		fos.close();
		bis.close();
		
		
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
	
	public static boolean checkYZM(String captcha_vcode_str,String captcha_input_str,String title,String content,String TieBaName,String fid) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("captcha_vcode_str="+captcha_vcode_str);
		sb.append("&captcha_code_type=4");
		sb.append("&captcha_input_str="+captcha_input_str);
		sb.append("&fid="+fid);
		InputStream in = doPost("http://tieba.baidu.com/f/commit/commonapi/checkVcode",getCookie(list),sb.toString(),false);
		String result = inToString(in);
		JSONObject json = new JSONObject(result);
		String anti_valve_err_no = json.getString("anti_valve_err_no");
		String sendResult;
		if(anti_valve_err_no.equals("0")){
			StringBuffer sb1 = new StringBuffer();
			sb1.append(getPostTopicData(TieBaName,title,content,fid));
			sb1.append("&vcode="+captcha_input_str);
			sb1.append("&vcode_md5="+captcha_vcode_str);
			InputStream in2 = doPost("http://tieba.baidu.com/f/commit/thread/add",getCookie(list),sb1.toString(),false);
			sendResult = inToString(in2);
			
		}else
			return false;
		JSONObject json1 = new JSONObject(sendResult);
		String no = json1.getString("no");
		if(no.equals("0"))
			return true;
		else
			return false;
	}
	
	public static boolean send_report_checkYZM(String captcha_vcode_str,String captcha_input_str,String content,String TieBaName,String fid,String tid) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("captcha_vcode_str="+captcha_vcode_str);
		sb.append("&captcha_code_type=4");
		sb.append("&captcha_input_str="+captcha_input_str);
		sb.append("&fid="+fid);
		InputStream in = doPost("http://tieba.baidu.com/f/commit/commonapi/checkVcode",getCookie(list),sb.toString(),false);
		String result = inToString(in);
		JSONObject json = new JSONObject(result);
		String anti_valve_err_no = json.getString("anti_valve_err_no");
		String sendResult;
		if(anti_valve_err_no.equals("0")){
			StringBuffer sb1 = new StringBuffer();
			sb1.append(getReportData(TieBaName,content,fid,tid));
			sb1.append("&vcode="+captcha_input_str);
			sb1.append("&vcode_md5="+captcha_vcode_str);
			InputStream in2 = doPost("http://tieba.baidu.com/f/commit/post/add",getCookie(list),sb1.toString(),false);
			sendResult = inToString(in2);
			
		}else
			return false;
		JSONObject json1 = new JSONObject(sendResult);
		String no = json1.getString("no");
		if(no.equals("0"))
			return true;
		else
			return false;
	}
	
	
	
	public static String getPostTopicData(String tieba,String title,String content,String fid) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("kw=").append(tieba).append("&");
		sb.append("ie=").append("utf-8").append("&");
		sb.append("rich_text=").append("1").append("&");
		sb.append("floor_num=").append("0").append("&");
		sb.append("fid=").append(fid).append("&");
		sb.append("tid=").append("0").append("&");
		sb.append("mouse_pwd=").append("88,81,87,77,85,89,84,87,104,80,77,81,77,80,77,81,77,80,77,81,77,80,77,81,77,80,77,81,104,83,83,86,89,86,84,104,80,85,85,81,77,88,81,81,13704126157971").append("&");
		sb.append("mouse_pwd_t=").append("1370412615797").append("&");
		sb.append("mouse_pwd_isclick=").append("1").append("&");
		sb.append("prefix=").append("").append("&");
		sb.append("title=").append(title).append("&");
		sb.append("content=").append(content).append("&");
		sb.append("anonymous=").append("0").append("&");
		sb.append("tbs=").append(getTbs()).append("&");
		sb.append("tag=").append("11").append("&");
		sb.append("new_vcode=").append("1");
		return sb.toString();
	}

	public static String getLoginData(String Token){
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(userName).append("&");
		sb.append("password=").append(passWord).append("&");
		sb.append("token=").append(Token).append("&");
		sb.append("charset=").append("UTF-8").append("&");
		sb.append("callback=").append("parent.bd12Pass.api.login._postCallback").append("&");
		sb.append("index=").append("0").append("&");
		sb.append("isPhone=").append("false").append("&");
		sb.append("mem_pass=").append("on").append("&");
		sb.append("loginType=").append("1").append("&");
		sb.append("safeflg=").append("0").append("&");
		sb.append("staticpage=").append("https://passport.baidu.com/v2Jump.html").append("&");
		sb.append("tpl=").append("mn").append("&");
		sb.append("u=").append("http://www.baidu.com/").append("&");
		sb.append("verifycode=").append("");
		return sb.toString();
	}
	
	public static String inToString(InputStream in) throws Exception{
		 ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] data = new byte[BUFFER_SIZE];  
	        int count = -1;  
	        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
	            outStream.write(data, 0, count);  
	          
	        data = null;  
	        return new String(outStream.toByteArray()); 
	}
	
	public static String getTbs() throws Exception{
		InputStream in = doGet("http://tieba.baidu.com/dc/common/tbs?t=0.17514605234768638",getCookie(list));
		String html = new String(inToString(in));
		System.out.println(html);
		JSONObject jsonObj = new JSONObject(html);
		String tbs = jsonObj.getString("tbs");
		System.out.println(tbs);
		return tbs;
	}
	
	public static String getCaptchaString(int i){
		String captchaStr[] = new String[9];
		captchaStr[0] = "00000000";
		captchaStr[1] = "00010000";
		captchaStr[2] = "00020000";
		captchaStr[3] = "00000001";
		captchaStr[4] = "00010001";
		captchaStr[5] = "00020001";
		captchaStr[6] = "00000002";
		captchaStr[7] = "00010002";
		captchaStr[8] = "00020002";
		if(i>=0&&i<9){
			return captchaStr[i];
		}else{
			return null;
		}
	}
	
	public static String getReportData(String TieBaName,String content,String fid,String tid) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("kw=").append(TieBaName).append("&");
		sb.append("ie=").append("utf-8").append("&");
		sb.append("rich_text=").append("1").append("&");
		sb.append("floor_num=").append("1").append("&");
		sb.append("fid=").append(fid).append("&");
		sb.append("tid=").append(tid).append("&");
		sb.append("mouse_pwd=").append("	47,37,47,48,45,43,42,36,21,45,48,44,48,45,48,44,48,45,48,44,48,45,48,44,48,45,48,44,21,46,40,36,37,40,21,45,40,40,44,48,37,44,44,13746553109281").append("&");
		sb.append("mouse_pwd_t=").append("1374655310928").append("&");
		sb.append("mouse_pwd_isclick=").append("1").append("&");
		sb.append("lp_type=").append("0").append("&");
		sb.append("lp_sub_type=").append("0").append("&");
		sb.append("content=").append(content).append("&");
		sb.append("anonymous=").append("0").append("&");
		sb.append("tbs=").append(getTbs()).append("&");
		sb.append("tag=").append("11").append("&");
		sb.append("new_vcode=").append("1");
		return sb.toString();
	}
	

}
