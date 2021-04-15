package com.darazscout.scraper.service;

import com.darazscout.scraper.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductRestServiceImpl implements ProductRestService {
    @Override
    public void saveProduct(Product product) {
        //"https://darazscout-wepp-app.herokuapp.com/api/product/create"
        final String uri = "http://localhost:8081/api/product/create";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> result = restTemplate.postForEntity( uri, product, Product.class);

        System.out.println(result);
    }

    @Override
    public Product findProductBySKU(String SKU) {
        //"https://darazscout-wepp-app.herokuapp.com/api/product/create"
        final String uri = "http://localhost:8081/api/product/findbysku?sku=" + SKU;
        RestTemplate restTemplate = new RestTemplate();
        Product result = restTemplate.getForEntity(uri, Product.class).getBody();
        return result;
    }

    @Override
    public Product findProductById(String id) {
        final String uri = "http://localhost:8081/api/product/findbyid?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        Product result = restTemplate.getForEntity(uri, Product.class).getBody();
        return result;
    }

    @Override
    public void updateProduct(Product product) {

    }
}
