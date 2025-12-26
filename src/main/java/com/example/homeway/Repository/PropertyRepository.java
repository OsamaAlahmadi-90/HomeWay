package com.example.homeway.Repository;

import com.example.homeway.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    Property findPropertyById(Integer id);
    List<Property> findAllByCustomer_Id(Integer customerId);
}
