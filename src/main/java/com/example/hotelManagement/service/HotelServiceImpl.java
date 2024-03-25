package com.example.hotelManagement.service;

import com.example.hotelManagement.dto.HotelDto;
import com.example.hotelManagement.entity.Hotel;
import com.example.hotelManagement.exception.RecordNotFoundException;
import com.example.hotelManagement.exception.UncoverableException;
import com.example.hotelManagement.repository.HotelRepository;
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
public class HotelServiceImpl implements HotelService{
    private final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @NewSpan("find-hotel")
    public List<HotelDto> findHotelByName(String name) {
        try {
            Optional<List<Hotel>> hotels = this.hotelRepository.findByNameContainingIgnoreCase(name);
            if (hotels.isEmpty())
                throw new RecordNotFoundException("There is no hotel with name containing: %s".formatted(name));
            return hotels
                    .get().stream()
                    .map(h -> this.modelMapper.map(h, HotelDto.class))
                    .toList();
        } catch (RecordNotFoundException r){
            logger.error(ExceptionUtils.getStackTrace(r));
            throw r;
        } catch (Exception ex){
            logger.error(ExceptionUtils.getStackTrace(ex));
            throw new UncoverableException(ExceptionUtils.getMessage(ex));
        }
    }
}
