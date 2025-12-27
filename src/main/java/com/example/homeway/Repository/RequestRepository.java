package com.example.homeway.Repository;

import com.example.homeway.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Request findRequestById(Integer id);

    List<Request> findAllByCompany_Id(Integer companyId);
    List<Request> findAllByCustomer_Id(Integer customerId);
}
