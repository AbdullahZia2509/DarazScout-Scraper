package com.darazscout.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesHistory {
    private Date dateOfSale;
    private int numOfSales;
    private double price;
}
