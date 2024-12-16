package com.example.springwebapp.model.request;

import com.example.springwebapp.model.Device;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicationsRequest {
    private int id;
    private String name;
    private float value;
    private String valueName;
    private int deviceId;
}
