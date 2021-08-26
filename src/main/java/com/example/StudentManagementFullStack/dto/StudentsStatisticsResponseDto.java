package com.example.StudentManagementFullStack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentsStatisticsResponseDto {

    private Integer debts;
    private Integer payed;
    private Integer totalMonthIncome;
}
