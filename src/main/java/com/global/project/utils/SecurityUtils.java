package com.global.project.utils;

import com.global.project.configuration.AccountDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Long getCurrentUserId() {
        return getCurrentUser().getAccount().getId();
    }

    public static String getCurrentUsername() {
        return getCurrentUser().getAccount().getUsername();
    }

    public static AccountDetailsImpl getCurrentUser() {
        if (getCurrentAuthentication() != null)
            return (AccountDetailsImpl) getCurrentAuthentication().getPrincipal();
        return null;
    }

    private static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return getCurrentAuthentication().isAuthenticated();
    }

    public static boolean hasRole(String role) {
        return getCurrentAuthentication().getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(role));
    }
}
