package com._32bit.project.cashier_system.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.*;

import com._32bit.project.cashier_system.DAO.ProductRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.product.CreateProductRequest;
import com._32bit.project.cashier_system.DTO.product.UpdatePriceRequest;
import com._32bit.project.cashier_system.DTO.product.UpdateQuantityRequest;
import com._32bit.project.cashier_system.domains.Product;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.Category;
import com._32bit.project.cashier_system.domains.enums.Unit;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private CreateProductRequest createProductRequest;
    private UpdateQuantityRequest updateQuantityRequest;
    private UpdatePriceRequest updatePriceRequest;
    private TeamMember teamMember;
    private String token;

    private List<Product> productList = new ArrayList<>();

    @BeforeEach
    void setUp() {

        teamMember = TeamMember.builder()
                .id(1L)
                .username("testUser")
                .password("testPassword")
                .email("testEmail")
                .deleted(false)
                .build();


        updateQuantityRequest = UpdateQuantityRequest.builder()
                .id(1L)
                .quantity(10.0)
                .build();

        updatePriceRequest = UpdatePriceRequest.builder()
                .id(1L)
                .price(100.0)
                .build();

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setBrand("Test Brand");
        product.setCategory(Category.CLOTHING);
        product.setQuantity(10.0);
        product.setUnit(Unit.g);
        product.setPrice(100.0);
        product.setIsDiscounted(false);
        product.setDiscountRate(0.0);
        product.setDiscountedPrice(0.0);
        product.setInsertedBy(teamMember);

        createProductRequest = CreateProductRequest.builder()
                .name("Test Product")
                .brand("Test Brand")
                .category("other")
                .quantity(10.0)
                .unit("other")
                .price(100.0)
                .build();

        productList.add(product);

        token = "Bearer testToken";
    }

    @Test
    void testGetAllProducts_NoProductsFound() {
        when(productRepository.findAllByDeleted(false)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = productService.getAllProducts();

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllProducts_ProductsFound() {
        when(productRepository.findAllByDeleted(false)).thenReturn(productList);

        ResponseEntity<?> response = productService.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testGetProductByIdAndDeleted_IdIsNull() {
        ResponseEntity<?> response = productService.getProductByIdAndDeleted(null, false);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Id is null", response.getBody());
    }

    @Test
    void testGetProductByIdAndDeleted_DeletedIsNull() {
        ResponseEntity<?> response = productService.getProductByIdAndDeleted(1L, null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Deleted is null", response.getBody());
    }

    @Test
    void testGetProductByIdAndDeleted_ProductNotFound() {
        when(productRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.getProductByIdAndDeleted(1L, false);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetProductByIdAndDeleted_ProductFound() {
        when(productRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(product));

        ResponseEntity<?> response = productService.getProductByIdAndDeleted(1L, false);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetProductsByNameContainsAndDeleted_NameIsNull() {
        ResponseEntity<?> response = productService.getProductsByNameContainsAndDeleted(null, false);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Name is null", response.getBody());
    }

    @Test
    void testGetProductsByNameContainsAndDeleted_DeletedIsNull() {
        ResponseEntity<?> response = productService.getProductsByNameContainsAndDeleted("test", null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Deleted is null", response.getBody());
    }

    @Test
    void testGetProductsByNameContainsAndDeleted_NoProductsFound() {
        when(productRepository.findByNameContainsAndDeleted(anyString(), anyBoolean())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = productService.getProductsByNameContainsAndDeleted("test", false);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetProductsByNameContainsAndDeleted_ProductsFound() {
        when(productRepository.findByNameContainsAndDeleted(anyString(), anyBoolean())).thenReturn(productList);

        ResponseEntity<?> response = productService.getProductsByNameContainsAndDeleted("test", false);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testGetProductsByBrandContainsAndDeleted_BrandIsNull() {
        ResponseEntity<?> response = productService.getProductsByBrandContainsAndDeleted(null, false);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Brand is null", response.getBody());
    }

    @Test
    void testGetProductsByBrandContainsAndDeleted_DeletedIsNull() {
        ResponseEntity<?> response = productService.getProductsByBrandContainsAndDeleted("brand", null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Deleted is null", response.getBody());
    }

    @Test
    void testGetProductsByBrandContainsAndDeleted_NoProductsFound() {
        when(productRepository.findByBrandContainsAndDeleted(anyString(), anyBoolean())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = productService.getProductsByBrandContainsAndDeleted("brand", false);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetProductsByBrandContainsAndDeleted_ProductsFound() {
        when(productRepository.findByBrandContainsAndDeleted(anyString(), anyBoolean())).thenReturn(productList);

        ResponseEntity<?> response = productService.getProductsByBrandContainsAndDeleted("brand", false);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testGetProductsByCategoryAndDeleted_CategoryIsNull() {
        ResponseEntity<?> response = productService.getProductsByCategoryAndDeleted(null, false);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Category is null", response.getBody());
    }

    @Test
    void testGetProductsByCategoryAndDeleted_DeletedIsNull() {
        ResponseEntity<?> response = productService.getProductsByCategoryAndDeleted("category", null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Deleted is null", response.getBody());
    }

    @Test
    void testGetProductsByCategoryAndDeleted_InvalidCategory() {
        ResponseEntity<?> response = productService.getProductsByCategoryAndDeleted("invalidCategory", false);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Category is invalid", response.getBody());
    }

    @Test
    void testGetProductsByCategoryAndDeleted_NoProductsFound() {
        lenient().when(productRepository.findByCategoryAndDeleted(anyString(), anyBoolean())).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = productService.getProductsByCategoryAndDeleted("category", false);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCreateProduct_ValidRequest() {
        when(jwtUtils.getUsernameFromJwtToken(anyString())).thenReturn("testUser");
        when(teamMemberRepository.findByUsernameAndDeleted(teamMember.getUsername(), false)).thenReturn(Optional.of(teamMember));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productService.createProduct(createProductRequest, token);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testCreateProduct_NullRequest() {
        ResponseEntity<?> response = productService.createProduct(null, token);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Request is null", response.getBody());
    }

    @Test
    void testCreateProduct_NullToken() {
        ResponseEntity<?> response = productService.createProduct(createProductRequest, null);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Token is null", response.getBody());
    }

    @Test
    void testUpdateProduct_ValidRequest() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productService.updateProduct(1L, createProductRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testUpdateProduct_InvalidId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.updateProduct(1L, createProductRequest);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteProduct_ValidId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ResponseEntity<?> response = productService.deleteProduct(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testDeleteProduct_InvalidId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.deleteProduct(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRestoreProduct_ValidId() {
        product.setDeleted(true);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productService.restoreProduct(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testRestoreProduct_InvalidId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.restoreProduct(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSetDiscount_ValidRequest() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productService.setDiscount(1L, 10.0);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testSetDiscount_InvalidDiscountRate() {
        ResponseEntity<?> response = productService.setDiscount(1L, -5.0);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Discount rate is invalid", response.getBody());
    }

    @Test
    void testRemoveDiscount_ValidRequest() {
        product.setIsDiscounted(true);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productService.removeDiscount(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void testRemoveDiscount_InvalidId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.removeDiscount(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateQuantities_ValidRequest() {
        List<UpdateQuantityRequest> requests = Arrays.asList(updateQuantityRequest);
        requests.get(0).setQuantity(5.0);

        when(productRepository.findById(updateQuantityRequest.getId())).thenReturn(Optional.of(product));

        ResponseEntity<?> response = productService.UpdateQuantities(requests);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
        verify(productRepository, times(1)).save(product);
        assertEquals(15, product.getQuantity());
    }

    @Test
    void testUpdateQuantities_InvalidRequest() {
        List<UpdateQuantityRequest> requests = Arrays.asList(updateQuantityRequest);

        when(productRepository.findById(updateQuantityRequest.getId())).thenReturn(Optional.empty());

        ResponseEntity<?> response = productService.UpdateQuantities(requests);

        assertEquals(404, response.getStatusCodeValue());
        verify(productRepository, times(0)).save(product);
    }

    @Test
    void testUpdateQuantities_NullRequest() {
        ResponseEntity<?> response = productService.UpdateQuantities(null);

        assertEquals(400, response.getStatusCodeValue());
    }


    @Test
    void testCreateProducts_ValidRequest() {
        List<CreateProductRequest> requests = Arrays.asList(createProductRequest);

        when(jwtUtils.getUsernameFromJwtToken(anyString())).thenReturn("testUser");
        when(teamMemberRepository.findByUsernameAndDeleted(anyString(), eq(false))).thenReturn(Optional.of(teamMember));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L); // Simulate database assigning an ID
            return savedProduct;
        });

        ResponseEntity<?> response = productService.createProducts(requests, token);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);

        // Verify save method call
        verify(productRepository, times(1)).save(argThat(product ->
                product.getName().equals("Test Product") &&
                        product.getBrand().equals("Test Brand") &&
                        product.getCategory().equals(Category.OTHER) &&
                        product.getQuantity() == 10.0 &&
                        product.getUnit().equals(Unit.other) &&
                        product.getPrice() == 100.0 &&
                        product.getInsertedBy().getUsername().equals("testUser")
        ));
    }


    @Test
    void testUpdatePrices_ValidRequest() {
        List<UpdatePriceRequest> requests = Arrays.asList(updatePriceRequest);
        requests.get(0).setPrice(200.0);
        when(productRepository.findByIdAndDeleted(updatePriceRequest.getId(),false)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productService.UpdatePrices(requests);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
        verify(productRepository, times(1)).save(product);
        assertEquals(200.0, product.getPrice());  // Ensure the product's price is updated
    }

}
