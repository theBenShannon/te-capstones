package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    public AccountService(String url){this.baseUrl = url;};

    private HttpEntity<?> createRequestEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return entity;
    }
    public BigDecimal returnBalance(String token) {
        HttpEntity<?> entity = createRequestEntity(token);
        return restTemplate.exchange(baseUrl + "users/balance", HttpMethod.GET, entity, BigDecimal.class).getBody();
    }



}
