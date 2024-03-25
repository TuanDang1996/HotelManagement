package com.example.hotelManagement.service;

import com.example.hotelManagement.dto.ReservationDto;
import com.example.hotelManagement.dto.requestDto.CreateReservationRq;
import com.example.hotelManagement.dto.requestDto.UpdateReservationRq;
import com.example.hotelManagement.entity.Reservation;
import com.example.hotelManagement.entity.Room;
import com.example.hotelManagement.exception.CannotPersistDataException;
import com.example.hotelManagement.exception.NotEnoughRoomException;
import com.example.hotelManagement.exception.RecordNotFoundException;
import com.example.hotelManagement.exception.UncoverableException;
import com.example.hotelManagement.repository.ReservationRepository;
import com.example.hotelManagement.repository.RoomRepository;
import com.example.hotelManagement.util.ReservationStatusEnum;
import io.micrometer.tracing.annotation.NewSpan;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Service
public class ReservationServiceImpl implements ReservationService{
    private final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, ModelMapper modelMapper) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    @NewSpan("save-reservation")
    public ReservationDto createNewReservation(CreateReservationRq createReservationRq) {
        try {
            Optional<Room> room = this.roomRepository.findById(createReservationRq.getRoomId());
            if(room.isEmpty())
                throw new RecordNotFoundException("There is no room to reserve, room id: %s".formatted(createReservationRq.getRoomId()));
            Room roomData = room.get();

            if(createReservationRq.getQuantity() > roomData.getQuantity())
                throw new NotEnoughRoomException("Not enough room for room id: %s".formatted(roomData.getId()));

            Reservation reservation = this.modelMapper.map(createReservationRq, Reservation.class);
            reservation.setStatus(ReservationStatusEnum.CREATED);
            reservation = this.reservationRepository.save(reservation);

            roomData.setQuantity(roomData.getQuantity() - createReservationRq.getQuantity());
            this.roomRepository.save(roomData);

            return this.modelMapper.map(reservation, ReservationDto.class);
        } catch (RecordNotFoundException | NotEnoughRoomException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new CannotPersistDataException(ExceptionUtils.getMessage(ex));
        }
    }

    @Override
    @NewSpan("find-reservation")
    public List<ReservationDto> findReservation(String phoneNumber) {
        try {
            Optional<List<Reservation>> reservations = this.reservationRepository.findByMobilePhone(phoneNumber);
            if (reservations.isEmpty())
                throw new RecordNotFoundException("There is no reservation with phone number: %s".formatted(phoneNumber));
            return reservations
                    .get().stream()
                    .map(r -> this.modelMapper.map(r, ReservationDto.class))
                    .toList();
        } catch (RecordNotFoundException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new UncoverableException(ExceptionUtils.getMessage(ex));
        }
    }

    @Transactional
    @Override
    @NewSpan("update-reservation")
    public ReservationDto updateReservation(UpdateReservationRq reservationRq) {
        try {
            Optional<Reservation> reservation = this.reservationRepository.findById(reservationRq.getId());
            if(reservation.isEmpty())
                throw new RecordNotFoundException("There is no reservation with id: %s".formatted(reservationRq.getId()));

            Optional<Room> room = this.roomRepository.findById(reservation.get().getRoomId());
            if(room.isEmpty())
                throw new RecordNotFoundException("There is no room to reserve, room id: %s".formatted(reservation.get().getRoomId()));
            Room roomData = room.get();

            int changedQuantity = reservationRq.getQuantity() - reservation.get().getQuantity();
            if(changedQuantity > roomData.getQuantity())
                throw new NotEnoughRoomException("Not enough room for room id: %s".formatted(roomData.getId()));

            Reservation reservationData = this.updateReservationInfo().apply(reservation.get(), reservationRq);
            this.reservationRepository.save(reservationData);

            roomData.setQuantity(roomData.getQuantity() - changedQuantity);
            this.roomRepository.save(roomData);

            return this.modelMapper.map(reservationData, ReservationDto.class);
        } catch (RecordNotFoundException | NotEnoughRoomException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new CannotPersistDataException(ExceptionUtils.getMessage(ex));
        }
    }

    private BiFunction<Reservation, UpdateReservationRq, Reservation> updateReservationInfo() {
        return (oldOne, newOne) -> {
            oldOne.setEmail(newOne.getEmail());
            oldOne.setFullName(newOne.getFullName());
            oldOne.setQuantity(newOne.getQuantity());
            oldOne.setMobilePhone(newOne.getMobilePhone());
            oldOne.setStatus(ReservationStatusEnum.MODIFIED);
            return oldOne;
        };
    }

    @Transactional
    @Override
    @NewSpan("cancel-reservation")
    public ReservationDto cancel(String reservationId) {
        try {
            Optional<Reservation> reservation = this.reservationRepository.findById(reservationId);
            if (reservation.isEmpty())
                throw new RecordNotFoundException("There is no reservation with id: %s".formatted(reservationId));
            Reservation reservationData = reservation.get();

            Optional<Room> room = this.roomRepository.findById(reservationData.getRoomId());
            if(room.isEmpty())
                throw new RecordNotFoundException("There is no room to reserve, room id: %s".formatted(reservationData.getRoomId()));
            Room roomData = room.get();

            reservationData.setStatus(ReservationStatusEnum.CANCELED);
            reservationData = this.reservationRepository.save(reservationData);

            roomData.setQuantity(roomData.getQuantity() + reservationData.getQuantity());
            this.roomRepository.save(roomData);

            return this.modelMapper.map(reservationData, ReservationDto.class);
        } catch (RecordNotFoundException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new UncoverableException(ExceptionUtils.getMessage(ex));
        }
    }


}
