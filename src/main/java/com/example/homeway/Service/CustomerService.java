package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.CustomerDTOIn;
import com.example.homeway.Model.Customer;
import com.example.homeway.Model.User;
import com.example.homeway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final UserRepository userRepository;

    public Customer getMyCustomer(User user) {
        if (user == null) {
            throw new ApiException("Unauthenticated");
        }

        if (user.getCustomer() == null) {
            throw new ApiException("Customer not found");
        }

        return user.getCustomer();
    }

    public void updateMyCustomer(User user, CustomerDTOIn dto) {
        if (user == null) {
            throw new ApiException("Unauthenticated");
        }

        if (user.getCustomer() == null) {
            throw new ApiException("Customer not found");
        }

        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());
        user.setPassword(dto.getPassword());

        userRepository.save(user);
    }


    public void deleteMyCustomer(User user) {
        if (user == null) {
            throw new ApiException("Unauthenticated");
        }

        if (user.getCustomer() == null) {
            throw new ApiException("Customer not found");
        }

        userRepository.delete(user);
    }



}
