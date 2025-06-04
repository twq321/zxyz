package com.iflytek.service;

import com.iflytek.entity.Parking;

public interface ParkingService {
    int stParking(int userId, int bikeId, Parking parking);
    int deParking(int userId, Parking parking);
    int stBorrow(int userId, Parking parking);
    int deBorrow(int userId, Parking parking);
}
