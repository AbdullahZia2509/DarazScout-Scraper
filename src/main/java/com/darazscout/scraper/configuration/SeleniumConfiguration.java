package com.darazscout.scraper.configuration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SeleniumConfiguration {

    @PostConstruct
    void postConstruct() {
        System.setProperty("webdriver.chrome.driver", "CHROMEDRIVER_PATH");
    }

    @Bean
    public ChromeDriver driver() {
        final ChromeOptions options = new ChromeOptions();
        options.setBinary("GOOGLE_CHROME_BIN");
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");
        return new ChromeDriver(options);
    }

}
