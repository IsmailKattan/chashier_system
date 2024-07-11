package com._32bit.project.cashier_system.mapper;

import com._32bit.project.cashier_system.DTO.salePoint.CreateSalePointRequest;
import com._32bit.project.cashier_system.DTO.salePoint.SalePointInfoResponse;
import com._32bit.project.cashier_system.DTO.salePoint.SessionOfSalePoint;
import com._32bit.project.cashier_system.DTO.salePoint.TeamMemberOfSalePoint;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.service.impl.AuthServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SalePointMapper {
    private final static Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    public static SalePointInfoResponse toSalePointInfoResponseDTO(SalePoint salePoint) {

        List<SessionOfSalePoint> sessions = new ArrayList<>();

        if (salePoint.getSessions() != null) {
            salePoint.getSessions().forEach(session -> {
                sessions.add(
                        SessionOfSalePoint

                                .builder()
                                .id(session.getId())
                                .openingDate(session.getOpeningDate())
                                .openingTime(session.getOpeningTime())
                                .closingDate(session.getClosingDate())
                                .closingTime(session.getClosingTime())
                                .build()
                );
            });
        }


        List<TeamMemberOfSalePoint> teamMembers = new ArrayList<>();
        if (salePoint.getTeamMembers() != null) {
            salePoint.getTeamMembers().forEach(teamMember -> {
                teamMembers.add(
                        TeamMemberOfSalePoint
                                .builder()
                                .id(teamMember.getId())
                                .name(teamMember.getUsername())
                                .username(teamMember.getUsername())
                                .build()
                );
            });
        }



        return SalePointInfoResponse.builder()
                .id(salePoint.getId())
                .name(salePoint.getName())
                .address(salePoint.getAddress())
                .createdBy(salePoint.getCreatedBy().getUsername())
                .createdAtDate(salePoint.getCreatingDate())
                .createdAtTime(salePoint.getCreatingTime())
                .createdBy(salePoint.getCreatedBy().getFirstname()+" "+salePoint.getCreatedBy().getLastname())
                .sessions(sessions)
                .teamMembers(teamMembers)
                .build();
    }


    public static SalePoint createRequestToDomain(CreateSalePointRequest request) {
        return SalePoint
                .builder()
                .id(request.getId())
                .address(request.getAddress())
                .name(request.getName())
                .creatingDate(LocalDate.now())
                .creatingTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS))
                .deleted(false)
                .sessions(new ArrayList<>())
                .teamMembers(new ArrayList<>())
                .build();
    }

    public static SalePoint updateSalePoint(SalePoint salePoint, CreateSalePointRequest request) {
        salePoint.setName(request.getName());
        salePoint.setAddress(request.getAddress());
        return salePoint;
    }

    public static List<SalePointInfoResponse> EmptyListResponse() {
        logger.debug("SalePointMapper.EmptyListResponse(): returns not initialized object");
        return new ArrayList<>();
    }

    public static List<SalePointInfoResponse> toSalePointInfoResponseDTOList(List<SalePoint> salePoints) {
        List<SalePointInfoResponse> salePointInfoResponses = new ArrayList<>();
        salePoints.forEach(salePoint -> {
            salePointInfoResponses.add(toSalePointInfoResponseDTO(salePoint));
        });
        return salePointInfoResponses;
    }

    public static List<TeamMemberOfSalePoint> toTeamMemberOfSalePointDTOList(List<TeamMember> teamMembers) {
        List<TeamMemberOfSalePoint> teamMemberOfSalePoints = new ArrayList<>();
        teamMembers.forEach(teamMember -> {
            teamMemberOfSalePoints.add(
                    TeamMemberOfSalePoint
                            .builder()
                            .id(teamMember.getId())
                            .name(teamMember.getFirstname() + " " + teamMember.getLastname())
                            .username(teamMember.getUsername())
                            .build()
            );
        });
        return teamMemberOfSalePoints;
    }

    public static List<SessionOfSalePoint> toSessionOfSalePointDTOList(List<Session> sessions) {
        List<SessionOfSalePoint> sessionOfSalePoints = new ArrayList<>();
        sessions.forEach(session -> {
            sessionOfSalePoints.add(
                    SessionOfSalePoint
                            .builder()
                            .id(session.getId())
                            .openingDate(session.getOpeningDate())
                            .openingTime(session.getOpeningTime())
                            .closingDate(session.getClosingDate())
                            .closingTime(session.getClosingTime())
                            .build()
            );
        });
        return sessionOfSalePoints;
    }
}
