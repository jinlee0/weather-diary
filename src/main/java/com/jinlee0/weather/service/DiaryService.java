package com.jinlee0.weather.service;

import com.jinlee0.weather.WeatherApplication;
import com.jinlee0.weather.adapter.OpenWeatherMapApiClient;
import com.jinlee0.weather.controller.Dtos;
import com.jinlee0.weather.domain.DateWeather;
import com.jinlee0.weather.domain.Diary;
import com.jinlee0.weather.error.InvalidDate;
import com.jinlee0.weather.repository.DateWeatherRepository;
import com.jinlee0.weather.repository.JpaDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final OpenWeatherMapApiClient client;
    private final JpaDiaryRepository jpaDiaryRepository;
    private final DateWeatherRepository dateWeatherRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    @Transactional
    public Diary createDiary(Dtos.CreateDiary.Request body) {
        logger.info("started to create diary");
        Diary save = jpaDiaryRepository.save(Diary.create(getDateWeather(body.getDate()), body.getText()));
        logger.info("ended to create diary");
        return save;
    }

    private DateWeather getDateWeather(LocalDate date) {
        return  dateWeatherRepository.findById(date).orElseGet(() -> {
            OpenWeatherMapApiClient.GetWeatherResult result = client.getWeather("seoul");
            return DateWeather.create(result.getMain(), result.getIcon(), Double.parseDouble(result.getTemp()), LocalDate.now());
        });
    }

    public List<Diary> retrieveDiariesOn(LocalDate date) {
        if(date.isAfter(LocalDate.ofYearDay(3050, 1)))
            throw new InvalidDate();
        return jpaDiaryRepository.findAllByDate(date);
    }

    public List<Diary> retrieveDiariesInRange(LocalDate start, LocalDate end) {
        return jpaDiaryRepository.findAllByDateBetween(start, end);
    }

    @Transactional
    public Diary updateDiary(Integer diaryId, Dtos.UpdateDiary.Request body) {
        Diary diary = jpaDiaryRepository.findById(diaryId).orElseThrow(RuntimeException::new);
        diary.update(body.getDate(), body.getText());
        return diary;
    }

    public List<Diary> retrieveAllDiaries() {
        return jpaDiaryRepository.findAll();
    }

    public List<Diary> retrieveDiaryUntil(LocalDate date) {
        return jpaDiaryRepository.findAllByDateLessThanEqual(date);
    }

    public List<Diary> retrieveDiarySince(LocalDate date) {
        return jpaDiaryRepository.findAllByDateGreaterThanEqual(date);
    }

    @Transactional
    public void deleteDiary(Integer diaryId) {
        jpaDiaryRepository.deleteById(diaryId);
    }

    @Transactional
    public void deleteDiaryByDate(LocalDate date) {
        jpaDiaryRepository.deleteAllByDate(date);
    }
}
