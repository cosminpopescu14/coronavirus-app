package com.example.coronavirusdemo.controller;


import com.example.coronavirusdemo.models.CoronaVirusStats;
import com.example.coronavirusdemo.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CoronaController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<CoronaVirusStats> coronaVirusStats = coronaVirusDataService.getAllStats();

        var totalReportedCases = coronaVirusStats.stream().mapToInt(CoronaVirusStats::getLatestTotalCases).sum();
        var totalNewCases = coronaVirusStats.stream().mapToInt(CoronaVirusStats::getDiffFromPrevDay).sum();

        model.addAttribute("locationStats", coronaVirusStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }
}
