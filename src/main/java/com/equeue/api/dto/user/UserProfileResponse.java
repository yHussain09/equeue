package com.equeue.api.dto.user;

import com.equeue.security.entity.Role;
import com.equeue.security.entity.User;
import com.equeue.security.enums.RoleName;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserProfileResponse {

    private Long id;
    private String username;
    private Set<RoleName> roles;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet()))
                .build();
    }
}

