package com.darazscout.scraper.service;

import static com.darazscout.scraper.util.Constants.URL;
import static com.darazscout.scraper.util.StringUtil.extractNumeric;

import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.model.Product;
import com.darazscout.scraper.util.Constants;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ChromeDriver driver;

    @Override
    public void scrapeProductPage(final String value) {
        Product product = new Product();
        driver.get(URL + value);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        final WebElement title = driver.findElement(By.className("pdp-mod-product-badge-title"));
        final WebElement avgRating = driver.findElement(By.className("score"));
        final WebElement reviewCount = driver.findElement(By.className("count"));
        final WebElement brand = driver.findElement(By.className("pdp-product-brand__brand-link"));
        final WebElement price = driver.findElement(By.className("pdp-price_size_xl"));
        System.out.println(price.getText());
        final List<WebElement> imageCount = driver.findElements(By.className("item-gallery__thumbnail"));
        final WebElement SKU = driver.findElement(By.xpath("//*[@id='module_product_detail']/div/div/div[3]/div[1]/ul/li[2]/div"));
        final List<WebElement> variationCount = driver.findElements(By.className("sku-variable-img-wrap"));
        final WebElement image = driver.findElement(By.className("gallery-preview-panel__image"));

        try {
            final WebElement viewMoreBtn = driver.findElement(By.className("pdp-view-more-btn"));
            viewMoreBtn.click();
        } catch (Exception e) {
            System.out.println("No 'View More' button Found!");
        }

        product.setTitle(title.getText());
        product.setAvgRating(extractNumeric(avgRating.getText()));
        product.setReviewCount((int) extractNumeric(reviewCount.getText()));
        product.setBrand(brand.getText());
        product.setPrice(price.getText().replace("Rs. ", ""));
        product.setNumberOfImages(imageCount.size());
        product.setImagesLink(image.getAttribute("src"));
        product.setSKU(SKU.getText());
        product.setVariationCount(variationCount.size() + 1);

        sendProductToWebApp(product);

    }

    @Override
    public void scrapeSearchPage(final String value) {
        driver.get(URL + value);

        final List<String> productLinks = new ArrayList<>();

        final List<WebElement> productTitles = driver.findElements(By.className("c16H9d"));
        for (WebElement e:
             productTitles) {
            productLinks.add(e.findElement(By.tagName("a")).getAttribute("href"));
        }

        for (String l:
             productLinks) {
            scrapeProductPage(l.replace("https://www.daraz.pk/", ""));
        }

    }

    private void printProductDetails(Product product) {
        System.out.println("Title: " + product.getTitle());
        System.out.println("Rating: " + product.getAvgRating());
        System.out.println("Review Count: " + product.getReviewCount());
        System.out.println("Brand: " + product.getBrand());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Image Count: " + product.getNumberOfImages());
        System.out.println("SKU: " + product.getSKU());
        System.out.println("Variation Count: " + product.getVariationCount());
        System.out.print("Image: " + product.getImagesLink());

    }

    public void sendProductToWebApp(Product product)
    {
        final String uri = "http://localhost:8080/api/product/create";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> result = restTemplate.postForEntity( uri, product, Product.class);

        System.out.println(result);
    }

}

