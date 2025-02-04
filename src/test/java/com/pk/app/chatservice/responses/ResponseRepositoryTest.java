package com.pk.app.chatservice.responses;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.core.pagination.sync.PaginatedItemsIterable;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest.Builder;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResponseRepositoryTest {

    @Mock
    private DynamoDbTable<ResponseEntity> table;

    @Mock
    private PageIterable<ResponseEntity> results;

    @Mock
    PaginatedScanList<ResponseEntity> responses;

    @Mock
    private SdkIterable<ResponseEntity> items;

    @InjectMocks
    private ResponseRepository responseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(table.scan()).thenReturn(results);
        when(results.items()).thenReturn(items);


       // List<@org.jetbrains.annotations.NotNull ResponseEntity> foo = List.of(new ResponseEntity(), new ResponseEntity());
       // when(table.scan()).thenReturn(responses);



       // PageIterable<Object> it = PageIterable.create( (Object) items);

        //    Objects.requireNonNull(pageIterable);
       //     return pageIterable::iterator;



       // PaginatedItemsIterable<Object, Object> it = PaginatedItemsIterable.builder().pagesIterable(items).itemIteratorFunction((page) -> page.items().iterator()).build();


        //   when(results.items()).thenReturn()
    }

    @Test
    void getAllReturnsListOfResponseEntities() {
       List<ResponseEntity> expectedEntities = List.of(new ResponseEntity(), new ResponseEntity());

        //results.items().stream().forEach(item -> System.out.println(item));

        when(items.stream()).thenReturn(expectedEntities.stream());

        List<ResponseEntity> result = responseRepository.getAll();

        assertEquals(result, expectedEntities);

    }

    @Test
    @Disabled
    void getAllReturnsEmptyListWhenNoEntitiesFound() {

        when(table.scan(any(ScanEnhancedRequest.class))).thenReturn(results);

        List<ResponseEntity> result = responseRepository.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllThrowsExceptionWhenScanFails() {
        when(table.scan()).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> responseRepository.getAll());
    }
}