package com.example.coronavirusdemo.dal;

import java.time.LocalDateTime;

//later should be used as a base entity for cases
public class BaseCasesEntity<T> {

    private Long id;
    private LocalDateTime date;
    private Long cases;
}
