package com.pk.dynamo;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import java.net.ServerSocket;
import lombok.Getter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;


public class LocalDbCreationRule implements AfterAllCallback, BeforeAllCallback {
    private DynamoDBProxyServer server;

    @Getter
    private String port;

    public LocalDbCreationRule() {
        System.setProperty("aws.region", "us-east-1");
        System.setProperty("aws.accessKeyId", "test");
        System.setProperty("aws.secretAccessKey", "test");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            port = String.valueOf(serverSocket.getLocalPort());
        }
        server = ServerRunner.createServerFromCommandLineArgs(
                new String[]{"-inMemory", "-port", port});
        server.start();
    }


    @Override
    public void afterAll(ExtensionContext context) {
        this.stopUnchecked(server);
    }

    protected void stopUnchecked(DynamoDBProxyServer dynamoDbServer) {
        try {
            dynamoDbServer.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}