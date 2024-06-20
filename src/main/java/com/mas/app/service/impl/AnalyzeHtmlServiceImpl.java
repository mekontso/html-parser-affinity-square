package com.mas.app.service.impl;

import com.mas.app.model.DocumentInfo;
import com.mas.app.service.AnalyzeHtmlService;
import com.mas.app.util.Utils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

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
                // get the !doctype tag as child node 0 and extract the version
                documentInfo.setHtmlVersion(
                        utils.extractHtmlVersion(document.childNode(0).toString())
                );
                // set title if any
                var title = document.title();
                if (title.isEmpty()) {
                    documentInfo.setTitle("No title found");
                } else {
                    documentInfo.setTitle(title);
                }
                documentInfo.setUrl(document.location());

                documentInfo.setHeadings( utils.countHeadings(document));
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
