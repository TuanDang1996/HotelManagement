package com.example.hotelManagement.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseEntity {
    @CreatedDate
    private String createdDate;
    @LastModifiedDate
    private String updatedDate;
}
