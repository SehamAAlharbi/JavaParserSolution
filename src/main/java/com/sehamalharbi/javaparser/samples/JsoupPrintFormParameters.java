package com.sehamalharbi.javaparser.samples;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupPrintFormParameters {
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.parse(new File("register.html"), "utf-8");
		Element loginform = doc.getElementById("registerform");

		Elements inputElements = loginform.getElementsByTag("input");
		for (Element inputElement : inputElements) {
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			System.out.println("Param name: " + key + " \nParam value: " + value);
		}
	}
}
