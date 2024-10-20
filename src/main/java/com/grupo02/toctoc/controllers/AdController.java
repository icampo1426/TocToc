package com.grupo02.toctoc.controllers;

import com.grupo02.toctoc.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private AdService adService;

    @GetMapping
    public ResponseEntity<?> getAds() {
        try {
            List<Map<String, Object>> ads = adService.getAds();
            return new ResponseEntity<>(ads, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los anuncios.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
