package com.example.StudentManagementFullStack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentStatisticsResponseDto {

    int deptToday;
    int deptMonth;
    int monthPay;
    int remainingPay;
    int payed;

}
