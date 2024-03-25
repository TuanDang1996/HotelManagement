package com.example.hotelManagement.service;

import com.example.hotelManagement.dto.RoomDto;
import com.example.hotelManagement.entity.Room;

import java.util.List;

public interface RoomService {
    List<RoomDto> findRoomByHotel(String hotelId);
    RoomDto findByRoomId(String roomId);
}
