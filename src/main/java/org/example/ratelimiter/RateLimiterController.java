package org.example.ratelimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimiterController {

    // Configure rate limiter to allow 1 request per 10 second
    private final RateLimiter rateLimiter = new RateLimiter(2, 0.1);

    @GetMapping("/testRateLimiter")
    public String testRateLimiter() {
        if (rateLimiter.allowRequest()) {
            return "Request allowed!";
        } else {
            return "Too many requests! Please try again later.";
        }
    }
}
