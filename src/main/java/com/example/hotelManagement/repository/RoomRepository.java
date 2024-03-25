package com.example.hotelManagement.repository;

import com.example.hotelManagement.entity.Room;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<List<Room>> findByHotelId(String hotelId);
    Optional<Room> findById(String id);
}
