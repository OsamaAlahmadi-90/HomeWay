package com.example.homeway.Repository;

import com.example.homeway.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle findVehicleById(Integer id);

    @Query("""
            select v from Vehicle v
            where v.company.id = ?1
              and v.available = true
              and v.company.user.role = 'MOVING_COMPANY'
           """)
    Vehicle findAvailableMovingVehicle(Integer companyId);

    List<Vehicle> findAllByCompany_Id(Integer companyId);

    List<Vehicle> findAllByCompany_IdAndAvailable(Integer companyId, Boolean available);

    List<Vehicle> findAllByCompany_IdAndTypeIgnoreCase(Integer companyId, String type);

    List<Vehicle> findAllByCompany_IdAndCapacityGreaterThanEqual(Integer companyId, Double capacity);


}