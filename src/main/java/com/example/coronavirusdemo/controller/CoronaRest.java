package com.example.coronavirusdemo.controller;

import com.example.coronavirusdemo.models.CoronaRestResponse;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import com.example.coronavirusdemo.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class CoronaRest {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/chart")
    public CoronaRestResponse chart() {
        List<CoronaVirusStats> coronaVirusStats = coronaVirusDataService.getAllStats();
        var response = new CoronaRestResponse();
        var totalReportedCases = coronaVirusStats.stream().mapToInt(CoronaVirusStats::getLatestTotalCases).sum();
        response.setUnixTime(Instant.now().getEpochSecond());
        response.setCases(totalReportedCases);
        System.out.println(response);
        return response;
    }
}
