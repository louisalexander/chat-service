package com.pk.app.chatservice.dynamo;

import java.net.URI;

import com.pk.app.chatservice.responses.ResponseEntity;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.extensions.VersionedRecordExtension;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
@NoArgsConstructor
@Slf4j
public class DynamoDbConfig {
    @Value("${chat-service.aws.dynamodb.endpoint:}")
    private String dynamodbEndpoint;
    @Value("${region:us-east-1}")
    private String region;

    
    @Bean
    public DynamoDbClient getDynamoDbClient() {
        var builder = DynamoDbClient
                .builder()
                .credentialsProvider(DefaultCredentialsProvider.create());

        if (dynamodbEndpoint != null && !dynamodbEndpoint.isBlank()) {
            builder.region(Region.of(region))
                    .endpointOverride(URI.create(dynamodbEndpoint));
            log.info("Initializing DynamoDB Client in region: " + region);
            log.debug("Using default credentials provider for DynamoDB Client.");
            log.warn("DynamoDB endpoint has been overridden to: " + dynamodbEndpoint);
        }
        return builder.build();
    }

    @Bean
    public DynamoDbEnhancedClient getDynamoDbEnhancedClient(DynamoDbClient ddbc) {
        return DynamoDbEnhancedClient
                .builder()
                .extensions(VersionedRecordExtension.builder().build())
                .dynamoDbClient(ddbc)
                .build();
    }

    @Bean
    public DynamoDbTable<ResponseEntity> getCountryLocaleTable(DynamoDbEnhancedClient dbClient) {
        return dbClient.table(ResponseEntity.TABLE_NAME, TableSchema.fromBean(ResponseEntity.class));
    }
}