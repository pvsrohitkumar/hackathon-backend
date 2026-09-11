package com.spring.boot.service.impl;

import com.spring.boot.model.JobTitle;
import com.spring.boot.repository.JobTitleRepository;
import com.spring.boot.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class JobTitleServiceImpl implements JobTitleService {

    private static final Logger logger = LoggerFactory.getLogger(JobTitleServiceImpl.class);

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Override
    public String saveJobTitle(List<JobTitle> jobTitle) {
        String status = "Job Title saved successfully";
        try {
            jobTitleRepository.saveAll(jobTitle);
        } catch (Exception e) {
            status = "Error saving Job title: " + e.getMessage();
        }
        return status;
    }

    @Override
    public JobTitle getJobById(Integer jobId) {
        return jobTitleRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job Title not found with id: " + jobId));
    }

    @Override
    public List<JobTitle> getAllJobTitles() {
        return jobTitleRepository.findAll();
    }

    @Override
    public JobTitle getJobTitleByName(String jobTitleName) {
        return jobTitleRepository.getJobTitleByName(jobTitleName)
                .orElseThrow(() -> new RuntimeException("Job Title not found with name: " + jobTitleName));
    }

    @Override
    public List<JobTitle> getJobNamesByIds(List<Integer> jobIds) {
        logger.info("Fetching job names for jobIds: {}", jobIds);
        return jobTitleRepository.findAllById(jobIds);
    }

}
