package com.example.springwebapp.model.request;

import com.example.springwebapp.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceRequest {
    private int id;
    private String name;
    private String size;
    private int price;
    private String mode;
    private String regcode;
    private int UserId;
}
