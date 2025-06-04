package com.iflytek.service.Impl;

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
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public int stParking(int userId, int bikeId, Parking parking) {
        Bike bike = bikeMapper.selectById(bikeId);
        bike.setParkingid(parking.getParkingid());
        bikeMapper.updateById(bike);

        Record record = new Record();
        record.setUserid(userId);
        record.setBikeid(bikeId);
        record.setStarttime(LocalDateTime.now());
        record.setSparkingid(parking.getParkingid());
        record.setTouser(0);
        record.setType("park");
        recordMapper.insert(record);

        parking.setRecordid(record.getRecordid());
        parking.setStatus(1);
        return parkingMapper.updateById(parking);
    }

    @Override
    public int deParking(int userId, Parking parking) {
        Bike bike = bikeMapper.selectById(parking.getBikeid());
        bike.setParkingid(0);
        bikeMapper.updateById(bike);

        parking.setStatus(0);
        parkingMapper.updateById(parking);

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
        return recordMapper.updateById(record);
    }

    @Override
    public int stBorrow(int userId, Parking parking) {
        if (parking.getBikeid() == 0) {
            return 0;
        }

        Bike bike = bikeMapper.selectById(parking.getBikeid());
        bike.setParkingid(0);
        bike.setUserid(userId);
        bike.setStatus(1);
        bikeMapper.updateById(bike);

        parking.setBikeid(0);
        parking.setStatus(0);
        parkingMapper.updateById(parking);

        Record record = new Record();
        record.setUserid(userId);
        record.setBikeid(parking.getBikeid());
        record.setStarttime(LocalDateTime.now());
        record.setSparkingid(parking.getParkingid());
        record.setTouser(bike.getOwnerid());
        record.setType("borrow");
        recordMapper.insert(record);

        User user = userMapper.selectById(userId);
        user.setUsebikeid(parking.getBikeid());
        user.setNowborrow(record.getRecordid());
        return userMapper.updateById(user);
    }

    @Override
    public int deBorrow(int userId, Parking parking) {
        if (parking.getBikeid() != 0) {
            return 0;
        }

        User user = userMapper.selectById(userId);
        user.setUsebikeid(0);

        Record record = recordMapper.selectById(user.getNowborrow());

        Bike bike = bikeMapper.selectById(record.getBikeid());
        bike.setParkingid(parking.getParkingid());
        bike.setUserid(0);
        bike.setStatus(0);
        bikeMapper.updateById(bike);

        parking.setBikeid(record.getBikeid());
        parking.setStatus(1);
        parkingMapper.updateById(parking);

        record.setEndtime(LocalDateTime.now());
        record.setEparkingid(parking.getParkingid());
        LocalDateTime starttime = record.getStarttime();
        LocalDateTime endtime = record.getEndtime();
        // 计费逻辑
        Long hours = Duration.between(starttime, endtime).toHours();
        Double money = 0.0;
        if (hours <= 2) {
            money = 1.0;
        } else {
            money = (double) hours / 2.0;
        }
        record.setMoney(BigDecimal.valueOf(money));
        recordMapper.updateById(record);

        user.setNowborrow(0);
        return userMapper.updateById(user);
    }
}
