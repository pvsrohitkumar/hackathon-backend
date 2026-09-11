package com.spring.boot.controller;

import com.spring.boot.model.JobTitle;
import com.spring.boot.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/job")
@Tag(name = "Job Title Controller", description = "APIs for managing job titles")
public class JobTitleController {

    private static final Logger logger = LoggerFactory.getLogger(JobTitleController.class);

    @Autowired
    private JobTitleService jobTitleService;

    /**
     * Create new Job Titles.
     * @param jobTitle List of JobTitle objects
     * @return ResponseEntity with status and message
     */
    @PostMapping(value = "/save")
    @Operation(summary = "Create new job titles", description = "Creates new job titles in the system")
    public ResponseEntity<String> createJobTitle(@RequestBody(required = false) List<JobTitle> jobTitle) {
        logger.info("Received request to create job titles: {}", jobTitle);
        if (jobTitle == null || jobTitle.isEmpty()) {
            logger.warn("JobTitle list is null or empty");
            return ResponseEntity.badRequest().body("JobTitle list cannot be null or empty");
        }
        String status = jobTitleService.saveJobTitle(jobTitle);
        if (status != null && status.toLowerCase().contains("error")) {
            logger.error("Error creating job titles: {}", status);
            return ResponseEntity.badRequest().body(status);
        }
        logger.info("Job titles created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }


    @GetMapping("/get_job_title/{jobId}")
    @Operation(summary = "Get job title by ID", description = "Retrieves a job title by its ID")
    public ResponseEntity<JobTitle> getJobById(@PathVariable Integer jobId) {
        JobTitle jobTitle = jobTitleService.getJobById(jobId);
        return ResponseEntity.ok(jobTitle);
    }

    @GetMapping("/get_all_job_titles")
    @Operation(summary = "Get all job titles", description = "Retrieves all job titles in the system")
    public ResponseEntity<List<JobTitle>> getAllJobTitles() {
        List<JobTitle> jobTitleList = jobTitleService.getAllJobTitles();
        return ResponseEntity.ok(jobTitleList);
    }

    @GetMapping("/get_job_title_by_name/{jobTitleName}")
    @Operation(summary = "Get job title by name", description = "Retrieves a job title by its name")
    public ResponseEntity<JobTitle> getJobTitleByName(@PathVariable String jobTitleName) {
        JobTitle jobTitle = jobTitleService.getJobTitleByName(jobTitleName);
        return ResponseEntity.ok(jobTitle);
    }

    @PostMapping("/get_job_names_by_ids")
    @Operation(summary = "Get job titles by IDs", description = "Retrieves job titles by a list of IDs")
    public ResponseEntity<List<JobTitle>> getJobNamesByIds(@RequestBody List<Integer> jobIds) {
        List<JobTitle> jobTitleList= jobTitleService.getJobNamesByIds(jobIds);
        return ResponseEntity.ok(jobTitleList);
    }

}
