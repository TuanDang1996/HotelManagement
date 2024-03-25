package com.example.hotelManagement.service;

import com.example.hotelManagement.dto.HotelDto;
import com.example.hotelManagement.entity.Hotel;

import java.util.List;

public interface HotelService {
    List<HotelDto> findHotelByName(String name);
}
