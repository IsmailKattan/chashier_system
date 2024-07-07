package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.session.CashInfoDto;
import org.springframework.http.ResponseEntity;

public interface SessionService {

    ResponseEntity<?> getSessionInfo(Long sessionId);

    ResponseEntity<?> deleteSession(Long sessionId);

    ResponseEntity<?> restoreSession(Long sessionId);

    ResponseEntity<?> getAllSessions();

    ResponseEntity<?> getAllDeletedSessions();

    ResponseEntity<?> openSession(CashInfoDto openSessionRequest, String token);

    ResponseEntity<?> closeSession(CashInfoDto closeSessionRequest, String token);

    ResponseEntity<?> getSalePointSessions(Long id);

    ResponseEntity<?> getSalePointOpenSession(Long id);

}
