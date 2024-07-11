package com._32bit.project.cashier_system.tests.sale;


import com._32bit.project.cashier_system.DAO.SaleRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.payment.PaymentOfSaleDto;
import com._32bit.project.cashier_system.DTO.sale.CreateSaleRequest;
import com._32bit.project.cashier_system.DTO.saleItem.ItemOfSaleDto;
import com._32bit.project.cashier_system.domains.*;
import com._32bit.project.cashier_system.domains.enums.PaymentMethod;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.PaymentService;
import com._32bit.project.cashier_system.service.ProductService;
import com._32bit.project.cashier_system.service.SaleItemService;
import com._32bit.project.cashier_system.service.SessionService;
import com._32bit.project.cashier_system.service.impl.SaleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SaleServiceTest {
    @Mock
    private SaleRepository saleRepository;
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @Mock
    private PaymentService paymentService;
    @Mock
    private SaleItemService saleItemService;
    @Mock
    private SessionService sessionService;
    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private ProductService productService;

    @InjectMocks
    private SaleServiceImpl saleService;

    private SalePoint salePoint;

    private Session session;

    private Sale sale;

    private SaleItem saleItem1;

    private SaleItem saleItem2;

    private TeamMember teamMember;

    private Product product;

    private Payment payment1;

    private Payment payment2;

    @BeforeEach
    void setUp() {

        salePoint = SalePoint.builder()
                .id(1L)
                .name("test name")
                .address("test address")
                .deleted(false)
                .build();

        session = Session.builder()
                .salePoint(salePoint)
                .id(1L)
                .closed(false)
                .deleted(false)
            .build();

        teamMember = TeamMember.builder()
                .id(1L)
                .username("test username")
                .password("test password")
                .salePoint(salePoint)
                .deleted(false)
                .build();

        sale = Sale.builder()
                .id(1L)
                .total(100.0)
                .saleDate(LocalDate.of(2022, 1, 1))
                .saleTime(LocalTime.of(12, 0))
                .soldBy(new TeamMember())
                .isInvoiced(false)
                .isPaid(false)
                .isPosted(false)
                .deleted(false)
                .session(session)
                .soldBy(teamMember)
                .build();

        product = Product.builder()
                .name("test product")
                .price(10.0)
                .deleted(false)
                .build();

        saleItem1 = SaleItem.builder()
                .id(1L)
                .quantity(3.0)
                .product(product)
                .total(40.0)
                .sale(sale)
                .deleted(false)
                .build();

        saleItem2 = SaleItem.builder()
                .id(2L)
                .quantity(3.0)
                .product(product)
                .total(40.0)
                .sale(sale)
                .deleted(false)
                .build();

        sale.setSaleItems(Arrays.asList(saleItem1, saleItem2));

        payment1 = Payment.builder()
                .id(1L)
                .amount(100.0)
                .paymentMethod(PaymentMethod.nakit)
                .sale(sale)
                .deleted(false)
                .build();

        payment2 = Payment.builder()
                .id(2L)
                .amount(100.0)
                .sale(sale)
                .paymentMethod(PaymentMethod.kredi_karti)
                .deleted(false)
                .build();

        sale.setPayments(Arrays.asList(payment1, payment2));
    }

    @Test
    void getSaleListByDeleted_shouldReturnSaleList() {
        // Arrange
        Boolean deleted = false;
        List<Sale> mockSales = Arrays.asList(sale,sale);
        when(saleRepository.findAllByDeleted(deleted)).thenReturn(mockSales);

        // Act
        ResponseEntity<?> response = saleService.getSaleListByDeleted(deleted);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void getSaleByIdAndDeleted_shouldReturnSale() {
        // Arrange

        when(saleRepository.findByIdAndDeleted(1L, false)).thenReturn(Optional.of(sale));

        // Act
        ResponseEntity<?> response = saleService.getSaleByIdAndDeleted(1L, false);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void createSale_shouldCreateNewSale() {
        // Arrange
        CreateSaleRequest request = CreateSaleRequest.builder()
                .salePointId(1L)
                .items(Arrays.asList(
                        ItemOfSaleDto.builder()
                                .productId(1L)
                                .quantity(3.0)
                                .build(),
                        ItemOfSaleDto.builder()
                                .quantity(3.0)
                                .productId(1L)
                                .build()
                ))
                .build();
        String token = "Bearer token";

        when(jwtUtils.getUsernameFromJwtToken(anyString())).thenReturn("username");
        when(teamMemberRepository.findByUsername("username")).thenReturn(Optional.of(teamMember));
        when(sessionService.getOpenSessionOfSalePoint(any())).thenReturn(session);
        when(saleItemService.getSaleItemsFromItemOfSaleDto(any(), any())).thenReturn(Arrays.asList(saleItem1,saleItem2));
        when(sessionService.getSalePointById(request.getSalePointId())).thenReturn(Optional.of(salePoint));

        // Act
        ResponseEntity<?> response = saleService.createSale(request, token);

        // Assert
        ArgumentCaptor<Sale> saleCaptor = ArgumentCaptor.forClass(Sale.class);
        verify(saleRepository, times(2)).save(saleCaptor.capture());
        List<Sale> capturedSales = saleCaptor.getAllValues();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, capturedSales.size());
    }


    @Test
    void getSalePointSalesByDeleted_shouldReturnSales() {
        // Arrange

        when(saleRepository.findSalesBySalePointId(1L)).thenReturn(Arrays.asList(sale,sale));

        // Act
        ResponseEntity<?> response = saleService.getSalePointSales(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void getSessionSalesByDeleted_shouldReturnSales() {
        // Arrange

        when(saleRepository.findBySessionId(1L)).thenReturn(Arrays.asList(sale,sale));

        // Act
        ResponseEntity<?> response = saleService.getSessionSales(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }


    @Test
    void deleteSale_shouldMarkSaleAsDeleted() {
        // Arrange

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

        // Act
        ResponseEntity<?> response = saleService.deleteSale(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
        assertTrue(sale.getDeleted());
        verify(saleRepository).save(sale);
    }

    @Test
    void restoreSale_shouldMarkSaleAsNotDeleted() {
        // Arrange

        sale.setDeleted(true);
        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

        // Act
        ResponseEntity<?> response = saleService.restoreSale(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
        assertFalse(sale.getDeleted());
        verify(saleRepository).save(sale);
    }

    @Test
    void getSalePointBySaleId_shouldReturnSalePoint() {
        // Arrange
        Long saleId = 1L;
        SalePoint mockSalePoint = new SalePoint();
        when(saleRepository.findSalePointBySaleId(saleId)).thenReturn(Optional.of(mockSalePoint));

        // Act
        SalePoint result = saleService.getSalePointBySaleId(saleId);

        // Assert
        assertNotNull(result);
        assertEquals(mockSalePoint, result);
    }

    @Test
    void createSale_withNullRequest_shouldReturnBadRequest() {
        // Arrange
        String token = "Bearer token";

        // Act
        ResponseEntity<?> response = saleService.createSale(null, token);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ObjectWithMessageResponse);
    }

    @Test
    void getSaleListByDeleted_withEmptyList_shouldReturnNotFound() {
        // Arrange
        Boolean deleted = false;
        when(saleRepository.findAllByDeleted(deleted)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = saleService.getSaleListByDeleted(deleted);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
