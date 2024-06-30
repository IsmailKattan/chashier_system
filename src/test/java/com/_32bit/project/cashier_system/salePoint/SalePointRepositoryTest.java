package com._32bit.project.cashier_system.salePoint;

import com._32bit.project.cashier_system.CashierSystemApplication;
import com._32bit.project.cashier_system.DAO.SalePointRepository;
import com._32bit.project.cashier_system.domains.SalePoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DataJpaTest
@Import(CashierSystemApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SalePointRepositoryTest {

    @Autowired
    private SalePointRepository salePointRepository;

    private Date date;

    @BeforeEach
    void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
    }

    @Test
    public void testSaveSalePoint() {

        SalePoint salePoint = SalePoint.builder()
                .name("SalePoint1")
                .address("Address1")
                .creatingTime(new Time(System.currentTimeMillis()))
                .creatingDate(date)
                .deleted(false)
                .build();
        SalePoint savedSalePoint = salePointRepository.save(salePoint);
        Assertions.assertNotNull(savedSalePoint.getId());
    }

    @Test
    public void testFindSalePointById() {
        SalePoint salePoint = SalePoint.builder()
                .name("SalePoint1")
                .address("Address1")
                .creatingTime(new Time(System.currentTimeMillis()))
                .creatingDate(date)
                .deleted(false)
                .build();
        SalePoint savedSalePoint = salePointRepository.save(salePoint);

        SalePoint foundSalePoint = salePointRepository.findById(savedSalePoint.getId()).orElseThrow();
        Assertions.assertNotNull(foundSalePoint);
        Assertions.assertEquals(savedSalePoint.getId(), foundSalePoint.getId());
    }

    @Test
    public void testFindAllSalePoint() {
        SalePoint salePoint1 = SalePoint.builder()
                .name("SalePoint2")
                .address("Address2")
                .creatingTime(new Time(System.currentTimeMillis()))
                .creatingDate(date)
                .deleted(false)
                .build();
        salePointRepository.save(salePoint1);

        SalePoint salePoint2 = SalePoint.builder()
                .name("SalePoint3")
                .address("Address3")
                .creatingTime(new Time(System.currentTimeMillis()))
                .creatingDate(date)
                .deleted(false)
                .build();
        salePointRepository.save(salePoint2);

        List<SalePoint> salePoints = salePointRepository.findAll();
        Assertions.assertEquals(2, salePoints.size());
    }

    @Test
    public void testFindSalePointByAddress() {
        SalePoint salePoint = SalePoint.builder()
                .name("SalePoint2")
                .address("Address2")
                .creatingTime(new Time(System.currentTimeMillis()))
                .creatingDate(date)
                .deleted(false)
                .build();
        salePointRepository.save(salePoint);

        List<SalePoint> foundSalePoints = salePointRepository.findByAddressContainsAndDeleted("Address2",false);
        Assertions.assertEquals(1, foundSalePoints.size());
    }

    @Test
    public void testFindSalePointByName() {
        SalePoint salePoint = SalePoint.builder()
                .name("SalePoint2")
                .address("Address2")
                .creatingTime(new Time(System.currentTimeMillis()))
                .creatingDate(date)
                .deleted(false)
                .build();
        salePointRepository.save(salePoint);

        List<SalePoint> foundSalePoints = salePointRepository.findByNameContainsAndDeleted("SalePoint2",false);
        Assertions.assertEquals(1, foundSalePoints.size());
    }
}