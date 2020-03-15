package com.example.coronavirusdemo.jobs;

import com.example.coronavirusdemo.models.CoronaVirusStatsRo;
import com.example.coronavirusdemo.models.StatsRo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//TODO
// The deserializasion is not done correctly
@Component
public class DataFetchingJobFromApi {

    private static final Logger log = LoggerFactory.getLogger(DataFetchingJobFromApi.class);

    //Doresc sa multumesc comunitatii http://geo-spatial.org/ pentru api-urile puse la dispozitie
    @Value("${api.url}")
    private String DATA_URL; //must not be static or final

    @Autowired
    private WebClient.Builder webClient;


    @PostConstruct
    @Scheduled(cron = "0 0/5 * * * ?") //run on 5 min basis
    public void getData() {

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.info("job {} started at {}", getClass().getMethods()[0], LocalDateTime.now().format(formatter));

        var roStats = webClient
                        .build()
                        .get()
                        .uri(DATA_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(StatsRo.class)
                        .block();

        System.out.println(roStats.getData().getTotal());
    }
}
