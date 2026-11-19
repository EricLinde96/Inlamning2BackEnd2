package com.example.inlamning2.dto;

public class VehicleDTO {

    private Long id;
    private String registrationNumber;
    private String brand;
    private String model;
    private int productionYear;
    private Long customerId;

    public VehicleDTO(Long id, String registrationNumber, String brand,
                      String model, int productionYear, Long customerId) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public Long getCustomerId() {
        return customerId;
    }
}