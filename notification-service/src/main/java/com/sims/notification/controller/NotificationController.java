package com.sims.notification.controller;

import com.sims.notification.entity.Notification;
import com.sims.notification.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Notification>> getUserNotifications(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        if (userId != null && !hasRole(request, "ADMIN")) {
            // Regular users can only see their own notifications
            return ResponseEntity.ok(notificationService.getNotificationsByUserId(Long.parseLong(userId)));
        }
        // Admins can see all notifications
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Notification notification = notificationService.getNotificationById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Check if user has access to this notification
        if (!hasRole(request, "ADMIN") && !notification.getUserId().toString().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(notification);
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Notification notification = notificationService.getNotificationById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Check if user has access to this notification
        if (!hasRole(request, "ADMIN") && !notification.getUserId().toString().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        Notification notification = notificationService.getNotificationById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Check if user has access to delete this notification
        if (!hasRole(request, "ADMIN") && !notification.getUserId().toString().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/webhook/{provider}")
    public ResponseEntity<Void> handleWebhook(@PathVariable String provider, @RequestBody String payload) {
        notificationService.processWebhook(provider, payload);
        return ResponseEntity.ok().build();
    }

    private boolean hasRole(HttpServletRequest request, String role) {
        return request.isUserInRole("ROLE_" + role);
    }
}
