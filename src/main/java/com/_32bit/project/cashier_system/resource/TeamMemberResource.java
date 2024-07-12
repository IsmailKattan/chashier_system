package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.UpdatePasswordDto;
import com._32bit.project.cashier_system.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team-member")
public class TeamMemberResource {

    private final TeamMemberService teamMemberService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerMember(@RequestBody CreateTeamMemberDto createTeamMemberDto) {
        return teamMemberService.registerMember(createTeamMemberDto);
    }

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMemberByIdAndDeleted(@PathVariable Long id) {
        return teamMemberService.getMemberByIdAndDeleted(id, false);
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMemberByUsernameAndDeleted(@PathVariable String username) {
        return teamMemberService.getMemberByUsernameAndDeleted(username, false);
    }

    @GetMapping("/deleted/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getDeletedMemberById(@PathVariable Long id) {
        return teamMemberService.getMemberByIdAndDeleted(id, true);
    }

    @GetMapping("/deleted/username/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getDeletedMemberByUsername(@PathVariable String username) {
        return teamMemberService.getMemberByUsernameAndDeleted(username, true);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllMembersByDeleted() {
        return teamMemberService.getAllMembersByDeleted(false);
    }

    @GetMapping("/all-deleted")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllDeletedMembers() {
        return teamMemberService.getAllMembersByDeleted(true);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody CreateTeamMemberDto createTeamMemberDto) {
        return teamMemberService.updateMember(id, createTeamMemberDto);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        return teamMemberService.deleteMember(id);
    }

    @PutMapping("/restore/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> restoreMember(@PathVariable Long id) {
        return teamMemberService.restoreMember(id);
    }

    @PutMapping("/update-authority/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateAuthority(@PathVariable Long id, @RequestBody List<String> authority) {
        return teamMemberService.updateAuthority(id, authority);
    }

    @PutMapping("/update-password/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDto password) {
        return teamMemberService.updatePassword(id, password);
    }

    @PutMapping("/update-sale-point/{id}/{salePointId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateSalePoint(@PathVariable Long id, @PathVariable Long salePointId) {
        return teamMemberService.updateSalePoint(id, salePointId);
    }

}
