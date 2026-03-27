package com.example.crud.service;

import com.example.crud.domain.product.Product;
import com.example.crud.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private AddressSearch addressSearch;

    public Boolean checkDistribution(String cep, String productId) {

        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        String city = addressSearch.getCityByCep(cep);

        if (city == null) {
            return false;
        }

        return product.getDistributionCenter().equalsIgnoreCase(city);
    }
}