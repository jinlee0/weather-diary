package com.jinlee0.weather.controller;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

public class Dtos {

    public static class CreateDiary {
        @Getter
        public static class Request {
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            private LocalDate date;
            String text;
        }
    }

    public static class UpdateDiary {
        @Getter
        public static class Request {
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            private LocalDate date;
            String text;
        }
    }
}
