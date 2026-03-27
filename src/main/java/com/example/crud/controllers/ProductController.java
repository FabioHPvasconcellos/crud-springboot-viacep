package com.example.crud.controllers;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import com.example.crud.domain.category.RequestCategory;
import com.example.crud.domain.product.RequestProduct;
import com.example.crud.service.AddressSearch;
import com.example.crud.service.ProductService; // 👈 IMPORTANTE
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService productService; // 👈 NECESSÁRIO PRO PASSO 4

    @Autowired
    private AddressSearch addressSearch;

    @GetMapping
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }


    @GetMapping("/endpoint1")
    public ResponseEntity getAllProducts1(@RequestParam String categoryAsParam){
        var allProducts = repository.findAllByCategory(categoryAsParam);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/endpoint2/{id}")
    public ResponseEntity getProduct(@PathVariable String id){
        Optional<Product> optionalProduct = repository.findById(id);
        return ResponseEntity.ok(optionalProduct);
    }

    @GetMapping("/endpoint3/top5byprice")
    public ResponseEntity getAllProducts3(){
        var allProducts = repository.findAllByActiveTrue();

        List<Product> topFive = allProducts
                .stream()
                .sorted(Comparator.comparingInt(Product::getPrice).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return ResponseEntity.ok(topFive);
    }

    @GetMapping("/category/{categoryAsPath}")
    public ResponseEntity getProductsByCategory(
            @RequestHeader String categoryAsHeader,
            @PathVariable String categoryAsPath,
            @RequestBody @Valid RequestCategory categoryAsBody,
            @RequestParam String categoryAsParam
    ){
        var allProducts = repository.findAllByActiveTrue();
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : allProducts) {
            if (categoryAsParam.equals(product.getCategory())) {
                filteredProducts.add(product);
            }
        }

        return ResponseEntity.ok(filteredProducts);
    }

    // 🚀 ENDPOINT DO PASSO 4
    @GetMapping("/check")
    public Boolean checkDistribution(
            @RequestParam String cep,
            @RequestParam String id) {

        return productService.checkDistribution(cep, id);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data){
        Optional<Product> optionalProduct = repository.findById(data.id());
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice(data.price());
            return ResponseEntity.ok(product);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteProduct(@PathVariable String id){
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setActive(false);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException();
        }
    }
}