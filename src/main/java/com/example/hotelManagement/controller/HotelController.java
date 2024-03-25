package com.example.hotelManagement.controller;

import com.example.hotelManagement.dto.HotelDto;
import com.example.hotelManagement.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "hotel")
@AllArgsConstructor
public class HotelController {
    private HotelService hotelService;

    @GetMapping(path = "findByName", produces = "application/json")
    public ResponseEntity<?> findByName(@RequestParam(value = "name", defaultValue = "") String name){
        List<HotelDto> hotelDtos = this.hotelService.findHotelByName(name);
        return new ResponseEntity<>(hotelDtos, HttpStatus.OK);
    }
}
