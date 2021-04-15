package com.darazscout.scraper.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection = "productlinks")
public class ProductLinks {
    private String link;
}
