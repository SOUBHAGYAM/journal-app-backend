package com.Ilearn.journalApp.service;

import com.Ilearn.journalApp.api.response.WeatherResponse;
import com.Ilearn.journalApp.cache.AppCache;
import com.Ilearn.journalApp.constants.Placeholders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherService {

	@Value("${weather_api_key:}")  // empty string as default
	private String weatherApiKey;
  //  private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate; //process http request and provides response

    public WeatherResponse getWeather(String city) {
        try {
            String url = appCache.appCache.get(AppCache.keys.WEATHER_API.toString())
                    .replace(Placeholders.CITY, city)
                    .replace(Placeholders.API_KEY, apikey);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            String body = response.getBody();

            if (body == null || body.contains("\"success\":false")) return null;

            return new ObjectMapper().readValue(body, WeatherResponse.class);
        } catch (Exception e) {
            System.err.println("❌ Weather API failed: " + e.getMessage());
            return null;
        }
    }
}
