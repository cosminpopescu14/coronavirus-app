package com.example.coronavirusdemo.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Cases {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private Long cases;

    protected Cases() {} //The default constructor exists only for the sake of JPA. You do not use it directly, so it is designated as protected.

    public Cases(LocalDate _date, Long _cases) {
        this.date = _date;
        this.cases = _cases;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCases() {
        return cases;
    }

    public void setCases(Long cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "Cases{" +
               "id=" + id +
               ", date=" + date +
               ", cases=" + cases +
               '}';
    }






}
