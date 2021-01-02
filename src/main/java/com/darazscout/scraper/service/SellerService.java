package com.darazscout.scraper.service;

import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService extends CommonService{

    public SellerService(ChromeDriver driver) {
        super(driver);
    }

    private void scrapeSeller(final String value) {
        //Before getting the page value will be checked if it already exists in the sellers list
        try {
            driver.get(URL + "shop/" + value + "/?path=profile.htm&langFlag=en&lang=en&pageTypeId=3");
        } catch (Exception e) {
            System.out.println("This Seller has no Profile.");
        }

        final WebElement followers = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[2]/div/div/div/div[2]/div[1]/div/div/div/div[2]/div[2]/div"));
        final WebElement positiveRating = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[2]/div/div/div/div[2]/div[1]/div/div/div/div[2]/div[3]"));
        final WebElement shippingRating = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[3]/div[2]/div/div/div[1]/div[1]/div[3]/div[2]/div[1]"));
        final WebElement chatResponseRate = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[3]/div[2]/div/div/div[1]/div[1]/div[4]/div[2]/div[2]"));
        final WebElement dateJoined = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[3]/div[2]/div/div/div[1]/div[1]/div[2]/div[2]/div/p[1]/span"));
        //final WebElement sellerSizeBox = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[3]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/div"));
        final WebElement reviewCount = driver.findElement(By.xpath("//*[@id=\"sis_seller_ratings_reviews\"]/div[2]/div[1]"));
        final  WebElement mainCategory = driver.findElement(By.xpath("//*[@id=\"pi-component-container\"]/div/div[3]/div[2]/div/div/div[1]/div[1]/div[1]/div[2]/span[2]"));

        System.out.println(followers.getText());
        System.out.println(positiveRating.getText());
        System.out.println(shippingRating.getAttribute("innerHTML"));
        System.out.println(chatResponseRate.getText());
        System.out.println(dateJoined.getText());
        //System.out.println(calcSellerSize(sellerSizeBox));
        System.out.println(reviewCount.getText());
        System.out.println(mainCategory.getText());
    }

    private int calcSellerSize(WebElement elm) {
        List<WebElement> blackBox = elm.findElements(By.cssSelector("div[style=\"position: relative; box-sizing: border-box; display: inline-block; flex-direction: column; align-content: flex-start; flex-shrink: 0; width: 20px; height: 24px; margin-left: 5px; background-color: rgb(0, 62, 82);\"]"));
        return blackBox.size();
    }


}
