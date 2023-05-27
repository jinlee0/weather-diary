package com.jinlee0.weather.adapter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class OpenWeatherMapApiClient {
    private WebClient webClient;

    @Value("${openweathermap.baseurl}")
    private String baseUrl;
    @Value("${openweathermap.key}")
    private String apiKey;

    @PostConstruct
    private void init() {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public GetWeatherResult getWeather(String city) {
        GetWeatherResponseBody responseBody = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(GetWeatherResponseBody.class)
                .block();
        assert responseBody != null;
        return GetWeatherResult.builder()
                .main(responseBody.getWeather().get(0).getMain())
                .icon(responseBody.getWeather().get(0).getIcon())
                .temp(responseBody.getMain().getTemp())
                .build();
    }

    @Getter
    @Builder
    public static class GetWeatherResult {
        private String main;
        private String icon;
        private String temp;
    }

    @Getter
    private static class GetWeatherResponseBody {
        private List<Weather> weather;
        private Main main;
        @Getter
        private static class Weather {
            private String main;
            private String icon;
        }
        @Getter
        private static class Main {
            private String temp;
        }
    }

}
