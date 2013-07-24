package com.cerosoft.tieba;


import java.io.File;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Jsop {
	public static void main(String[] arr) throws IOException{
//		File input = new File("c:/tieba.html");
//		Document doc = Jsoup.parse(input, "GBK");
//		System.out.println(doc.title());
//		Elements content = doc.getElementsByClass("j_th_tit");
//		for (Element link : content) {
//		  String linkHref = link.attr("title");
//		  String a = link.ownText();
//		 // System.out.println(a);
//		  String linkText = link.text();
//		  //System.out.println(linkHref);threadlist_reply_date j_reply_data
//		  //System.out.println(linkText);tb_icon_author threadlist_abs threadlist_abs_onlyline
//		}
//		Elements author = doc.getElementsByClass("threadlist_author");
//		int i =1;
//		int i1=2;
//		int i2=1;
//		for(Element a : author){
//			Elements es = a.getElementsByTag("a");
//			Element e = es.get(0);
//			if(i%2!=0){
//			System.out.println("作者："+e.ownText());
//			i++;
//			}else{
//				System.out.println("最后回复人："+e.ownText());
//				i++;
//			}
//			Elements es1 = a.getElementsByTag("span");
//			if(es1.size()>1){
//				System.out.println("最后回复时间："+es1.get(1).ownText()+"----第"+i1+"帖子"+'\n'); 
//				i1++;
//			}
//		}
//		Elements title = doc.getElementsByClass("threadlist_text");
//		for(Element a : title){
//			Elements es = a.children();
//			Element e = es.get(0);
//			//System.out.println(e.ownText()); threadlist_author 
//		}
//		Elements courr = doc.getElementsByClass("j_thread_list");
//		for(Element a : courr){
//			Elements es = a.getElementsByTag("div");
//			if(es.size()>1)
//			System.out.println(es.get(1).attr("title")+"第"+i2+"个"); 
//			i2++;
//		}
//		System.out.println(i1+"---"+i2);
		func();
	}
	
	@SuppressWarnings("unused")
	public static void func() throws IOException{
		File input = new File("c:/huifu.html");
		Document doc = Jsoup.parse(input, "GBK");
		System.out.println(doc.title());
		Elements all = doc.getElementsByClass("l_post");
		System.out.println(all.size());
		for (Element a : all) {
			Elements  et= a.getElementsByTag("div");
			Elements  et1= a.getElementsByTag("a");
			Elements  et2= a.getElementsByTag("span");
			
				for(Element t : et){
					if(t.attr("class").equals("d_post_content")){
						System.out.println("内容："+t.ownText());
					}

						
				}
				for(Element t : et2){
						
				}
				
				for(Element t : et1){
					if(t.attr("class").equals("p_author_name")){
						System.out.println("作者名字："+t.ownText());
					}
						
				}
				System.out.println("-----------------------------------------------------------");
			}
		
	}
}
