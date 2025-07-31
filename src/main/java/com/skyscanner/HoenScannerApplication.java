package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hoen-scanner";
    }

    @Override
    public void initialize(final Bootstrap<HoenScannerConfiguration> bootstrap) {

    }

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResult> searchResults = new ArrayList<>();

        try (InputStream carStream = getClass().getResourceAsStream("/rental_cars.json");
             InputStream hotelStream = getClass().getResourceAsStream("/hotels.json")) {

            List<SearchResult> cars = mapper.readValue(carStream, new TypeReference<List<SearchResult>>() {});
            List<SearchResult> hotels = mapper.readValue(hotelStream, new TypeReference<List<SearchResult>>() {});
            searchResults.addAll(cars);
            searchResults.addAll(hotels);
        }

        environment.jersey().register(new SearchResource(searchResults));
    }

}
