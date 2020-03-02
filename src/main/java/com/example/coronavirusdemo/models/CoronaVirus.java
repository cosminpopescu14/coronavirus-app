package com.example.coronavirusdemo.models;

public record CoronaVirus(String state, String country, int latestTotalCases, int diffFromPrevDay) {
}
