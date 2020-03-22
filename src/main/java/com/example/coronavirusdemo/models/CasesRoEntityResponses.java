package com.example.coronavirusdemo.models;

import java.time.LocalDateTime;

public class CasesRoEntityResponses {

    private LocalDateTime dateTime;
    private int cases;

    public CasesRoEntityResponses(LocalDateTime dateTime, int cases) {
        this.dateTime = dateTime;
        this.cases = cases;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "CasesRoEntityResponses{" +
               "dateTime=" + dateTime +
               ", cases=" + cases +
               '}';
    }
}
