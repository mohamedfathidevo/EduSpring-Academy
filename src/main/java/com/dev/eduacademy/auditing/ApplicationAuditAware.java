package com.dev.eduacademy.auditing;

import com.dev.eduacademy.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return Optional.empty();
        User currentUserAuthenticated = (User) authentication.getPrincipal();
        return Optional.ofNullable(currentUserAuthenticated.getEmail());
    }
}
