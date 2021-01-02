package com.darazscout.scraper.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "category")
public class Category {
    @Id
    private String categoryId;
    private String categoryName;
    private String categoryLink;
    private List<Category> categoryList = new ArrayList<>();
}
