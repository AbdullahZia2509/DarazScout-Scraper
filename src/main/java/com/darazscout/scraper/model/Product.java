package com.darazscout.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String productId;
    private String title;
    private String brand;
    private String SKU;
    private double monthlyRevenue;
    private double price;
    private int stock;
    private int reviewCount;
    private double avgRating;
    private int numberOfSellers;
    private int numberOfImages;
    private int variationCount;
    private String imagesLink;
    private List<PriceChangeHistory> priceChangeHistory = new ArrayList<>();
    private List<SalesHistory> salesHistory = new ArrayList<>();
    private Date dateCreated;
    private Date dateUpdated;
}
