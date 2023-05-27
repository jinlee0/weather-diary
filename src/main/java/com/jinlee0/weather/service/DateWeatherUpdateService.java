package com.jinlee0.weather.service;

import com.jinlee0.weather.adapter.OpenWeatherMapApiClient;
import com.jinlee0.weather.domain.DateWeather;
import com.jinlee0.weather.repository.DateWeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DateWeatherUpdateService {
    private final DateWeatherRepository dateWeatherRepository;
    private final OpenWeatherMapApiClient client;

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void updateDateWeather() {
        dateWeatherRepository.save(getDateWeatherFromApi());
    }

    private DateWeather getDateWeatherFromApi() {
        OpenWeatherMapApiClient.GetWeatherResult result = client.getWeather("seoul");
        return DateWeather.create(result.getMain(), result.getIcon(), Double.parseDouble(result.getTemp()), LocalDate.now());
    }
}
