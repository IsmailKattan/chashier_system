package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.teamMember.request.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.request.UpdatePasswordDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TeamMemberService {

    ResponseEntity<?> registerMember(CreateTeamMemberDto createTeamMemberDto);

    ResponseEntity<?> getMemberByIdAndDeleted(Long id,Boolean deleted);

    ResponseEntity<?> getMemberByUsernameAndDeleted(String username,Boolean deleted);

    ResponseEntity<?> getAllMembersByDeleted(Boolean deleted);

    ResponseEntity<?> updateMember(Long id, CreateTeamMemberDto createTeamMemberDto);

    ResponseEntity<?> deleteMember(Long id);

    ResponseEntity<?> restoreMember(Long id);

    ResponseEntity<?> updateAuthority(Long id, List<String> authority);

    ResponseEntity<?> updatePassword(Long id, UpdatePasswordDto password);

    ResponseEntity<?> updateSalePoint(Long id, Long salePointId);

}
