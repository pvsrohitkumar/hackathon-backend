package com.spring.boot.repository.impl;

import com.spring.boot.model.JobTitle;
import com.spring.boot.repository.JobTitleRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryJobTitleRepository implements JobTitleRepository {

    private final Map<Integer, JobTitle> store = new ConcurrentHashMap<>();

    public InMemoryJobTitleRepository() {
        initializeDummyData();
    }

    private void initializeDummyData() {
        save(createJobTitle(1, "Software Developer"));
        save(createJobTitle(2, "Senior Software Developer"));
        save(createJobTitle(3, "Technical Lead"));
        save(createJobTitle(4, "Software Architect"));
        save(createJobTitle(5, "QA Engineer"));
        save(createJobTitle(6, "Project Manager"));
        save(createJobTitle(7, "Business Analyst"));
    }

    private JobTitle createJobTitle(Integer id, String name) {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleId(id);
        jobTitle.setJobTitleName(name);
        return jobTitle;
    }

    @Override
    public List<JobTitle> saveAll(Iterable<JobTitle> jobTitles) {
        List<JobTitle> saved = new ArrayList<>();
        jobTitles.forEach(jobTitle -> {
            store.put(jobTitle.getJobTitleId(), jobTitle);
            saved.add(jobTitle);
        });
        return saved;
    }

    @Override
    public Optional<JobTitle> findById(Integer id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<JobTitle> getJobTitleByName(String name) {
        return store.values().stream()
                .filter(jobTitle -> Objects.equals(jobTitle.getJobTitleName(), name))
                .findFirst();
    }

    @Override
    public List<JobTitle> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<JobTitle> findAllById(Iterable<Integer> ids) {
        List<JobTitle> result = new ArrayList<>();
        ids.forEach(id -> {
            JobTitle jobTitle = store.get(id);
            if (jobTitle != null) {
                result.add(jobTitle);
            }
        });
        return result;
    }

    private JobTitle save(JobTitle jobTitle) {
        store.put(jobTitle.getJobTitleId(), jobTitle);
        return jobTitle;
    }
}
