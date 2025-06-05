package com.iflytek.service;

import com.iflytek.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> selectRecordsById(int userId);
}
