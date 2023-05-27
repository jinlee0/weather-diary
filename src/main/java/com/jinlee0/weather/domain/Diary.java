package com.jinlee0.weather.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "weather", nullable = false)
    private String weather;
    @Column(name = "icon", nullable = false)
    private String icon;
    @Column(name = "temperature", nullable = false)
    private Double temperature;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Builder
    private Diary(String weather, String icon, Double temperature, String text, LocalDate date) {
        this.weather = weather;
        this.icon = icon;
        this.temperature = temperature;
        this.text = text;
        this.date = date;
    }

    public static Diary create(DateWeather dateWeather, String text) {
        return builder()
                .weather(dateWeather.getWeather())
                .text(text)
                .date(dateWeather.getDate())
                .icon(dateWeather.getIcon())
                .temperature(dateWeather.getTemperature())
                .build();
    }

    public void update(LocalDate date, String text) {
        this.date = date;
        this.text = text;
    }
}
