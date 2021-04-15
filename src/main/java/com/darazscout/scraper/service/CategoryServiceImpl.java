package com.darazscout.scraper.service;

import com.darazscout.scraper.controller.DashboardController;
import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.repository.CategoryRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static com.darazscout.scraper.util.Constants.URL;


@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ChromeDriver driver;

    @Override
    public List<Category> scrapeCategories() {
        List<Category> categoryList = new ArrayList<>();
        Category tempCategoryLvl1;
        Category tempCategoryLvl2;
        Category tempCategoryLvl3;

        driver.get(URL);

        final List<WebElement> categoryLvl1 = driver
                .findElements(By.className("lzd-site-menu-root-item"));
        final List<WebElement> categoryLvl2 = driver.findElements(By.className("lzd-site-menu-sub"));

        for (int i = 0; i < categoryLvl2.size(); i++) {
            tempCategoryLvl1 = new Category();
            tempCategoryLvl1.setCategoryName(categoryLvl1.get(i).getText());

            List<WebElement> subCategory = categoryLvl2.get(i)
                    .findElements(By.className("lzd-site-menu-sub-item"));
            for (WebElement webElement : subCategory) {
                tempCategoryLvl2 = new Category();
                tempCategoryLvl2.setCategoryName(
                        webElement.findElement(By.tagName("span")).getAttribute("innerHTML")
                                .replace("&amp;", "&"));
                tempCategoryLvl2
                        .setCategoryLink(webElement.findElement(By.tagName("a")).getAttribute("href"));

                final List<WebElement> grandCategory = webElement
                        .findElements(By.className("lzd-site-menu-grand-item"));
                for (WebElement element : grandCategory) {
                    tempCategoryLvl3 = new Category();
                    tempCategoryLvl3.setCategoryName(
                            element.findElement(By.tagName("span")).getAttribute("innerHTML")
                                    .replace("&amp;", "&"));
                    tempCategoryLvl3
                            .setCategoryLink(element.findElement(By.tagName("a")).getAttribute("href"));
                    tempCategoryLvl3.setCategoryList(null);
                    tempCategoryLvl2.getCategoryList().add(tempCategoryLvl3);
                }
                tempCategoryLvl1.getCategoryList().add(tempCategoryLvl2);
            }
            categoryList.add(tempCategoryLvl1);
        }
        return categoryList;
    }

    @Override
    public void sendCategoryToWebApp() {
        //https://darazscout-wepp-app.herokuapp.com/api/category/createall
        final String uri = "http://localhost:8081/api/category/createall";
        RestTemplate restTemplate = new RestTemplate();
        List<Category> categoryList = categoryRepository.findAll();
        ResponseEntity<Category> result = restTemplate.postForEntity(uri, categoryList, Category.class);
        System.out.println(result);

/*
        for (Category c:
                categoryList) {
            ResponseEntity<Category> result = restTemplate.postForEntity(uri, c, Category.class);
            System.out.println(result);
        }
*/
    }

    @Async
    @Override
    public Future<String> scrapeProductsAgainstAllCategories() {
        // update tracking status
        LOGGER.info("Getting categories to scrape products.");
        List<Category> categoryList = categoryRepository.findAll();
        for (Category c : categoryList) {
            LOGGER.info("Going to scrape categories under parent: {}", c.getCategoryName());
            List<Category> categoryList1 = c.getCategoryList();
            for (Category category : categoryList1) {
                LOGGER.info("Going to scrape sub-categories of : {}", category.getCategoryName());
                List<Category> categoryList2 = category.getCategoryList();
                for (Category ctgry : categoryList2) {
                    LOGGER.info("Going to scrape pages under: {}", ctgry.getCategoryName());
                    productService
                            .scrapeSearchPage(ctgry.getCategoryLink().replace("https://www.daraz.pk/", ""), ctgry.getCategoryName());

                }
            }
        }
        return new AsyncResult<>("SUCCESS");
    }

}
