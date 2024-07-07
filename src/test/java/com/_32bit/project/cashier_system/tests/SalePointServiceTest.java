package com._32bit.project.cashier_system.tests;

import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.salePoint.CreateSalePointRequest;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.impl.SalePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalePointServiceTest {
    @Mock
    private SalePointRepository salePointRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private SalePointServiceImpl salePointService;

    private SalePoint salePoint;
    private TeamMember teamMember;

    private List<Session> sessions;
    private List<TeamMember> teamMembers;
    private TeamMember teamMember1;
    private String token;
    private String username;

    @BeforeEach
    void setUp() {

        sessions = new ArrayList<>();
        teamMembers = new ArrayList<>();
        sessions.add(new Session());
        salePoint = new SalePoint();
        salePoint.setId(1L);
        salePoint.setName("Test SalePoint");
        salePoint.setAddress("Test Address");
        salePoint.setSessions(sessions);

        teamMember = new TeamMember();
        teamMember.setId(1L);
        teamMember.setUsername("testUser");

        teamMember1 = new TeamMember();
        teamMember1.setId(2L);
        teamMember1.setUsername("testUser1");

        salePoint.setCreatedBy(teamMember);
        teamMembers.add(teamMember1);  // Adding teamMember1 instead of teamMember
        salePoint.setTeamMembers(teamMembers);

        token = "Bearer testToken";
        username = "testUser";
    }


    @Test
    void testGetSalePointInfo_ValidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(salePoint));
        ResponseEntity<?> response = salePointService.getSalePointInfo(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testGetSalePointInfo_InvalidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.empty());

        ResponseEntity<?> response = salePointService.getSalePointInfo(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateSalePoint_ValidRequest() {
        CreateSalePointRequest request = new CreateSalePointRequest();
        request.setName("Test SalePoint");
        request.setAddress("Test Address");

        when(jwtUtils.getUsernameFromJwtToken(anyString())).thenReturn(username);
        when(teamMemberRepository.findByUsername(username)).thenReturn(Optional.of(teamMember));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);

        ResponseEntity<?> response = salePointService.createSalePoint(request, token);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testCreateSalePoint_InvalidRequest() {
        CreateSalePointRequest request = new CreateSalePointRequest();

        ResponseEntity<?> response = salePointService.createSalePoint(request, token);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testUpdateSalePoint_ValidRequest() {
        CreateSalePointRequest request = new CreateSalePointRequest();
        request.setName("Updated SalePoint");
        request.setAddress("Updated Address");

        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);

        ResponseEntity<?> response = salePointService.updateSalePoint(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testUpdateSalePoint_InvalidId() {
        CreateSalePointRequest request = new CreateSalePointRequest();
        request.setName("Updated SalePoint");
        request.setAddress("Updated Address");

        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.empty());

        ResponseEntity<?> response = salePointService.updateSalePoint(1L, request);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSalePoint_ValidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);

        ResponseEntity<?> response = salePointService.deleteSalePoint(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testDeleteSalePoint_InvalidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.empty());

        ResponseEntity<?> response = salePointService.deleteSalePoint(1L);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testRestoreSalePoint_ValidId() {
        salePoint.setDeleted(true);

        when(salePointRepository.findByIdAndDeleted(1L, true)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);

        ResponseEntity<?> response = salePointService.restoreSalePoint(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testRestoreSalePoint_InvalidId() {
        when(salePointRepository.findByIdAndDeleted(1L, true)).thenReturn(Optional.empty());

        ResponseEntity<?> response = salePointService.restoreSalePoint(1L);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetAllSalePoints() {
        when(salePointRepository.findAllByDeleted(false)).thenReturn(List.of(salePoint));

        ResponseEntity<?> response = salePointService.getAllSalePoints();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllDeletedSalePoints() {
        salePoint.setDeleted(true);
        when(salePointRepository.findAllByDeleted(true)).thenReturn(List.of(salePoint));

        ResponseEntity<?> response = salePointService.getAllDeletedSalePoints();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetDeletedSalePoint_ValidId() {
        when(salePointRepository.findByIdAndDeleted(1L, true)).thenReturn(Optional.of(salePoint));

        ResponseEntity<?> response = salePointService.getDeletedSalPoint(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetDeletedSalePoint_InvalidId() {
        when(salePointRepository.findByIdAndDeleted(1L, true)).thenReturn(Optional.empty());

        ResponseEntity<?> response = salePointService.getDeletedSalPoint(1L);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetSalePointByAddressLike() {
        when(salePointRepository.findAllByAddressContainsAndDeleted("Test", false)).thenReturn(List.of(salePoint));

        ResponseEntity<?> response = salePointService.getSalePointByAddressLike("Test");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSalePointByName() {
        when(salePointRepository.findAllByNameContainsAndDeleted("Test", false)).thenReturn(List.of(salePoint));

        ResponseEntity<?> response = salePointService.getSalePointByName("Test");

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSalePointSessions_ValidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(salePoint));

        ResponseEntity<?> response = salePointService.getSalePointSessions(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSalePointSessions_InvalidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.empty());

        ResponseEntity<?> response = salePointService.getSalePointSessions(1L);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetSalePointTeamMembers_ValidId() {
        when(salePointRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(salePoint));

        ResponseEntity<?> response = salePointService.getSalePointTeamMembers(1L);

        assertEquals(200, response.getStatusCodeValue());
    }
}
