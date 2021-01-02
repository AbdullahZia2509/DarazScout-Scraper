package com.darazscout.scraper.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "scraped_page")
public class ScrapedPage {
    @Id
    private String scrapedPageId;
    private String pageURL;
    private List<String> productURLs;

    @DBRef
    private ScraperExecution scraperExecution;
}
