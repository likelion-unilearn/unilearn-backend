package unilearn.unilearn.Schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unilearn.unilearn.Schedule.dto.ScheduleRequestDto;
import unilearn.unilearn.Schedule.dto.ScheduleResponseDto;
import unilearn.unilearn.Schedule.service.ScheduleService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/monthly-schedule-count")
    public ResponseEntity<ScheduleResponseDto.MonthResponseDto> monthlyCount(
            @RequestParam("year")int yearNumber, @RequestParam("month")int monthNumber,
            Principal principal) {
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ScheduleResponseDto.MonthResponseDto responseDto = scheduleService.monthlyCount(yearNumber, monthNumber, principal);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/todays-schedule-count")
    public ResponseEntity<ScheduleResponseDto.TodayScheduleCountDto> todayCount(Principal principal){
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ScheduleResponseDto.TodayScheduleCountDto responseForm = scheduleService.todayCount(principal);
        return ResponseEntity.status(HttpStatus.OK).body(responseForm);
    }

    @GetMapping("/daily-schedule")
    public ResponseEntity<List<ScheduleResponseDto.OneDayScheduleResponseDto>> oneDayList(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate deadline, Principal principal){
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<ScheduleResponseDto.OneDayScheduleResponseDto> responseFormList = scheduleService.onedayList(deadline, principal);
        return ResponseEntity.status(HttpStatus.OK).body(responseFormList);
    }

    @PostMapping("/create-schedule")
    public ResponseEntity<List<ScheduleResponseDto.OneDayScheduleResponseDto>> createSchedule(
            @RequestBody ScheduleRequestDto.ScheduleCreateDto form, Principal principal){
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<ScheduleResponseDto.OneDayScheduleResponseDto> responseFormList = scheduleService.createSchedule(form, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseFormList);
    }

    @PutMapping("/update-schedule/{schedule_id}")
    public ResponseEntity<?> checkSchedule(@PathVariable("schedule_id") Long scheduleId, Principal principal){
        if (principal == null || principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        scheduleService.checkSchedule(scheduleId, principal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
