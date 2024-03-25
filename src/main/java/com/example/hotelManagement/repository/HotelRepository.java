package com.example.hotelManagement.repository;

import com.example.hotelManagement.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, String> {
    Optional<List<Hotel>> findByNameContainingIgnoreCase(String name);
}
