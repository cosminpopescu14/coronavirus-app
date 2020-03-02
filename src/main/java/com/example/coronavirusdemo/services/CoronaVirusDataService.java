package com.example.coronavirusdemo.services;

import com.example.coronavirusdemo.models.CoronaVirus;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private final static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private List<CoronaVirus> allStats = new ArrayList<>();

    public List<CoronaVirus> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {

        List<CoronaVirus> newStats = new ArrayList<>();
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(DATA_URL))
                .build();

        var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        var csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);


        for(var record : records) {


            /*stats.state(record.get("Province/State"));
            stats.country(record.get("Country/Region"));*/

            var latestCases = Integer.parseInt(record.get(record.size() - 1));
            var prevDayCases = Integer.parseInt(record.get(record.size() - 2));

            /*stats.setLatestTotalCases(latestCases);
            stats.setDiffFromPrevDay(latestCases - prevDayCases); //current - prev*/
            var stats = new CoronaVirus(record.get("Province/State"), record.get("Country/Region"),
                    latestCases, (latestCases - prevDayCases));
            newStats.add(stats);
        }
        this.allStats = newStats;
    }
}
