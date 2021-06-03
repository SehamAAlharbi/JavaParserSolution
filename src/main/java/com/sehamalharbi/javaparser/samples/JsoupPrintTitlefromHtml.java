package com.sehamalharbi.javaparser.samples;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupPrintTitlefromHtml {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new File("register.html"), "utf-8");
		String title = doc.title();
		System.out.println("title is: " + title);
	}
}
