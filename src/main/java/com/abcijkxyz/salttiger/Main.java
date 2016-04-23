package com.abcijkxyz.salttiger;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static void main(String[] args) throws IOException {
		// var json=[];
		// jQuery("ul.car-list a[href]").each(function (i, e) { json.push(jQuery(e).attr('href')) });
		// JSON.stringify(json)
		// json.length //1573
		int timeout = 1000 * 30;
		String userAgent = "Mozilla/5.0 (ArchLinux Linux 3.16) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.0 Chrome/30.0.1599.101 Safari/537.36";
		Response res = Jsoup.connect("http://www.salttiger.com/archives/")
				// .data( )
				.method(Connection.Method.GET).timeout(timeout).userAgent(userAgent).execute();
		Document archives = res.parse();
		Map<String, String> cookies = res.cookies();
		Elements books = archives.select("ul.car-list a[href]");

		for (Element bookLink : books) {
			String href = bookLink.attr("href");
			String text = bookLink.text();
			System.out.println(href + "\t\t" + text);
			Document book = Jsoup.connect(href).method(Connection.Method.GET).cookies(cookies).timeout(timeout).userAgent(userAgent).get();
			// jQuery("#content .entry-content")
			Elements entrycontent = book.select("#content .entry-content");
			String content = entrycontent.text();
			System.out.println(content);

			Elements downloads = entrycontent.select("a[href]");
			if (downloads != null) {
				for (Element element : downloads) {
					String download = element.attr("href");
					String site = element.text();
					System.out.println(download + "\t\t" + site);

				}
			}
			Element cover = entrycontent.select("img[src]").first();
			if (cover != null) {
				String coversrc = cover.attr("src");
				System.out.println(coversrc);
			}
			System.out.println("--------------------------------------------------------------------------------------------------------\n");
			// test once
			// break;
		}
	}
}
