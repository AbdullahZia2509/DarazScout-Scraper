package com.darazscout.scraper.service;

import com.darazscout.scraper.model.Category;
import java.util.List;
import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;

public interface CategoryService {

  List<Category> scrapeCategories();

  void sendCategoryToWebApp();

  Future<String> scrapeProductsAgainstAllCategories();
}
