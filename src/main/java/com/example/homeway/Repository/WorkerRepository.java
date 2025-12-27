package com.example.homeway.Repository;

import com.example.homeway.Model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    Worker findWorkerById(Integer id);

    @Query("""
            select w from Worker w
            where w.company.id = ?1
              and w.isAvailable = true
              and w.user.role = 'WORKER'
              and w.isActive = true
              and w.company.user.role = 'INSPECTION_COMPANY'
           """)
    Worker findAvailableInspectorWorker(Integer companyId);

    @Query("""
            select w from Worker w
            where w.company.id = ?1
              and w.isAvailable = true
              and w.user.role = 'WORKER'
              and w.isActive = true
              and w.company.user.role = 'MOVING_COMPANY'
           """)
    Worker findAvailableMovingWorker(Integer companyId);

    @Query("""
            select w from Worker w
            where w.company.id = ?1
              and w.isAvailable = true
              and w.user.role = 'WORKER'
              and w.isActive = true
              and w.company.user.role = 'MAINTENANCE_COMPANY'
           """)
    Worker findAvailableMaintenanceWorker(Integer companyId);

    @Query("""
        select w from Worker w
        where w.company.id = ?1
          and w.isAvailable = true
          and w.user.role = 'WORKER'
          and w.isActive = true
          and w.company.user.role = 'REDESIGN_COMPANY'
       """)
    Worker findAvailableRedesignWorker(Integer companyId);

    List<Worker> findAllByCompany_Id(Integer companyId);

}


