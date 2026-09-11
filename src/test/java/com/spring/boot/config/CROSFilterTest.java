package com.spring.boot.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CROSFilterTest {
    private CROSFilter crosFilter;
    private ServletRequest servletRequest;
    private FilterChain filterChain;
    private HttpServletResponse httpServletResponse;

    @BeforeEach
    void setUp() {
        crosFilter = new CROSFilter();
        servletRequest = Mockito.mock(ServletRequest.class);
        HttpServletResponse servletResponse = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);
        httpServletResponse = servletResponse;
    }

    @Test
    void testDoFilter_setsCORSHeaders() throws IOException, ServletException {
        crosFilter.doFilter(servletRequest, httpServletResponse, filterChain);
        Mockito.verify(httpServletResponse).setHeader("Access-Control-Allow-Origin", "*");
        Mockito.verify(httpServletResponse).setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        Mockito.verify(httpServletResponse).setHeader("Access-Control-Max-Age", "3600");
        Mockito.verify(httpServletResponse).setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");
        Mockito.verify(httpServletResponse).setHeader("Access-Control-Expose-Headers", "Location");
        Mockito.verify(filterChain).doFilter(servletRequest, httpServletResponse);
    }

    @Test
    void testInitAndDestroy_doNotThrow() {
        assertDoesNotThrow(() -> crosFilter.init(Mockito.mock(jakarta.servlet.FilterConfig.class)));
        assertDoesNotThrow(() -> crosFilter.destroy());
    }
}

