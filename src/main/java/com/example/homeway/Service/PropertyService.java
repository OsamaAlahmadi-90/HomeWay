package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.PropertyDTOIn;
import com.example.homeway.Model.Customer;
import com.example.homeway.Model.Property;
import com.example.homeway.Model.User;
import com.example.homeway.Repository.CustomerRepository;
import com.example.homeway.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final CustomerRepository customerRepository;

    // ROLE: CUSTOMER
    public void createProperty(User user, PropertyDTOIn dto) {

        Customer customer = customerRepository.findCustomerById(user.getId());
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        Property property = new Property();
        property.setAddress(dto.getAddress());
        property.setNickname(dto.getNickname());
        property.setType(dto.getType());
        property.setCreatedAt(LocalDateTime.now());
        property.setCustomer(customer);

        propertyRepository.save(property);
    }

    //// ROLE: CUSTOMER
    public List<Property> getMyProperties(User user) {

        Customer customer = customerRepository.findCustomerById(user.getId());
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        return propertyRepository.findAllByCustomer_Id(customer.getId());
    }

    // ROLE: CUSTOMER
// Description: Customer can update his own property only
    public void updateProperty(User user, Integer propertyId, PropertyDTOIn dto) {

        Customer customer = customerRepository.findCustomerById(user.getId());
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property == null) {
            throw new ApiException("Property not found");
        }

        if (!property.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("You are not allowed to update this property");
        }

        property.setAddress(dto.getAddress());
        property.setNickname(dto.getNickname());
        property.setType(dto.getType());

        propertyRepository.save(property);
    }

    // ROLE: CUSTOMER
// Description: Customer can delete his own property only
    public void deleteProperty(User user, Integer propertyId) {

        Customer customer = customerRepository.findCustomerById(user.getId());
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        Property property = propertyRepository.findById(propertyId).orElse(null);
        if (property == null) {
            throw new ApiException("Property not found");
        }

        if (!property.getCustomer().getId().equals(customer.getId())) {
            throw new ApiException("You are not allowed to delete this property");
        }

        propertyRepository.delete(property);
    }
}
