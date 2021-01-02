package com.darazscout.scraper.repository;

import com.darazscout.scraper.model.ScraperExecution;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScraperExecutionRepository extends MongoRepository<ScraperExecution, String> {
}
