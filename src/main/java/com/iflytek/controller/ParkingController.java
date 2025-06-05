package com.iflytek.controller;

import com.iflytek.dto.CustomUserDetails;
import com.iflytek.entity.Bike;
import com.iflytek.entity.Parking;
import com.iflytek.entity.User;
import com.iflytek.service.BikeService;
import com.iflytek.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "停车位控制层")
@RestController
@RequestMapping("/parking")
public class ParkingController {
    @Resource
    private ParkingService parkingService;
    @Resource
    private BikeService bikeService;
    @ApiOperation("查找所有车位")
    @GetMapping
    public ResponseEntity<?> getParking(@RequestParam int groupId) {
        return ResponseEntity.ok(parkingService.selectByGroupId(groupId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateParking(@RequestParam Parking parking) {

        return ResponseEntity.ok(parkingService.updateById(parking));
    }

    @ApiOperation("存车/还车")
    @PostMapping("/park")
    public ResponseEntity<?> park(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam int parkingId, @RequestParam(required = false) int bikeId) {
        User user = userDetails.getUser();
        Parking parking = parkingService.selectById(parkingId);
        if (parking.getStatus() == 1) {
            return ResponseEntity.badRequest().body("操作失败, 车位已满.");
        }
        int res = 0;
        if (user.getNowborrow() == 0) {
            // 存车
            res = parkingService.stParking(user, bikeId, parking);
        } else {
            // 还车
            res = parkingService.deBorrow(user, parking);
        }
        if (res == 0) {
            return ResponseEntity.badRequest().body("操作失败.");
        } else {
            return ResponseEntity.ok("操作成功.");
        }
    }
    @ApiOperation("取车/借车")
    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam int parkingId) {
        User user = userDetails.getUser();
        if (user.getNowborrow() != 0) {
            return ResponseEntity.badRequest().body("操作失败, 还有自行车未还.");
        }
        Parking parking = parkingService.selectById(parkingId);
        if (parking.getBikeid() == 0) {
            return ResponseEntity.badRequest().body("操作失败, 车位为空.");
        }
        List<Bike> bikes = bikeService.findBikesByOwnerId(user.getUserid());
        int isOwn = 0;
        for (Bike bike : bikes) {
            if (bike.getBikeid() == parking.getBikeid()) {
                isOwn = 1;
                break;
            }
        }
        int res = 0;
        if (isOwn != 0) {
            // 不是自己的车
            if (parking.getRecordid() != 0) {
                parking.setBikeid(0);
                parking.setStatus(0);
                return ResponseEntity.badRequest().body("操作失败, 自行车还有订单.");
            }
            if (parking.getStatus() == 1) {
                return ResponseEntity.badRequest().body("操作失败, 自行车不可取.");
            }
            res = parkingService.stBorrow(user, parking);
        } else {
            // 是自己的车
            if (parking.getStatus() == 2) {
                // 共享状态
                parking.setBikeid(0);
                parking.setStatus(0);
                res = parkingService.getParkingMapper().updateById(parking);
            } else {
                // 停车状态
                res = parkingService.deParking(user, parking);
            }

        }

        if (res == 0) {
            return ResponseEntity.badRequest().body("操作失败.");
        } else {
            return ResponseEntity.ok(res);
        }
    }
}
