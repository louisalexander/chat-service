package com.pk.app.chatservice.responses;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResponseService {

    private final ResponseRepository repository;


    public List<ResponseEntity> getAll() {
        return repository.getAll();
    }

    public void save(ResponseEntity response) {
        if (repository != null) {
            repository.save(response);
        }
    }
}