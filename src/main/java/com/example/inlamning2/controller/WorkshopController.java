package com.example.inlamning2.controller;

import com.example.inlamning2.dto.CustomerWithVehiclesDTO;
import com.example.inlamning2.dto.VehicleBrandDTO;
import com.example.inlamning2.dto.VehicleDTO;
import com.example.inlamning2.entity.Customer;
import com.example.inlamning2.entity.Vehicle;
import com.example.inlamning2.projection.VehicleBrandProjection;
import com.example.inlamning2.repository.CustomerRepository;
import com.example.inlamning2.repository.VehicleRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkshopController {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public WorkshopController(CustomerRepository customerRepository,
                              VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    // Convert a vehicle entity to a DTO
    private VehicleDTO convertToDTO(Vehicle vehicle) {

        Long customerId = null;

        if (vehicle.getCustomer() != null) {
            customerId = vehicle.getCustomer().getId();
        }

        return new VehicleDTO(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getProductionYear(),
                customerId
        );
    }

    // Create a customer
    @PostMapping("/customers")
    public CustomerWithVehiclesDTO createCustomer(
            @RequestParam String name,
            @RequestParam String phone) {

        Customer customer = new Customer(name, phone);
        Customer savedCustomer = customerRepository.save(customer);

        return new CustomerWithVehiclesDTO(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getPhoneNumber(),
                List.of()
        );
    }

    // Find a customer ID by name
    @GetMapping("/customer-id")
    public ResponseEntity<Long> getCustomerId(@RequestParam String name) {

        return customerRepository.findByName(name)
                .map(customer -> ResponseEntity.ok(customer.getId()))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all customers with their vehicles
    @GetMapping("/customers")
    public List<CustomerWithVehiclesDTO> getAllCustomers() {

        return customerRepository.findAllCustomersWithVehicles()
                .stream()
                .map(customer -> {

                    List<VehicleDTO> vehicles = customer.getVehicles()
                            .stream()
                            .map(vehicle -> convertToDTO(vehicle))
                            .toList();

                    return new CustomerWithVehiclesDTO(
                            customer.getId(),
                            customer.getName(),
                            customer.getPhoneNumber(),
                            vehicles
                    );
                })
                .toList();
    }

    // Create a vehicle
    @PostMapping("/vehicles")
    public VehicleDTO createVehicle(
            @RequestParam String registrationNumber,
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam int productionYear) {

        Vehicle vehicle = new Vehicle(
                registrationNumber,
                brand,
                model,
                productionYear
        );

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return convertToDTO(savedVehicle);
    }

    // Get all vehicles
    @GetMapping("/vehicles")
    public List<VehicleDTO> getAllVehicles() {

        return vehicleRepository.findAll()
                .stream()
                .map(vehicle -> convertToDTO(vehicle))
                .toList();
    }

    // Find vehicles by brand
    @GetMapping("/vehicles-by-brand")
    public List<VehicleDTO> getVehiclesByBrand(@RequestParam String brand) {

        return vehicleRepository.findByBrand(brand)
                .stream()
                .map(vehicle -> convertToDTO(vehicle))
                .toList();
    }

    // Connect a vehicle to a customer
    @PostMapping("/addvehicletocustomer")
    public ResponseEntity<VehicleDTO> addVehicleToCustomer(
            @RequestParam Long customerId,
            @RequestParam Long vehicleId) {

        var customer = customerRepository.findById(customerId);
        var vehicle = vehicleRepository.findById(vehicleId);

        if (customer.isEmpty() || vehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vehicle savedVehicle = vehicle.get();
        savedVehicle.setCustomer(customer.get());

        vehicleRepository.save(savedVehicle);

        return ResponseEntity.ok(convertToDTO(savedVehicle));
    }

    // Update the brand of a vehicle
    @PutMapping("/vehicles/{id}/brand")
    public ResponseEntity<String> updateVehicleBrand(
            @PathVariable Long id,
            @RequestParam String brand) {

        int updatedRows = vehicleRepository.updateVehicleBrand(id, brand);

        if (updatedRows == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Vehicle brand updated successfully");
    }

    // Get vehicle brand information using a projection
    @GetMapping("/vehicle-brand-info")
    public List<VehicleBrandDTO> getVehicleBrandInfo() {

        List<VehicleBrandProjection> projections =
                vehicleRepository.findAllVehicleBrandInfo();

        return projections.stream()
                .map(projection -> new VehicleBrandDTO(
                        projection.getRegistrationNumber(),
                        projection.getBrand()
                ))
                .toList();
    }
}