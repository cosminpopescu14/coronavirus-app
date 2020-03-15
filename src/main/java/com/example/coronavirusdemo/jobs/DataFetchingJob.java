package com.example.coronavirusdemo.jobs;

import com.example.coronavirusdemo.models.CoronaVirusStats;
import com.example.coronavirusdemo.services.CoronaVirusDataService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataFetchingJob {

    private static final Logger log = LoggerFactory.getLogger(DataFetchingJob.class);


    private CoronaVirusDataService coronaVirusDataService;

    private List<CoronaVirusStats> allStats = new ArrayList<>();
    public List<CoronaVirusStats> getAllStats() {
        return allStats;
    }

    //the Lazy annotation is due to avoid circular dependency. It it a workaround
    //the design shoud be revised in order to avoid that
    //https://docs.spring.io/spring/docs/4.3.10.RELEASE/spring-framework-reference/htmlsingle/#beans-dependency-resolution
    public DataFetchingJob(@Lazy CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @Value("${corona.data.url}")
    private String DATA_URL; //must not be static or final

    @PostConstruct
    @Scheduled(cron = "0 0 7 * * *")
    public void fetchData() throws IOException, InterruptedException {

        List<CoronaVirusStats> newStats = new ArrayList<>();
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(DATA_URL))
                .build();

        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (var record : records) {

            try{
                var stats = new CoronaVirusStats();

                stats.setState(record.get("Province/State"));
                stats.setCountry(record.get("Country/Region"));

                var latestCases = Integer.parseInt(record.get(record.size() - 1));
                var prevDayCases = Integer.parseInt(record.get(record.size() - 2));

                stats.setLatestTotalCases(latestCases);
                stats.setDiffFromPrevDay(latestCases - prevDayCases); //current - prev
                newStats.add(stats);
            }
            catch (NumberFormatException ex) {
                log.info("An exception", ex);
            }
        }
        this.allStats = newStats;
        coronaVirusDataService.save();
    }
}
