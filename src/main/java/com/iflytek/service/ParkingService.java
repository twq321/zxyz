package com.iflytek.service;

import com.iflytek.entity.Parking;
import com.iflytek.entity.User;
import com.iflytek.mapper.ParkingMapper;

import java.util.List;

public interface ParkingService {
    ParkingMapper getParkingMapper();

    List<Parking> selectByGroupId(int id);

    Parking selectById(int parkingId);

    int stParking(User user, int bikeId, Parking parking);
    int deParking(User user, Parking parking);
    int stBorrow(User user, Parking parking);
    int deBorrow(User user, Parking parking);
}
