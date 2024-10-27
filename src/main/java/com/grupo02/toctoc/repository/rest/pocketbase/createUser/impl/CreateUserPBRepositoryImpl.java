package com.grupo02.toctoc.repository.rest.pocketbase.createUser.impl;

import com.google.gson.Gson;
import com.grupo02.toctoc.models.dto.NewUserPBDTO;
import com.grupo02.toctoc.models.dto.UserPBDTO;
import com.grupo02.toctoc.repository.rest.pocketbase.createUser.CreateUserPBRepository;
import com.grupo02.toctoc.services.exception.EmailAlreadyUsedException;
import lombok.extern.slf4j.Slf4j;
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
public class CreateUserPBRepositoryImpl implements CreateUserPBRepository {

    @Autowired
    private Gson gson;

    public Optional<UserPBDTO> execute(NewUserPBDTO newUserPBDTO) {

        String url = "https://pocketbase-production-a64c.up.railway.app/api/collections/users/records";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(gson.toJson(newUserPBDTO), headers);
        try {

            ResponseEntity<String> responseEntityStr = restTemplate
                    .exchange(url, HttpMethod.POST, request, String.class);

            return Optional.of(gson.fromJson(responseEntityStr.getBody(), UserPBDTO.class));

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Capturar el status code y el body del error
            HttpStatus statusCode = (HttpStatus) e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            Map body = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            log.error("Error occurred: Status Code: {}, Response Body: {}", statusCode, responseBody);
            throw new EmailAlreadyUsedException(body.get("data").toString());
            // Procesar y registrar el mensaje de error
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
        }

        return Optional.empty();
    }

}
