package com.example.homeway.Repository;

import com.example.homeway.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Report findReportById(Integer id);
    List<Report> findAllByRequest_Id(Integer requestId);

}
