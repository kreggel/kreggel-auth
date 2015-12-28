package de.kreggel.auth.service.bootstrap.internal;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RequestIdLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        String uuid = UUID.randomUUID().toString();

        WebInvocationContext.getInstance().setRequestId(uuid);

        try {
            chain.doFilter(req, resp);
        } finally {
            WebInvocationContext.getInstance().setRequestId(null);
        }
    }

    @Override
    public void destroy() {
    }
}