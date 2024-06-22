package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.AuthRequestDto;
import com._32bit.project.cashier_system.DTO.AuthResponseDto;
import com._32bit.project.cashier_system.DTO.enums.AuthStatus;
import com._32bit.project.cashier_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody AuthRequestDto authRequestDto) {
        var jwtToken = authService.login(authRequestDto.username(), authRequestDto.password());

        var authResponse = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESS);

        return ResponseEntity.status(HttpStatus.OK).body(authResponse);

    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup (@RequestBody AuthRequestDto authRequestDto) {
        try {
            var jwtToken = authService.signup(authRequestDto.username(),authRequestDto.password(),authRequestDto.roles());

            var authResponseDto = new AuthResponseDto(jwtToken,AuthStatus.USER_CREATED_SUCCESSFULLY);

            return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
        } catch (Exception e) {
            var authResponseDto = new AuthResponseDto(null,AuthStatus.USER_NOT_CREATED);

            return ResponseEntity.status(HttpStatus.CONFLICT).body(authResponseDto);
        }


    }


}
