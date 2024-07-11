package com._32bit.project.cashier_system.tests.sale;


import com._32bit.project.cashier_system.DAO.SaleRepository;
import com._32bit.project.cashier_system.domains.Sale;
import com._32bit.project.cashier_system.domains.SalePoint;
import com._32bit.project.cashier_system.domains.Session;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class SaleRepositoryTest {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testFindSalesBySalePointId() {
        // Setup
        SalePoint salePoint = new SalePoint();
        entityManager.persist(salePoint);

        Session session = new Session();
        session.setSalePoint(salePoint);
        entityManager.persist(session);

        Sale sale1 = new Sale();
        sale1.setSession(session);
        entityManager.persist(sale1);

        Sale sale2 = new Sale();
        sale2.setSession(session);
        entityManager.persist(sale2);

        entityManager.flush();

        // Execute
        List<Sale> foundSales = saleRepository.findSalesBySalePointId(salePoint.getId());

        // Assert
        assertThat(foundSales).hasSize(2);
        assertThat(foundSales).containsExactlyInAnyOrder(sale1, sale2);
    }

    @Test
    public void testFindSalePointBySaleId() {
        // Setup
        SalePoint salePoint = new SalePoint();
        entityManager.persist(salePoint);

        Session session = new Session();
        session.setSalePoint(salePoint);
        entityManager.persist(session);

        Sale sale = new Sale();
        sale.setSession(session);
        entityManager.persist(sale);

        entityManager.flush();

        // Execute
        Optional<SalePoint> foundSalePoint = saleRepository.findSalePointBySaleId(sale.getId());

        // Assert
        assertThat(foundSalePoint).isPresent();
        assertThat(foundSalePoint.get()).isEqualTo(salePoint);
    }
}
