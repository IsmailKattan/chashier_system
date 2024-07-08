package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.product.CreateProductRequest;
import com._32bit.project.cashier_system.DTO.product.UpdatePriceRequest;
import com._32bit.project.cashier_system.DTO.product.UpdateQuantityRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> getAllProducts();

    ResponseEntity<?> getProductByIdAndDeleted(Long id, Boolean deleted);

    ResponseEntity<?> getProductsByNameContainsAndDeleted(String name, Boolean deleted);

    ResponseEntity<?> getProductsByBrandContainsAndDeleted(String brand, Boolean deleted);

    ResponseEntity<?> getProductsByCategoryAndDeleted(String category, Boolean deleted);

    ResponseEntity<?> createProduct(CreateProductRequest request,String token);

    ResponseEntity<?> updateProduct(Long id, CreateProductRequest request);

    ResponseEntity<?> deleteProduct(Long id);

    ResponseEntity<?> restoreProduct(Long id);

    ResponseEntity<?> setDiscount(Long id, Double discountRate);

    ResponseEntity<?> removeDiscount(Long id);

    ResponseEntity<?> setOffer(Long productId, Long offerId);

    ResponseEntity<?> removeOffer(Long productId);

    ResponseEntity<?> UpdateQuantities(List<UpdateQuantityRequest> requests);

    ResponseEntity<?> createProducts(List<CreateProductRequest> requests,String token);

    ResponseEntity<?> UpdatePrices(List<UpdatePriceRequest> requests);

    Double getPriceById(Long id);

}
