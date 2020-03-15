package com.example.coronavirusdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

//For Romania
public class CoronaVirusData {

    private String countyCode;
    private Long totalCounty;
    private Long totalHealed; //:)
    private Long totalDead;
    private String county;

    @JsonProperty("county_code")
    public String getCountyCode() { return countyCode; }
    @JsonProperty("county_code")
    public void setCountyCode(String value) { this.countyCode = value; }

    @JsonProperty("total_county")
    public Long getTotalCounty() { return totalCounty; }
    @JsonProperty("total_county")
    public void setTotalCounty(Long value) { this.totalCounty = value; }

    @JsonProperty("total_healed")
    public Long getTotalHealed() { return totalHealed; }
    @JsonProperty("total_healed")
    public void setTotalHealed(Long value) { this.totalHealed = value; }

    @JsonProperty("total_dead")
    public Long getTotalDead() { return totalDead; }
    @JsonProperty("total_dead")
    public void setTotalDead(Long value) { this.totalDead = value; }

    @JsonProperty("county")
    public String getCounty() { return county; }
    @JsonProperty("county")
    public void setCounty(String value) { this.county = value; }
}
