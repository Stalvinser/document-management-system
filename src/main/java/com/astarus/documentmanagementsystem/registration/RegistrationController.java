package com.astarus.documentmanagementsystem.registration;

import com.astarus.documentmanagementsystem.email.EmailSendingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@AllArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @GetMapping()
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "/registration/registrationForm";
    }

    @GetMapping("/success")
    public String showRegistrationForm() {
        return "/registration/registrationSuccess";
    }

    @PostMapping()
    public String registerUserAccount(@ModelAttribute("registrationRequest") RegistrationRequest registrationRequest,
                                      RedirectAttributes redirectAttributes,
                                      Model model) {
        try {
            String userEmail = registrationRequest.getEmail();
            registrationService.register(registrationRequest);
            System.out.println(userEmail);
            redirectAttributes.addAttribute("userEmail", userEmail);
            return "redirect:/registration/success";
        } catch (EmailSendingException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "/registration/registrationForm";
        }
    }
    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        registrationService.confirmToken(token);
        String successMessage = "Email подтвержден. Сейчас можно авторизироваться.";
        redirectAttributes.addFlashAttribute("successMessage", successMessage);
        return "redirect:/login";
    }

}
