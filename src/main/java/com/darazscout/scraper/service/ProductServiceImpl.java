package com.darazscout.scraper.service;

import static com.darazscout.scraper.util.Constants.URL;
import static com.darazscout.scraper.util.StringUtil.extractNumeric;

import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.model.Product;
import com.darazscout.scraper.model.ProductLinks;
import com.darazscout.scraper.repository.ProductLinksRepository;
import com.darazscout.scraper.util.Constants;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ChromeDriver driver;

    @Autowired
    ProductLinksRepository productLinksRepository;

    @Autowired
    private ProductRestService productRestService;

    @Override
    public void scrapeProductPage(final String value, String category) {
        Product product = new Product();
        driver.get(URL + value);
        //driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

//        ProductLinks productLinks = new ProductLinks(value);
//        productLinksRepository.save(productLinks);

        try {
            final WebElement viewMoreBtn = driver.findElement(By.className("pdp-view-more-btn"));
            viewMoreBtn.click();
        } catch (Exception e) {
            System.out.println("No 'View More' button Found!");
        }

        final WebElement title = driver.findElement(By.className("pdp-mod-product-badge-title"));
        LOGGER.info(title.getText());
        final WebElement avgRating = driver.findElement(By.className("score"));
        LOGGER.info(avgRating.getText());
        final WebElement reviewCount = driver.findElement(By.className("count"));
        LOGGER.info(reviewCount.getText());
        final WebElement brand = driver.findElement(By.className("pdp-product-brand__brand-link"));
        LOGGER.info(brand.getText());
        final WebElement price = driver.findElement(By.className("pdp-price_size_xl"));
        LOGGER.info(price.getText());
        final List<WebElement> imageCount = driver.findElements(By.className("item-gallery__thumbnail"));
        LOGGER.info(String.valueOf(imageCount.size()));
        final WebElement SKU = driver.findElement(By.xpath("//*[@id='module_product_detail']/div/div/div[3]/div[1]/ul/li[2]/div"));
        LOGGER.info(SKU.getText());
        final List<WebElement> variationCount = driver.findElements(By.className("sku-variable-img-wrap"));
        LOGGER.info(String.valueOf(variationCount.size()));
        final WebElement image = driver.findElement(By.className("gallery-preview-panel__image"));
        LOGGER.info(image.getText());

        String priceTemp = price.getText().replace("Rs. ", "");
        priceTemp = priceTemp.replace(",", "");

        product.setTitle(title.getText());
        product.setCategory(category);
        product.setAvgRating(extractNumeric(avgRating.getText().replace(avgRating.getText().charAt(avgRating.getText().length()-1), ' ')));
        product.setReviewCount((int) extractNumeric(reviewCount.getText()));
        product.setBrand(brand.getText());
        product.setNumberOfImages(imageCount.size());
        product.setImagesLink(image.getAttribute("src"));
        product.setSKU(SKU.getText());
        product.setVariationCount(variationCount.size() + 1);
        product.setPrice(Integer.parseInt(priceTemp));

        //productRestService.saveProduct(product);

    }

    @Override
    public void scrapeSearchPage(String value) {
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

    @Override
    public void scrapeSearchPage(final String value, String category) {
        driver.get(URL + value);

        final List<String> productLinks = new ArrayList<>();

        final List<WebElement> productTitles = driver.findElements(By.className("c16H9d"));
        LOGGER.info("Found {} titles under category: {}", productTitles.size(), category);
        for (WebElement e: productTitles) {
            productLinks.add(e.findElement(By.tagName("a")).getAttribute("href"));
        }

        for (String l: productLinks) {
            LOGGER.info("Going to scrape page against {}", l);
            scrapeProductPage(l.replace("https://www.daraz.pk/", ""), category);
        }

    }

    @Override
    public Product scrapeProductPage(String value) {
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

        String priceTemp = price.getText().replace("Rs. ", "");
        priceTemp = priceTemp.replace(",", "");

        product.setTitle(title.getText());
        product.setAvgRating(extractNumeric(avgRating.getText()));
        product.setReviewCount((int) extractNumeric(reviewCount.getText()));
        product.setBrand(brand.getText());
        product.setPrice(Integer.parseInt(priceTemp));
        product.setNumberOfImages(imageCount.size());
        product.setImagesLink(image.getAttribute("src"));
        product.setSKU(SKU.getText());
        product.setVariationCount(variationCount.size() + 1);

        return product;
    }

}

