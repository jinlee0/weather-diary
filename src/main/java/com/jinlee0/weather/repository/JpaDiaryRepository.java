package com.jinlee0.weather.repository;

import com.jinlee0.weather.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaDiaryRepository extends JpaRepository<Diary, Integer> {
    List<Diary> findAllByDate(LocalDate date);
    List<Diary> findAllByDateBetween(LocalDate start, LocalDate end);
    List<Diary> findAllByDateGreaterThanEqual(LocalDate date);
    List<Diary> findAllByDateLessThanEqual(LocalDate date);
    void deleteAllByDate(LocalDate date);
}
