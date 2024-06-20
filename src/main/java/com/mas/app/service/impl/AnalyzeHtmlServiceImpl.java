package com.mas.app.service.impl;

import com.mas.app.model.DocumentInfo;
import com.mas.app.service.AnalyzeHtmlService;
import com.mas.app.util.Utils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyzeHtmlServiceImpl implements AnalyzeHtmlService {

    private final Utils utils;

    @Override
    public DocumentInfo analyzeHtml(String url) {
        DocumentInfo documentInfo = new DocumentInfo();
        try {
            // send request get document page
            Document document = Jsoup.connect(url).get();
            //check if connection is a success
            if (document.connection().response().statusCode() == 200) {
                documentInfo.setReachable(true);
                System.out.println("Log doctype of page");
                System.out.println(document.getElementsByTag("!doctype").html());
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
