package com.pk.app.chatservice.responses;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository repository;

    private ResponseService() {
        this.repository = null;
    }

    public List<ResponseEntity> getAll() {
        return repository.getAll();
    }
}