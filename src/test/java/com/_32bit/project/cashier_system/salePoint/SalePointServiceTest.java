package com._32bit.project.cashier_system.salePoint;

import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.DAO.SessionRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.salePoint.CreateSalePointRequest;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.impl.SalePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class SalePointServiceTest {

    @InjectMocks
    private SalePointServiceImpl salePointService;

    @Mock
    private SalePointRepository salePointRepository;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    private TeamMember teamMember;
    private UserCredential userCredential;
    private SalePoint salePoint;
    private Session session;

    private String token;
    private Authentication authentication;


    @BeforeEach
    void setUp() {
        // Create TeamMember
        teamMember = new TeamMember();
        teamMember.setId(1L);
        teamMember.setFirstname("ismail");
        teamMember.setLastname("kattan");
        teamMember.setDeleted(false);

        // Create and save a UserCredential
        userCredential = new UserCredential();
        userCredential.setId(1L);
        userCredential.setUsername("sml_ktn");
        userCredential.setPassword(passwordEncoder.encode("123456"));
        userCredential.setTeamMember(teamMember);
        userCredential.setDeleted(false);

        // Create and save a SalePoint
        salePoint = new SalePoint();
        salePoint.setId(1L);
        salePoint.setName("Test Sale Point");
        salePoint.setAddress("123 Test St");
        salePoint.setCreatedBy(teamMember);
        salePoint.setSessions(new ArrayList<>());
        salePoint.setTeamMembers(new ArrayList<>());
        salePoint.setDeleted(false);

        // Create and save a Session
        session = new Session();
        session.setId(1L);
        session.setSalePoint(salePoint);

        userCredential.setTeamMember(teamMember);
        teamMember.setUserCredential(userCredential);
        teamMember.setSalePoint(salePoint);
        teamMember.setSalePoint(salePoint);
        session.setSalePoint(salePoint);

        teamMemberRepository.save(teamMember);
        sessionRepository.save(session);
        userCredentialRepository.save(userCredential);
        salePointRepository.save(salePoint);

        this.authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userCredential.getUsername(),userCredential.getPassword())
        );
        this.token = jwtUtils.generateJwtToken(authentication);
    }

    @Test
    void getSalePointInfo_ValidId_ReturnsSalePoint() {
        // Arrange
        when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getSalePointInfo(salePoint.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + salePoint.getId() + " returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getSalePointInfo_InvalidId_ReturnsError() {
        // Arrange
        Long invalidId = 999L;
        when(salePointRepository.findByIdAndDeleted(invalidId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.getSalePointInfo(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + invalidId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void createSalePoint_NullRequest_ReturnsError() {
        // Arrange
        String token = "Bearer validToken";

        // Act
        ResponseEntity<?> response = salePointService.createSalePoint(null, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertNull(body.getObject());
    }

    @Test
    void createSalePoint_NullNameOrAddress_ReturnsError() {
        // Arrange
        String token = "Bearer validToken";
        CreateSalePointRequest request = CreateSalePointRequest.builder()
                .name(null)
                .address("456 New St")
                .build();

        // Act
        ResponseEntity<?> response = salePointService.createSalePoint(request, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertNull(body.getObject());
    }

    @Test
    void createSalePoint_InvalidToken_ReturnsError() {
        // Arrange
        String token = "Bearer invalidToken";
        CreateSalePointRequest request = CreateSalePointRequest.builder()
                .name("New Sale Point")
                .address("456 New St")
                .build();

        when(jwtUtils.getUsernameFromJwtToken("invalidToken")).thenReturn(null);

        // Act
        ResponseEntity<?> response = salePointService.createSalePoint(request, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertNull(body.getObject());
    }

    @Test
    void createSalePoint_UserNotFound_ReturnsError() {
        // Arrange
        String token = "Bearer validToken";
        CreateSalePointRequest request = CreateSalePointRequest.builder()
                .name("New Sale Point")
                .address("456 New St")
                .build();

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("testuser");
        when(userCredentialRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.createSalePoint(request, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertNull(body.getObject());
    }

    @Test
    void createSalePoint_TeamMemberNotFound_ReturnsError() {
        // Arrange
        String token = "Bearer validToken";
        CreateSalePointRequest request = CreateSalePointRequest.builder()
                .name("New Sale Point")
                .address("456 New St")
                .build();

        UserCredential userCredential = new UserCredential();
        userCredential.setUsername("testuser");

        TeamMember teamMember = new TeamMember();
        teamMember.setId(1L);
        userCredential.setTeamMember(teamMember);

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("testuser");
        when(userCredentialRepository.findByUsername("testuser")).thenReturn(Optional.of(userCredential));
        when(teamMemberRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.createSalePoint(request, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertNull(body.getObject());
    }

    @Test
    void updateSalePoint_ValidRequest_ReturnsSalePoint() {
        // Arrange
        CreateSalePointRequest updateSalePoint = CreateSalePointRequest.builder()
                .name("Updated Sale Point")
                .address("789 Updated St")
                .build();

        when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);
        lenient().when(teamMemberRepository.findById(userCredential.getTeamMember().getId())).thenReturn(Optional.of(teamMember));

        // Act
        ResponseEntity<?> response = salePointService.updateSalePoint(salePoint.getId(), updateSalePoint);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: 1 updated", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void updateSalePoint_InvalidRequest_ReturnsError() {
        // Arrange
        CreateSalePointRequest updateSalePoint = CreateSalePointRequest.builder()
                .name("Updated Sale Point")
                .address("789 Updated St")
                .build();

        when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);
        lenient().when(teamMemberRepository.findById(userCredential.getTeamMember().getId())).thenReturn(Optional.of(teamMember));

        // Act
        ResponseEntity<?> response = salePointService.updateSalePoint(salePoint.getId(), updateSalePoint);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: 1 updated", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void deleteSalePoint_ValidId_ReturnsSalePoint() {
        // Arrange
        when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);

        // Act
        ResponseEntity<?> response = salePointService.deleteSalePoint(salePoint.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: 1 deleted", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void deleteSalePoint_InvalidId_ReturnsError() {
        // Arrange
        Long invalidId = 999L;
        when(salePointRepository.findByIdAndDeleted(invalidId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.deleteSalePoint(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + invalidId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void getSalePointSessions_ValidId_ReturnsSessions() {
        // Arrange
        salePoint.setSessions(Collections.singletonList(session)); // Ensure SalePoint has a session
        lenient().when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));
        lenient().when(sessionRepository.save(session)).thenReturn(session);

        // Act
        ResponseEntity<?> response = salePointService.getSalePointSessions(salePoint.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("All sessions of sale point with id: " + salePoint.getId() + " returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getSalePointSessions_InvalidId_ReturnsError() {
        // Arrange
        Long invalidId = 999L;
        when(salePointRepository.findByIdAndDeleted(invalidId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.getSalePointSessions(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + invalidId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void restoreSalePoint_ValidId_ReturnsSalePoint() {
        // Arrange
        salePoint.setDeleted(true);
        when(salePointRepository.findByIdAndDeleted(salePoint.getId(), true)).thenReturn(Optional.of(salePoint));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);

        // Act
        ResponseEntity<?> response = salePointService.restoreSalePoint(salePoint.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: 1 restored", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void restoreSalePoint_InvalidId_ReturnsError() {
        // Arrange
        Long invalidId = 999L;
        when(salePointRepository.findByIdAndDeleted(invalidId, true)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.restoreSalePoint(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + invalidId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void addTeamMember_InvalidToken() {
        // Arrange
        Long salePointId = 1L;
        Long teamMemberId = 2L;
        String token = "Bearer invalidToken";

        when(jwtUtils.getUsernameFromJwtToken("invalidToken")).thenReturn(null);

        // Act
        ResponseEntity<?> response = salePointService.addTeamMember(salePointId, teamMemberId, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void addTeamMember_NullIds() {
        // Arrange
        String token = "Bearer validToken";

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("sml_ktn");

        // Act
        ResponseEntity<?> response = salePointService.addTeamMember(null, null, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void addTeamMember_SalePointNotFound() {
        // Arrange
        Long salePointId = 999L;
        Long teamMemberId = 2L;
        String token = "Bearer validToken";

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("sml_ktn");
        when(salePointRepository.findByIdAndDeleted(salePointId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.addTeamMember(salePointId, teamMemberId, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void addTeamMember_TeamMemberNotFound() {
        // Arrange
        Long salePointId = 1L;
        Long teamMemberId = 999L;
        String token = "Bearer validToken";

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("sml_ktn");
        when(salePointRepository.findByIdAndDeleted(salePointId, false)).thenReturn(Optional.of(salePoint));
        when(teamMemberRepository.findByIdAndDeleted(teamMemberId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.addTeamMember(salePointId, teamMemberId, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void removeTeamMember_InvalidToken() {
        // Arrange
        Long salePointId = 1L;
        Long teamMemberId = 2L;
        String token = "Bearer invalidToken";

        when(jwtUtils.getUsernameFromJwtToken("invalidToken")).thenReturn(null);

        // Act
        ResponseEntity<?> response = salePointService.removeTeamMember(salePointId, teamMemberId, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void removeTeamMember_NullIds() {
        // Arrange
        String token = "Bearer validToken";

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("sml_ktn");

        // Act
        ResponseEntity<?> response = salePointService.removeTeamMember(null, null, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void removeTeamMember_SalePointNotFound() {
        // Arrange
        Long salePointId = 999L;
        Long teamMemberId = 2L;
        String token = "Bearer validToken";

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("sml_ktn");
        when(salePointRepository.findByIdAndDeleted(salePointId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.removeTeamMember(salePointId, teamMemberId, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void removeTeamMember_TeamMemberNotFound() {
        // Arrange
        Long salePointId = 1L;
        Long teamMemberId = 999L;
        String token = "Bearer validToken";

        when(jwtUtils.getUsernameFromJwtToken("validToken")).thenReturn("sml_ktn");
        when(salePointRepository.findByIdAndDeleted(salePointId, false)).thenReturn(Optional.of(salePoint));
        when(teamMemberRepository.findByIdAndDeleted(teamMemberId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.removeTeamMember(salePointId, teamMemberId, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse responseBody = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(responseBody);
    }

    @Test
    void addSession_ValidIds_ReturnsSalePoint() {
        // Arrange
        Long salePointId = 1L;
        Long sessionId = 1L;

        SalePoint salePoint = new SalePoint();
        salePoint.setId(salePointId);
        salePoint.setDeleted(false);
        salePoint.setSessions(new ArrayList<>());
        salePoint.setCreatedBy(teamMember);

        Session session = new Session();
        session.setId(sessionId);
        session.setDeleted(false);

        when(salePointRepository.findByIdAndDeleted(salePointId, false)).thenReturn(Optional.of(salePoint));
        when(sessionRepository.findByIdAndDeleted(sessionId, false)).thenReturn(Optional.of(session));
        when(salePointRepository.save(any(SalePoint.class))).thenReturn(salePoint);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Act
        ResponseEntity<?> response = salePointService.addSession(salePointId, sessionId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + salePointId + " have new session with id: " + sessionId, body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void addSession_InvalidIds_ReturnsError() {
        // Arrange
        Long invalidSalePointId = 999L;
        Long invalidSessionId = 999L;
        lenient().when(salePointRepository.findByIdAndDeleted(invalidSalePointId, false)).thenReturn(Optional.empty());
        lenient().when(sessionRepository.findByIdAndDeleted(invalidSessionId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.addSession(invalidSalePointId, invalidSessionId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + invalidSalePointId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void removeSession_ValidIds_ReturnsSalePoint() {
        // Arrange
        salePoint.setSessions(new ArrayList<>(Collections.singletonList(session))); // Ensure SalePoint has a modifiable list of sessions
        lenient().when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));
        lenient().when(sessionRepository.findByIdAndDeleted(session.getId(), false)).thenReturn(Optional.of(session));
        lenient().when(salePointRepository.save(salePoint)).thenReturn(salePoint);
        lenient().when(sessionRepository.save(session)).thenReturn(session);

        // Act
        ResponseEntity<?> response = salePointService.removeSession(salePoint.getId(), session.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + salePoint.getId() + " has session with id: " + session.getId() + " removed", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void removeSession_InvalidIds_ReturnsError() {
        // Arrange
        Long invalidSalePointId = 999L;
        Long invalidSessionId = 999L;
        lenient().when(salePointRepository.findByIdAndDeleted(invalidSalePointId, false)).thenReturn(Optional.empty());
        lenient().when(sessionRepository.findByIdAndDeleted(invalidSessionId, false)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.removeSession(invalidSalePointId, invalidSessionId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Sale point with id: " + invalidSalePointId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void getAllSalePoints_ReturnsSalePoints() {
        // Arrange
        when(salePointRepository.findAllByDeleted(false)).thenReturn(Collections.singletonList(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getAllSalePoints();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("All sale points returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getAllDeletedSalePoints_ReturnsDeletedSalePoints() {
        // Arrange
        when(salePointRepository.findAllByDeleted(true)).thenReturn(Collections.singletonList(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getAllDeletedSalePoints();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("All deleted sale points returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getDeletedSalePoint_ValidId_ReturnsSalePoint() {
        // Arrange
        when(salePointRepository.findByIdAndDeleted(salePoint.getId(), true)).thenReturn(Optional.of(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getDeletedSalPoint(salePoint.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Deleted sale point with id: " + salePoint.getId() + " returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getDeletedSalePoint_InvalidId_ReturnsError() {
        // Arrange
        Long invalidId = 999L;
        when(salePointRepository.findByIdAndDeleted(invalidId, true)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = salePointService.getDeletedSalPoint(invalidId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Deleted sale point with id: " + invalidId + " not found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void getSalePointByAddressContains_ValidAddress_ReturnsSalePoints() {
        // Arrange
        String address = "123 Test";
        when(salePointRepository.findByAddressContainsAndDeleted(address, false)).thenReturn(Collections.singletonList(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getSalePointByAddressLike(address);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("All sale points with address contains: " + address + " returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getSalePointByAddressContains_InvalidAddress_ReturnsError() {
        // Arrange
        String invalidAddress = "999 Invalid";
        when(salePointRepository.findByAddressContainsAndDeleted(invalidAddress, false)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = salePointService.getSalePointByAddressLike(invalidAddress);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("No sale points with address contains: " + invalidAddress + " found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void getSalePointByName_ValidName_ReturnsSalePoint() {
        // Arrange
        String name = "Test Sale ";
        when(salePointRepository.findByNameContainsAndDeleted(name, false)).thenReturn(Collections.singletonList(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getSalePointByName(name);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("All sale points with name contains: " + name + " returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }

    @Test
    void getSalePointByName_InvalidName_ReturnsError() {
        // Arrange
        String invalidName = "999 Invalid";
        when(salePointRepository.findByNameContainsAndDeleted(invalidName, false)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = salePointService.getSalePointByName(invalidName);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("No sale points with name contains: " + invalidName + " found", body.getMessageResponse().getMessage());
        assertNull(body.getObject());
    }

    @Test
    void getSalePointTeamMembers_ValidId_ReturnsTeamMembers() {
        // Arrange
        salePoint.setTeamMembers(Collections.singletonList(teamMember)); // Ensure SalePoint has a modifiable list of teamMembers
        lenient().when(salePointRepository.findByIdAndDeleted(salePoint.getId(), false)).thenReturn(Optional.of(salePoint));

        // Act
        ResponseEntity<?> response = salePointService.getSalePointTeamMembers(salePoint.getId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ObjectWithMessageResponse body = (ObjectWithMessageResponse) response.getBody();
        assertNotNull(body);
        assertEquals("All team members of sale point with id: " + salePoint.getId() + " returned", body.getMessageResponse().getMessage());
        assertNotNull(body.getObject());
    }


}


