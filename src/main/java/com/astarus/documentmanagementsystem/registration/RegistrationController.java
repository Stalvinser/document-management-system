package com.astarus.documentmanagementsystem.registration;

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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "registrationForm";
    }

    @GetMapping("/registrationSuccess")
    public String showRegistrationForm() {
        return "registrationSuccess";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest,
                                      RedirectAttributes redirectAttributes) {
        String message = registrationService.register(registrationRequest);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/registrationSuccess";
    }
    //TODO: REDIRECT TO LOGIN, CATCH METHOD
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
