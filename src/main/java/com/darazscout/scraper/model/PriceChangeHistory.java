package com.darazscout.scraper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceChangeHistory {
    private Date dateRecorded;
    private double oldPrice;
    private double newPrice;
}
