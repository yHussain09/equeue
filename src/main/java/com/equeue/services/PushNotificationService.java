package com.equeue.services;

import com.equeue.entities.UserDevice;
import com.equeue.repositories.UserDeviceRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PushNotificationService {

    private final UserDeviceRepository deviceRepository;

    public void notifyUser(
            Long userId,
            String title,
            String body,
            Map<String, String> data) {

        List<UserDevice> devices =
                deviceRepository.findByUserId(userId);

        for (UserDevice device : devices) {

            Message message = Message.builder()
                    .setToken(device.getFcmToken())
                    .setNotification(
                            Notification.builder()
                                    .setTitle(title)
                                    .setBody(body)
                                    .build()
                    )
                    .putAllData(data)
                    .build();

            FirebaseMessaging.getInstance().sendAsync(message);
        }
    }
}

