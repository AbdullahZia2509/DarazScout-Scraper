package com.darazscout.scraper.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    private String sellerId;
    private String name;
    private int followers;
    private int positiveRating;
    private int reviewCount;
    private Date dateJoined;
    private String sellerSize;
    private String mainCategory;
    private int chatResponseRate;
    private Date dateCreated;
    private Date dateUpdated;
}
