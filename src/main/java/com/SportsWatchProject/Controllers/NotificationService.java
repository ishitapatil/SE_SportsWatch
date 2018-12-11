package com.SportsWatchProject.Controllers;
import com.SportsWatchProject.Models.Notifications;
import com.SportsWatchProject.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public void saveNotification(Notifications notifications){
        notificationRepository.save(notifications);
    }

    public List<Notifications> getNotificationsForUser(long id){
        return notificationRepository.findByToBeNotified(id);
    }

    public Notifications getNotification(int id){
        return notificationRepository.findById(id);
    }

}
