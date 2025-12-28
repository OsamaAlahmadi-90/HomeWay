package com.example.homeway.Repository;


import com.example.homeway.Model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    Offer findOfferById(Integer id);
    List<Offer> findAllByRequest_Customer_Id(Integer customerId);
}
