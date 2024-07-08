package com._32bit.project.cashier_system.service.impl;

import com._32bit.project.cashier_system.DAO.OfferRepository;
import com._32bit.project.cashier_system.DAO.ProductRepository;
import com._32bit.project.cashier_system.DAO.TeamMemberRepository;
import com._32bit.project.cashier_system.DTO.MessageResponse;
import com._32bit.project.cashier_system.DTO.ObjectWithMessageResponse;
import com._32bit.project.cashier_system.DTO.product.CreateProductRequest;
import com._32bit.project.cashier_system.DTO.product.ProductInfoResponse;
import com._32bit.project.cashier_system.DTO.product.UpdatePriceRequest;
import com._32bit.project.cashier_system.DTO.product.UpdateQuantityRequest;
import com._32bit.project.cashier_system.domains.Product;
import com._32bit.project.cashier_system.domains.TeamMember;
import com._32bit.project.cashier_system.domains.enums.Category;
import com._32bit.project.cashier_system.domains.enums.Unit;
import com._32bit.project.cashier_system.mapper.ProductMapper;
import com._32bit.project.cashier_system.security.jwt.JwtUtils;
import com._32bit.project.cashier_system.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final static Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    private final OfferRepository offerRepository;

    private final TeamMemberRepository teamMemberRepository;

    private final JwtUtils jwtUtils;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, OfferRepository offerRepository, TeamMemberRepository teamMemberRepository, JwtUtils jwtUtils) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> getAllProducts() {
        List<?> products = productRepository.findAllByDeleted(false);
        if (products.isEmpty()) {
            logger.error("No products found");
            return ResponseEntity.notFound().build();
        }
        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Object product : products) {
            productInfoResponses.add(ProductMapper.toProductInfoResponse((Product) product));
        }
        logger.info("Products found successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Products found successfully"),
                        productInfoResponses
                )
        );

    }

    @Override
    public ResponseEntity<?> getProductByIdAndDeleted(Long id, Boolean deleted) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body("Id is null");
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body("Deleted is null");
        }
        Optional<Product> product = productRepository.findByIdAndDeleted(id, deleted);
        if (product.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        logger.info("Product found successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Product found successfully"),
                        ProductMapper.toProductInfoResponse(product.get())
                )
        );
    }

    @Override
    public ResponseEntity<?> getProductsByNameContainsAndDeleted(String name, Boolean deleted) {
        if (name == null) {
            logger.error("Name is null");
            return ResponseEntity.badRequest().body("Name is null");
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body("Deleted is null");
        }
        List<Product> products = productRepository.findByNameContainsAndDeleted(name, deleted);
        if (products.isEmpty()) {
            logger.error("No products found");
            return ResponseEntity.notFound().build();
        }
        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Product product : products) {
            productInfoResponses.add(ProductMapper.toProductInfoResponse(product));
        }
        logger.info("Products found successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Products found successfully"),
                        productInfoResponses
                )
        );
    }

    @Override
    public ResponseEntity<?> getProductsByBrandContainsAndDeleted(String brand, Boolean deleted) {
        if (brand == null) {
            logger.error("Brand is null");
            return ResponseEntity.badRequest().body("Brand is null");
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body("Deleted is null");
        }
        List<Product> products = productRepository.findByBrandContainsAndDeleted(brand, deleted);
        if (products.isEmpty()) {
            logger.error("No products found");
            return ResponseEntity.notFound().build();
        }
        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Product product : products) {
            productInfoResponses.add(ProductMapper.toProductInfoResponse(product));
        }
        logger.info("Products found successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Products found successfully"),
                        productInfoResponses
                )
        );
    }

    @Override
    public ResponseEntity<?> getProductsByCategoryAndDeleted(String category, Boolean deleted) {
        if (category == null) {
            logger.error("Category is null");
            return ResponseEntity.badRequest().body("Category is null");
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body("Deleted is null");
        }
        if(!Category.contains(category)){
            logger.error("Category is invalid");
            return ResponseEntity.badRequest().body("Category is invalid");
        }

        List<Product> products = productRepository.findByCategoryAndDeleted(category, deleted);
        if (products.isEmpty()) {
            logger.error("No products found");
            return ResponseEntity.notFound().build();
        }
        List<ProductInfoResponse> productInfoResponses = new ArrayList<>();
        for (Product product : products) {
            productInfoResponses.add(ProductMapper.toProductInfoResponse(product));
        }
        logger.info("Products found successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Products found successfully"),
                        productInfoResponses
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> createProduct(CreateProductRequest request, String token) {
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body("Request is null");
        }
        if (token == null) {
            logger.error("Token is null");
            return ResponseEntity.badRequest().body("Token is null");
        }

        if (!Unit.contains(request.getUnit())) {
            logger.error("Unit is invalid");
            return ResponseEntity.badRequest().body("Unit is invalid");
        }

        if (!Category.contains(request.getCategory())) {
            logger.error("Category is invalid");
            return ResponseEntity.badRequest().body("Category is invalid");
        }

        Product product = null;
        try {
            String username = jwtUtils.getUsernameFromJwtToken(token.substring(7));
            Optional<?> teamMemberOptional = teamMemberRepository.findByUsernameAndDeleted(username,false);
            if (teamMemberOptional.isEmpty()) {
                logger.error("Team member not found");
                return ResponseEntity.notFound().build();
            }
            product = ProductMapper.createProductRequestToProductDomain(request);
            product.setInsertedBy((TeamMember) teamMemberOptional.get());
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Product created successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Product created successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProduct(Long id, CreateProductRequest request) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body("Id is null");
        }
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body("Request is null");
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if(haveNullFields(request)){
            logger.error("Request has null fields");
            return ResponseEntity.badRequest().body("Request has null fields");
        }

        if (!Category.contains(request.getCategory())) {
            logger.error("Category is invalid");
            return ResponseEntity.badRequest().body("Category is invalid");
        }

        if (!Unit.contains(request.getUnit())) {
            logger.error("Unit is invalid");
            return ResponseEntity.badRequest().body("Unit is invalid");
        }

        try {
            product.setName(request.getName());
            product.setBrand(request.getBrand());
            product.setCategory(Category.valueOf(request.getCategory().toUpperCase()));
            product.setQuantity(request.getQuantity());
            product.setUnit(Unit.valueOf(request.getUnit().toLowerCase()));
            product.setPrice(request.getPrice());
            productRepository.save(product);
        } catch (IllegalArgumentException e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Product updated successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Product updated successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );
    }

    private boolean haveNullFields(CreateProductRequest request) {
        return request.getName() == null || request.getBrand() == null || request.getCategory() == null || request.getUnit() == null || request.getPrice() == null;
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProduct(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body("Id is null");
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (product.getDeleted()) {
            logger.error("Product is already deleted");
            return ResponseEntity.badRequest().body("Product is already deleted");
        }
        try {
            product.setDeleted(true);
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Product deleted successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Product deleted successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> restoreProduct(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body("Id is null");
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (!product.getDeleted()) {
            logger.error("Product is not deleted");
            return ResponseEntity.badRequest().body("Product is not deleted");
        }
        try {
            product.setDeleted(false);
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Product restored successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Product restored successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );


    }

    @Override
    @Transactional
    public ResponseEntity<?> setDiscount(Long id, Double discountRate) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body("Id is null");
        }
        if (discountRate == null) {
            logger.error("Discount rate is null");
            return ResponseEntity.badRequest().body("Discount rate is null");
        }
        if (discountRate < 0 || discountRate > 100) {
            logger.error("Discount rate is invalid");
            return ResponseEntity.badRequest().body("Discount rate is invalid");
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (product.getIsDiscounted()) {
            logger.error("Product is already discounted");
            return ResponseEntity.badRequest().body("Product is already discounted");
        }
        try {
            product.setIsDiscounted(true);
            product.setDiscountRate(discountRate);
            product.setDiscountedPrice(product.getPrice() - (product.getPrice() * discountRate / 100));
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Discount set successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Discount set successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeDiscount(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body("Id is null");
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (!product.getIsDiscounted()) {
            logger.error("Product is not discounted");
            return ResponseEntity.badRequest().body("Product is not discounted");
        }
        try {
            product.setIsDiscounted(false);
            product.setDiscountRate(0.0);
            product.setDiscountedPrice(0.0);
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Discount removed successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Discount removed successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );
    }


    // Todo: Implement this method after implementing OfferService

    @Override
    public ResponseEntity<?> setOffer(Long productId, Long offerId) {
        return null;
    }

    // Todo: Implement this method after implementing OfferService

    @Override
    public ResponseEntity<?> removeOffer(Long productId) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<?> UpdateQuantities(List<UpdateQuantityRequest> requests) {
        if (requests == null) {
            logger.error("Requests is null");
            return ResponseEntity.badRequest().body("Requests is null");
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body("Requests is empty");
        }
        try {
            for (UpdateQuantityRequest request : requests) {
                if (request.getId() == null || request.getQuantity() == null) {
                    logger.error("Id or quantity is null");
                    return ResponseEntity.badRequest().body("Id or quantity is null");
                }
                Optional<Product> productOptional = productRepository.findById(request.getId());
                if (productOptional.isEmpty()) {
                    logger.error("Product not found");
                    return ResponseEntity.notFound().build();
                }
                Product product = productOptional.get();
                product.setQuantity(product.getQuantity() + request.getQuantity());
                productRepository.save(product);
            }
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Quantities updated successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Quantities updated successfully"),
                        ProductMapper.toProductsInfoResponse(productRepository.findAll())
                )
        );

    }

    @Override
    @Transactional
    public ResponseEntity<?> createProducts(List<CreateProductRequest> requests, String token) {
        if (requests == null) {
            logger.error("Requests is null");
            return ResponseEntity.badRequest().body("Requests is null");
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body("Requests is empty");
        }
        if (token == null) {
            logger.error("Token is null");
            return ResponseEntity.badRequest().body("Token is null");
        }
        String username = jwtUtils.getUsernameFromJwtToken(token.substring(7));
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByUsernameAndDeleted(username,false);
        if (teamMemberOptional.isEmpty()) {
            logger.error("Team member not found");
            return ResponseEntity.notFound().build();
        }
        TeamMember teamMember = teamMemberOptional.get();

        try {
            for (CreateProductRequest request : requests) {
                if (request == null) {
                    logger.error("Request is null");
                    return ResponseEntity.badRequest().body("Request is null");
                }
                if (haveNullFields(request)) {
                    logger.error("Request has null fields");
                    return ResponseEntity.badRequest().body("Request has null fields");
                }
                if (!Unit.contains(request.getUnit())) {
                    logger.error("Unit is invalid");
                    return ResponseEntity.badRequest().body("Unit is invalid");
                }
                if (!Category.contains(request.getCategory())) {
                    logger.error("Category is invalid");
                    return ResponseEntity.badRequest().body("Category is invalid");
                }
                Product product = ProductMapper.createProductRequestToProductDomain(request);
                product.setInsertedBy(teamMember);
                productRepository.save(product);
            }
        } catch (Exception e) {
            logger.error("An error occurred during the create process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the create process");
        }
        logger.info("Products created successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Products created successfully"),
                        ProductMapper.toProductsInfoResponse(productRepository.findAll())
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> UpdatePrices(List<UpdatePriceRequest> requests) {
        if (requests == null) {
            logger.error("Requests is null");
            return ResponseEntity.badRequest().body("Requests is null");
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body("Requests is empty");
        }
        try {
            for (UpdatePriceRequest request : requests) {
                if (request.getId() == null || request.getPrice() == null) {
                    logger.error("Id or price is null");
                    return ResponseEntity.badRequest().body("Id or price is null");
                }
                Optional<Product> productOptional = productRepository.findByIdAndDeleted(request.getId(),false);
                if (productOptional.isEmpty()) {
                    logger.error("Product not found");
                    return ResponseEntity.notFound().build();
                }
                Product product = productOptional.get();
                product.setPrice(request.getPrice());
                productRepository.save(product);
            }
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Prices updated successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Prices updated successfully"),
                        ProductMapper.toProductsInfoResponse(productRepository.findAll())
                )
        );
    }

    @Override
    public Double getPriceById(Long id) {
        if (id == null) {
            logger.error("Id is null");
            return null;
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return null;
        }

        if (productOptional.get().getIsDiscounted()) {
            return productOptional.get().getDiscountedPrice();

        } else {
            return productOptional.get().getPrice();
        }
    }
}
