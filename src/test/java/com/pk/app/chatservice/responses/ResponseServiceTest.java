package com.pk.app.chatservice.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResponseServiceTest {

    @Mock
    private ResponseRepository repository;

    @InjectMocks
    private ResponseService responseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReturnsListOfResponseEntities() {
        List<ResponseEntity> expectedEntities = List.of(new ResponseEntity(), new ResponseEntity());
        when(repository.getAll()).thenReturn(expectedEntities);

        List<ResponseEntity> result = responseService.getAll();

        assertEquals(expectedEntities, result);
    }

    @Test
    void getAllReturnsEmptyListWhenNoEntitiesFound() {
        when(repository.getAll()).thenReturn(List.of());

        List<ResponseEntity> result = responseService.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllThrowsExceptionWhenRepositoryFails() {
        when(repository.getAll()).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> responseService.getAll());
    }
}