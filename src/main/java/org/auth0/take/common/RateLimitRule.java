package org.auth0.take.common;

public class RateLimitRule {
    private String endpoint;
    private int burst;
    private int sustained;
    private int  refillRate;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public int getSustained() {
        return sustained;
    }

    public void setSustained(int sustained) {
        this.sustained = sustained;
        this.refillRate = 60000 / sustained;
    }

    public int getRefillRate(){
        return refillRate;
    }

}