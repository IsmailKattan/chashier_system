package com._32bit.project.cashier_system.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.DAO.SessionRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.impl.SessionServiceImpl;
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

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SalePointRepository salePointRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private SessionServiceImpl sessionService;

    private Session session;
    private Sale sale;

    private Sale sale1;
    private SalePoint salePoint;
    private TeamMember teamMember;

    @BeforeEach
    void setUp() {
        salePoint = new SalePoint(); // Initialize salePoint first
        salePoint.setId(1L);
        salePoint.setDeleted(false);

        session = new Session();
        session.setId(1L);
        session.setDeleted(false);
        session.setClosed(true);
        session.setSalePoint(salePoint); // Set salePoint to session

        sale = new Sale();
        sale.setId(1L);
        sale.setDeleted(false);
        sale.setTotal(1000.0);

        sale1 = new Sale();
        sale1.setId(2L);
        sale1.setDeleted(false);
        sale1.setTotal(2000.0);

        List<Sale> sales = new ArrayList<>();
        sales.add(sale1);
        session.setSales(sales);

        salePoint.setSessions(List.of(session)); // Add session to salePoint's sessions

        teamMember = new TeamMember();
        teamMember.setId(1L);
        teamMember.setUsername("testUser");
        teamMember.setDeleted(false);

        session.setOpenedBy(teamMember);
        session.setClosedBy(teamMember);

    }

    @Test
    void testGetSessionInfo() {
        when(sessionRepository.findByIdAndDeleted(anyLong(), eq(false))).thenReturn(Optional.of(session));

        ResponseEntity<?> response = sessionService.getSessionInfo(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetSessionInfo_NotFound() {
        when(sessionRepository.findByIdAndDeleted(anyLong(), eq(false))).thenReturn(Optional.empty());

        ResponseEntity<?> response = sessionService.getSessionInfo(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSession() {
        when(sessionRepository.findByIdAndDeleted(anyLong(), eq(false))).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        ResponseEntity<?> response = sessionService.deleteSession(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteSession_NotFound() {
        when(sessionRepository.findByIdAndDeleted(anyLong(), eq(false))).thenReturn(Optional.empty());

        ResponseEntity<?> response = sessionService.deleteSession(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRestoreSession() {
        session.setDeleted(true);
        when(sessionRepository.findByIdAndDeleted(anyLong(), eq(true))).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        ResponseEntity<?> response = sessionService.restoreSession(1L);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testRestoreSession_NotFound() {
        when(sessionRepository.findByIdAndDeleted(anyLong(), eq(true))).thenReturn(Optional.empty());

        ResponseEntity<?> response = sessionService.restoreSession(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllSessions() {
        when(sessionRepository.findAllByDeleted(eq(false))).thenReturn(List.of(session));

        ResponseEntity<?> response = sessionService.getAllSessions();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetAllDeletedSessions() {
        session.setDeleted(true);
        when(sessionRepository.findAllByDeleted(eq(true))).thenReturn(List.of(session));

        ResponseEntity<?> response = sessionService.getAllDeletedSessions();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testOpenSession_ValidRequest() {
        when(jwtUtils.getUsernameFromJwtToken(anyString())).thenReturn("testUser");
        when(teamMemberRepository.findByUsernameAndDeleted(eq("testUser"), eq(false))).thenReturn(Optional.of(teamMember));
        when(salePointRepository.findByIdAndDeleted(anyLong(), eq(false))).thenReturn(Optional.of(salePoint));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        ResponseEntity<?> response = sessionService.openSession(salePoint.getId(), "Bearer token");

        assertEquals(200, response.getStatusCodeValue());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals("Session opened successfully", responseBody.getMessageResponse().getMessage());
    }

    @Test
    void testGetSalePointSessions_ValidRequest() {
        when(salePointRepository.findByIdAndDeleted(anyLong(), eq(false))).thenReturn(Optional.of(salePoint));

        ResponseEntity<?> response = sessionService.getSalePointSessions(1L);

        assertEquals(200, response.getStatusCodeValue());
    }
}
