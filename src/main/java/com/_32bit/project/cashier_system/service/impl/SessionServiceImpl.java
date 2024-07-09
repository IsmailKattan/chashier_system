package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.*;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.session.CashInfoDto;
import com._32bit.project.cashier_system.DTO.session.SessionInfoResponse;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.mapper.SessionMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final static Logger logger = LogManager.getLogger(SessionServiceImpl.class);

    private final SessionRepository sessionRepository;

    private final SalePointRepository salePointRepository;

    private final SaleRepository saleRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final JwtUtils jwtUtil;


    @Override
    public ResponseEntity<?> getSessionInfo(Long sessionId) {
        if (sessionId == null) {
            logger.error("Session: Session id is null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session id is null"),
                            null
                    )
            );
        }
        if (sessionRepository.findByIdAndDeleted(sessionId, false).isEmpty()) {
            logger.error("Session: Session with id: " + sessionId + " not found");
            return ResponseEntity.notFound().build();
        }

        var session = sessionRepository.findByIdAndDeleted(sessionId, false).get();
        SessionInfoResponse sessionInfoResponse = SessionMapper.toSessionInfoResponseDTO(session);
        logger.info("Session: Session info returned successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Session info"),
                        sessionInfoResponse
                )
        );
    }

    @Override
    public ResponseEntity<?> deleteSession(Long sessionId) {
        if (sessionId == null) {
            logger.error("Session: Session id is null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session id is null"),
                            null
                    )
            );
        }
        if (sessionRepository.findByIdAndDeleted(sessionId, false).isEmpty()) {
            logger.error("Session: Session with id: " + sessionId + " not found");
            return ResponseEntity.notFound().build();
        }
        if (sessionRepository.findById(sessionId).isPresent()) {
            var session = sessionRepository.findById(sessionId).get();
            if (session.getDeleted()) {
                logger.error("Session: Session with id: " + sessionId + " already deleted");
                return ResponseEntity.status(409).body(
                        new ObjectWithMessageResponse(
                                new MessageResponse("Session with id: " + sessionId + " already deleted"),
                                null
                        )
                );
            }
        }
        var session = sessionRepository.findByIdAndDeleted(sessionId,false).get();
        if (!session.getClosed()) {
            logger.error("Session: Session with id: " + sessionId + " can't be deleted without closing");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session with id: " + sessionId + " can't be deleted without closing"),
                            null
                    )
            );
        }
        session.setDeleted(true);
        sessionRepository.save(session);
        SessionInfoResponse sessionInfoResponse = SessionMapper.toSessionInfoResponseDTO(session);
        logger.info("Session: Session deleted successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Session deleted successfully"),
                        sessionInfoResponse
                )
        );
    }

    @Override
    public ResponseEntity<?> restoreSession(Long sessionId) {
        if (sessionId == null) {
            logger.error("Session: Session id is null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session id is null"),
                            null
                    )
            );
        }
        if (sessionRepository.findByIdAndDeleted(sessionId, true).isEmpty()) {
            logger.error("Session: Session with id: " + sessionId + " not found");
            return ResponseEntity.notFound().build();
        }

        var session = sessionRepository.findByIdAndDeleted(sessionId, true).get();
        if (!session.getDeleted()) {
            logger.error("Session: Session with id: " + sessionId + " already restored");
            return ResponseEntity.status(409).body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session with id: " + sessionId + " already restored"),
                            null
                    )
            );
        }

        session.setDeleted(false);
        sessionRepository.save(session);
        SessionInfoResponse sessionInfoResponse = SessionMapper.toSessionInfoResponseDTO(session);
        logger.info("Session: Session restored successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Session restored successfully"),
                        sessionInfoResponse
                )
        );
    }

    @Override
    public ResponseEntity<?> getAllSessions() {
        if (sessionRepository.findAllByDeleted(false).isEmpty()) {
            logger.error("Session: No sessions found");
            return ResponseEntity.notFound().build();
        }
        var sessions = SessionMapper.toSessionInfoResponseDTOList(sessionRepository.findAll());
        logger.info("Session: All sessions returned successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("All sessions"),
                        sessions
                )
        );
    }

    @Override
    public ResponseEntity<?> getAllDeletedSessions() {

        if (sessionRepository.findAllByDeleted(true).isEmpty()) {
            logger.error("Session: No deleted sessions found");
            return ResponseEntity.notFound().build();
        }
        var sessions = SessionMapper.toSessionInfoResponseDTOList(sessionRepository.findAllByDeleted(true));
        logger.info("Session: All deleted sessions returned successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("All deleted sessions"),
                        sessions
                )
        );

    }

    @Override
    public ResponseEntity<?> openSession(CashInfoDto openSessionRequest, String token) {
        String username = jwtUtil.getUsernameFromJwtToken(token.substring(7));
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByUsernameAndDeleted(username, false);
        Optional<SalePoint> salePointOptional = salePointRepository.findByIdAndDeleted(openSessionRequest.getSalePointId(), false);

        // Check if user exists
        if (teamMemberOptional.isEmpty()) {
            logger.error("Session: User with username: " + username + " not found");
            return ResponseEntity.notFound().build();
        }

        // Check if sale point exists
        if (salePointOptional.isEmpty()) {
            logger.error("Session: Sale point with id: " + openSessionRequest.getSalePointId() + " not found");
            return ResponseEntity.notFound().build();
        }

        SalePoint salePoint = salePointOptional.get();
        TeamMember teamMember = teamMemberOptional.get();

        if (salePoint != teamMember.getSalePoint() && teamMember.getSalePoint() != null) {
            logger.error("Session: User with username: " + username + " can't open session in sale point with id: " + openSessionRequest.getSalePointId());
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("User with username: " + username + " can't open session in sale point with id: " + openSessionRequest.getSalePointId()),
                            null
                    )
            );
        }

        if (haveOpenSession(salePoint)) {
            logger.error("Session: Sale point with id: " + openSessionRequest.getSalePointId() + " already have open session");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + openSessionRequest.getSalePointId() + " already have open session"),
                            null
                    )
            );
        }
        // Create a new session
        Session session = SessionMapper.toDomain(salePoint);
        logger.info("Session: Session created successfully");

        session.setOpeningDate(LocalDate.now());
        session.setOpeningTime(LocalTime.now());
        session.setOpenedBy(teamMember);
        session.setClosed(false);
        session.setOpeningCash(openSessionRequest.getCashInfo());
        session.setSalePoint(salePoint);

        session.setClosedBy(null);  // Ensure closedBy is null when opening a session
        sessionRepository.save(session);
        logger.info("Session: Session opened successfully");

        SessionInfoResponse sessionInfoResponse = SessionMapper.toSessionInfoResponseDTO(session);
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Session opened successfully"),
                        sessionInfoResponse
                )
        );
    }

    private boolean haveOpenSession(SalePoint salePoint) {
        List<Session> sessionList = salePoint.getSessions();
        if (sessionList.isEmpty()) {
            return false;
        }
        for (Session session : sessionList) {
            if (!session.getClosed()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public ResponseEntity<?> closeSession(CashInfoDto closeSessionRequest,String token) {
        String username = jwtUtil.getUsernameFromJwtToken(token.substring(7));
        Optional<TeamMember> teamMemberOptional= teamMemberRepository.findByUsernameAndDeleted(username,false);
        Optional<SalePoint> salePointOptional = salePointRepository.findByIdAndDeleted(closeSessionRequest.getSalePointId(), false);
        // check if user exists
        if (teamMemberOptional.isEmpty()) {
            logger.error("Session: User with username: " + username + " not found");
            return ResponseEntity.notFound().build();
        }
        // check if sale point exists
        if (salePointOptional.isEmpty()) {
            logger.error("Session: Sale point with id: " + closeSessionRequest.getSalePointId() + " not found");
            return ResponseEntity.notFound().build();
        }
        // check if sale point have open session
        SalePoint salePoint = salePointOptional.get();
        List<Session> sessionList = salePoint.getSessions();
        TeamMember teamMember = teamMemberOptional.get();

        if (salePoint != teamMember.getSalePoint() && teamMember.getSalePoint() != null) {
            logger.error("Session: User with username: " + username + " can't close session in sale point with id: " + closeSessionRequest.getSalePointId());
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("User with username: " + username + " can't close session in sale point with id: " + closeSessionRequest.getSalePointId()),
                            null
                    )
            );
        }

        if (!haveOpenSession(salePoint)) {
            logger.error("Session: Sale point with id: " + closeSessionRequest.getSalePointId() + " have no open session");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + closeSessionRequest.getSalePointId() + " have no open session"),
                            null
                    )
            );
        }

        Session session = sessionList.stream().filter(s -> !s.getClosed()).findFirst().get();
        logger.info("Session: Session found successfully");
        session.setClosingDate(LocalDate.now());
        session.setClosingTime(LocalTime.now());
        session.setClosedBy(teamMember);
        session.setClosingCash(closeSessionRequest.getCashInfo());
        session.setClosingCash(closeSessionRequest.getCashInfo());
        session.setOpeningClosingBalance(closeSessionRequest.getCashInfo() - session.getOpeningCash());
        session.setExceptedRealCashBalance(session.getOpeningCash() + session.getSalesCash() - closeSessionRequest.getCashInfo());
        session.setClosed(true);
        sessionRepository.save(session);
        logger.info("Session: Session closed successfully");
        SessionInfoResponse sessionInfoResponse = SessionMapper.toSessionInfoResponseDTO(session);
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Session closed successfully"),
                        sessionInfoResponse
                )
        );


    }

    @Override
    public ResponseEntity<?> getSalePointSessions(Long id) {

        if (id == null) {
            logger.error("Session: Sale point id is null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id is null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(id, false).isEmpty()) {
            logger.error("Session: Sale point with id: " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        if (salePointRepository.findByIdAndDeleted(id, false).get().getSessions().isEmpty()) {
            logger.error("Session: Sale point with id: " + id + " has no sessions");
            return ResponseEntity.notFound().build();
        }

        var salePoint = salePointRepository.findByIdAndDeleted(id, false).get();
        var sessions = SessionMapper.toSessionInfoResponseDTOList(salePoint.getSessions());
        logger.info("Session: Sale point sessions returned successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale point sessions"),
                        sessions
                )
        );

    }

    @Override
    public ResponseEntity<?> getSalePointOpenSession(Long id) {
        if (id == null) {
            logger.error("Session: Sale point id is null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id is null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(id, false).isEmpty()) {
            logger.error("Session: Sale point with id: " + id + " not found");
            return ResponseEntity.notFound().build();
        }

        if (salePointRepository.findByIdAndDeleted(id, false).get().getSessions().isEmpty()) {
            logger.error("Session: Sale point with id: " + id + " has no sessions");
            return ResponseEntity.notFound().build();
        }

        if (!haveOpenSession(salePointRepository.findByIdAndDeleted(id, false).get())) {
            logger.error("Session: Sale point with id: " + id + " have no open session");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + id + " have no open session"),
                            null
                    )
            );
        }

        var salePoint = salePointRepository.findByIdAndDeleted(id, false).get();
        var session = salePoint.getSessions().stream().filter(s -> !s.getClosed()).findFirst().get();
        logger.info("Session: Sale point open session returned successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Sale point open session"),
                        SessionMapper.toSessionInfoResponseDTO(session)
                )
        );
    }

    @Override
    public Session getOpenSessionOfSalePoint(Long id) {
        if (id == null) {
            logger.error("Session: Sale point id is null");
            return null;
        }
        if (salePointRepository.findByIdAndDeleted(id, false).isEmpty()) {
            logger.error("Session: Sale point with id: " + id + " not found");
            return null;
        }

        if (salePointRepository.findByIdAndDeleted(id, false).get().getSessions().isEmpty()) {
            logger.error("Session: Sale point with id: " + id + " has no sessions");
            return null;
        }

        if (!haveOpenSession(salePointRepository.findByIdAndDeleted(id, false).get())) {
            logger.error("Session: Sale point with id: " + id + " have no open session");
            return null;
        }

        var salePoint = salePointRepository.findByIdAndDeleted(id, false).get();
        return salePoint.getSessions().stream().filter(s -> !s.getClosed()).findFirst().get();
    }
}
