package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.RequestCreateDTOIn;
import com.example.homeway.DTO.In.RequestUpdateDTOIn;
import com.example.homeway.DTO.Out.RequestDTOOut;
import com.example.homeway.Model.*;
import com.example.homeway.Repository.CompanyRepository;
import com.example.homeway.Repository.NotificationRepository;
import com.example.homeway.Repository.PropertyRepository;
import com.example.homeway.Repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final PropertyRepository propertyRepository;
    private final CompanyRepository companyRepository;
    private final NotificationRepository notificationRepository;

    // admin
    public List<RequestDTOOut> getAllRequests() {
        List<RequestDTOOut> outs = new ArrayList<>();

        for (Request r : requestRepository.findAll()) {
            outs.add(convertToDTO(r));
        }

        return outs;
    }

    //endpoint1 - admin - company
    public RequestDTOOut getRequestById(Integer requestId) {
        Request r = requestRepository.findRequestById(requestId);
        if (r == null) throw new ApiException("request not found with id: " + requestId);

        return convertToDTO(r);
    }

    //endpoint2 - admin - company?
    public List<RequestDTOOut> getRequestsByCompany(Integer companyId) {
        List<RequestDTOOut> outs = new ArrayList<>();

        for (Request r : requestRepository.findAllByCompany_Id(companyId)) {
            outs.add(convertToDTO(r));
        }

        return outs;
    }

    //endpoint3 - admin - company
    public List<RequestDTOOut> getRequestsByCustomer(Integer customerId) {
        List<RequestDTOOut> outs = new ArrayList<>();

        for (Request r : requestRepository.findAllByCustomer_Id(customerId)) {
            outs.add(convertToDTO(r));
        }

        return outs;
    }

    public void updateRequest(Integer requestId, RequestUpdateDTOIn dto) {
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found with id: " + requestId);

        Company company = companyRepository.findCompanyById(dto.getCompanyId());
        if (company == null) throw new ApiException("company not found with id: " + dto.getCompanyId());

        Property property = propertyRepository.findPropertyById(dto.getPropertyId());
        if (property == null) throw new ApiException("property not found with id: " + dto.getPropertyId());

        request.setCompany(company);
        request.setProperty(property);

        request.setTimeWindow(dto.getTimeWindow());
        request.setDescription(dto.getDescription());

        requestRepository.save(request);
    }

    public void deleteRequest(Integer requestId) {
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found with id: " + requestId);

        requestRepository.delete(request);
    }

    @Transactional
    public void requestInspection(User user, Integer propertyId, RequestCreateDTOIn dto, Integer companyID) {

        if (user == null) {
            throw new ApiException("unauthorized");
        }
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

        createCompanyNotification(company, "New inspection request", "A new inspection request has been created for your company.");
    }

    @Transactional
    public void requestMoveToHouse(User user, Integer propertyId, RequestCreateDTOIn dto, Integer companyID){
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
        createCompanyNotification(company,"New moving request", "A new moving request has been created for your company.");
    }

    public void requestMaintenance(User user, Integer propertyId, RequestCreateDTOIn dto, Integer companyID){
        if (user == null) throw new ApiException("unauthorized");
        Customer customer = user.getCustomer();
        if (customer == null) throw new ApiException("customer profile not found");


        Property property = propertyRepository.findPropertyById(propertyId);
        if (property == null) {
            throw new ApiException("property not found with id: " + propertyId);
        }

        if (property.getCustomer() == null || !property.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("you can only request maintenance for your own property");
        }

        Company company = companyRepository.findCompanyById(companyID);
        if (company == null) {
            throw new ApiException("company not found with id: " + companyID);
        }

        if (company.getUser() == null || !"MAINTENANCE_COMPANY".equalsIgnoreCase(company.getUser().getRole())) {
            throw new ApiException("selected company is not an MAINTENANCE_COMPANY");
        }

        Request request = new Request();
        request.setStatus("pending");
        request.setType("maintenance");
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

        createCompanyNotification(company, "New maintenance request", "A new maintenance request has been created for your company.");
    }

    @Transactional
    public void requestResign(User user, Integer propertyId, RequestCreateDTOIn dto, Integer companyID){
        if (user == null) throw new ApiException("unauthorized");
        Customer customer = user.getCustomer();
        if (customer == null) throw new ApiException("customer profile not found");


        Property property = propertyRepository.findPropertyById(propertyId);
        if (property == null) {
            throw new ApiException("property not found with id: " + propertyId);
        }

        if (property.getCustomer() == null || !property.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("you can only request Redesign for your own property");
        }

        Company company = companyRepository.findCompanyById(companyID);
        if (company == null) {
            throw new ApiException("company not found with id: " + companyID);
        }

        if (company.getUser() == null || !"REDESIGN_COMPANY".equalsIgnoreCase(company.getUser().getRole())) {
            throw new ApiException("selected company is not an REDESIGN_COMPANY");
        }

        Request request = new Request();
        request.setStatus("pending");
        request.setType("redesign");
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

        createCompanyNotification(company, "New redesign request", "A new redesign request has been created for your company.");
    }

    private void createCompanyNotification(Company company, String title, String message) {
        if (company == null) return;

        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setCreated_at(LocalDateTime.now());
        notification.setCompany(company);
        notificationRepository.save(notification);
    }
    public RequestDTOOut convertToDTO(Request r) {
        return new RequestDTOOut(
                r.getId(),
                r.getStatus(), r.getType(), r.getCreatedAt(), r.getStartDate(),
                r.getEndDate(), r.getTimeWindow(), r.getDescription(), r.getIsPaid(),
                (r.getCustomer() != null ? r.getCustomer().getId() : null),
                (r.getCompany() != null ? r.getCompany().getId() : null),
                (r.getProperty() != null ? r.getProperty().getId() : null),
                (r.getWorker() != null ? r.getWorker().getId() : null),
                (r.getVehicle() != null ? r.getVehicle().getId() : null),
                (r.getOffer() != null ? r.getOffer().getId() : null)
        );
    }
}
