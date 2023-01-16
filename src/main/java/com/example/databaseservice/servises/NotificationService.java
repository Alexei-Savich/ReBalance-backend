package com.example.databaseservice.servises;

import com.example.databaseservice.entities.Notification;
import com.example.databaseservice.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<Notification> findAllByUserIdAndDeleteThem(Long id) {
        List<Notification> notifications = notificationRepository.findAllByUserId(id);

        for (Notification notification : notifications) {
            deleteNotificationById(notification.getId());
        }

        return notifications;
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }
}
