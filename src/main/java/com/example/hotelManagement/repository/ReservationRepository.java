package com.example.hotelManagement.repository;

import com.example.hotelManagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Optional<List<Reservation>> findByMobilePhone(String mobilePhone);
}
