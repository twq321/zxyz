package com.iflytek.controller;

import com.iflytek.dto.CustomUserDetails;
import com.iflytek.entity.Bike;
import com.iflytek.entity.Parking;
import com.iflytek.service.BikeService;
import com.iflytek.service.ParkingService;
import com.iflytek.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "自行车管理层")
@RestController
@RequestMapping("/bike")
public class BikeController {
    @Resource
    private BikeService bikeService;
    @Autowired
    private ParkingService parkingService;
    @Autowired
    private RecordService recordService;

    @ApiOperation("查看自行车")
    @GetMapping
    public ResponseEntity<?> selectBike(@AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(userDetails.getUser());
        return ResponseEntity.ok(bikeService.findBikesByOwnerId(userDetails.getUser().getUserid()));
    }
    @PostMapping("/add")
    public ResponseEntity<?> addBike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Bike bike) {
        bike.setOwnerid(userDetails.getUser().getUserid());
        int res = bikeService.addBike(bike);
        if (res == 1) {
            return ResponseEntity.ok("添加成功, bikeId: " + bike.getBikeid());
        } else {
            return ResponseEntity.badRequest().body("添加失败.");
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateBike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Bike bike) {
        if (bike.getBikeid() != userDetails.getUser().getUserid()) {
            return ResponseEntity.badRequest().body("更新失败, 车辆不属于您.");
        }
        Bike dbBike = bikeService.getBike(bike.getBikeid());
        if (!bike.getType().equals(dbBike.getType())) {
            Parking parking = parkingService.selectByBikeId(bike.getBikeid());
            parking.setStatus(bike.getType().equals("share") ? 2 : 1);
            parkingService.updateById(parking);
            if (dbBike.getType().equals("share")) {
                parkingService.stParking(userDetails.getUser(), parking.getBikeid(), parking);
            } else {
                parkingService.deParking(userDetails.getUser(), parking);
            }
        }
        int res = bikeService.updateBike(bike);

        if (res == 1) {
            return ResponseEntity.ok(bike);
        } else {
            return ResponseEntity.badRequest().body("更新失败.");
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam int id) {
        Bike bike = bikeService.getBike(id);
        if (userDetails.getUser().getUserid() != bike.getOwnerid()) {
            return ResponseEntity.badRequest().body("删除失败, 车辆不属于您.");
        }
        int res = bikeService.deleteBike(id);
        if (res == 1) {
            return ResponseEntity.ok("删除成功.");
        } else {
            return ResponseEntity.badRequest().body("删除失败.");
        }
    }
}
