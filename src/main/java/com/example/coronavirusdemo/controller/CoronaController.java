package com.example.coronavirusdemo.controller;


import com.example.coronavirusdemo.jobs.DataFetchingJob;
import com.example.coronavirusdemo.jobs.DataFetchingJobFromApi;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import com.example.coronavirusdemo.models.StatsRo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class CoronaController {

    private static final Logger log = LoggerFactory.getLogger(CoronaController.class);

    DataFetchingJob dataFetchingJob;
    DataFetchingJobFromApi dataFetchingJobFromApi;

    public CoronaController(DataFetchingJob dataFetchingJob, DataFetchingJobFromApi dataFetchingJobFromApi) {
        this.dataFetchingJob = dataFetchingJob;
        this.dataFetchingJobFromApi = dataFetchingJobFromApi;
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

    @GetMapping("/roStats")
    public String stats(Model model) {

        var totalCasesInRo = dataFetchingJobFromApi.getAllStats().get(0).getData().getTotal();
        var statisticsPerCounty = dataFetchingJobFromApi.getAllStats().get(0).getData().getData();

        dataFetchingJobFromApi.getAllStats().forEach(s -> log.info(s.getData().getTotal().toString()));
        model.addAttribute("totalRoCases", totalCasesInRo);
        model.addAttribute("statisticsPerCounty", statisticsPerCounty);

        return "roStats";
    }

    @GetMapping("/roChart")
    public String roChart() {
        return "roChart";
    }
}
