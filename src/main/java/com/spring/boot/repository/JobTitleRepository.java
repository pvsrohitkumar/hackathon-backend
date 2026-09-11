package com.spring.boot.repository;

import com.spring.boot.model.JobTitle;
import java.util.List;
import java.util.Optional;

public interface JobTitleRepository {
    List<JobTitle> saveAll(Iterable<JobTitle> jobTitles);
    Optional<JobTitle> findById(Integer id);
    Optional<JobTitle> getJobTitleByName(String name);
    List<JobTitle> findAll();
    List<JobTitle> findAllById(Iterable<Integer> ids);
}
