package com.astarus.documentmanagementsystem.registration;

import com.astarus.documentmanagementsystem.email.EmailSendingException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registrationForm";
    }

    @GetMapping("/registrationSuccess")
    public String showRegistrationForm() {
        return "registrationSuccess";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        try {
            String userEmail = registrationRequest.getEmail();
            registrationService.register(registrationRequest);
            System.out.println(userEmail);
            redirectAttributes.addAttribute("userEmail", userEmail);
            return "redirect:/registrationSuccess";
        } catch (EmailSendingException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "registrationForm";
        }
    }
    @GetMapping(path = "/registration/confirm")
    public String confirm(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        registrationService.confirmToken(token);
        String successMessage = "Email confirmed. You can login now.";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/login";
    }

}
