package com.example.homeway.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.Model.*;
import com.example.homeway.Repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final RequestRepository requestRepository;
    private final WorkerRepository workerRepository;
    private final OfferRepository offerRepository;
    private final NotificationRepository notificationRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        Company company = companyRepository.findCompanyById(companyId);
        if (company == null) throw new ApiException("company not found with id: " + companyId);
        return company;
    }

    public List<Company> getCompaniesByRole(String role) {
        return companyRepository.findAllByUser_Role(role);
    }

    public void approveInspectionRequest(User user, Integer requestId, Double price) {

        if (user == null) throw new ApiException("unauthorized");
        Company company = user.getCompany();
        if (company == null) {
            throw new ApiException("company profile not found");
        }

        if (!"INSPECTION_COMPANY".equalsIgnoreCase(user.getRole())) {
            throw new ApiException("only INSPECTION_COMPANY can approve inspection requests");
        }

        Request request = requestRepository.findRequestById(requestId);
        if (request == null) {
            throw new ApiException("request not found with id: " + requestId);
        }

        if (!"inspection".equalsIgnoreCase(request.getType())) {
            throw new ApiException("request type is not INSPECTION");
        }

        if (request.getCompany() == null || !request.getCompany().getId().equals(company.getId())) {
            throw new ApiException("request is not assigned to this company");
        }

        if (!"pending".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("only pending requests can be approved");
        }

        if (request.getOffer() != null) {
            throw new ApiException("offer already exists for this request");
        }

        //create offer
        Offer offer = new Offer();
        offer.setPrice(price);
        offer.setStatus("not_paid");
        offer.setCreatedAt(LocalDateTime.now());
        offer.setRequest(request);
        offerRepository.save(offer);

        request.setStatus("approved");
        requestRepository.save(request);

        //notification
        createCustomerNotification(request.getCustomer(),"approved request","Your request was approved. Please accept/reject the offer.");
    }


    @Transactional
    public void startInspectionRequest(User user, Integer requestId) {

        if (user == null) throw new ApiException("unauthorized");
        Company company = user.getCompany();
        if (company == null) throw new ApiException("company profile not found");

        if (!"INSPECTION_COMPANY".equalsIgnoreCase(user.getRole())) {
            throw new ApiException("only INSPECTION_COMPANY can start inspection requests");
        }
        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found with id: " + requestId);

        if (!"inspection".equalsIgnoreCase(request.getType())) {
            throw new ApiException("request type is not INSPECTION");
        }

        if (request.getCompany() == null || !request.getCompany().getId().equals(company.getId())) {
            throw new ApiException("request is not assigned to this company");
        }

        if (!"approved".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("only approved requests can be started");
        }

        Offer offer = request.getOffer();
        if (offer == null) {
            throw new ApiException("offer does not exist for this request");
        }

        if (!"paid".equalsIgnoreCase(offer.getStatus())) {
            throw new ApiException("offer is not paid yet");
        }

        Worker worker = workerRepository.findAvailableInspectorWorker(company.getId());
        if (worker == null){
            throw new ApiException("no available inspector worker found");
        }

        worker.setIsAvailable(false);
        workerRepository.save(worker);

        request.getWorkers().add(worker);

        request.setStatus("in_progress");
        request.setStartDate(LocalDate.now());

        requestRepository.save(request);

        createCustomerNotification(request.getCustomer(), "Request started", "Your request is now in progress.");
    }

    @Transactional
    public void completeInspectionRequest(User user, Integer requestId) {

        if (user == null) throw new ApiException("unauthorized");

        Company company = user.getCompany();
        if (company == null) throw new ApiException("company profile not found");

        if (!"INSPECTION_COMPANY".equalsIgnoreCase(user.getRole())) {
            throw new ApiException("only INSPECTION_COMPANY can complete inspection requests");
        }

        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found with id: " + requestId);

        if (!"inspection".equalsIgnoreCase(request.getType())) {
            throw new ApiException("request type is not INSPECTION");
        }

        if (request.getCompany() == null || !request.getCompany().getId().equals(company.getId())) {
            throw new ApiException("request is not assigned to this company");
        }

        if (!"in_progress".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("only in_progress requests can be completed");
        }

        if (request.getWorkers() != null) {
            for (Worker w : request.getWorkers()) {
                w.setIsAvailable(true);
            }
            request.getWorkers().clear();
        }

        request.setStatus("completed");
        request.setEndDate(LocalDate.now());

        requestRepository.save(request);

        createCustomerNotification(request.getCustomer(), "Request completed", "Your request has been completed. Report will be received soon.");
    }

    @Transactional
    public void rejectInspectionRequest(User user, Integer requestId) {

        if (user == null) throw new ApiException("unauthorized");
        Company company = user.getCompany();
        if (company == null) throw new ApiException("company profile not found");

        if (!"INSPECTION_COMPANY".equalsIgnoreCase(user.getRole())) {
            throw new ApiException("only INSPECTION_COMPANY can reject inspection requests");
        }

        Request request = requestRepository.findRequestById(requestId);
        if (request == null) throw new ApiException("request not found with id: " + requestId);

        if (!"inspection".equalsIgnoreCase(request.getType())) {
            throw new ApiException("request type is not INSPECTION");
        }

        if (request.getCompany() == null || !request.getCompany().getId().equals(company.getId())) {
            throw new ApiException("request is not assigned to this company");
        }

        if (!"pending".equalsIgnoreCase(request.getStatus())) {
            throw new ApiException("only pending requests can be rejected");
        }

        if (request.getOffer() != null) {
            offerRepository.delete(request.getOffer());
            request.setOffer(null);
        }

        request.setStatus("rejected");
        requestRepository.save(request);

        createCustomerNotification(request.getCustomer(), "Request rejected", "Your request has been rejected.");
    }

    private void createCustomerNotification(Customer customer, String title, String message) {
        if (customer == null) return;

        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setCreated_at(LocalDateTime.now());
        notification.setCustomer(customer);

        notificationRepository.save(notification);
    }

}
