package com.spring.boot.controller;

import com.spring.boot.model.JobTitle;
import com.spring.boot.service.JobTitleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobTitleControllerTest {

    @Mock
    private JobTitleService jobTitleService;

    @InjectMocks
    private JobTitleController jobTitleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateJobTitle() {
        List<JobTitle> jobTitles = Arrays.asList(new JobTitle(), new JobTitle());
        when(jobTitleService.saveJobTitle(jobTitles)).thenReturn("Success");
        ResponseEntity<String> response = jobTitleController.createJobTitle(jobTitles);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); // Adjusted to match controller behavior
        assertEquals("Success", response.getBody());
        verify(jobTitleService, times(1)).saveJobTitle(jobTitles);
    }

    @Test
    void testGetJobById() {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleId(1);
        jobTitle.setJobTitleName("Developer");
        when(jobTitleService.getJobById(1)).thenReturn(jobTitle);
        ResponseEntity<JobTitle> response = jobTitleController.getJobById(1);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobTitle, response.getBody());
        verify(jobTitleService, times(1)).getJobById(1);
    }

    @Test
    void testGetAllJobTitles() {
        List<JobTitle> jobTitles = Arrays.asList(new JobTitle(), new JobTitle());
        when(jobTitleService.getAllJobTitles()).thenReturn(jobTitles);
        ResponseEntity<List<JobTitle>> response = jobTitleController.getAllJobTitles();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobTitles, response.getBody());
        verify(jobTitleService, times(1)).getAllJobTitles();
    }

    @Test
    void testGetJobTitleByName() {
        JobTitle jobTitle = new JobTitle();
        jobTitle.setJobTitleName("Tester");
        when(jobTitleService.getJobTitleByName("Tester")).thenReturn(jobTitle);
        ResponseEntity<JobTitle> response = jobTitleController.getJobTitleByName("Tester");
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobTitle, response.getBody());
        verify(jobTitleService, times(1)).getJobTitleByName("Tester");
    }

    @Test
    void testCreateJobTitle_withNullInput_shouldReturnBadRequest() {
        ResponseEntity<String> response = jobTitleController.createJobTitle(null);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
