package com.mjobb.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Value("${cors.allowed.origin}")
    private String corsAllowedOrigin;

    @Value("${cors.allowed.methods}")
    private String corsAllowedMethods;

    @Value("${cors.allowed.headers}")
    private String corsAllowedHeaders;

    @Value("${cors.allowed.max.age}")
    private String corsAllowedMaxAge;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, corsAllowedOrigin);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, corsAllowedMethods);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, corsAllowedHeaders);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, corsAllowedMaxAge);

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
