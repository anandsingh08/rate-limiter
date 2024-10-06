package org.example.ratelimiter;

public class RateLimiter {
    private final int capacity;       // Max number of tokens in the bucket
    private final double refillRate;  // Tokens added per second
    private double tokens;            // Current number of tokens
    private long lastRefillTimestamp; // Last refill time in nanoseconds

    public RateLimiter(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTimestamp = System.nanoTime();
    }

    private synchronized void refillTokens() {
        long now = System.nanoTime();
        long timeElapsed = now - lastRefillTimestamp;
        double tokensToAdd = (timeElapsed / 1e9) * refillRate; // Refill tokens based on time elapsed
        tokens = Math.min(capacity, tokens + tokensToAdd);
        lastRefillTimestamp = now;
    }

    public synchronized boolean allowRequest() {
        refillTokens();
        if (tokens >= 1) {
            tokens--;
            return true; // Request is allowed
        }
        return false; // Request is denied
    }
}
