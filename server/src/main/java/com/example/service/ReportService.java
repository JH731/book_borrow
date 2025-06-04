package com.example.service;

import com.example.vo.SalesTop10ReportVO;
import com.example.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
