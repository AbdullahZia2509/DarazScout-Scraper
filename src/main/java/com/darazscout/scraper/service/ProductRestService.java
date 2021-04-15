package com.darazscout.scraper.service;

import com.darazscout.scraper.model.Product;

public interface ProductRestService {
    void saveProduct(Product product);
    Product findProductBySKU(String SKU);
    Product findProductById(String id);
    void updateProduct(Product product);
}
