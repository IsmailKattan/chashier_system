package com._32bit.project.cashier_system.service;

import com._32bit.project.cashier_system.DTO.product.CreateProductRequest;
import com._32bit.project.cashier_system.DTO.product.UpdatePriceRequest;
import com._32bit.project.cashier_system.DTO.product.UpdateQuantityRequest;
import com._32bit.project.cashier_system.domains.Offer;
import com._32bit.project.cashier_system.domains.Product;
import com._32bit.project.cashier_system.domains.SaleItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<?> getAllProducts();
    ResponseEntity<?> getProductsByDeleted(Boolean deleted);

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

    ResponseEntity<?> UpdateAddQuantities(List<UpdateQuantityRequest> requests);

    ResponseEntity<?> createProducts(List<CreateProductRequest> requests,String token);

    ResponseEntity<?> UpdatePrices(List<UpdatePriceRequest> requests);

    Double getPriceById(Long id);

    Product getProductById(Long id);

    Offer getOfferDetails(Long id);

    boolean productHaveOffer(Long productId);

    void decreaseQuantity(Long productId, Double quantity);

    void increaseQuantities(List<SaleItem> saleItems);

    void decreaseQuantities(List<SaleItem> saleItems);
}
