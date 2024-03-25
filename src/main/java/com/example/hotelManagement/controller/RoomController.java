package com.example.hotelManagement.controller;

import com.example.hotelManagement.dto.RoomDto;
import com.example.hotelManagement.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "room")
@AllArgsConstructor
public class RoomController {
    private RoomService roomService;

    @GetMapping(path = "findByHotelId", produces = "application/json")
    public ResponseEntity<?> findByName(@RequestParam(value = "hotelId", defaultValue = "") String hotelId){
        List<RoomDto> roomDtos = this.roomService.findRoomByHotel(hotelId);
        return new ResponseEntity<>(roomDtos, HttpStatus.OK);
    }

    @GetMapping(path = "findByRoomId/{id}", produces = "application/json")
    public ResponseEntity<?> findByRoomId(@PathVariable("id") String roomId){
        RoomDto roomDto = this.roomService.findByRoomId(roomId);
        return new ResponseEntity<>(roomDto, HttpStatus.OK);
    }
}
