package com.zenbyte.service;

import com.zenbyte.model.JobApplication;
import com.zenbyte.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplication saveJobApplication(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    public Optional<JobApplication> getJobApplicationById(Long id) {
        return jobApplicationRepository.findById(id);
    }

    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    public List<JobApplication> getJobApplicationsByStatus(String status) {
        return jobApplicationRepository.findByStatus(status);
    }

    public List<JobApplication> getJobApplicationsByPosition(String position) {
        return jobApplicationRepository.findByPosition(position);
    }

    public JobApplication updateJobApplication(Long id, JobApplication jobApplication) {
        Optional<JobApplication> existing = jobApplicationRepository.findById(id);
        if (existing.isPresent()) {
            JobApplication ja = existing.get();
            if (jobApplication.getFullName() != null) ja.setFullName(jobApplication.getFullName());
            if (jobApplication.getEmail() != null) ja.setEmail(jobApplication.getEmail());
            if (jobApplication.getPhone() != null) ja.setPhone(jobApplication.getPhone());
            if (jobApplication.getPosition() != null) ja.setPosition(jobApplication.getPosition());
            if (jobApplication.getYearsOfExperience() != null) ja.setYearsOfExperience(jobApplication.getYearsOfExperience());
            if (jobApplication.getCoverLetter() != null) ja.setCoverLetter(jobApplication.getCoverLetter());
            if (jobApplication.getStatus() != null) ja.setStatus(jobApplication.getStatus());
            return jobApplicationRepository.save(ja);
        }
        return null;
    }

    public void deleteJobApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }
}
