package com.example.inlamning2.dto;

import java.util.List;

public class CustomerWithVehiclesDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private List<VehicleDTO> vehicles;

    public CustomerWithVehiclesDTO(Long id, String name,
                                   String phoneNumber, List<VehicleDTO> vehicles) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.vehicles = vehicles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }
}