package com.cerosoft.tieba;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TieBaObj implements Serializable{
	private String reply;
	private String title;
	private String author;
	private String time;
	private String content;
	private String lastReply;
	private String pID;
	private String fid;
	
	

	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getpID() {
		return pID;
	}
	public void setpID(String pID) {
		this.pID = pID;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLastReply() {
		return lastReply;
	}
	public void setLastReply(String lastReply) {
		this.lastReply = lastReply;
	}
	
}
