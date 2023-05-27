package com.jinlee0.weather.controller;

import com.jinlee0.weather.domain.Diary;
import com.jinlee0.weather.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @ApiOperation(value = "오늘의 일기 추가", notes = "해당 날짜의 서울 날씨를 사용합니다.")
    @PostMapping("/diaries")
    Diary createDiary(@RequestBody Dtos.CreateDiary.Request body) {
        return diaryService.createDiary(body);
    }

    @GetMapping("/diaries")
    List<Diary> retrieveDiary(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "yyyy-MM-dd") LocalDate date,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "yyyy-MM-dd") LocalDate start,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "yyyy-MM-dd") LocalDate end) {
        if (date != null) {
            return diaryService.retrieveDiariesOn(date);
        } else if (start == null && end == null) {
            return diaryService.retrieveAllDiaries();
        } else if (start == null) {
            return diaryService.retrieveDiaryUntil(end);
        } else if (end == null) {
            return diaryService.retrieveDiarySince(start);
        } else {
            return diaryService.retrieveDiariesInRange(start, end);
        }
    }

    @PutMapping("/diaries/{diaryId}")
    Diary updateDiary(@PathVariable Integer diaryId,
                      @RequestBody Dtos.UpdateDiary.Request body) {
        return diaryService.updateDiary(diaryId, body);
    }

    @DeleteMapping("/diaries/{diaryId}")
    void deleteDiary(@PathVariable Integer diaryId) {
        diaryService.deleteDiary(diaryId);
    }

    @DeleteMapping("/diaries")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(value = "yyyy-MM-dd") LocalDate date) {
        diaryService.deleteDiaryByDate(date);
    }
}
