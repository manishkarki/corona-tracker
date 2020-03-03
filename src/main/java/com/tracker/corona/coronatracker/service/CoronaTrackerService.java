package com.tracker.corona.coronatracker.service;

import com.tracker.corona.coronatracker.model.LocationStatistics;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * @author mkarki
 */
@Service
public class CoronaTrackerService {
    private final Logger LOGGER = LoggerFactory.getLogger(CoronaTrackerService.class);

    private final String VIRUS_DATA_URL;
    private final String VIRUS_DATA_URL_LITERAL = "virus.data.url";

    private List<LocationStatistics> locationStatistics = new ArrayList<>();
    private final HttpClient client;
    private final HttpRequest httpRequest;

    @Autowired
    public CoronaTrackerService(Environment environment) {
        this.VIRUS_DATA_URL = environment.getRequiredProperty(VIRUS_DATA_URL_LITERAL, String.class);
        this.client =  HttpClient.newHttpClient();
        this.httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
    }

    public List<LocationStatistics> getLocationStatistics() {
        return locationStatistics;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStatistics> newStats = new ArrayList<>();
        HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationStatistics locationStat = new LocationStatistics();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDifferenceFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
        Collections.sort(newStats, Comparator.comparing(LocationStatistics::getCountry));
        this.locationStatistics = newStats;
        LOGGER.info("record updated at:"+ new Date());
    }

}
