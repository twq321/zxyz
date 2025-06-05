package com.iflytek.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iflytek.entity.Record;
import com.iflytek.mapper.RecordMapper;
import com.iflytek.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    RecordMapper recordMapper;

    @Override
    public List<Record> selectRecordsById(int userId) {
        return recordMapper.selectList(new QueryWrapper<Record>().eq("userId", userId));
    }
}
