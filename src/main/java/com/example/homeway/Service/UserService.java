package com.example.homeway.Service;

import com.example.homeway.API.ApiException;
import com.example.homeway.DTO.In.UserDTOIn;
import com.example.homeway.DTO.Out.UserDTOOut;
import com.example.homeway.Model.User;
import com.example.homeway.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTOOut> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTOOut> outs = new ArrayList<>();

        for (User u : users) {
            outs.add(convertToDTO(u));
        }

        return outs;
    }

    public UserDTOOut getUserById(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("user not found with id: " + userId);

        return convertToDTO(user);
    }

    public void updateUser(Integer userId, UserDTOIn dto) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("user not found with id: " + userId);

        if (!user.getUsername().equals(dto.getUsername())) {
            if (userRepository.findUserByUsername(dto.getUsername()) != null) {
                throw new ApiException("username already exists");
            }
        }

        if (!user.getEmail().equals(dto.getEmail())) {
            if (userRepository.findUserByEmail(dto.getEmail()) != null) {
                throw new ApiException("email already exists");
            }
        }

        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String hashed = new BCryptPasswordEncoder().encode(dto.getPassword());
            user.setPassword(hashed);
        }

        userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("user not found with id: " + userId);

        userRepository.delete(user);
    }

    //helper method
    private UserDTOOut convertToDTO(User user) {
        return new UserDTOOut(
                user.getId(), user.getUsername(), user.getName(),
                user.getEmail(), user.getPhone(), user.getCountry(),
                user.getCity(), user.getCreated_at(), user.getRole());
    }
}