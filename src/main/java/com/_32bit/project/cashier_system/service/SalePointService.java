package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.salePoint.CreateSalePointRequest;
import com._32bit.project.cashier_system.DTO.salePoint.SalePointInfoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SalePointService {

    ResponseEntity<?> getSalePointInfo(Long salePointId);

    ResponseEntity<?> createSalePoint(CreateSalePointRequest request, String token);

    ResponseEntity<?> updateSalePoint(Long salePointId, CreateSalePointRequest request);

    ResponseEntity<?> deleteSalePoint(Long salePointId);

    ResponseEntity<?> restoreSalePoint(Long salePointId);

    ResponseEntity<?> getAllSalePoints();

    ResponseEntity<?> getAllDeletedSalePoints();

    ResponseEntity<?> getDeletedSalPoint(Long Id);

    ResponseEntity<?> getSalePointByAddressLike(String address);

    ResponseEntity<?> getSalePointByName(String salePointName);

    ResponseEntity<?> getSalePointSessions(Long id);

    ResponseEntity<?> getSalePointTeamMembers(Long id);
}
