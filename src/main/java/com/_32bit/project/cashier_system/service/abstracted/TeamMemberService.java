package com._32bit.project.cashier_system.service.abstracted;

import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.TeamMember;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface TeamMemberService {
    TeamMember saveMember(TeamMember teamMember);
    Role saveRole(Role role);
    void addRoleToMember(String username, String roleName);
    TeamMember getMember(String username);
    List<TeamMember> getMembers();
    Page<TeamMember> getMembers(int page,int size,String sortBy,String sortDir);
    List<TeamMember> getFilteredByRole(String role);
    List<TeamMember> getFilteredBySalePoint(String salePointName);
    List<TeamMember> getFilteredByDeleted(boolean deleted);
    List<TeamMember> getFilteredById(long id);
    List<TeamMember> getFilteredByPhoneNumber(String phoneNumber);
    List<TeamMember> getFilteredByEmail(String email);
    List<TeamMember> getFilteredByInsertionDateBefore(Date date);
    List<TeamMember> getFilteredBtInsertionDateAfter(Date date);
    List<TeamMember> getFilteredByInsertionDateBetween(Date start,Date end);
}
