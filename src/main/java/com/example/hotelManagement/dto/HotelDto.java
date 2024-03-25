package com.example.hotelManagement.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelDto implements Serializable {
    private String id;
    private String name;
    private String address;
    private String overview;
    private Float rating;
}
