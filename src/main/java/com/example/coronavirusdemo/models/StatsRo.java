package com.example.coronavirusdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsRo {

    private CoronaVirusStatsRo data;

    @JsonProperty("data")
    public CoronaVirusStatsRo getData() { return data; }
    @JsonProperty("data")
    public void setData(CoronaVirusStatsRo value) { this.data = value; }


}
