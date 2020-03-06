package com.example.coronavirusdemo.dal;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CasesRepository extends CrudRepository<Cases, Long> {

    Cases save(Cases cases);
    Cases findByDate(LocalDate date);
    List<Cases> findAll();

}
