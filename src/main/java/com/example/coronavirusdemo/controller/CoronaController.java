package com.example.coronavirusdemo.controller;


import com.example.coronavirusdemo.jobs.DataFetchingJob;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class CoronaController {

    DataFetchingJob dataFetchingJob;

    public CoronaController(DataFetchingJob dataFetchingJob) {
        this.dataFetchingJob = dataFetchingJob;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<CoronaVirusStats> coronaVirusStats = dataFetchingJob.getAllStats();

        var totalReportedCases = coronaVirusStats.stream().mapToInt(CoronaVirusStats::getLatestTotalCases).sum();
        var totalNewCases = coronaVirusStats.stream().mapToInt(CoronaVirusStats::getDiffFromPrevDay).sum();

        model.addAttribute("locationStats", coronaVirusStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return "home";
    }

    @GetMapping("/chart2")
    public String chart() {
        return "chart";
    }
}
