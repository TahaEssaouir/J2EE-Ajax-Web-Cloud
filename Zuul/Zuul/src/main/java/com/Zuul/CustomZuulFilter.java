package com.Zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@Component
public class CustomZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre"; // This filter will execute before routing the request
    }

    @Override
    public int filterOrder() {
        return 1; // Lower values are executed first
    }

    public boolean shouldFilter() {
        return true; // This filter will apply for all requests
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // Example 1: Log Request Method and URL
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URL: " + request.getRequestURL().toString());

        // Example 2: Authentication Check
        String authToken = request.getHeader("Authorization");
        if (authToken == null || !isValidToken(authToken)) {
            ctx.setResponseStatusCode(401); // Unauthorized
            ctx.setResponseBody("Unauthorized access - token is missing or invalid.");
            ctx.setSendZuulResponse(false); // Prevent routing the request
            return null; // Early exit from filter
        }

        // Example 3: Modify Request Headers
        ctx.addZuulRequestHeader("X-Custom-Header", "CustomValue");

        // Example 4: Rate Limiting (Basic Example)
        Integer requestCount = (Integer) ctx.get("requestCount");
        if (requestCount == null) {
            requestCount = 0;
        }
        requestCount++;
        ctx.set("requestCount", requestCount);
        
        // Assuming a simple rate limit of 100 requests
        if (requestCount > 100) {
            ctx.setResponseStatusCode(429); // Too Many Requests
            ctx.setResponseBody("Rate limit exceeded - please try again later.");
            ctx.setSendZuulResponse(false); // Prevent routing the request
            return null; // Early exit from filter
        }

        // Example 5: Add Request Time to Header
        long startTime = System.currentTimeMillis();
        ctx.addZuulRequestHeader("X-Request-Time", String.valueOf(startTime));

        // Example 6: Validate Request Parameters
        String requiredParam = request.getParameter("requiredParam");
        if (requiredParam == null || requiredParam.isEmpty()) {
            ctx.setResponseStatusCode(400); // Bad Request
            ctx.setResponseBody("Missing required parameter: requiredParam");
            ctx.setSendZuulResponse(false); // Prevent routing the request
            return null; // Early exit from filter
        }

        // Example 7: IP Whitelisting
        List allowedIPs = Arrays.asList("192.168.1.100", "192.168.1.101"); // Example IPs
        String clientIP = request.getRemoteAddr();
        if (!allowedIPs.contains(clientIP)) {
            ctx.setResponseStatusCode(403); // Forbidden
            ctx.setResponseBody("Forbidden access - your IP is not allowed.");
            ctx.setSendZuulResponse(false); // Prevent routing the request
            return null; // Early exit from filter
        }

        // Example 8: Service Discovery and Redirection (Custom Logic)
        String serviceId = "microserviceclient1";
        if (serviceId.equals(request.getParameter("serviceId"))) {
            ctx.set("serviceId", serviceId); // Optionally set serviceId in context
        }

        // Example 9: Modify Request URL Path
        String newPath = "/modified-path" + request.getRequestURI();
        ctx.set("requestURI", newPath); // Modify the request URI
        ctx.set("requestPath", newPath); // Optionally set modified request path in context

        return null; // Returning null means no additional processing
    }

    // Method to validate the token (placeholder logic)
    private boolean isValidToken(String token) {
        return "valid-token".equals(token); // Replace with actual validation logic
    }
}