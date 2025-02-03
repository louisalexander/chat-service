package com.pk.app.chatservice.responses;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Component
@RequiredArgsConstructor
public class ResponseRepository {
    private final DynamoDbTable<ResponseEntity> table;

    public List<ResponseEntity> getAll() {
        return table.scan().items().stream().toList();
    }
}