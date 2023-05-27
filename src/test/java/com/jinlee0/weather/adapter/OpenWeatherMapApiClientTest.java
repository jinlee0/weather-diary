package com.jinlee0.weather.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OpenWeatherMapApiClientTest {
    @Autowired
    OpenWeatherMapApiClient client;
    @Test
    void getWeather() {
        OpenWeatherMapApiClient.GetWeatherResult result = client.getWeather("seoul");
        System.out.println(result);
        assertNotNull(result);
    }
}