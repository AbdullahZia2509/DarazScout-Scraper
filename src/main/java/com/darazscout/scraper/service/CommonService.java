package com.darazscout.scraper.service;

import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class CommonService {
    public final ChromeDriver driver;
    public final String URL = "https://www.daraz.pk/";

    public double extractNumeric(String s) {
        //str.replaceAll("[^\\d.]", "")
        return Double.parseDouble(s.replaceAll("[^0-9.]", ""));
    }

}
