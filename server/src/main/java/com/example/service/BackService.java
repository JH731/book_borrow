package com.example.service;

import com.example.dto.BackQueryDTO;
import com.example.entity.Back;
import com.example.result.PageResult;

public interface BackService {
    PageResult pageQuery(BackQueryDTO backQueryDTO);

    void save(Back back);

    PageResult adminList(BackQueryDTO backQueryDTO);

    void allow(Integer id);
}
