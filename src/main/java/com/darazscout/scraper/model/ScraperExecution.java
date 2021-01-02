package com.darazscout.scraper.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "scraper_execution")
public class ScraperExecution {
    @Id
    private String exeId;
    private Date start_time;
    private Date end_time;
    private String type;
    private String executedBy;
}
