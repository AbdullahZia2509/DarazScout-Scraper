package com.darazscout.scraper.repository;

import com.darazscout.scraper.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
