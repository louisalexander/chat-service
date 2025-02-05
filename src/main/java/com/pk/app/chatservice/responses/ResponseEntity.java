package com.pk.app.chatservice.responses;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
public class ResponseEntity {
    public static final String TABLE_NAME = "UserResponses";

    private String userId;

    private String liked;

    private String didntLike;

    private String recipeId;

    @DynamoDbPartitionKey
    public String getRecipeId() {
        return recipeId;
    }

    @DynamoDbSortKey
    public String getUserId() {
        return userId;
    }

}