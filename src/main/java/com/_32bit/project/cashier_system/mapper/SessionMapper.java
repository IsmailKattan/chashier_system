package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.session.SaleOfSession;
import com._32bit.project.cashier_system.DTO.session.SessionInfoResponse;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SessionMapper {
    public static SessionInfoResponse toSessionInfoResponseDTO(Session session) {
        List<SaleOfSession> sales = new ArrayList<>();
        if (session.getSales() != null) {
            List<Sale> salesFromDomain = session.getSales();
            sales = salesFromDomain.stream().map(sale -> SaleOfSession.builder()
                    .id(sale.getId())
                    .saleDate(sale.getSaleDate())
                    .saleTime(sale.getSaleTime())
                    .saleAmount(sale.getTotal())
                    .build()).toList();
        }

        SessionInfoResponse sessionInfoResponse = SessionInfoResponse.builder()
                .salePointId(session.getSalePoint().getId())
                .salePointName(session.getSalePoint().getName())
                .openingDate(session.getOpeningDate())
                .openingTime(session.getOpeningTime())
                .closingDate(session.getClosingDate())
                .closingTime(session.getClosingTime())
                .openedBy(session.getOpenedBy().getUsername())
                .closedBy(session.getClosedBy() != null ? session.getClosedBy().getUsername() : null)  // Null check
                .sales(sales)
                .build();

        long setSessionId = ((session.getId() == null) ? 1 : session.getId());
        sessionInfoResponse.setId(setSessionId);
        boolean closed = session.getClosed() != null && session.getClosed();
        sessionInfoResponse.setClosed(closed);
        return sessionInfoResponse;
    }

    public static Session toDomain(SalePoint salePoint) {
        return Session.builder()
                .openingDate(LocalDate.of(0, 1, 1))
                .openingTime(LocalTime.of(0, 0, 0))
                .closingDate(LocalDate.of(0, 1, 1))
                .closingTime(LocalTime.of(0, 0, 0))
                .openedBy(new TeamMember())
                .closedBy(null)  // Ensure closedBy is null when initializing
                .deleted(false)
                .salePoint(salePoint)
                .sales(new ArrayList<>())
                .build();
    }

    public static List<SessionInfoResponse> toSessionInfoResponseDTOList(List<Session> all) {
        List<SessionInfoResponse> sessionInfoResponses = new ArrayList<>();
        for (Session session : all) {
            sessionInfoResponses.add(toSessionInfoResponseDTO(session));
        }
        return sessionInfoResponses;
    }
}

