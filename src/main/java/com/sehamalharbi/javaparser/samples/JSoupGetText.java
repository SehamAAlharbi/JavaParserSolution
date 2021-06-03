package com.sehamalharbi.javaparser.samples;

import java.io.File;	
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupGetText {
	
	
	
	   public void getText () throws IOException{

	        String fileName = "register.html";
	        File myFile = new File(fileName);

	        Document doc = Jsoup.parse(myFile, "UTF-8");

	        System.out.println(doc.text());

	        System.out.println("---------------------------");
	        System.out.println(doc.body().text());

	        System.out.println("---------------------------");
	        Element e1 = doc.select("body>p").first();
	        System.out.println(e1.text());

	        System.out.println("---------------------------");
	        Element e2 = doc.select("body>ul").first();
	        System.out.println(e2.text());

	        System.out.println("---------------------------");
	        Elements lis = e2.children();
	        System.out.println(lis.first().text());
	        System.out.println(lis.last().text());
	    }
	   
}