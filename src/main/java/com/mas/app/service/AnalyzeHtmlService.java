package com.mas.app.service;

import com.mas.app.model.DocumentInfo;

public interface AnalyzeHtmlService {
    /**
     * This method is used to analyze the HTML content of the given URL
     * @param url URL to be analyzed
     * @return DocumentInfo object containing the analysis result
     */
    public DocumentInfo analyzeHtml(String url);
}
