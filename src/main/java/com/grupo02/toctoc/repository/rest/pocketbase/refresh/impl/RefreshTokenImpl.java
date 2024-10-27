package com.grupo02.toctoc.repository.rest.pocketbase.refresh.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.repository.rest.pocketbase.refresh.RefreshToken;
import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
public class RefreshTokenImpl implements RefreshToken {

    @Autowired
    private Gson gson;

    @Override
    public Optional<LoginPBDTO> execute(String jwt) {

        String url = "https://pocketbase-production-a64c.up.railway.app/api/collections/users/auth-refresh";

        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);


        JSONObject personJsonObject = new JSONObject();

        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(), headers);

        try {
            ResponseEntity<String> responseEntityStr = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);

            return Optional.of(gson.fromJson(responseEntityStr.getBody(), LoginPBDTO.class));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();

    }
}
