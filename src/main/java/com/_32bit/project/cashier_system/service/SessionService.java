package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SessionService {

    ResponseEntity<?> getSessionInfo(Long sessionId);

    ResponseEntity<?> deleteSession(Long sessionId);

    ResponseEntity<?> restoreSession(Long sessionId);

    ResponseEntity<?> getAllSessions();

    ResponseEntity<?> getAllDeletedSessions();

    ResponseEntity<?> openSession(Long salePointId, String token);

    ResponseEntity<?> closeSession(Long salePointId, String token);

    ResponseEntity<?> getSalePointSessions(Long id);

    ResponseEntity<?> getSalePointOpenSession(Long id);

    Session getOpenSessionOfSalePoint(Long id);

    List<Sale> getSessionSalesBySessionId(Long sessionId);

    Optional<SalePoint> getSalePointById(Long salePointId);
}
