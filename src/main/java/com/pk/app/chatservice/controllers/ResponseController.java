package com.pk.app.chatservice.controllers;

import java.util.List;

import com.pk.app.chatservice.responses.ResponseEntity;
import com.pk.app.chatservice.responses.ResponseService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService service;

//    private ResponseController() {
//        this.service = null;
//    }

    @GetMapping("/responses")
    public List<ResponseEntity> getAll() {
        return service.getAll();
    }

    @PostMapping("/responses")
    public void saveResponse(@RequestBody ResponseEntity response) {
        service.save(response);
    }
}