package com.example.coronavirusdemo.controller;

import com.example.coronavirusdemo.models.CasesRoEntityResponses;
import com.example.coronavirusdemo.models.CoronaRestResponse;
import com.example.coronavirusdemo.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CoronaRest {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/chart")
    public List<CoronaRestResponse> chart() {
        var cases = coronaVirusDataService.findAllCases();

        var response = cases
                 .stream()
                 .map(c -> new CoronaRestResponse(c.getDate(), c.getCases().intValue()))
                 .collect(Collectors.toList());
        return response;
    }

    @GetMapping("/rochart")
    public List<CasesRoEntityResponses> roChartData() {

        var roCases = coronaVirusDataService.findAllRoCases();

        return roCases
                .stream()
                .map(r -> new CasesRoEntityResponses(r.getDate(), r.getCases().intValue()))
                .collect(Collectors.toList());
    }
}
