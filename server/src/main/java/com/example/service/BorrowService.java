package com.example.service;

import com.example.dto.BorrowQueryDTO;
import com.example.entity.Borrow;
import com.example.result.PageResult;
import com.example.vo.BorrowStatisticVO;
import com.example.vo.BorrowVO;

public interface BorrowService {
    PageResult pageQuery(BorrowQueryDTO borrowQueryDTO);

    BorrowStatisticVO statistics();

    void statusUpdate(Integer status, Long id);

    BorrowVO details(Long id);

    Integer getCurrentBorrowCount(Long userID);

    void save(Borrow record);

    PageResult borrowList(BorrowQueryDTO borrowQueryDTO);

    void allow(Integer id);

    void notAllow(Integer id);
}
