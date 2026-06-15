package com.zenbyte.repository;

import com.zenbyte.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByEmail(String email);
    List<JobApplication> findByPosition(String position);
    List<JobApplication> findByStatus(String status);
}
