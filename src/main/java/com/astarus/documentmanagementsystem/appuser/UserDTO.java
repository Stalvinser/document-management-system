package com.astarus.documentmanagementsystem.appuser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public record UserDTO(Long id, String email, String fullName) {
    public static List<UserDTO> convertToDTOList(List<AppUser> appUsers) {
        if (appUsers.isEmpty()) {
            return Collections.emptyList();
        }
        return appUsers.stream()
                .map(appUser -> new UserDTO(appUser.getId(), appUser.getEmail(), appUser.getFullName()))
                .collect(Collectors.toList());
    }
}
