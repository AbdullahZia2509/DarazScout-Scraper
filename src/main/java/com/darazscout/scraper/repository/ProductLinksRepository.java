package com.darazscout.scraper.repository;

import com.darazscout.scraper.model.ProductLinks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductLinksRepository extends MongoRepository<ProductLinks, String> {
}
