package com.example.springwebapp.model.request;

import com.example.springwebapp.model.Detail;
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
public class ListOfDetailRequest {
    private int id;
    private int DeviceId;
    private int DetailId;
    private int count;
}
