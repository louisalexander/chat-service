package com.pk.app.chatservice.responses;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
public class ResponseEntity {
    public static final String TABLE_NAME = "UserResponses";

    private String userId;

    @DynamoDbPartitionKey
    public String getUser() {
        return userId;
    }
}