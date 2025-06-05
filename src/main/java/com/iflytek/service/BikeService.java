package com.iflytek.service;

import com.iflytek.entity.Bike;

import java.util.List;

public interface BikeService {
    int addBike(Bike bike);
    int updateBike(Bike bike);
    int deleteBike(int bikeId);
    Bike getBike(int bikeId);

    List<Bike> findBikesByOwnerId(int ownerId);
}
