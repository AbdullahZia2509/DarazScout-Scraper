package com.darazscout.scraper.controller;

import com.darazscout.scraper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/scrape")
    private void scrapeProduct(String productPageLink) {
        productService.scrapeProductPage(productPageLink);
    }

    @RequestMapping("/scrapesearch")
    private void scrapeSearchPage(String searchPageLink) {
        productService.scrapeSearchPage(searchPageLink);
    }

}
