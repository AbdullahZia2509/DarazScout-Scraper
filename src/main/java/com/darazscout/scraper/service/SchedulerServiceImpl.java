package com.darazscout.scraper.service;

import com.darazscout.scraper.model.PriceChangeHistory;
import com.darazscout.scraper.model.Product;
import com.darazscout.scraper.model.ProductLinks;
import com.darazscout.scraper.model.SalesHistory;
import com.darazscout.scraper.repository.ProductLinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private ProductLinksRepository productLinksRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRestService productRestService;

    @Override
    @Scheduled(cron = "0 0 0 * * *" , zone="GMT+5")
    public void scheduleProductUpdates() {
        SalesHistory salesHistory = new SalesHistory();
        PriceChangeHistory priceChangeHistory = new PriceChangeHistory();
        List<ProductLinks> productLinks = productLinksRepository.findAll();
        for (ProductLinks pl :
                productLinks) {
            Product scrapedProduct = productService.scrapeProductPage(pl.getLink());
            Product storedProduct = productRestService.findProductBySKU(scrapedProduct.getSKU());

            salesHistory.setDateOfSale(getCurrentDate());
            salesHistory.setPrice(scrapedProduct.getPrice());
            salesHistory.setNumOfSales(scrapedProduct.getReviewCount() - storedProduct.getReviewCount());

            priceChangeHistory.setDateRecorded(getCurrentDate());
            priceChangeHistory.setNewPrice(scrapedProduct.getPrice());
            priceChangeHistory.setOldPrice(storedProduct.getPrice());

            if (storedProduct.getReviewCount() != scrapedProduct.getReviewCount())
                storedProduct.setReviewCount(scrapedProduct.getReviewCount());

            if (storedProduct.getPrice() != scrapedProduct.getPrice())
                storedProduct.setPrice(scrapedProduct.getPrice());

            if (storedProduct.getAvgRating() != scrapedProduct.getAvgRating())
                storedProduct.setAvgRating(scrapedProduct.getAvgRating());

            productRestService.updateProduct(storedProduct);
        }

    }

    private Date getCurrentDate() {
        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return date;
    }
}
