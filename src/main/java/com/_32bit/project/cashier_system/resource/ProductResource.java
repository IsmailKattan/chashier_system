package com._32bit.project.cashier_system.resource;

import com._32bit.project.cashier_system.DTO.product.CreateProductRequest;
import com._32bit.project.cashier_system.DTO.product.UpdatePriceRequest;
import com._32bit.project.cashier_system.DTO.product.UpdateQuantityRequest;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Product Resource");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/all-deleted")
    public ResponseEntity<?> getProductsByDeleted() {
        return productService.getProductsByDeleted(true);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<?> getProductsByNameContainsAndDeleted(@PathVariable String name) {
        return productService.getProductsByNameContainsAndDeleted(name, false);
    }

    @GetMapping("/by-brand/{brand}")
    public ResponseEntity<?> getProductsByBrandContainsAndDeleted(@PathVariable String brand) {
        return productService.getProductsByBrandContainsAndDeleted(brand, false);
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<?> getProductsByCategoryAndDeleted(@PathVariable String category) {
        return productService.getProductsByCategoryAndDeleted(category, false);
    }

    @GetMapping("deleted-by-name/{name}")
    public ResponseEntity<?> getDeletedProductsByNameContains(@PathVariable String name) {
        return productService.getProductsByNameContainsAndDeleted(name, true);
    }

    @GetMapping("deleted-by-brand/{brand}")
    public ResponseEntity<?> getDeletedProductsByBrandContains(@PathVariable String brand) {
        return productService.getProductsByBrandContainsAndDeleted(brand, true);
    }

    @GetMapping("deleted-by-category/{category}")
    public ResponseEntity<?> getDeletedProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategoryAndDeleted(category, true);
    }

    @GetMapping("by-id/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productService.getProductByIdAndDeleted(id, false);
    }

    @GetMapping("deleted-by-id/{id}")
    public ResponseEntity<?> getDeletedProductById(@PathVariable Long id) {
        return productService.getProductByIdAndDeleted(id, true);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody CreateProductRequest request, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        return productService.createProduct(request, token);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody CreateProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<?> restoreProduct(@PathVariable Long id) {
        return productService.restoreProduct(id);
    }

    @PostMapping("/set-discount/{id}/{discountRate}")
    public ResponseEntity<?> setDiscount(@PathVariable Long id, @PathVariable Double discountRate) {
        return productService.setDiscount(id, discountRate);
    }

    @DeleteMapping("/remove-discount/{id}")
    public ResponseEntity<?> removeDiscount(@PathVariable Long id) {
        return productService.removeDiscount(id);
    }

    @PostMapping("/set-offer/{productId}/{offerId}")
    public ResponseEntity<?> setOffer(@PathVariable Long productId, @PathVariable Long offerId) {
        return productService.setOffer(productId, offerId);
    }

    @DeleteMapping("/remove-offer/{productId}")
    public ResponseEntity<?> removeOffer(@PathVariable Long productId) {
        return productService.removeOffer(productId);
    }

    @PostMapping("/update-quantities")
    public ResponseEntity<?> updateQuantities(@RequestBody List<UpdateQuantityRequest> requests) {
        return productService.UpdateQuantities(requests);
    }

    @PostMapping("/update-add-quantities")
    public ResponseEntity<?> updateAddQuantities(@RequestBody List<UpdateQuantityRequest> requests) {
        return productService.UpdateAddQuantities(requests);
    }

    @PostMapping("/create-products")
    public ResponseEntity<?> createProducts(@RequestBody List<CreateProductRequest> requests, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization");
        return productService.createProducts(requests, token);
    }

    @PostMapping("/update-prices")
    public ResponseEntity<?> updatePrices(@RequestBody List<UpdatePriceRequest> requests) {
        return productService.UpdatePrices(requests);
    }

    @GetMapping("/price/{id}")
    public Double getPriceById(@PathVariable Long id) {
        return productService.getPriceById(id);
    }


}
