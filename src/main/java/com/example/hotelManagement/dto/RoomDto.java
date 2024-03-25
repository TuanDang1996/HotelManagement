package com.example.hotelManagement.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomDto implements Serializable {
    private String id;
    private String bedInfo;
    private String size;
    private String roomTye;
    private boolean isSmoking;
    private boolean isCityView;
    private Integer quantity;
    private String hotelId;
}
