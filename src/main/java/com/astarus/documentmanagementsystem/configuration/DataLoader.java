package com.astarus.documentmanagementsystem.configuration;

import com.astarus.documentmanagementsystem.appuser.AppUser;
import com.astarus.documentmanagementsystem.appuser.AppUserRepository;
import com.astarus.documentmanagementsystem.appuser.AppUserRole;
import com.astarus.documentmanagementsystem.registration.token.ConfirmationToken;
import com.astarus.documentmanagementsystem.registration.token.ConfirmationTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner run(AppUserRepository appUserRepository, ConfirmationTokenService confirmationTokenService, PasswordEncoder passwordEncoder) {
        return args -> {
            AppUser user = new AppUser(
                    "Тест",
                    "Тестович",
                    "Alterway25@gmail.com",
                    passwordEncoder.encode("1"),
                    AppUserRole.USER
            );
            user.setLocked(false);
            user.setEnabled(true);
            appUserRepository.save(user);

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    "b5ac2ff4-76d2-46d3-9f72-afa429d42fd5",
                    LocalDateTime.now(),
                    LocalDateTime.now().plusHours(1),
                    user
            );
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationTokenService.saveConfirmationToken(confirmationToken);
        };
    }
}

