package com.example.coronavirusdemo.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//For Romania
public class CoronaVirusStatsRo {

    private Long total;
    private List<CoronaVirusData> data;

    @JsonProperty("total")
    public Long getTotal() { return total; }
    @JsonProperty("total")
    public void setTotal(Long value) { this.total = value; }

    @JsonProperty("data")
    public List<CoronaVirusData> getData() { return data; }
    @JsonProperty("data")
    public void setData(List<CoronaVirusData> value) { this.data = value; }

    @Override
    public String toString() {
        return "CoronaVirusStatsRo{" +
               "total=" + total +
               '}';
    }
}