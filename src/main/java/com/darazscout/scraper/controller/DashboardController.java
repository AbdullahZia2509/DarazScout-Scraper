package com.darazscout.scraper.controller;

import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.repository.CategoryRepository;
import com.darazscout.scraper.service.CategoryService;
import com.darazscout.scraper.service.ProductService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
  private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

  @Autowired
  private ProductService productService;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private CategoryService categoryService;

//  @Scheduled(cron = "0/5 * * ? * *" , zone="GMT+5")
//  private void testSchedule() {
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    Date now = new Date();
//    String strDate = sdf.format(now);
//    System.out.println("Java cron job expression:: " + strDate);
//  }

  @RequestMapping("/scrapeproduct")
  private void scrapeProduct(String productPageLink) {
    productService.scrapeProductPage(productPageLink);
  }

  @RequestMapping("/getcategories")
  private void sendCategoriesToWebApp() {
    categoryService.sendCategoryToWebApp();
  }

  @RequestMapping("/scrapeallcategory")
  private ResponseEntity<List<Category>> scrapeAllCategory() {
    List<Category> result = categoryService.scrapeCategories();
    result = categoryRepository.saveAll(result);
    return ResponseEntity.ok().body(result);
  }

  @RequestMapping("fullblownscrape")
  public ResponseEntity scrapeFullData() {
    try {
      LOGGER.info("Going to scrape all categories");
      categoryService.scrapeProductsAgainstAllCategories();
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Exception while scraping full data", e);
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
      // update error status in tracking collection
    }
  }


}
