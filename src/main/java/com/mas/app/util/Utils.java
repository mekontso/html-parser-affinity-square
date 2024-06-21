package com.mas.app.util;

import com.mas.app.model.Link;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private static final int TIMEOUT = 5000;

    /**
     * Extracts the HTML version from the doctype tag
     *
     * @param doctypeTag the doctype tag
     * @return the HTML version
     */
    public String extractHtmlVersion(String doctypeTag) {
        //Stream and filter map entries to get matching result
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


    /**
     * Function will check if the URL is reachable by sending a simple ping request to the URL
     * @param url the URL to check
     * @return a Link object with the information after getting a response
     */
    public Link pingRequest(String url) {
        Link link = new Link();
        link.setUrl(url);
        try{
            HttpURLConnection connection = (HttpURLConnection) new java.net.URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setInstanceFollowRedirects(true);
            var responseCode = connection.getResponseCode();
            link.setStatusCode(responseCode);
            if (responseCode == 200) {
                link.setReachable(true);
                link.setMessage("Success connection");
            } else {
                link.setReachable(false);
                link.setMessage("Failed with response message: " + connection.getResponseMessage());
            }
        } catch (Exception e) {
            link.setReachable(false);
            link.setMessage(e.getMessage());
        }
        return link;
    }

    /**
     * Function will validate all the links in the document
     * Step 1. Get all the links in the document
     * Step 2. Check if the link is an external link
     * Step 3. Create multiple threads to make multiple request at the same time
     * Step 4. Send a ping request to each link
     * Step 5. Get the response and set the status code and message
     * Step 6. Return a list of Link objects with the information
     * @param document the document object
     * @return a list of Link objects with the information after getting a response
     */
    public List<Link> validateLinks(Document document) {
        Elements links = document.select("a[href]");
        List<Link> linkList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(35);

        List<Future<Link>> futures = new ArrayList<>();

        for (Element link : links) {
            String url = link.attr("href");
            if (url.startsWith("http://") || url.startsWith("https://")) {
                Future<Link> future = executorService.submit(() -> pingRequest(url));
                futures.add(future);
            }
        }

        for(Future<Link> future : futures) {
            try {
                linkList.add(future.get());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        executorService.shutdown();
        return linkList;
    }
}
