package com.equeue.controllers;

import com.equeue.entities.UserDevice;
import com.equeue.repositories.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final UserDeviceRepository repository;

    @PostMapping("/fcm-token")
    public void registerToken(
            @AuthenticationPrincipal JwtUser user,
            @RequestBody String fcmToken) {

        UserDevice device = new UserDevice();
        device.setUserId(user.getId());
        device.setFcmToken(fcmToken);
        device.setLastUpdated(Instant.now());

        repository.save(device);
    }
}

