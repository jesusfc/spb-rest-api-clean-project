package com.jesusfc.spb.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

/**
 * Author Jesús Fdez. Caraballo
 * jesus.fdez.caraballo@gmail.com
 * Created on nov - 2024
 */
public class TestSecurityBeforeFilter extends HttpFilter {

    // Get api-key from same place as in application.properties or application.yml or other configuration file, etc.
    public static String API_KEY = "my-api-key";
    public static String HEADER_PROPERTY_NAME = "API-KEY";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String header = servletRequest.getHeader(HEADER_PROPERTY_NAME);

        if (header != null && header.contains(API_KEY)) {
            chain.doFilter(request, response);
        } else {
            throw new AccessDeniedException("DENIED");
        }

    }
}
