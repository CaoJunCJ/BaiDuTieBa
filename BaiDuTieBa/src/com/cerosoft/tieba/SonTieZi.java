package com.cerosoft.tieba;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SonTieZi implements Serializable{
	
	public String tieZiContent;
	public String title;
	public String date;
	public String author;
	public int allPage;





	






	public String sign = "empty";
	public String author_image = "";
	public ArrayList<String> contentImage = new ArrayList<String>(); 
	
	public ArrayList<zifuhui> ziHuiFu = new ArrayList<zifuhui>(); 
	public ArrayList<String> replyer = new ArrayList<String>(); 
	public ArrayList<String> replyDate = new ArrayList<String>(); 
	public ArrayList<String> replyContent = new ArrayList<String>(); 
	public ArrayList<String> replyAuthorImage = new ArrayList<String>(); 
	
	public int getAllPage() {
		return allPage;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}
	
	public String getTitle() {
		return title;
	}

	public ArrayList<String> getContentImage() {
		return contentImage;
	}


	public String getAuthor_image() {
		return author_image;
	}

	public void addContentImage(String s){
		contentImage.add(s);
	}


	public void setAuthor_image(String author_image) {
		this.author_image = author_image;
	}




	public String getSign() {
		return sign;
	}




	public void setSign(String sign) {
		this.sign = sign;
	}




	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getTieZiContent() {
		return tieZiContent;
	}




	public void setTieZiContent(String tieZiContent) {
		this.tieZiContent = tieZiContent;
	}




	public String getDate() {
		return date;
	}




	public void setDate(String date) {
		this.date = date;
	}




	public String getAuthor() {
		return author;
	}




	public void setAuthor(String author) {
		this.author = author;
	}

	public void addZiHuiFu(String ziAuthor,String ziDate,String ziContent) {
		if(ziAuthor!=null&&ziDate!=null&&ziContent!=null)
			ziHuiFu.add(new zifuhui(ziAuthor,ziDate,ziContent));
	}


	



	public class zifuhui{
		public String ziAuthor;
		public String ziDate;
		public String ziContent;
		
		public zifuhui(String ziAuthor,String ziDate,String ziContent){
			setZiAuthor(ziAuthor);
			setZiDate(ziDate);
			setZiContent(ziContent);
		}
		
		public String getZiAuthor() {
			return ziAuthor;
		}
		public void setZiAuthor(String ziAuthor) {
			this.ziAuthor = ziAuthor;
		}
		public String getZiDate() {
			return ziDate;
		}
		public void setZiDate(String ziDate) {
			this.ziDate = ziDate;
		}
		public String getZiContent() {
			return ziContent;
		}
		public void setZiContent(String ziContent) {
			this.ziContent = ziContent;
		}
		
	}
}
