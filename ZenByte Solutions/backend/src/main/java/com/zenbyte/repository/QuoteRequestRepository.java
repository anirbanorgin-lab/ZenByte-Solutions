package com.zenbyte.repository;

import com.zenbyte.model.QuoteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuoteRequestRepository extends JpaRepository<QuoteRequest, Long> {
    List<QuoteRequest> findByContactEmail(String email);
    List<QuoteRequest> findByStatus(String status);
}
