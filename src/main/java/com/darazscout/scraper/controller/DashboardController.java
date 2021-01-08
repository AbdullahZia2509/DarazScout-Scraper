package com.darazscout.scraper.controller;

import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.repository.CategoryRepository;
import com.darazscout.scraper.service.CategoryService;
import com.darazscout.scraper.service.ProductService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
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

  @RequestMapping({"/hello"})
  public String hello() {
    return "Helo World";
  }

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
  private ResponseEntity scrapeFullData() {
    try {
      categoryService.scrapeProductsAgainstAllCategories();
      return new ResponseEntity(HttpStatus.OK);
    } catch (Exception e) {
      LOGGER.error("Exception while scraping full data", e);
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
      // update error status in tracking collection
    }
  }


}
