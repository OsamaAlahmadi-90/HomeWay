package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.CompanyDTOIn;
import com.example.homeway.DTO.In.CustomerDTOIn;
import com.example.homeway.DTO.In.WorkerDTOIn;
import com.example.homeway.Model.Company;
import com.example.homeway.Model.Customer;
import com.example.homeway.Model.User;
import com.example.homeway.Model.Worker;
import com.example.homeway.Repository.CompanyRepository;
import com.example.homeway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public void registerCustomer(CustomerDTOIn dto) {

        if (userRepository.findUserByUsername(dto.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }
        if (userRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());
        user.setCreated_at(LocalDateTime.now());
        user.setRole("CUSTOMER");
        user.setIsSubscribed(false);

        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hashedPassword);

        Customer customer = new Customer();
        customer.setUser(user);

        user.setCustomer(customer);
        userRepository.save(user);
    }

    public void registerCompany(CompanyDTOIn dto) {
        if (userRepository.findUserByUsername(dto.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }
        if (userRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());
        user.setCreated_at(LocalDateTime.now());
        user.setRole(dto.getRole());//company role
        user.setIsSubscribed(false);

        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hashedPassword);

        Company company = new Company();
        company.setUser(user);
        company.setStatus("pending"); //admin approves

        user.setCompany(company);
        userRepository.save(user);
    }

    public void registerWorker(WorkerDTOIn dto) {

        if (userRepository.findUserByUsername(dto.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }
        if (userRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        Company company = companyRepository.findCompanyById(dto.getCompanyId());
        if (company == null) {
            throw new ApiException("Company not found with id: " + dto.getCompanyId());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());
        user.setCreated_at(LocalDateTime.now());
        user.setRole("WORKER");
        user.setIsSubscribed(false);

        String hashedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hashedPassword);

        Worker worker = new Worker();
        worker.setUser(user); // mapsId
        worker.setCompany(company);
        worker.setIsActive(false);
        worker.setIsAvailable(true);

        user.setWorker(worker);
        userRepository.save(user);
    }

}
