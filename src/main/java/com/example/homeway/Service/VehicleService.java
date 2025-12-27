package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.Model.*;
import com.example.homeway.Repository.CompanyRepository;
import com.example.homeway.Repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CompanyRepository companyRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }


    public void addVehicle(User user, Vehicle vehicle) {
        Company company = user.getCompany();
        if (company == null) {
            throw new ApiException("Company not found");
        }

        Vehicle newVehicle = new Vehicle();
        newVehicle.setPlateNumber(vehicle.getPlateNumber());
        newVehicle.setType(vehicle.getType());
        newVehicle.setCapacity(vehicle.getCapacity());
        newVehicle.setAvailable(vehicle.getAvailable());
        newVehicle.setCompany(company);

        vehicleRepository.save(newVehicle);
    }

    public void updateVehicleLimited(User user, Integer vehicleId, Vehicle vehicle) {
        Company company = user.getCompany();
        if (company == null) {
            throw new ApiException("Company not found");
        }

        Vehicle oldVehicle = vehicleRepository.findVehicleById(vehicleId);
        if (oldVehicle == null) {
            throw new ApiException("Vehicle not found");
        }

        if (oldVehicle.getCompany() == null || !oldVehicle.getCompany().getId().equals(company.getId())) {
            throw new ApiException("You are not allowed to update this vehicle");
        }

        oldVehicle.setAvailable(vehicle.getAvailable());

        if (vehicle.getCapacity() != null) {
            oldVehicle.setCapacity(vehicle.getCapacity());
        }
        if (vehicle.getType() != null && !vehicle.getType().isEmpty()) {
            oldVehicle.setType(vehicle.getType());
        }

        vehicleRepository.save(oldVehicle);
    }

    public void deleteVehicle(User user, Integer vehicleId) {
        Company company = user.getCompany();
        if (company == null) {
            throw new ApiException("Company not found");
        }

        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId);
        if (vehicle == null) {
            throw new ApiException("Vehicle not found");
        }

        if (vehicle.getCompany() == null || !vehicle.getCompany().getId().equals(company.getId())) {
            throw new ApiException("You are not allowed to delete this vehicle");
        }

        if (vehicle.getRequests() != null && !vehicle.getRequests().isEmpty()) {
            throw new ApiException("Cannot delete vehicle because it has requests");
        }

        vehicleRepository.delete(vehicle);
    }

}
