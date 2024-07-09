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
import com._32bit.project.cashier_system.domains.Offer;
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
    public ResponseEntity<?> getProductsByDeleted(Boolean deleted) {
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted is null"));
        }
        List<Product> products = productRepository.findAllByDeleted(deleted);
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
    public ResponseEntity<?> getProductByIdAndDeleted(Long id, Boolean deleted) {
        if (id == null) {
            logger.error("Id is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Id is null"));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted is null"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Name is null"));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted is null"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Brand is null"));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted is null"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Category is null"));
        }
        if (deleted == null) {
            logger.error("Deleted is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Deleted is null"));
        }
        if(!Category.contains(category)){
            logger.error("Category is invalid");
            return ResponseEntity.badRequest().body(new MessageResponse("Category is invalid"));
        }
        Category categoryEnum = Category.valueOf(category.toUpperCase());

        List<Product> products = productRepository.findByCategoryAndDeleted(categoryEnum, deleted);
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
            return ResponseEntity.badRequest().body(new MessageResponse("Request is null"));
        }
        if (token == null) {
            logger.error("Token is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Token is null"));
        }

        if (!Unit.contains(request.getUnit())) {
            logger.error("Unit is invalid");
            return ResponseEntity.badRequest().body(new MessageResponse("Unit is invalid"));
        }

        if (!Category.contains(request.getCategory())) {
            logger.error("Category is invalid");
            return ResponseEntity.badRequest().body(new MessageResponse("Category is invalid"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Id is null"));
        }
        if (request == null) {
            logger.error("Request is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Request is null"));
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if(haveNullFields(request)){
            logger.error("Request has null fields");
            return ResponseEntity.badRequest().body(new MessageResponse("Request has null fields"));
        }

        if (!Category.contains(request.getCategory())) {
            logger.error("Category is invalid");
            return ResponseEntity.badRequest().body(new MessageResponse("Category is invalid"));
        }

        if (!Unit.contains(request.getUnit())) {
            logger.error("Unit is invalid");
            return ResponseEntity.badRequest().body(new MessageResponse("Unit is invalid"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Id is null"));
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (product.getDeleted()) {
            logger.error("Product is already deleted");
            return ResponseEntity.badRequest().body(new MessageResponse("Product is already deleted"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Id is null"));
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (!product.getDeleted()) {
            logger.error("Product is not deleted");
            return ResponseEntity.badRequest().body(new MessageResponse("Product is not deleted"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Id is null"));
        }
        if (discountRate == null) {
            logger.error("Discount rate is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Discount rate is null"));
        }
        if (discountRate < 0 || discountRate > 100) {
            logger.error("Discount rate is invalid");
            return ResponseEntity.badRequest().body(new MessageResponse("Discount rate is invalid"));
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (product.getIsDiscounted()) {
            logger.error("Product is already discounted");
            return ResponseEntity.badRequest().body(new MessageResponse("Product is already discounted"));
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
            return ResponseEntity.badRequest().body(new MessageResponse("Id is null"));
        }
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (!product.getIsDiscounted()) {
            logger.error("Product is not discounted");
            return ResponseEntity.badRequest().body(new MessageResponse("Product is not discounted"));
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

    @Override
    @Transactional
    public ResponseEntity<?> setOffer(Long productId, Long offerId) {
        if (productId == null || offerId == null) {
            logger.error("ProductId or offerId is null");
            return ResponseEntity.badRequest().body(new MessageResponse("ProductId or offerId is null"));
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isEmpty()) {
            logger.error("Offer not found");
            return ResponseEntity.notFound().build();
        }
        try {
            product.setOffer(offerOptional.get());
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Offer set successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer set successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeOffer(Long productId) {
        if (productId == null) {
            logger.error("ProductId is null");
            return ResponseEntity.badRequest().body(new MessageResponse("ProductId is null"));
        }
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            logger.error("Product not found");
            return ResponseEntity.notFound().build();
        }
        Product product = productOptional.get();
        if (product.getOffer() == null) {
            logger.error("Product does not have an offer");
            return ResponseEntity.badRequest().body(new MessageResponse("Product does not have an offer"));
        }
        try {
            product.setOffer(null);
            productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred during the update process");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during the update process");
        }
        logger.info("Offer removed successfully");
        return ResponseEntity.ok().body(
                new ObjectWithMessageResponse(
                        new MessageResponse("Offer removed successfully"),
                        ProductMapper.toProductInfoResponse(product)
                )
        );

    }

    @Override
    @Transactional
    public ResponseEntity<?> UpdateQuantities(List<UpdateQuantityRequest> requests) {
        if (requests == null) {
            logger.error("Requests is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is null"));
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is empty"));
        }
        List<Product> products = new ArrayList<>();
        for (UpdateQuantityRequest request : requests) {
            if (request.getId() == null || request.getQuantity() == null) {
                logger.error("Id or quantity is null");
                return ResponseEntity.badRequest().body(new MessageResponse("Id or quantity is null"));
            }
            Optional<Product> productOptional = productRepository.findByIdAndDeleted(request.getId(),false);
            if (productOptional.isEmpty()) {
                logger.error("Product not found for ID: " + request.getId());
                return ResponseEntity.notFound().build();
            }
            Product product = productOptional.get();
            product.setQuantity(request.getQuantity());
            products.add(product);
        }
        try {
            productRepository.saveAll(products);
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
    public ResponseEntity<?> UpdateAddQuantities(List<UpdateQuantityRequest> requests) {
        if (requests == null) {
            logger.error("Requests is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is null"));
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is empty"));
        }
        List<Product> products = new ArrayList<>();
        for (UpdateQuantityRequest request : requests) {
            if (request.getId() == null || request.getQuantity() == null) {
                logger.error("Id or quantity is null");
                return ResponseEntity.badRequest().body(new MessageResponse("Id or quantity is null"));
            }
            Optional<Product> productOptional = productRepository.findByIdAndDeleted(request.getId(),false);
            if (productOptional.isEmpty()) {
                logger.error("Product not found for ID: " + request.getId());
                return ResponseEntity.notFound().build();
            }
            Product product = productOptional.get();
            product.setQuantity(request.getQuantity() + product.getQuantity());
            products.add(product);
        }
        try {
            productRepository.saveAll(products);
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
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is null"));
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is empty"));
        }
        if (token == null) {
            logger.error("Token is null");
            return ResponseEntity.badRequest().body(new MessageResponse("Token is null"));
        }
        String username = jwtUtils.getUsernameFromJwtToken(token.substring(7));
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findByUsernameAndDeleted(username,false);
        if (teamMemberOptional.isEmpty()) {
            logger.error("Team member not found");
            return ResponseEntity.notFound().build();
        }
        TeamMember teamMember = teamMemberOptional.get();
        try {
            List<Product> products = new ArrayList<>();
            for (CreateProductRequest request : requests) {
                if (request == null) {
                    logger.error("Request is null");
                    return ResponseEntity.badRequest().body(new MessageResponse("Request is null"));
                }
                if (haveNullFields(request)) {
                    logger.error("Request has null fields");
                    return ResponseEntity.badRequest().body(new MessageResponse("Request has null fields"));
                }
                if (!Unit.contains(request.getUnit())) {
                    logger.error("Unit is invalid");
                    return ResponseEntity.badRequest().body(new MessageResponse("Unit is invalid"));
                }
                if (!Category.contains(request.getCategory())) {
                    logger.error("Category is invalid");
                    return ResponseEntity.badRequest().body(new MessageResponse("Category is invalid"));
                }
                Product product = ProductMapper.createProductRequestToProductDomain(request);
                product.setInsertedBy(teamMember);
                products.add(product);
            }
            productRepository.saveAll(products);
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
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is null"));
        }
        if (requests.isEmpty()) {
            logger.error("Requests is empty");
            return ResponseEntity.badRequest().body(new MessageResponse("Requests is empty"));
        }

        try {
            // First validate all requests
            for (UpdatePriceRequest request : requests) {
                if (request.getId() == null || request.getPrice() == null) {
                    logger.error("Id or price is null");
                    return ResponseEntity.badRequest().body(new MessageResponse("Id or price is null"));
                }
                Optional<Product> productOptional = productRepository.findByIdAndDeleted(request.getId(), false);
                if (productOptional.isEmpty()) {
                    logger.error("Product not found for id: " + request.getId());
                    return ResponseEntity.notFound().build();
                }
            }

            // If validation passes, proceed to update prices
            List<Product> products = new ArrayList<>();
            for (UpdatePriceRequest request : requests) {
                Product product = productRepository.findByIdAndDeleted(request.getId(), false).get();
                product.setPrice(request.getPrice());
                products.add(product);
            }
            productRepository.saveAll(products);

        } catch (Exception e) {
            logger.error("An error occurred during the update process", e);
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
