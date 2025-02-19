package com.sims.notification.service;

import com.sims.notification.entity.Notification;
import com.sims.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public Notification saveNotification(Notification notification) {
        logger.info("📌 Attempting to save notification: {}", notification);
        Notification savedNotification = notificationRepository.save(notification);
        logger.info("✅ Successfully saved notification in DB: {}", savedNotification);
        return savedNotification;
    }

    // ✅ Retrieve All Notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
