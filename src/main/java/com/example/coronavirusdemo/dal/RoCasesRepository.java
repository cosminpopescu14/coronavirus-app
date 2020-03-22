package com.example.coronavirusdemo.dal;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface RoCasesRepository extends CrudRepository<RoCases, Long> {

    RoCases save(Cases cases);
    Cases findByDate(LocalDate date);
    List<RoCases> findAll();
}
