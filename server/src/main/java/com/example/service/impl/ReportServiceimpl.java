package com.example.service.impl;

import com.example.service.ReportService;
import com.example.vo.SalesTop10ReportVO;
import com.example.vo.UserReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportServiceimpl implements ReportService {
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        return null;
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        return null;
    }
}
