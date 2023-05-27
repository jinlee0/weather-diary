package com.jinlee0.weather.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class DateWeather {
    @Id
    private LocalDate date;
    private String weather;
    private String icon;
    private Double temperature;

    @Builder
    private DateWeather(LocalDate date, String weather, String icon, Double temperature) {
        this.date = date;
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
    }

    public static DateWeather create(String main, String icon, Double temp, LocalDate now) {
        return builder()
                .date(now)
                .weather(main)
                .icon(icon)
                .temperature(temp)
                .build();
    }
}
