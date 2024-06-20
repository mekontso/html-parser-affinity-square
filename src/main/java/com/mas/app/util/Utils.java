package com.mas.app.util;

import lombok.NoArgsConstructor;
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
}
