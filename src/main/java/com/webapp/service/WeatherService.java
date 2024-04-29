package com.webapp.service;

import com.webapp.payload.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherData getWeather(String city, String date) {
        String url = apiUrl + "?code=" + apiKey + "&city=" + city + "&date=" + date;
        return restTemplate.getForObject(url,WeatherData.class);
    }
}
