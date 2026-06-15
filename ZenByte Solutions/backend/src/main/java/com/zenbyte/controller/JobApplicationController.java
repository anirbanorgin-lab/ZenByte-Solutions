package com.zenbyte.controller;

import com.zenbyte.model.JobApplication;
import com.zenbyte.service.JobApplicationService;
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
@RequestMapping("/applications")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplication> createJobApplication(@RequestBody JobApplication jobApplication) {
        JobApplication savedApplication = jobApplicationService.saveJobApplication(jobApplication);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications() {
        List<JobApplication> applications = jobApplicationService.getAllJobApplications();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable Long id) {
        Optional<JobApplication> application = jobApplicationService.getJobApplicationById(id);
        return application.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobApplication>> getJobApplicationsByStatus(@PathVariable String status) {
        List<JobApplication> applications = jobApplicationService.getJobApplicationsByStatus(status);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<JobApplication>> getJobApplicationsByPosition(@PathVariable String position) {
        List<JobApplication> applications = jobApplicationService.getJobApplicationsByPosition(position);
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateJobApplication(@PathVariable Long id, @RequestBody JobApplication jobApplication) {
        JobApplication updatedApplication = jobApplicationService.updateJobApplication(id, jobApplication);
        if (updatedApplication != null) {
            return new ResponseEntity<>(updatedApplication, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        jobApplicationService.deleteJobApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
