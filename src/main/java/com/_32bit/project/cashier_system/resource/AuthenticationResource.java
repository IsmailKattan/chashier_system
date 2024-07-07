package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.response.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.LoginInfoDto;
import com._32bit.project.cashier_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        JwtResponseDto jwtResponseDto = authService.authenticateUser(loginInfoDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateTeamMemberDto createTeamMemberDto) {
        return authService.registerUser(createTeamMemberDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
    }

}