package com.example.StudentManagementFullStack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeekHours {

    //String interval; //todo 2 Local dates
    LocalDate startWeek;
    LocalDate endWeek;
    float hours=0;
}
