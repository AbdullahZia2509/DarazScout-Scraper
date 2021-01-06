package com.darazscout.scraper.controller;

import com.darazscout.scraper.model.Category;
import com.darazscout.scraper.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Category> addNew(@RequestBody Category category) throws URISyntaxException {
        Category result = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/addAdmin/addNew" + result.getCategoryName())).body(result);
    }

    @GetMapping("/findall")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/findbyid")
    public ResponseEntity<?> findById(String id) {
        Optional<Category> admin = categoryRepository.findById(id);
        return admin.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        Category result = categoryRepository.save(category);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(String id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
