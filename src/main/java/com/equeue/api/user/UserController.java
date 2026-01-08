package com.equeue.api.user;

import com.equeue.api.dto.user.UserProfileResponse;
import com.equeue.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse me(
            @AuthenticationPrincipal User user) {
        return UserProfileResponse.from(user);
    }
}

