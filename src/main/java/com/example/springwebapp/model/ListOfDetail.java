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
@Table(name="list_of_detail")
public class ListOfDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Device.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device Device;

    @ManyToOne(targetEntity = Detail.class, fetch = FetchType.LAZY)
    @JoinColumn(name="detail_id")
    private Detail Detail;

    @Column(name="count")
    private int count;
}
