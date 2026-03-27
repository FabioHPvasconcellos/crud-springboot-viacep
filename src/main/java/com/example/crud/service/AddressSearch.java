package com.example.crud.service;

import com.example.crud.domain.address.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressSearch {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AddressSearch(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getCityByCep(String cep) {

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            Address address = objectMapper.readValue(response.getBody(), Address.class);
            return address.getLocalidade();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}