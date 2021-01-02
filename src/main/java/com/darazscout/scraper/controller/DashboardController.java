package com.darazscout.scraper.controller;

import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.repository.CategoryRepository;
import com.darazscout.scraper.service.CategoryService;
import com.darazscout.scraper.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

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
    private void scrapeFullData() {
    List<Category> categoryList = categoryRepository.findAll();
        for (Category c:
             categoryList) {
            List<Category> categoryList1 = c.getCategoryList();
            for (Category category:
                 categoryList1) {
                List<Category> categoryList2 = category.getCategoryList();
                for (Category ctgry:
                     categoryList2) {
                    productService.scrapeSearchPage(ctgry.getCategoryLink().replace("https://www.daraz.pk/", ""));
                }
            }
        }
    }
}
