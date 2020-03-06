package com.example.coronavirusdemo.services;

import com.example.coronavirusdemo.dal.Cases;
import com.example.coronavirusdemo.dal.CasesRepository;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private final static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private List<CoronaVirusStats> allStats = new ArrayList<>();

    public List<CoronaVirusStats> getAllStats() {
        return allStats;
    }

    @Autowired
    private CasesRepository casesRepository;

    private static final Logger log = LoggerFactory.getLogger(CoronaVirusDataService.class);

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {

        List<CoronaVirusStats> newStats = new ArrayList<>();
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(DATA_URL))
                .build();

        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);


        for(var record : records) {
            var stats = new CoronaVirusStats();

            stats.setState(record.get("Province/State"));
            stats.setCountry(record.get("Country/Region"));

            var latestCases = Integer.parseInt(record.get(record.size() - 1));
            var prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            stats.setLatestTotalCases(latestCases);
            stats.setDiffFromPrevDay(latestCases - prevDayCases); //current - prev
            newStats.add(stats);
        }
        this.allStats = newStats;
        save();
    }

    public void save() {
        var coronaVirusStats = getAllStats();

        var date = LocalDate.now(ZoneId.of("Europe/Bucharest"));
        var totalCases = coronaVirusStats.stream()
                .mapToLong(CoronaVirusStats::getLatestTotalCases)
                .sum();
        var cases = new Cases(date, totalCases);
        casesRepository.save(cases);
        log.info("Persisted record {0}, {1}, {2}", cases.toString());
    }

    public List<Cases> findAllCases() {
        return casesRepository.findAll();
    }
}
