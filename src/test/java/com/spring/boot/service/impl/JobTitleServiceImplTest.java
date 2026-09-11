package com.spring.boot.service.impl;

import com.spring.boot.model.JobTitle;
import com.spring.boot.repository.JobTitleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobTitleServiceImplTest {

    @Mock
    private JobTitleRepository jobTitleRepository;

    @InjectMocks
    private JobTitleServiceImpl jobTitleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetJobNamesByIds_ReturnsNames() {
        JobTitle jt1 = new JobTitle();
        jt1.setJobTitleName("Developer");
        JobTitle jt2 = new JobTitle();
        jt2.setJobTitleName("Manager");
        List<JobTitle> jobTitles = Arrays.asList(jt1, jt2);
        List<Integer> ids = Arrays.asList(1, 2);
        when(jobTitleRepository.findAllById(ids)).thenReturn(jobTitles);

        List<JobTitle> result = jobTitleService.getJobNamesByIds(ids);
        assertNotNull(result);
    }

    @Test
    void testGetJobById_Found() {
        JobTitle jt = new JobTitle();
        jt.setJobTitleName("Tester");
        when(jobTitleRepository.findById(1)).thenReturn(Optional.of(jt));
        JobTitle result = jobTitleService.getJobById(1);
        assertEquals("Tester", result.getJobTitleName());
    }

    @Test
    void testGetJobById_NotFound() {
        when(jobTitleRepository.findById(99)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> jobTitleService.getJobById(99));
        assertTrue(exception.getMessage().contains("Job Title not found with id: 99"));
    }

    @Test
    void testGetAllJobTitles() {
        JobTitle jt = new JobTitle();
        jt.setJobTitleName("Lead");
        when(jobTitleRepository.findAll()).thenReturn(Arrays.asList(jt));
        List<JobTitle> result = jobTitleService.getAllJobTitles();
        assertEquals(1, result.size());
        assertEquals("Lead", result.get(0).getJobTitleName());
    }

    @Test
    void testGetJobTitleByName_Found() {
        JobTitle jt = new JobTitle();
        jt.setJobTitleName("Architect");
        when(jobTitleRepository.getJobTitleByName("Architect")).thenReturn(Optional.of(jt));
        JobTitle result = jobTitleService.getJobTitleByName("Architect");
        assertEquals("Architect", result.getJobTitleName());
    }

    @Test
    void testGetJobTitleByName_NotFound() {
        when(jobTitleRepository.getJobTitleByName("Unknown")).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> jobTitleService.getJobTitleByName("Unknown"));
        assertTrue(exception.getMessage().contains("Job Title not found with name: Unknown"));
    }
}

