package com.astarus.documentmanagementsystem.registration;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    @Override
    public boolean test(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

