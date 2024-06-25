package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.teamMember.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.JwtResponseDto;
import com._32bit.project.cashier_system.DTO.teamMember.LoginInfoDto;
import com._32bit.project.cashier_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthResource {
    private final AuthService authService;
    private final UserCredentialRepository userCredentialRepository;

    @Autowired
    public AuthResource(AuthService authService, UserCredentialRepository userCredentialRepository) {
        this.authService = authService;
        this.userCredentialRepository = userCredentialRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginInfoDto loginInfoDto) {
        JwtResponseDto jwtResponseDto = authService.authenticateUser(loginInfoDto);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateTeamMemberDto createTeamMemberDto) {
        if (userCredentialRepository.existsByUsername(createTeamMemberDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userCredentialRepository.existsByEmail(createTeamMemberDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        return ResponseEntity.ok(authService.registerUser(createTeamMemberDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(new MessageResponse("User logged out successfully!"));
    }

}
