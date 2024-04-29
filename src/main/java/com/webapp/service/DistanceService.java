package com.webapp.service;

import com.webapp.payload.DistanceDto;
import com.webapp.payload.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DistanceService {


    @Value("${distance.api.url}")
    private String apiUrl;

    @Value("${distance.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public DistanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public double getDistance(double lat1, double lon1,double lon2,double lat2) {
        String url = apiUrl + "?code=" + apiKey + "&latitude1=" + lat1 + "&longitude1=" + lon1
                + "&latitude2=" + lat2 + "&longitude2=" + lon2;
        DistanceDto distance = restTemplate.getForObject(url, DistanceDto.class);
        return distance != null ? distance.getDistance() : -1; // Or handle null appropriately
    }
}
