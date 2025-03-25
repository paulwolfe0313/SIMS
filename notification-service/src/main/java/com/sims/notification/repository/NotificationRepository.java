package com.sims.notification.repository;

import com.sims.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndRead(Long userId, boolean read);
    List<Notification> findByOrderId(Long orderId);
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Notification> findAllByOrderByCreatedAtDesc();
}
