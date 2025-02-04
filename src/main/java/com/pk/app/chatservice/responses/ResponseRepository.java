package com.pk.app.chatservice.responses;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@Component
@RequiredArgsConstructor
public class ResponseRepository {
    private final DynamoDbTable<ResponseEntity> table;


    public List<ResponseEntity> getAll() {
        if (table == null) {
            throw new IllegalStateException("DynamoDbTable is not initialized");
        }

        PageIterable<ResponseEntity> bar = table.scan();
        SdkIterable<ResponseEntity> foo = bar.items();

       return foo.stream().toList();
    }
}