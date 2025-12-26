package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.RequestDTOIn;
import com.example.homeway.Model.*;
import com.example.homeway.Repository.CompanyRepository;
import com.example.homeway.Repository.PropertyRepository;
import com.example.homeway.Repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final PropertyRepository propertyRepository;
    private final CompanyRepository companyRepository;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<Request> getAllRequestsByCompany(Integer companyId) {
        return requestRepository.findAllByCompany_Id(companyId);
    }

    @Transactional
    public void requestInspection(User user, Integer propertyId, RequestDTOIn dto, Integer companyID) {

        if (user == null) throw new ApiException("unauthorized");
        Customer customer = user.getCustomer();
        if (customer == null) throw new ApiException("customer profile not found");



        Property property = propertyRepository.findPropertyById(propertyId);
        if (property == null) {
            throw new ApiException("property not found with id: " + propertyId);
        }

        if (property.getCustomer() == null || !property.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("you can only request inspection for your own property");
        }

        Company company = companyRepository.findCompanyById(companyID);
        if (company == null) {
            throw new ApiException("company not found with id: " + companyID);
        }

        if (company.getUser() == null || !"INSPECTION_COMPANY".equalsIgnoreCase(company.getUser().getRole())) {
            throw new ApiException("selected company is not an INSPECTION_COMPANY");
        }

        Request request = new Request();
        request.setStatus("pending");
        request.setType("inspection");
        request.setCreatedAt(LocalDateTime.now());
        request.setStartDate(null);
        request.setEndDate(null);
        request.setIsPaid(false);

        request.setTimeWindow(dto.getTimeWindow());
        request.setDescription(dto.getDescription());

        request.setCustomer(customer);
        request.setProperty(property);

        request.setCompany(company);
        requestRepository.save(request);
    }
    public void requestMoveToHouse(User user, Integer propertyId, RequestDTOIn dto,Integer companyID){
        if (user == null) throw new ApiException("unauthorized");
        Customer customer = user.getCustomer();
        if (customer == null) throw new ApiException("customer profile not found");



        Property property = propertyRepository.findPropertyById(propertyId);
        if (property == null) {
            throw new ApiException("property not found with id: " + propertyId);
        }

        if (property.getCustomer() == null || !property.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("you can only request moving for your own property");
        }

        Company company = companyRepository.findCompanyById(companyID);
        if (company == null) {
            throw new ApiException("company not found with id: " + companyID);
        }

        if (company.getUser() == null || !"MOVING_COMPANY".equalsIgnoreCase(company.getUser().getRole())) {
            throw new ApiException("selected company is not an MOVING_COMPANY");
        }

        Request request = new Request();
        request.setStatus("pending");
        request.setType("moving");
        request.setCreatedAt(LocalDateTime.now());
        request.setStartDate(null);
        request.setEndDate(null);
        request.setIsPaid(false);

        request.setTimeWindow(dto.getTimeWindow());
        request.setDescription(dto.getDescription());

        request.setCustomer(customer);
        request.setProperty(property);

        request.setCompany(company);
        requestRepository.save(request);
    }

}
