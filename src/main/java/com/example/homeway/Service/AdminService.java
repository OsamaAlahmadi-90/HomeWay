package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.Model.Company;
import com.example.homeway.Repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {


    private  final CompanyRepository companyRepository;


    public void approveCompany(Integer companyId) {

        Company company = companyRepository.findCompanyById(companyId);
        if (company == null) {
            throw new ApiException("Company not found with id: " + companyId);
        }

        if ("approved".equalsIgnoreCase(company.getStatus())) {
            throw new ApiException("Company is already approved");
        }

        company.setStatus("approved");
        companyRepository.save(company);
    }


    public void rejectCompany(Integer companyId) {

        Company company = companyRepository.findCompanyById(companyId);
        if (company == null) {
            throw new ApiException("Company not found with id: " + companyId);
        }

        if ("rejected".equalsIgnoreCase(company.getStatus())) {
            throw new ApiException("Company is already rejected");
        }

        company.setStatus("rejected");
        companyRepository.save(company);
    }

    public List<Company> getPendingCompanies() {

        List<Company> companies = companyRepository.getPendingCompanies();

        if (companies.isEmpty()) {
            throw new ApiException("No pending companies found");
        }

        return companies;
    }


}
