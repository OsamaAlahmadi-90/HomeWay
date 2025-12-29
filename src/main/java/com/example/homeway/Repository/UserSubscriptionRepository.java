package com.example.homeway.Repository;

import com.example.homeway.Model.User;
import com.example.homeway.Model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Integer> {

    UserSubscription findUserSubscriptionById(Integer id);

    UserSubscription findUserSubscriptionByUser(User user);

    UserSubscription findUserSubscriptionByUser_Id(Integer userId);

    List<UserSubscription> findUserSubscriptionsByUser(User user);
}
