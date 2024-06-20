package com.mas.app;

import com.mas.app.util.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	void extractHtmlVersionTest(){
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
