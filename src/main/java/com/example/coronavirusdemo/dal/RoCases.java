package com.example.coronavirusdemo.dal;

import javax.persistence.*;
import java.time.LocalDateTime;

//the class have the same structure as Cases
//later, should implement a BaseEntity<Cases>

@Entity
@Table(name = "rocases", schema = "coronavirus") //table with name and in schema. a database is called schema in mysql
//see https://thoughts-on-java.org/hibernate-tips-define-schema-table-names/ for custom table name
public class RoCases {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime date;

    private Long cases;

    protected RoCases() {} //The default constructor exists only for the sake of JPA. You do not use it directly, so it is designated as protected.

    public RoCases(LocalDateTime _date, Long _cases) {
        this.date = _date;
        this.cases = _cases;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
