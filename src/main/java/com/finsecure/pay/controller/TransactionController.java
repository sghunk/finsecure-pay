package com.finsecure.pay.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("")
public class TransactionController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateTransaction(@RequestBody Map<String, Object> transaction) {
        Double amount = null;
        if (transaction.get("amount") != null) {
            amount = Double.valueOf(transaction.get("amount").toString());
        }

        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "FAILED",
                "reason", "Invalid transaction amount"
            ));
        }

        if (amount > 10000) {
            return ResponseEntity.ok(Map.of(
                "status", "FAILED",
                "reason", "Transaction exceeds risk limits"
            ));
        }

        return ResponseEntity.ok(Map.of(
            "status", "SUCCESS",
            "transactionId", transaction.getOrDefault("transactionId", "UNKNOWN")
        ));
    }
}