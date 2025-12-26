package com.example.homeway.Service;


import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.WorkerDTOIn;
import com.example.homeway.Model.Company;
import com.example.homeway.Model.User;
import com.example.homeway.Model.Worker;
import com.example.homeway.Repository.CompanyRepository;
import com.example.homeway.Repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private final WorkerRepository workerRepository;
    private final CompanyRepository companyRepository;

    // ===============================
    // Get company workers
    // ===============================
    public List<Worker> getCompanyWorkers(User user) {

        Company company = companyRepository.findCompanyById(user.getId());
        if (company == null) {
            throw new ApiException("Company not found");
        }

        return workerRepository.findAllByCompany_Id(company.getId());
    }

    // ===============================
    // Update worker
    // ===============================
    public void updateWorker(User companyUser, Integer workerId, WorkerDTOIn dto) {

        Company company = companyRepository.findCompanyById(companyUser.getId());
        if (company == null) {
            throw new ApiException("Company not found");
        }

        Worker worker = workerRepository.findWorkerById(workerId);
        if (worker == null) {
            throw new ApiException("Worker not found");
        }

        if (!worker.getCompany().getId().equals(company.getId())) {
            throw new ApiException("You are not allowed to update this worker");
        }

        User user = worker.getUser();

        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());



        workerRepository.save(worker);
    }

    // ===============================
    // Delete worker
    // ===============================
    public void deleteWorker(User user, Integer workerId) {

        Company company = companyRepository.findCompanyById(user.getId());
        if (company == null) {
            throw new ApiException("Company not found");
        }

        Worker worker = workerRepository.findWorkerById(workerId);
        if (worker == null) {
            throw new ApiException("Worker not found");
        }

        if (!worker.getCompany().getId().equals(company.getId())) {
            throw new ApiException("You are not allowed to delete this worker");
        }

        workerRepository.delete(worker);
    }
}
