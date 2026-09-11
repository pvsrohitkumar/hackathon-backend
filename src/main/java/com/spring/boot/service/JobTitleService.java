package com.spring.boot.service;

import com.spring.boot.model.JobTitle;

import java.util.List;

public interface JobTitleService {
   String saveJobTitle(List<JobTitle> jobTitleList);
   JobTitle getJobById(Integer jobId);
   List<JobTitle> getAllJobTitles();
   JobTitle getJobTitleByName(String jobTitleName);
   List<JobTitle> getJobNamesByIds(List<Integer> jobIds);
}
