package com.astarus.documentmanagementsystem.registration;

import com.astarus.documentmanagementsystem.appuser.AppUser;
import com.astarus.documentmanagementsystem.appuser.AppUserRole;
import com.astarus.documentmanagementsystem.appuser.AppUserService;
import com.astarus.documentmanagementsystem.email.EmailSender;
import com.astarus.documentmanagementsystem.email.EmailSendingException;
import com.astarus.documentmanagementsystem.registration.token.ConfirmationToken;
import com.astarus.documentmanagementsystem.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public void register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = appUserService.signUpUser(new AppUser(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                AppUserRole.USER));
        String link = "http://localhost:8080/registration/confirm?token=" + token;
        try {
            emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));
        } catch (Exception e) {
            logger.error("Failed to send confirmation email to {}", request.getEmail(), e);
            throw new EmailSendingException("Failed to send confirmation email", e);
        }

    }

    @Transactional
    public void confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());
    }

    private String buildEmail(String name, String link) throws IOException {
        String filePath = new ClassPathResource("templates/emailTemplate.html").getFile().getAbsolutePath();
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        content = content.replace("{{name}}", name).replace("{{link}}", link);
        return content;
    }


}
