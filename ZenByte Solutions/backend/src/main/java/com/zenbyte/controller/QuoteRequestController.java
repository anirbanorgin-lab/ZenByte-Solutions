package com.zenbyte.controller;

import com.zenbyte.model.QuoteRequest;
import com.zenbyte.service.QuoteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quotes")
public class QuoteRequestController {
    @Autowired
    private QuoteRequestService quoteRequestService;

    @PostMapping
    public ResponseEntity<QuoteRequest> createQuoteRequest(@RequestBody QuoteRequest quoteRequest) {
        QuoteRequest savedQuote = quoteRequestService.saveQuoteRequest(quoteRequest);
        return new ResponseEntity<>(savedQuote, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<QuoteRequest>> getAllQuoteRequests() {
        List<QuoteRequest> quotes = quoteRequestService.getAllQuoteRequests();
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteRequest> getQuoteRequestById(@PathVariable Long id) {
        Optional<QuoteRequest> quote = quoteRequestService.getQuoteRequestById(id);
        return quote.map(q -> new ResponseEntity<>(q, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<QuoteRequest>> getQuoteRequestsByStatus(@PathVariable String status) {
        List<QuoteRequest> quotes = quoteRequestService.getQuoteRequestsByStatus(status);
        return new ResponseEntity<>(quotes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteRequest> updateQuoteRequest(@PathVariable Long id, @RequestBody QuoteRequest quoteRequest) {
        QuoteRequest updatedQuote = quoteRequestService.updateQuoteRequest(id, quoteRequest);
        if (updatedQuote != null) {
            return new ResponseEntity<>(updatedQuote, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuoteRequest(@PathVariable Long id) {
        quoteRequestService.deleteQuoteRequest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
