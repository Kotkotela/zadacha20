package org.example.weather.controller;

import lombok.Value;
import org.example.weather.model.Main;
import org.example.weather.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;


@RestController
public class WeatherController {

    @Autowired
    private RestTemplate restTemplate;

    private final String appId = "688ad16d0a70af2b0104911a47817045";
    private final String urlWeather = "https://api.openweathermap.org/data/2.5/weather";

    @Cacheable(value = "weather", key = "#lat + '-' + #lon")
    @GetMapping("/weather")
    public Main getWeather(@RequestParam String lat, @RequestParam String lon) {
        String request = String.format("%s?lat=%s&lon=%s&units=metric&appid=%s",
                urlWeather, lat, lon, appId);
        return restTemplate.getForObject(request, Root.class).getMain();
    }
}




