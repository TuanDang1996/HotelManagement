package com.example.hotelManagement.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateReservationRq implements Serializable {
    @NotNull(message = "fullName is mandatory")
    private String fullName;
    @NotNull(message = "email is mandatory")
    private String email;
    @NotNull(message = "mobilePhone is mandatory")
    private String mobilePhone;
    @NotNull(message = "roomId is mandatory")
    private String roomId;
    @NotNull(message = "quantity is mandatory")
    private Integer quantity;
}
