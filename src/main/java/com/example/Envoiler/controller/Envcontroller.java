package com.example.Envoiler.controller;

import com.example.Envoiler.service.EnvService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController

 class EnvController {

    private final EnvService envService;

    public EnvController(EnvService envService) {
        this.envService = envService;
    }

    @GetMapping("/getResponse/{assetId}")
    public ResponseEntity<?> getResponse(@PathVariable String assetId) throws IOException, JSONException {

        List<String> response = envService.getResponse(assetId);
        List<JsonNode> jsonNodes=new ArrayList<>();
        for (String res : response) {
            JsonNode jsonNode = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                jsonNode = objectMapper.readTree(res);
                jsonNodes.add(jsonNode);

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
         return ResponseEntity.ok(jsonNodes);
    }
}
