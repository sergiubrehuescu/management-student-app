package com.example.StudentManagementFullStack.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PastStudentStatisticsResponseDto {

    int monthPay;
    int remainingPay;
    int payed;
}
