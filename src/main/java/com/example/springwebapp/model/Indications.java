package com.example.springwebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "indications")
public class Indications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="value")
    private float value;

    @Column(name="value_name")
    private String valueName;

    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

}
