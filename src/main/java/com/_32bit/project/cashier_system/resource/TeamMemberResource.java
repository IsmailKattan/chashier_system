package com._32bit.project.cashier_system.resource;

import com._32bit.project.cashier_system.DTO.TeamMemberDTO;
import com._32bit.project.cashier_system.service.abstracted.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class TeamMemberResource {

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/save")
    public ResponseEntity<TeamMemberDTO> saveTeamMember(@RequestBody TeamMemberDTO teamMemberDTO) {
        teamMemberService.saveMember(teamMemberDTO);
    }

}
