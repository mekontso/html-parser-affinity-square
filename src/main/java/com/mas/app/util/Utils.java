package com.mas.app.util;

import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@NoArgsConstructor
public class Utils {
    private static final Map<String, String> DOCTYPE_MAP = new HashMap<>();

    static {
        DOCTYPE_MAP.put("<!doctype html>", "HTML5");
        DOCTYPE_MAP.put("html 4.01", "HTML 4.01");
        DOCTYPE_MAP.put("html 4.0", "HTML 4.0");
        DOCTYPE_MAP.put("html 3.2", "HTML 3.2");
        DOCTYPE_MAP.put("html 2.0", "HTML 2.0");
    }

    /**
     * Extracts the HTML version from the doctype tag
     *
     * @param doctypeTag the doctype tag
     * @return the HTML version
     */
    public String extractHtmlVersion(String doctypeTag) {
        /**
         * Stream and filter map entries to get matching result
         */
        return DOCTYPE_MAP.entrySet()
                .stream()
                .filter(entry -> doctypeTag.toLowerCase().contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Unknown HTML version");
    }

    /**
     * Function will get each heading count from h1 to h6. It will return a map with heading and count
     *
     * @param document the document object
     * @return a map with headings and count
     */
    public Map<String, Integer> countHeadings(Document document) {
        Map<String, Integer> headingsCount = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            Elements headings = document.select("h" + i);
            headingsCount.put("h" + i, headings.size());
        }
        return headingsCount;
    }

    /**
     * Function will count the number of internal and external links in the document
     *
     * @param document the document object
     * @return a map with internal and external links count
     */
    public Map<String, Integer> countLinks(Document document) {
        Map<String, Integer> linksCount = new HashMap<>();
        Elements links = document.getElementsByTag("a");
        int external = 0;
        int internal = 0;
        for (Element link : links) {
            String href = link.attr("href");
            if (href.startsWith("http://") || href.startsWith("https://")) {
                external++;
            } else {
                internal++;
            }
        }
        linksCount.put("external", external);
        linksCount.put("internal", internal);
        return linksCount;
    }

    /**
     * Function will detect if a login form is present on the page.
     * Step 1. We get all the forms on the page.
     * Step 2. We try to parse only the forms with a post method. For security reasons, this will be the method used for a login form.
     * Step 3. We check if the form has a text field for username or email and a password field.
     *
     * @param document the document object
     * @return true if a login form is detected, false otherwise
     */
    public boolean detectLoginForm(Document document) {
        Elements forms = document.getElementsByTag("form");
        for (Element form : forms) {
            if ("post".equalsIgnoreCase(form.attr("method"))) {
                boolean hasTextField = form.select("input[type=text], input[type=email]").isEmpty();
                boolean hasPasswordField = form.select("input[type=password]").isEmpty();
                if (!hasTextField && !hasPasswordField) {
                    return true;
                }
            }
        }
        return false;
    }


}
