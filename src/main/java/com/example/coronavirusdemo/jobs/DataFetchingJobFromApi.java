package com.example.coronavirusdemo.jobs;

import com.example.coronavirusdemo.models.CoronaVirusData;
import com.example.coronavirusdemo.models.CoronaVirusStats;
import com.example.coronavirusdemo.models.StatsRo;
import com.example.coronavirusdemo.services.CoronaVirusDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;


@Component
public class DataFetchingJobFromApi {

    private static final Logger log = LoggerFactory.getLogger(DataFetchingJobFromApi.class);

    //Doresc sa multumesc comunitatii http://geo-spatial.org/ pentru api-urile puse la dispozitie
    @Value("${api.url}")
    private String DATA_URL;

    private WebClient.Builder webClient;
    private CoronaVirusDataService coronaVirusDataService;

    public DataFetchingJobFromApi(WebClient.Builder webClient, CoronaVirusDataService coronaVirusDataService) {
        this.webClient = webClient;
        this.coronaVirusDataService = coronaVirusDataService;
    }

    private List<StatsRo> allStats = new ArrayList<>();
    public List<StatsRo> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "0 0/5 * * * ?") //run on 5 min basis
    public void getData() {

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.info("job {} started at {}", getClass().getMethods()[0], now().format(formatter));
        var newStats = new ArrayList<StatsRo>();

        try {
            var roStats = webClient
                            .build()
                            .get()
                            .uri(DATA_URL)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(StatsRo.class)
                            .block();

            newStats.add(roStats);
            this.allStats = newStats;
            coronaVirusDataService.saveRoCases();
            log.info("Got a fresh case {} at {}", allStats.get(0).getData().getTotal(), now().format(formatter));
        }
        catch (Exception e) {
            log.error("Exception including loss of connectivity or could not deserialize", e);
        }
    }
}
