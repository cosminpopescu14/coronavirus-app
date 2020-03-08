package com.example.coronavirusdemo.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CoronaStats {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public String county;
    public String state;
    public Integer totalCaseReported;
    public Integer changesSinceLastDay;
}
