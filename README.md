# HTML Parser Affinity Square

HTML Parser Affinity Square is a web application built with Spring Boot that allows users to analyze HTML web pages. This application leverages the power of various Spring Boot dependencies to provide a robust and scalable solution.

## Table of Contents
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Built With](#built-with)
- [Assumptions and Design Decisions](#assumptions-and-design-decisions)
- [Known Constraints and Limitations](#known-constraints-and-limitations)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
- You can test online [HTML PARSER](http://html-parser.kontsotech.com/) 

## Prerequisites

Before you begin, ensure you have met the following requirements:
- You have installed [Java 21 or higher](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).
- You have installed [Maven](https://maven.apache.org/install.html).
- You have a basic understanding of Spring Boot and web development.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/html-parser-affinity-square.git
2. Change into the project directory:
   ```bash
   cd html-parser-affinity-square
3. Open the project in your favorite IDE.


## Running the Application

1. Build the project using Maven:
   ```bash
   mvn clean install
2. Run the application:
   ```bash
   mvn spring-boot:run
3. You can run tests using the following command:
   ```bash
   mvn test

## Usage

1. When app is running, open your web browser and navigate to http://localhost:8080.
2. Enter a valid HTTP(s) URL in the text field.
3. Click the submit button to analyze the HTML page.
4. The results will be displayed below the form in a tabular format, showing:

   *  HTML version of the document
   *  Page title
   *  Number of headings grouped by heading level
   *  Number of hypermedia links grouped into internal and external links
   *  Presence of a login form
   *  Validation results of all links

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for building production-ready applications
- [Jsoup](https://jsoup.org/) - Java library for working with real-world HTML
- [Lombok](https://projectlombok.org/) - Java library for minimizing boilerplate code
- [Thymeleaf](https://www.thymeleaf.org/) - Modern server-side Java template engine for web and standalone environments

## Assumptions and Design Decisions

- **HTML Version Detection**: The HTML version is determined by the doctype tag present in the HTML document. The `Utils` class maps common doctype declarations to their respective HTML versions.
- **Login Form Detection**: Login forms are identified based on the presence of a form with a POST method containing both a text/email input field and a password input field. This logic covers most common login form structures.
- **Link Validation**: To ensure efficient validation of links, the application uses a thread pool to parallelize HTTP requests. This approach minimizes the overall validation time.
- **Error Handling**: If the URL is not reachable, a message is displayed indicating the issue. The application captures and displays relevant error messages for unreachable links.

## Known Constraints and Limitations

- **Login Form Detection**: The current logic may not detect all possible variations of login forms, especially those with unconventional structures or labels in different languages.
- **Performance**: While the application uses a thread pool for link validation, very large numbers of links may still result in noticeable delays.
- **HTML Version Detection**: The detection is based on a predefined map of doctype declarations, which may not cover all possible variants.
