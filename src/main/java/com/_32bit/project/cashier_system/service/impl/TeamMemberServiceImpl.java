package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.RoleRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.domains.Role;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.service.abstracted.TeamMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final RoleRepository roleRepository;
    @Override
    public TeamMember saveMember(TeamMember teamMember) {
        return teamMemberRepository.save(teamMember);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToMember(String key/* username or email or phone number*/, String roleName) {
        Optional<TeamMember> member = teamMemberRepository.findByUsername(key);
    }

    @Override
    public TeamMember getMember(String username) {
        return null;
    }

    @Override
    public List<TeamMember> getMembers() {
        return null;
    }

    @Override
    public Page<TeamMember> getMembers(int page, int size, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredByRole(String role) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredBySalePoint(String salePointName) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredByDeleted(boolean deleted) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredById(long id) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredByEmail(String email) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredByInsertionDateBefore(Date date) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredBtInsertionDateAfter(Date date) {
        return null;
    }

    @Override
    public List<TeamMember> getFilteredByInsertionDateBetween(Date start, Date end) {
        return null;
    }
}
