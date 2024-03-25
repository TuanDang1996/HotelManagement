package com.example.hotelManagement.dto;

import com.example.hotelManagement.util.ReservationStatusEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReservationDto implements Serializable {
    private String id;
    private String fullName;
    private String email;
    private String mobilePhone;
    private String roomId;
    private Integer quantity;
    private ReservationStatusEnum status;
}
