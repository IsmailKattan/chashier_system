package com._32bit.project.cashier_system.resource;


import com._32bit.project.cashier_system.DTO.salePoint.CreateSalePointRequest;
import com._32bit.project.cashier_system.DTO.salePoint.SalePointInfoResponse;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.SalePointService;
import com._32bit.project.cashier_system.service.impl.SalePointServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sale-point")
public class SalePointResource {

    @Autowired
    private final SalePointService salePointService;

    @Autowired
    private final JwtUtils jwtUtils;
    @GetMapping("/test")
    public String test(){
        return "Sale Point Resource";
    }

    @GetMapping("/point/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getSalePointById(@PathVariable Long id){
        return salePointService.getSalePointInfo(id);
    }

    @PostMapping("/create-point")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createSalePoint(@RequestBody CreateSalePointRequest request, HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        return salePointService.createSalePoint(request, token);
    }

    @PutMapping("/update-point/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateSalePoint(@PathVariable Long id,@RequestBody CreateSalePointRequest request){
        return salePointService.updateSalePoint(id, request);
    }

    @GetMapping("/delete-point/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSalePoint(@PathVariable Long id){
        return salePointService.deleteSalePoint(id);
    }

    @GetMapping("/restore-point/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> restoreSalePoint(@PathVariable Long id){
        return salePointService.restoreSalePoint(id);
    }

    @GetMapping("/add-team-member/{salePointId}/{teamMemberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> addTeamMember(@PathVariable Long salePointId, @PathVariable Long teamMemberId , HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        return salePointService.addTeamMember(salePointId, teamMemberId,token);
    }

    @GetMapping("/remove-team-member/{salePointId}/{teamMemberId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> removeTeamMember(@PathVariable Long salePointId, @PathVariable Long teamMemberId, HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        return salePointService.removeTeamMember(salePointId, teamMemberId,token);
    }

    // TODO: have to test

    @GetMapping("/add-session/{salePointId}/{sessionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> addSession(@PathVariable Long salePointId, @PathVariable Long sessionId){
        return salePointService.addSession(salePointId, sessionId);
    }

    // TODO: have to test

    @GetMapping("/remove-session/{salePointId}/{sessionId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> removeSession(@PathVariable Long salePointId, @PathVariable Long sessionId){
        return salePointService.removeSession(salePointId, sessionId);
    }

    @GetMapping("/all-points")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllSalePoints(){
        return salePointService.getAllSalePoints();
    }

    @GetMapping("/all-deleted-points")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllDeletedSalePoints(){
        return salePointService.getAllDeletedSalePoints();
    }

    @GetMapping("/deleted-point/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getDeletedSalePoint(@PathVariable Long id){
        return salePointService.getDeletedSalPoint(id);
    }


    @GetMapping("/points-by-address/{address}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getSalePointByAddressLike(@PathVariable String address){
        return salePointService.getSalePointByAddressLike(address);
    }


    @GetMapping("/points-by-name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getSalePointByName(@PathVariable String name){
        return salePointService.getSalePointByName(name);
    }

    @GetMapping("/point-sessions/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getSalePointSessions(@PathVariable Long id){
        return salePointService.getSalePointSessions(id);
    }

    @GetMapping("/point-team-members/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getSalePointTeamMembers(@PathVariable Long id){
        return salePointService.getSalePointTeamMembers(id);
    }

}
