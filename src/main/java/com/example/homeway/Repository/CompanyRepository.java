package com.example.homeway.Repository;

import com.example.homeway.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findCompanyById(Integer id);

    @Query("select c from Company c where c.user.role = ?1")
    List<Company> findAllByUser_Role(String role);

    @Query("SELECT c FROM Company c WHERE c.status = 'pending'")
    List<Company> getPendingCompanies();
}