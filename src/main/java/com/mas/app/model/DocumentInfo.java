package com.mas.app.model;

import java.util.HashMap;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DocumentInfo {
  @NotEmpty(message = "URL is required")
  @Pattern(regexp = "https?://.+", message = "Invalid URL format. Please use format. e.g. http://www.example.com or https://www.example.com")
  private String url;
  private String htmlVersion;
  private String title;
  private List<HashMap<String, Number>> headings;
}
