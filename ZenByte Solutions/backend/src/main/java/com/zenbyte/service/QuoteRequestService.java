package com.zenbyte.service;

import com.zenbyte.model.QuoteRequest;
import com.zenbyte.repository.QuoteRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteRequestService {
    @Autowired
    private QuoteRequestRepository quoteRequestRepository;

    public QuoteRequest saveQuoteRequest(QuoteRequest quoteRequest) {
        return quoteRequestRepository.save(quoteRequest);
    }

    public Optional<QuoteRequest> getQuoteRequestById(Long id) {
        return quoteRequestRepository.findById(id);
    }

    public List<QuoteRequest> getAllQuoteRequests() {
        return quoteRequestRepository.findAll();
    }

    public List<QuoteRequest> getQuoteRequestsByStatus(String status) {
        return quoteRequestRepository.findByStatus(status);
    }

    public QuoteRequest updateQuoteRequest(Long id, QuoteRequest quoteRequest) {
        Optional<QuoteRequest> existing = quoteRequestRepository.findById(id);
        if (existing.isPresent()) {
            QuoteRequest q = existing.get();
            if (quoteRequest.getCompanyName() != null) q.setCompanyName(quoteRequest.getCompanyName());
            if (quoteRequest.getContactName() != null) q.setContactName(quoteRequest.getContactName());
            if (quoteRequest.getContactEmail() != null) q.setContactEmail(quoteRequest.getContactEmail());
            if (quoteRequest.getProjectType() != null) q.setProjectType(quoteRequest.getProjectType());
            if (quoteRequest.getBudget() != null) q.setBudget(quoteRequest.getBudget());
            if (quoteRequest.getProjectDescription() != null) q.setProjectDescription(quoteRequest.getProjectDescription());
            if (quoteRequest.getStatus() != null) q.setStatus(quoteRequest.getStatus());
            return quoteRequestRepository.save(q);
        }
        return null;
    }

    public void deleteQuoteRequest(Long id) {
        quoteRequestRepository.deleteById(id);
    }
}
