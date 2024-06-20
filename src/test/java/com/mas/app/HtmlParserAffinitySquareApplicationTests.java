package com.mas.app;

import com.mas.app.util.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HtmlParserAffinitySquareApplicationTests {

	@Autowired
	private Utils utils;

	@Test
	void contextLoads() {
		assertNotNull(utils);
	}

	@Test
	void testCode(){
	}

	@Test
	void testCountHeadings() {
		String html = "<html><head><title>Test</title></head>"
				+ "<body>"
				+ "<h1>Heading 1</h1>"
				+ "<h1>Another Heading 1</h1>"
				+ "<h2>Heading 2</h2>"
				+ "<h3>Heading 3</h3>"
				+ "<h3>Another Heading 3</h3>"
				+ "<h3>Yet Another Heading 3</h3>"
				+ "<h4>Heading 4</h4>"
				+ "<h5>Heading 5</h5>"
				+ "<h6>Heading 6</h6>"
				+ "<h6>Another Heading 6</h6>"
				+ "</body></html>";

		Document document = Jsoup.parse(html);
		Map<String, Integer> headingCount = utils.countHeadings(document);

		assertThat(headingCount).isNotNull();
		assertThat(headingCount.get("h1")).isEqualTo(2);
		assertThat(headingCount.get("h2")).isEqualTo(1);
		assertThat(headingCount.get("h3")).isEqualTo(3);
		assertThat(headingCount.get("h4")).isEqualTo(1);
		assertThat(headingCount.get("h5")).isEqualTo(1);
		assertThat(headingCount.get("h6")).isEqualTo(2);
	}



	@Test
	void testExtractHtmlVersion(){
		// case html 5
		String doctypeTag = "<!DOCTYPE html>";
		String result = utils.extractHtmlVersion(doctypeTag);
		assertEquals("HTML5", result);
		// case html 4.01
		doctypeTag = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
		result = utils.extractHtmlVersion(doctypeTag);
		assertEquals("HTML 4.01", result);
		// case html 4.0
		doctypeTag = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
		result = utils.extractHtmlVersion(doctypeTag);
		assertEquals("HTML 4.0", result);
		// case html 3.2
		doctypeTag = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
		result = utils.extractHtmlVersion(doctypeTag);
		assertEquals("HTML 3.2", result);
		// cas html 2.0
		doctypeTag = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">";
		result = utils.extractHtmlVersion(doctypeTag);
		assertEquals("HTML 2.0", result);
		// case unknown
		doctypeTag = "kasjhsad";
		result = utils.extractHtmlVersion(doctypeTag);
		assertEquals("Unknown HTML version", result);
	}

}
