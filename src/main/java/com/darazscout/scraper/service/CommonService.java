package com.darazscout.scraper.service;

import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public abstract class CommonService {
    public final ChromeDriver driver;



}
