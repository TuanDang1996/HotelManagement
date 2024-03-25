package com.example.hotelManagement.service;

import com.example.hotelManagement.dto.ReservationDto;
import com.example.hotelManagement.dto.requestDto.CreateReservationRq;
import com.example.hotelManagement.dto.requestDto.UpdateReservationRq;

import java.util.List;

public interface ReservationService {
    ReservationDto createNewReservation(CreateReservationRq createReservationRq);
    List<ReservationDto> findReservation(String phoneNumber);
    ReservationDto updateReservation(UpdateReservationRq createReservationRq);
    ReservationDto cancel(String reservationId);
}
