package com.example.coronavirusdemo.controller;

import com.example.coronavirusdemo.models.CoronaRestResponse;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import com.example.coronavirusdemo.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CoronaRest {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/chart")
    public List<CoronaRestResponse> chart() {
        List<CoronaVirusStats> coronaVirusStats = coronaVirusDataService.getAllStats();

        var totalReportedCases = coronaVirusStats
                .stream()
                .mapToInt(CoronaVirusStats::getLatestTotalCases)
                .sum();

        System.out.println(List.of(new CoronaRestResponse(LocalDate.now(ZoneId.of("Europe/Bucharest")), totalReportedCases)));
        return List
                .of(new CoronaRestResponse(LocalDate.now(ZoneId.of("Europe/Bucharest")), totalReportedCases));
    }
}
