package org.example.coindesk.controller;

import org.example.coindesk.service.CoindeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskService coindeskService;

    @GetMapping("/raw")
    public ResponseEntity<String> getRawData() {
        return ResponseEntity.ok(coindeskService.getRawData());
    }

    @GetMapping("/converted")
    public ResponseEntity<?> getConvertedData() {
        try {
            return ResponseEntity.ok(coindeskService.getConvertedData());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("轉換失敗");
        }
    }

}
