package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.LoginInfoDto;
import com._32bit.project.cashier_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationResource {

    private static final Logger logger = LogManager.getLogger(AuthenticationResource.class);
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginInfoDto loginInfoDto) {
        logger.info("Login attempt for user: " + loginInfoDto.getUsername());
        try {
            JwtResponseDto jwtResponseDto = authService.authenticateUser(loginInfoDto);
            logger.info("Login successful for user: " + loginInfoDto.getUsername());
            return ResponseEntity.ok(jwtResponseDto);
        } catch (Exception e) {
            logger.error("Login failed for user: " + loginInfoDto.getUsername(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Authentication failed: " + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
    }

}