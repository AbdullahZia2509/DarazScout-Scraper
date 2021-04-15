package com.darazscout.scraper.controller;

import com.darazscout.scraper.service.SellerServiceImp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerServiceImp sellerServiceImp;

    @GetMapping("/scrape")
    private void scrapeProduct(String sellerPageLink) {
        sellerServiceImp.scrapeSeller(sellerPageLink);
    }

}
