package com.mas.app.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Link {
    private String url;
    private boolean reachable;
    private int statusCode;
    private String message;
}
