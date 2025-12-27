package com.example.homeway.Repository;

import com.example.homeway.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("""
       SELECT n
       FROM Notification n
       ORDER BY n.created_at DESC
       """)
    List<Notification> getAllNotificationsOrdered();

    Notification findNotificationById(Integer id);

    @Query("""
           SELECT n
           FROM Notification n
           WHERE n.customer IS NOT NULL
           ORDER BY n.created_at DESC
           """)
    List<Notification> getAllCustomerNotifications();

    @Query("""
       SELECT n
       FROM Notification n
       WHERE n.company.id = :companyId
       ORDER BY n.created_at DESC
       """)
    List<Notification> getCompanyNotifications(Integer companyId);

}
