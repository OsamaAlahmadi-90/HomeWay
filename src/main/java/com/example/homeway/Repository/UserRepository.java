package com.example.homeway.Repository;

import com.example.homeway.Model.User;
import com.example.homeway.Model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    User findUserByUserSubscription(UserSubscription userSubscription);

}