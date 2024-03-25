package com.example.hotelManagement.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateReservationRq implements Serializable {
    @NotBlank(message = "id is mandatory")
    private String id;
    private String fullName;
    private String email;
    private String mobilePhone;
    private Integer quantity;
}
