package com.darazscout.scraper.service;

/**
 * @author Muhammad Haris
 * @date 08-Jan-21
 */
public interface ProductService {

  void scrapeProductPage(String value);

  void scrapeSearchPage(String value);
}
