package com.finsecure.pay;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PayApplication.class)
class TransactionControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHealthEndpoint() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/health", Map.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().get("status")).isEqualTo("DOWN");
    }

    @Test
    void testSuccessfulValidation() {
        Map<String, Object> request = new HashMap<>();
        request.put("transactionId", "TXN123");
        request.put("amount", 250.0);

        ResponseEntity<Map> response = restTemplate.postForEntity("/validate", request, Map.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().get("status")).isEqualTo("SUCCESS");
    }

    @Test
    void testFailedValidation_NegativeAmount() {
        Map<String, Object> request = new HashMap<>();
        request.put("transactionId", "TXN124");
        request.put("amount", -5.0);

        ResponseEntity<Map> response = restTemplate.postForEntity("/validate", request, Map.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}