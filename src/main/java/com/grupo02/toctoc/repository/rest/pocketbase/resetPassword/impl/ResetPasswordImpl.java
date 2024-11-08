package com.grupo02.toctoc.repository.rest.pocketbase.resetPassword.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.grupo02.toctoc.models.dto.LoginPBDTO;
import com.grupo02.toctoc.repository.rest.pocketbase.resetPassword.ResetPassword;
import com.grupo02.toctoc.services.exception.LoginFailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ResetPasswordImpl implements ResetPassword {

    @Autowired
    private Gson gson;

    /**
     * user puede ser email o username
     * @param user
     * @param password
     * @return
     */
    public void execute(String email) {

        String url = "https://pocketbase-production-a64c.up.railway.app/api/collections/users/request-password-reset";

        ObjectMapper objectMapper = new ObjectMapper();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("email", email);

        HttpEntity<String> request =
                new HttpEntity<>(personJsonObject.toString(),headers);


        try {

            ResponseEntity<String> responseEntityStr = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Capturar el status code y el body del error
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            Map body = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            log.error("Error occurred: Status Code: {}, Response Body: {}", statusCode, responseBody);
            throw new LoginFailException(body.get("data").toString());
            // Procesar y registrar el mensaje de error
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
        }
        log.info("reset password email sent to: " + email + " successfully");
    }
}
