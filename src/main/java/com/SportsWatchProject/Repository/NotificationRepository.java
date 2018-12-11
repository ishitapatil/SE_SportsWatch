package com.SportsWatchProject.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.SportsWatchProject.Models.Notifications;
import java.util.List;


@Repository
public interface NotificationRepository extends CrudRepository<Notifications, Integer> {
    List<Notifications> findByToBeNotified(long toBeNotified);

    Notifications findById(int notificationId);

}
