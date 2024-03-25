package com.example.hotelManagement.controller;

import com.example.hotelManagement.dto.ReservationDto;
import com.example.hotelManagement.dto.RoomDto;
import com.example.hotelManagement.dto.requestDto.CreateReservationRq;
import com.example.hotelManagement.dto.requestDto.UpdateReservationRq;
import com.example.hotelManagement.service.ReservationService;
import com.example.hotelManagement.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "reservation")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;

    @PostMapping(path = "reserve", produces = "application/json")
    public ResponseEntity<?> makeReservation(@Validated @RequestBody CreateReservationRq reservationRq){
        ReservationDto reservationDto = this.reservationService.createNewReservation(reservationRq);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @PutMapping(path = "updateReservation", produces = "application/json")
    public ResponseEntity<?> updateReservation(@Validated @RequestBody UpdateReservationRq reservationRq){
        ReservationDto reservationDto = this.reservationService.updateReservation(reservationRq);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping(path = "fetchReservationByPhone", produces = "application/json")
    public ResponseEntity<?> fetchReservationByPhoneNumber(@RequestParam(value = "phoneNumber", defaultValue = "") String phoneNumber){
        List<ReservationDto> reservationDto = this.reservationService.findReservation(phoneNumber);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "cancelReservation/{id}", produces = "application/json")
    public ResponseEntity<?> cancelReservation(@PathVariable(value = "id") String reservationId){
        ReservationDto reservationDto = this.reservationService.cancel(reservationId);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
}
