package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.TeamMemberDTO;
import com._32bit.project.cashier_system.DTO.teamMember.CreateTeamMemberDto;
import com._32bit.project.cashier_system.DTO.teamMember.TeamMemberInfoDto;
import com._32bit.project.cashier_system.DTO.teamMember.UserCredentialInfoDto;
import com._32bit.project.cashier_system.service.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team-member")
@RequiredArgsConstructor
public class TeamMemberResource {

    @Autowired
    private final TeamMemberService teamMemberService;

    @GetMapping("/admin/get-member/{username}")
    public ResponseEntity<TeamMemberInfoDto> getTeamMemberInfoDto(@PathVariable String username) {
        TeamMemberInfoDto teamMemberDTO =  teamMemberService.getTeamMemberInfo(username);
        return ResponseEntity.ok(teamMemberDTO);
    }

    @GetMapping("/admin/get-member/by-firstname-or-lastname/{name}")
    public ResponseEntity<List<TeamMemberInfoDto>> getTeamMemberInfDtoByName(@PathVariable String name) {
        List<TeamMemberInfoDto> teamMemberDto = teamMemberService.getTeamMemberInfoByName(name);
        return ResponseEntity.ok(teamMemberDto);
    }

    @GetMapping("/admin/get-member-credential/{username}")
    public ResponseEntity<UserCredentialInfoDto> getMemberCredential(@PathVariable String username) {
        UserCredentialInfoDto userCredentialInfoDto = teamMemberService.getMemberCredential(username);
        return ResponseEntity.ok(userCredentialInfoDto);
    }

    @GetMapping("/admin/delete-member/{username}")
    public ResponseEntity<String> deleteMember(@PathVariable String username) {
        return ResponseEntity.ok(teamMemberService.deleteMember(username));
    }

    @GetMapping("/admin/undelete-member/{username}")
    public ResponseEntity<String> UnDeleteMember(@PathVariable String username) {
        return ResponseEntity.ok(teamMemberService.UnDeleteMember(username));
    }

    @PostMapping("/admin/create-member")
    public ResponseEntity<TeamMemberInfoDto> createTeamMemberDto(@RequestBody CreateTeamMemberDto createTeamMemberDto) {
        TeamMemberInfoDto teamMemberInfoDto = teamMemberService.createTeamMember(createTeamMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberInfoDto);
    }

    @PostMapping("/admin/update-member/{username}")
    public ResponseEntity<TeamMemberInfoDto> updateTeamMemberDto(@PathVariable String username, @RequestBody CreateTeamMemberDto createTeamMemberDto) {
        try {
            TeamMemberInfoDto teamMemberInfoDto = teamMemberService.updateResource(username, createTeamMemberDto);
            return ResponseEntity.ok(teamMemberInfoDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

//        try {
//            Resource updatedResource = resourceService.updateResource(id, resourceDTO);
//            return ResponseEntity.ok(updatedResource);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
    }



}
