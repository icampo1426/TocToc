package com.grupo02.toctoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class AdService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, Object>> getAds() {
        String adsUrl = "https://my-json-server.typicode.com/chrismazzeo/advertising_da1/ads";

        List<Map<String, Object>> ads = restTemplate.getForObject(adsUrl, List.class);

        return ads;
    }
}

