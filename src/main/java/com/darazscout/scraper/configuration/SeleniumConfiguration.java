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
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
    }

    //.chromedriver/bin/chromedriver
    @Bean
    public ChromeDriver driver() {
        final ChromeOptions options = new ChromeOptions();
        //options.setBinary(".apt/usr/bin/google-chrome");
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
                "--ignore-certificate-errors", "--enable-features=NetworkService,NetworkServiceInProcess",
                "--disable-dev-shm-usage",
                "disable-features=VizDisplayCompositor");
        return new ChromeDriver(options);
    }

}
