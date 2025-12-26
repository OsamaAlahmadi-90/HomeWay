package com.example.homeway.Repository;

import com.example.homeway.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    Worker findCustomerById(Integer id);
}
