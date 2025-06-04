package com.iflytek.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iflytek.entity.Bike;
import com.iflytek.mapper.BikeMapper;
import com.iflytek.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BikeServiceImpl implements BikeService {
    @Autowired
    BikeMapper bikeMapper;
    public int addBike(Bike bike) {
        return bikeMapper.insert(bike);
    }

    @Override
    public int updateBike(Bike bike) {
        QueryWrapper<Bike> wrapper = new QueryWrapper<>();
        wrapper.eq("bikeId", bike.getBikeid());
        return bikeMapper.update(bike, wrapper);
    }

    @Override
    public int deleteBike(int bikeId) {
        return bikeMapper.deleteById(bikeId);
    }

    @Override
    public Bike getBike(int bikeId) {
        return bikeMapper.selectById(bikeId);
    }

    @Override
    public List<Bike> findBikesByOwnerId(Integer ownerId) {
        return bikeMapper.selectList(new QueryWrapper<Bike>().eq("ownerId", ownerId));
    }

}
