package com.iflytek.service;

import com.iflytek.entity.Bike;

public interface BikeService {
    int addBike(Bike bike);
    int updateBike(Bike bike);
    int deleteBike(int bikeId);
    Bike getBike(int bikeId);
}
