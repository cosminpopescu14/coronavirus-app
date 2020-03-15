package com.example.coronavirusdemo.models;

import java.time.LocalDate;

//For corona cases globally
public class CoronaRestResponse {

    private LocalDate unixTime;
    private int cases;

    public LocalDate getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(LocalDate unixTime) {
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

    public CoronaRestResponse(){}

    public CoronaRestResponse(LocalDate unixTime, int cases) {
        this.unixTime = unixTime;
        this.cases = cases;
    }
}
