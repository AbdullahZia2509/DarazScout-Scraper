package com.darazscout.scraper.service;

import com.darazscout.scraper.model.Product;
import org.springframework.http.ResponseEntity;

public interface ProductService {

  void scrapeProductPage(String value, String category);
  Product scrapeProductPage(String value);
  void scrapeSearchPage(String value, String category);
  void scrapeSearchPage(String value);
}
