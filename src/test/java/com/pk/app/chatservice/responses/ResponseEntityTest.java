package com.pk.app.chatservice.responses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResponseEntityTest {

    @Test
    void getUserReturnsCorrectUserId() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setUserId("user123");
        assertEquals("user123", responseEntity.getUser());
    }

    @Test
    void setUserIdStoresCorrectValue() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setUserId("user456");
        assertEquals("user456", responseEntity.getUserId());
    }

    @Test
    void getUserReturnsNullWhenUserIdNotSet() {
        ResponseEntity responseEntity = new ResponseEntity();
        assertNull(responseEntity.getUser());
    }

    @Test
    void setUserIdToNull() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setUserId(null);
        assertNull(responseEntity.getUserId());
    }
}