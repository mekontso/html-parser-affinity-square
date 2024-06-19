package com.mas.app.service.impl;

import com.mas.app.model.DocumentInfo;
import com.mas.app.service.AnalyzeHtmlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyzeHtmlServiceImpl implements AnalyzeHtmlService {
    @Override
    public DocumentInfo analyzeHtml(String url) {
        DocumentInfo documentInfo = new DocumentInfo();
        try {
            // send request get document page
            Document document = Jsoup.connect(url).get();
            //check is
            if (document.connection().response().statusCode() == 200) {
                documentInfo.setReachable(true);
                documentInfo.setUrl(document.location());
                documentInfo.setTitle(document.title());
                documentInfo.setHeadings(List.of(
                        new HashMap<>(Map.of("h1", 1)),
                        new HashMap<>(Map.of("h2", 3))
                ));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            documentInfo.setMessage("The URL is not reachable, please check the URL and try again.");
            documentInfo.setReachable(false);
        }
        documentInfo.setParsed(true);
        return documentInfo;
    }
}
