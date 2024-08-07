package com._32bit.project.cashier_system.resource;

import com._32bit.project.cashier_system.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionResource {

    private final SessionService sessionService;

    @GetMapping("/test")
    public String test() {
        return "Session Resource works!";
    }

    @GetMapping("/sessions")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<?> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/deleted-sessions")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getAllDeletedSessions() {
        return sessionService.getAllDeletedSessions();
    }

    @GetMapping("/sale-point-sessions/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getSalePointSessions(@PathVariable Long id) {
        return sessionService.getSalePointSessions(id);
    }

    @PostMapping("/open-session/{salePointId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<?> openSession(@PathVariable Long salePointId, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return sessionService.openSession(salePointId,token);
    }

    @PostMapping("/close-session/{salePointId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<?> closeSession(@PathVariable Long salePointId, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        return sessionService.closeSession(salePointId, token);
    }

    @GetMapping("/{sessionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_CASHIER')")
    public ResponseEntity<?> getSessionInfo(@PathVariable Long sessionId) {
        return sessionService.getSessionInfo(sessionId);
    }

    @GetMapping("/delete-session/{sessionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteSession(@PathVariable Long sessionId) {
        return sessionService.deleteSession(sessionId);
    }

    @GetMapping("/restore-session/{sessionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> restoreSession(@PathVariable Long sessionId) {
        return sessionService.restoreSession(sessionId);
    }

    @GetMapping("/sale-point-open-session/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getSalePointOpenSession(@PathVariable Long id) {
        return sessionService.getSalePointOpenSession(id);
    }
}
