package zerobase.weather.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 보통 get은 조회할때 post는 저장할때 많이 사용
    @ApiOperation(value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장할 수 있습니다.", notes = "notes")
    @PostMapping("/create/diary")
    void createDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "2022-06-29") LocalDate date, @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    // request body에 일기값을 넣기에는 Post가 좋지만 조회시에는 Get
    @ApiOperation("선택한 날짜의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diary")
    List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "2022-06-29") LocalDate date) {
        return diaryService.readDiary(date);
    }

    @ApiOperation("선택한 기간중의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(value = "조회할 기간의 첫번째날", example = "2022-04-24") LocalDate startDate,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(value = "조회할 기간의 마지막날", example = "2022-06-29") LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    // 수정시 Put사용
    @ApiOperation("선택한 날짜의 일기 데이터를 수정할 수 있습니다.")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "2022-06-29") LocalDate date, @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    // 삭제시 delete사용
    @ApiOperation("선택한 날짜의 일기 데이터를 삭제할 수 있습니다.")
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ApiParam(example = "2022-06-29") LocalDate date) {
        diaryService.deleteDiary(date);
    }
}
