package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.DAO.SessionRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DAO.UserCredentialRepository;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.salePoint.CreateSalePointRequest;
import com._32bit.project.cashier_system.DTO.salePoint.SalePointInfoResponse;
import com._32bit.project.cashier_system.DTO.salePoint.SessionOfSalePoint;
import com._32bit.project.cashier_system.DTO.salePoint.TeamMemberOfSalePoint;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.UserCredential;
import com._32bit.project.cashier_system.mapper.SalePointMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.SalePointService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalePointServiceImpl implements SalePointService {

    private final static Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    private final SalePointRepository salePointRepository;

    private final UserCredentialRepository userCredentialRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final SessionRepository sessionRepository;

    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<?> getSalePointInfo(Long salePointId) {
        if (salePointId == null) {
            logger.warn("Sale point: Sale point id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(()->new RuntimeException("Sale point not found"));
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(salePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " returned"),
                salePointInfoResponse
        );
        logger.info("Sale point: Sale point with id: " + salePointInfoResponse.getId() + " returned" );
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> createSalePoint(CreateSalePointRequest request, String token) {

        if (request == null) {
            logger.warn("Sale point: NULL request");

            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Request can't be null"),
                            null
                    )
            );

        }
        if (request.getName() == null || request.getAddress() == null) {
            logger.warn("Sale point: unable to be null variables: name, address");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Name and address can't be null"),
                            null
                    )
            );
        }
        String tokenWithoutBearer = token.substring(7);
        String username = jwtUtils.getUsernameFromJwtToken(tokenWithoutBearer);
        if(username == null){
            logger.warn("Sale point: Username not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Username not found"),
                            null
                    )
            );
        }
        if (userCredentialRepository.findByUsername(username).isEmpty()) {
            logger.warn("Sale point: User not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("User not found"),
                            null
                    )
            );
        }
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (teamMemberRepository.findById(userCredential.getTeamMember().getId()).isEmpty()) {
            logger.warn("Sale point: Team member not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Team member not found"),
                            null
                    )
            );
        }
        if (teamMemberRepository.findById(userCredential.getTeamMember().getId()).isEmpty()) {
            logger.warn("Sale point: Created by team member not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Created by team member not found"),
                            null
                    )
            );
        }
        TeamMember createdBy = teamMemberRepository.findById(userCredential.getTeamMember().getId()).orElseThrow(() -> new RuntimeException("Team member not found"));
        SalePoint salePoint = SalePointMapper.createRequestToDomain(request);
        salePoint.setCreatedBy(createdBy);
        var saveSalePoint =  salePointRepository.save(salePoint);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +" and name: " + saveSalePoint.getName() + " saved");
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " created"),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> updateSalePoint(Long salePointId, CreateSalePointRequest request) {
        if (salePointId == null || request == null) {
            logger.warn("Sale point: Sale point id or request can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id or request can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )

            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        SalePoint updatedSalePoint = SalePointMapper.updateSalePoint(salePoint, request);
        var saveSalePoint = salePointRepository.save(updatedSalePoint);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +" and name: " + saveSalePoint.getName() + " updated");
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " updated"),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);

    }

    @Override
    public ResponseEntity<?> deleteSalePoint(Long salePointId) {

        if (salePointId == null) {
            logger.warn("Sale point: Sale point id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        salePoint.setDeleted(true);
        var saveSalePoint = salePointRepository.save(salePoint);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +" and name: " + saveSalePoint.getName() + " deleted");
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " deleted"),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> restoreSalePoint(Long salePointId) {
        if (salePointId == null) {
            logger.warn("Sale point: Sale point id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,true).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,true).orElseThrow(() -> new RuntimeException("Sale point not found"));
        salePoint.setDeleted(false);
        var saveSalePoint = salePointRepository.save(salePoint);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +" and name: " + saveSalePoint.getName() + " restored");
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " restored"),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);

    }

    @Override
    public ResponseEntity<?> addTeamMember(Long salePointId, Long teamMemberId,String token) {
        String tokenWithoutBearer = token.substring(7);
        String username = jwtUtils.getUsernameFromJwtToken(tokenWithoutBearer);
        if(username == null){
            logger.warn("Sale point: Username not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Username not found"),
                            null
                    )
            );
        }
        if (teamMemberRepository.findById(teamMemberId).isPresent() && userCredentialRepository.findByUsername(username).isPresent()) {
            if (userCredentialRepository.findByUsername(username).get() == teamMemberRepository.findById(teamMemberId).get().getUserCredential()) {
                logger.warn("Sale Point: user tries to add him self in sale point!!!");
                return ResponseEntity.badRequest().body(
                        new ObjectWithMessageResponse(
                                new MessageResponse("you can't add your self into sale point"),
                                null
                        )
                );
            }
        }


        if (salePointId == null || teamMemberId == null) {
            logger.warn("Sale point: Sale point id or team member id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id or team member id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        if (teamMemberRepository.findByIdAndDeleted(teamMemberId,false).isEmpty()) {
            logger.warn("Sale point: Team member with id: " + teamMemberId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Team member with id: " + teamMemberId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        TeamMember teamMember = teamMemberRepository.findByIdAndDeleted(teamMemberId,false).orElseThrow(() -> new RuntimeException("Team member not found"));
        if (salePoint.getTeamMembers().contains(teamMember)) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " already have team member with id: " + teamMemberId);
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " already have team member with id: " + teamMemberId),
                            null
                    )
            );
        }
        salePoint.getTeamMembers().add(teamMember);
        var saveSalePoint = salePointRepository.save(salePoint);
        teamMember.setSalePoint(salePoint);
        teamMemberRepository.save(teamMember);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +
                " and name: " + saveSalePoint.getName() +
                " have new team member with id: " + teamMember.getId() +
                " and name: " + teamMember.getFirstname() + " " + teamMember.getLastname());
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " have new team member with id: " + teamMember.getId()),
                salePointInfoResponse);
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> removeTeamMember(Long salePointId, Long teamMemberId,String token) {

        String tokenWithoutBearer = token.substring(7);
        String username = jwtUtils.getUsernameFromJwtToken(tokenWithoutBearer);
        if(username == null){
            logger.warn("Sale point: Username not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Username not found"),
                            null
                    )
            );
        }
        if (teamMemberRepository.findById(teamMemberId).isPresent() && userCredentialRepository.findByUsername(username).isPresent()) {
            if (userCredentialRepository.findByUsername(username).get() == teamMemberRepository.findById(teamMemberId).get().getUserCredential()) {
                logger.warn("Sale Point: user tries to remove him self from sale point!!!");
                return ResponseEntity.badRequest().body(
                        new ObjectWithMessageResponse(
                                new MessageResponse("you can't remove your self into sale point"),
                                null
                        )
                );
            }
        }

        if (salePointId == null || teamMemberId == null) {
            logger.warn("Sale point: Sale point id or team member id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id or team member id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        if (teamMemberRepository.findByIdAndDeleted(teamMemberId,false).isEmpty()) {
            logger.warn("Sale point: Team member with id: " + teamMemberId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Team member with id: " + teamMemberId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        TeamMember teamMember = teamMemberRepository.findByIdAndDeleted(teamMemberId,false).orElseThrow(() -> new RuntimeException("Team member not found"));
        if (!salePoint.getTeamMembers().contains(teamMember)) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " doesn't have team member with id: " + teamMemberId);
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " doesn't have team member with id: " + teamMemberId),
                            null
                    )
            );
        }
        salePoint.getTeamMembers().remove(teamMember);
        var saveSalePoint = salePointRepository.save(salePoint);
        teamMember.setSalePoint(null);
        teamMemberRepository.save(teamMember);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +
                " and name: " + saveSalePoint.getName() +
                " removed team member with id: " + teamMember.getId() +
                " and name: " + teamMember.getFirstname() + " " + teamMember.getLastname());
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " has team member with id: " + teamMember.getId() + " removed"),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> addSession(Long salePointId, Long sessionId) {
        if (salePointId == null || sessionId == null) {
            logger.warn("Sale point: Sale point id or session id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id or session id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        if (sessionRepository.findByIdAndDeleted(sessionId,false).isEmpty()) {
            logger.warn("Sale point: Session with id: " + sessionId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session with id: " + sessionId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        Session session = sessionRepository.findByIdAndDeleted(sessionId,false).orElseThrow(() -> new RuntimeException("Session not found"));
        if (salePoint.getSessions().contains(session)) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " already have session with id: " + sessionId);
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " already have session with id: " + sessionId),
                            null
                    )
            );
        }
        salePoint.getSessions().add(session);
        var saveSalePoint = salePointRepository.save(salePoint);
        session.setSalePoint(saveSalePoint);
        sessionRepository.save(session);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +
                " and name: " + saveSalePoint.getName() +
                " have new session with id: " + sessionId);
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " have new session with id: " + sessionId),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> removeSession(Long salePointId, Long sessionId) {
        if (salePointId == null || sessionId == null) {
            logger.warn("Sale point: Sale point id or session id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id or session id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(salePointId,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " not found"),
                            null
                    )
            );
        }
        if (sessionRepository.findByIdAndDeleted(sessionId,false).isEmpty()) {
            logger.warn("Sale point: Session with id: " + sessionId + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Session with id: " + sessionId + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(salePointId,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        Session session = sessionRepository.findByIdAndDeleted(sessionId,false).orElseThrow(() -> new RuntimeException("Session not found"));
        if (!salePoint.getSessions().contains(session)) {
            logger.warn("Sale point: Sale point with id: " + salePointId + " doesn't have session with id: " + sessionId);
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + salePointId + " doesn't have session with id: " + sessionId),
                            null
                    )
            );
        }
        salePoint.getSessions().remove(session);
        var saveSalePoint = salePointRepository.save(salePoint);
        session.setSalePoint(null);
        sessionRepository.save(session);
        logger.info("Sale Point: salePoint with id: " + saveSalePoint.getId() +
                " and name: " + saveSalePoint.getName() +
                " removed session with id: " + sessionId);
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(saveSalePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Sale point with id: " + salePointInfoResponse.getId() + " has session with id: " + sessionId + " removed"),
                salePointInfoResponse
        );
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getAllSalePoints() {
        List<SalePoint> salePoints = salePointRepository.findAllByDeleted(false);
        if (salePoints.isEmpty()) {
            logger.warn("Sale point: No sale points found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("No sale points found"),
                            null
                    )
            );
        }
        List<SalePointInfoResponse> salePointInfoResponseList = SalePointMapper.toSalePointInfoResponseDTOList(salePoints);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("All sale points returned"),
                salePointInfoResponseList
        );
        logger.info("Sale point: All sale points returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getAllDeletedSalePoints() {
        List<SalePoint> salePoints = salePointRepository.findAllByDeleted(true);
        if (salePoints.isEmpty()) {
            logger.warn("Sale point: No deleted sale points found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("No deleted sale points found"),
                            null
                    )
            );
        }
        List<SalePointInfoResponse> salePointInfoResponseList = SalePointMapper.toSalePointInfoResponseDTOList(salePoints);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("All deleted sale points returned"),
                salePointInfoResponseList
        );
        logger.info("Sale point: All deleted sale points returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getDeletedSalPoint(Long Id) {
        if (Id == null) {
            logger.warn("Sale point:Deleted sale point id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Deleted sale point id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(Id,true).isEmpty()) {
            logger.warn("Sale point: Deleted sale point with id: " + Id + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Deleted sale point with id: " + Id + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(Id,true).orElseThrow(() -> new RuntimeException("Sale point not found"));
        SalePointInfoResponse salePointInfoResponse = SalePointMapper.toSalePointInfoResponseDTO(salePoint);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("Deleted sale point with id: " + salePointInfoResponse.getId() + " returned"),
                salePointInfoResponse
        );
        logger.info("Sale point: Deleted sale point with id: " + salePointInfoResponse.getId() + " returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getSalePointByAddressLike(String address) {

        if (address == null) {
            logger.warn("Sale point: Sale point address can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point address can't be null"),
                            null
                    )
            );
        }
        List<SalePoint> salePoints = salePointRepository.findByAddressContainsAndDeleted(address,false);

        if (salePoints.isEmpty()) {
            logger.warn("Sale point: No sale points with address contains: " + address + " found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("No sale points with address contains: " + address + " found"),
                            null
                    )
            );
        }
        List<SalePointInfoResponse> salePointInfoResponseList = SalePointMapper.toSalePointInfoResponseDTOList(salePoints);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("All sale points with address contains: " + address + " returned"),
                salePointInfoResponseList
        );
        logger.info("Sale point: All sale points with address contains: " +address + " returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getSalePointByName(String salePointName) {
        if (salePointName == null) {
            logger.warn("Sale point: Sale point name can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point name can't be null"),
                            null
                    )
            );
        }
        List<SalePoint> salePoints = salePointRepository.findByNameContainsAndDeleted(salePointName,false);
        if (salePoints.isEmpty()) {
            logger.warn("Sale point: No sale points with name contains: " + salePointName + " found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("No sale points with name contains: " + salePointName + " found"),
                            null
                    )
            );
        }
        List<SalePointInfoResponse> salePointInfoResponseList = SalePointMapper.toSalePointInfoResponseDTOList(salePoints);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("All sale points with name contains: " + salePointName + " returned"),
                salePointInfoResponseList
        );
        logger.info("Sale point: All sale points with name contains: " +salePointName + " returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getSalePointSessions(Long id) {
        if (id == null) {
            logger.warn("Sale point: Sale point id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(id,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + id + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + id + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(id,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        if (salePoint.getSessions().isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + id + " doesn't have any sessions");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + id + " doesn't have any sessions"),
                            null
                    )
            );
        }
        List<Session> sessions = salePoint.getSessions();
        List<SessionOfSalePoint> sessionOfSalePoints = SalePointMapper.toSessionOfSalePointDTOList(sessions);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("All sessions of sale point with id: " + id + " returned"),
                sessions
        );
        logger.info("Sale point: All sessions of sale point with id: " + id + " returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }

    @Override
    public ResponseEntity<?> getSalePointTeamMembers(Long id) {

        if (id == null) {
            logger.warn("Sale point: Sale point id can't be null");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point id can't be null"),
                            null
                    )
            );
        }
        if (salePointRepository.findByIdAndDeleted(id,false).isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + id + " not found");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + id + " not found"),
                            null
                    )
            );
        }
        SalePoint salePoint = salePointRepository.findByIdAndDeleted(id,false).orElseThrow(() -> new RuntimeException("Sale point not found"));
        if (salePoint.getTeamMembers().isEmpty()) {
            logger.warn("Sale point: Sale point with id: " + id + " doesn't have any team members");
            return ResponseEntity.badRequest().body(
                    new ObjectWithMessageResponse(
                            new MessageResponse("Sale point with id: " + id + " doesn't have any team members"),
                            null
                    )
            );
        }
        List<TeamMember> teamMembers = salePoint.getTeamMembers();
        List<TeamMemberOfSalePoint> teamMemberOfSalePoints = SalePointMapper.toTeamMemberOfSalePointDTOList(teamMembers);
        ObjectWithMessageResponse infoWithMessageResponse = new ObjectWithMessageResponse(
                new MessageResponse("All team members of sale point with id: " + id + " returned"),
                teamMemberOfSalePoints
        );
        logger.info("Sale point: All team members of sale point with id: " + id + " returned");
        return ResponseEntity.ok().body(infoWithMessageResponse);
    }


}
