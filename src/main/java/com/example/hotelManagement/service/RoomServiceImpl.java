package com.example.hotelManagement.service;

import com.example.hotelManagement.dto.RoomDto;
import com.example.hotelManagement.entity.Room;
import com.example.hotelManagement.exception.RecordNotFoundException;
import com.example.hotelManagement.exception.UncoverableException;
import com.example.hotelManagement.repository.RoomRepository;
import io.micrometer.tracing.annotation.NewSpan;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @NewSpan("find-room-by-hotel")
    public List<RoomDto> findRoomByHotel(String hotelId) {
        try {
            Optional<List<Room>> rooms = this.roomRepository.findByHotelId(hotelId);
            if (rooms.isEmpty())
                throw new RecordNotFoundException("There is no room in the hotel id: %s".formatted(hotelId));
            return rooms
                    .get().stream()
                    .map(h -> this.modelMapper.map(h, RoomDto.class))
                    .toList();
        } catch (RecordNotFoundException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new UncoverableException(ExceptionUtils.getMessage(ex));
        }
    }

    @Override
    @NewSpan("find-room")
    public RoomDto findByRoomId(String roomId) {
        try {
            Optional<Room> room = this.roomRepository.findById(roomId);
            if (room.isEmpty())
                throw new RecordNotFoundException("There is no room in the room id: %s".formatted(roomId));
            return this.modelMapper.map(room.get(), RoomDto.class);
        } catch (RecordNotFoundException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new UncoverableException(ExceptionUtils.getMessage(ex));
        }
    }
}
