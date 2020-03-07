package com.example.coronavirusdemo.services;

import com.example.coronavirusdemo.dal.Cases;
import com.example.coronavirusdemo.dal.CasesRepository;
import com.example.coronavirusdemo.jobs.DataFetchingJob;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class CoronaVirusDataService {


    @Autowired
    private CasesRepository casesRepository;

    private DataFetchingJob dataFetchingJob;

    public CoronaVirusDataService(@Lazy DataFetchingJob dataFetchingJob) {
        this.dataFetchingJob = dataFetchingJob;
    }


    @Value("${user.timezone}")
    private String userTimeZone;


    private static final Logger log = LoggerFactory.getLogger(CoronaVirusDataService.class);

    public void save() {
        var coronaVirusStats = dataFetchingJob.getAllStats();

        var date = LocalDate.now(ZoneId.of(userTimeZone));
        var totalCases = coronaVirusStats.stream()
                .mapToLong(CoronaVirusStats::getLatestTotalCases)
                .sum();
        var cases = new Cases(date, totalCases);

        try {
            casesRepository.save(cases);
            log.info("Persisted record", cases.toString());
        }
        catch (DataIntegrityViolationException ex) {
            log.info("An exception of data integrity occurred", ex);
        }
    }

    public List<Cases> findAllCases() {
        return casesRepository.findAll();
    }
}
