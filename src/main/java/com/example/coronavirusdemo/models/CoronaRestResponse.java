package com.example.coronavirusdemo.models;

import java.time.Instant;

public class CoronaRestResponse {


    public Long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(Long unixTime) {
        this.unixTime = unixTime;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "CoronaRestResponse{" +
               "unixTime=" + unixTime +
               ", cases=" + cases +
               '}';
    }

    private Long unixTime;
    private int cases;


}
