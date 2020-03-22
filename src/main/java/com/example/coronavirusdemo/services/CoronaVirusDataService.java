package com.example.coronavirusdemo.services;

import com.example.coronavirusdemo.dal.Cases;
import com.example.coronavirusdemo.dal.CasesRepository;
import com.example.coronavirusdemo.dal.RoCases;
import com.example.coronavirusdemo.dal.RoCasesRepository;
import com.example.coronavirusdemo.jobs.DataFetchingJob;
import com.example.coronavirusdemo.jobs.DataFetchingJobFromApi;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CoronaVirusDataService {


    @Autowired
    private CasesRepository casesRepository;

    @Autowired
    private RoCasesRepository roCasesRepository;

    private DataFetchingJob dataFetchingJob;
    private DataFetchingJobFromApi dataFetchingJobFromApi;

    public CoronaVirusDataService(@Lazy DataFetchingJob dataFetchingJob, @Lazy DataFetchingJobFromApi dataFetchingJobFromApi) {
        this.dataFetchingJob = dataFetchingJob;
        this.dataFetchingJobFromApi = dataFetchingJobFromApi;
    }


    @Value("${user.timezone}")
    private String userTimeZone;


    private static final Logger log = LoggerFactory.getLogger(CoronaVirusDataService.class);

    public void saveGlobalCases() {
        var coronaVirusStats = dataFetchingJob.getAllStats();
        var date = LocalDate.now(ZoneId.of(userTimeZone));
        var totalCases = coronaVirusStats.stream()
                .mapToLong(CoronaVirusStats::getLatestTotalCases)
                .sum();
        var cases = new Cases(date, totalCases);

        try {
            casesRepository.save(cases);
            log.info("Persisted record {}", cases);
        }
        catch (DataIntegrityViolationException ex) {
            log.info("An exception of data integrity occurred", ex);
        }
    }

    public void saveRoCases() {
        var coronaVirusStats = dataFetchingJobFromApi.getAllStats();
        var date = LocalDateTime.now(ZoneId.of(userTimeZone));
        var totalCases = coronaVirusStats.get(0).getData().getTotal();
        var cases = new RoCases(date, totalCases);

        try {
            roCasesRepository.save(cases);
            log.info("Persisted record {}", cases);
        }
        catch (DataIntegrityViolationException ex) {
            log.info("An exception of data integrity occurred", ex);
        }
    }

    public List<Cases> findAllCases() {
        return casesRepository.findAll();
    }

    public List<RoCases> findAllRoCases() {
        return  roCasesRepository.findAll();
    }

}
