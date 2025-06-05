package com.iflytek.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iflytek.entity.Bike;
import com.iflytek.entity.Parking;
import com.iflytek.entity.Record;
import com.iflytek.entity.User;
import com.iflytek.mapper.BikeMapper;
import com.iflytek.mapper.ParkingMapper;
import com.iflytek.mapper.RecordMapper;
import com.iflytek.mapper.UserMapper;
import com.iflytek.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    ParkingMapper parkingMapper;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    BikeMapper bikeMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public ParkingMapper getParkingMapper() {
        return parkingMapper;
    }
    @Override
    public List<Parking> selectByGroupId(int groupId) {
        return parkingMapper.selectList(new QueryWrapper<Parking>().eq("groupId", groupId));
    }
    @Override
    public Parking selectById(int parkingId) {
        return parkingMapper.selectById(parkingId);
    }
    @Override
    public int updateById(Parking parking) {
        return parkingMapper.updateById(parking);
    }
    @Override
    public Parking selectByBikeId(int bikeId) {
        return parkingMapper.selectOne(new QueryWrapper<Parking>().eq("bikeId", bikeId));
    }
    @Override
    public int stParking(User user, int bikeId, Parking parking) {
        Bike bike = bikeMapper.selectById(bikeId);
        bike.setParkingid(parking.getParkingid());
        bikeMapper.updateById(bike);

        Record record = new Record();
        record.setUserid(user.getUserid());
        record.setBikeid(bikeId);
        record.setStarttime(LocalDateTime.now());
        record.setSparkingid(parking.getParkingid());
        record.setTouser(0);
        record.setType("park");
        recordMapper.insert(record);

        parking.setRecordid(record.getRecordid());
        parking.setStatus(1);
        parking.setBikeid(bikeId);
        return parkingMapper.updateById(parking);
    }

    @Override
    public int deParking(User user, Parking parking) {
        Bike bike = bikeMapper.selectById(parking.getBikeid());
        bike.setParkingid(0);
        bikeMapper.updateById(bike);



        Record record = recordMapper.selectById(parking.getRecordid());
        record.setEndtime(LocalDateTime.now());
        record.setEparkingid(parking.getParkingid());
        LocalDateTime starttime = record.getStarttime();
        LocalDateTime endtime = record.getEndtime();
        // 计费逻辑
        Long hours = Duration.between(starttime, endtime).toHours();
        Double money = 0.0;
        if (hours <= 3) {
            money = 1.0;
        } else {
            money = (double) hours / 3.0;
        }
        record.setMoney(BigDecimal.valueOf(money));
        recordMapper.updateById(record);

        parking.setStatus(0);
        parking.setBikeid(0);
        parking.setRecordid(0);
        return parkingMapper.updateById(parking);
    }

    @Override
    public int stBorrow(User user, Parking parking) {
        if (parking.getBikeid() == 0) {
            return 0;
        }

        Bike bike = bikeMapper.selectById(parking.getBikeid());
        bike.setParkingid(0);
        bike.setUserid(user.getUserid());
        bike.setStatus(1);
        bikeMapper.updateById(bike);

        parking.setBikeid(0);
        parking.setStatus(0);
        parkingMapper.updateById(parking);

        Record record = new Record();
        record.setUserid(user.getUserid());
        record.setBikeid(parking.getBikeid());
        record.setStarttime(LocalDateTime.now());
        record.setSparkingid(parking.getParkingid());
        record.setTouser(bike.getOwnerid());
        record.setType("borrow");
        recordMapper.insert(record);

        user.setUsebikeid(parking.getBikeid());
        user.setNowborrow(record.getRecordid());
        return userMapper.updateById(user);
    }

    @Override
    public int deBorrow(User user, Parking parking) {
        if (parking.getBikeid() != 0) {
            return 0;
        }



        Record record = recordMapper.selectById(user.getNowborrow());

        Bike bike = bikeMapper.selectById(record.getBikeid());
        bike.setParkingid(parking.getParkingid());
        bike.setUserid(0);
        bike.setStatus(0);
        bikeMapper.updateById(bike);

        parking.setBikeid(record.getBikeid());
        parking.setStatus(2);
        parkingMapper.updateById(parking);

        record.setEndtime(LocalDateTime.now());
        record.setEparkingid(parking.getParkingid());
        LocalDateTime starttime = record.getStarttime();
        LocalDateTime endtime = record.getEndtime();
        // 计费逻辑
        long hours = Duration.between(starttime, endtime).toHours();
        double money = 0.0;
        if (hours <= 2) {
            money = 1.0;
        } else {
            money = (double) hours / 2.0;
        }
        record.setMoney(BigDecimal.valueOf(money));
        recordMapper.updateById(record);

        user.setUsebikeid(0);
        user.setNowborrow(0);
        return userMapper.updateById(user);
    }
}
