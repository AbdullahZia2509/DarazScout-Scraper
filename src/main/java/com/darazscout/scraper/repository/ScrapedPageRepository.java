package com.darazscout.scraper.repository;

import com.darazscout.scraper.model.ScrapedPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrapedPageRepository extends MongoRepository<ScrapedPage, String> {
}
