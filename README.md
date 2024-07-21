# cashier_system

This project is a web sale point for managing sales and products.

This project is a RESTful API built with Spring Boot, providing a robust backend for.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Main User Credential](#Main-User-Credential)
- [Project Structure](#project-structure)
- [Endpoints](#endpoints)
- [Request Bodies](#request-bodies)
- [Author](#author)

## Features


- **Authentication and Authorization**
    * Secure token-based system for user authentication and authorization

- **Sale Point Management**
    * Create and manage sale points
    * Add and remove sale point members
    * Open, close, and manage sessions for each sale point

- **Product Management**
    * Maintain a shared catalog of products across all sale points
    * Full CRUD (Create, Read, Update, Delete) operations for products
    * Set discounts and offers on products
    * Implement soft deletion for all objects, including products

- **Sales and Invoicing**
    * Record sales with relation to specific sale points and sessions
    * Generate invoices for completed sales
    * Export invoices as PDF files for printing or digital distribution

- **Data Integrity**
    * Maintain relationships between sales, sale points, and sessions
    * Ensure data consistency across all operations


## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.9.6 or higher
- Postegresql

### Installation

1. Clone the repository
```cmd
git clone
https://github.com/IsmailKattan/chashier_system.git
```


2. Navigate to the project directory
```cmd
cd chashier_system 
```

3. Build the project
```cmd
mvn clean install
```cmd

4. Run the application
```cmd
mvn spring-boot:run
```

The application will start running at http://localhost:8080.

## Main User Credential

```JSON
{
    "username" : "admin",
    "password" : "32-bit"
}
```

## Project Structure
```
.
├── .mvn/
│   └── wrapper/
│       ├── maven-wrapper.jar
│       └── maven-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com._32bit.porject.cashier_system/
│   │   │       ├── DAO/
│   │   │       │   ├── InvoiceRepository
│   │   │       │   ├── OfferRepository
│   │   │       │   ├── PaymentRepository
│   │   │       │   ├── ProductRepository
│   │   │       │   ├── RoleRepository
│   │   │       │   ├── SaleItemRepository
│   │   │       │   ├── SalePointRepository
│   │   │       │   ├── SaleRepository
│   │   │       │   ├── SessionRepository
│   │   │       │   └── TeamMemberRepository
│   │   │       ├── domains/
│   │   │       │   ├── enums/
│   │   │       │   │   ├── Category
│   │   │       │   │   ├── ERole
│   │   │       │   │   ├── PaymentMethod
│   │   │       │   │   └── Unit
│   │   │       │   ├── Invoice
│   │   │       │   ├── Offer
│   │   │       │   ├── Payment
│   │   │       │   ├── Product
│   │   │       │   ├── Role
│   │   │       │   ├── Sale
│   │   │       │   ├── SaleItem
│   │   │       │   ├── SalePoint
│   │   │       │   ├── Session
│   │   │       │   └── TeamMember
│   │   │       ├── DTO/
│   │   │       │   ├── invoice/
│   │   │       │   │   ├── InvoceEmailRequest
│   │   │       │   │   └── InvoceInfoDto
│   │   │       │   ├── offer/
│   │   │       │   │   ├── CreateOfferRequest
│   │   │       │   │   └── OfferInfoResponse
│   │   │       │   ├── payment/
│   │   │       │   │   └── PaymentOfSaleDto
│   │   │       │   ├── product/
│   │   │       │   │   ├── CreateProductRequest
│   │   │       │   │   ├── ProductInfoResponse
│   │   │       │   │   ├── UpdatePriceRequest
│   │   │       │   │   └── UpdateQuantityrequest
│   │   │       │   ├── sale/
│   │   │       │   │   ├── CreateSaleRequest
│   │   │       │   │   ├── SaleInfoResponse
│   │   │       │   │   └── SaleSpecification
│   │   │       │   ├── saleItem/
│   │   │       │   │   ├── ItemOfSaleDto
│   │   │       │   │   ├── SaleItemInfoResponseDto
│   │   │       │   │   └── SaleItemInforesponseSerializer
│   │   │       │   ├── salePoint/
│   │   │       │   │   ├── CreateSalePointRequest
│   │   │       │   │   ├── SalePointInfoResponse
│   │   │       │   │   ├── SessionOfSalePoint
│   │   │       │   │   └── TeamMemberOfSalePoint
│   │   │       │   ├── session/
│   │   │       │   │   ├── SaleOfSession
│   │   │       │   │   └── SessionInforesponse
│   │   │       │   ├── teamMember/
│   │   │       │   │   ├── request/
│   │   │       │   │   │   ├── CreateTeamMemberDto
│   │   │       │   │   │   ├── LoginInfoDto
│   │   │       │   │   │   └── UpdatePasswordDto
│   │   │       │   │   └── response/
│   │   │       │   │       ├── JwtResponseDto
│   │   │       │   │       └── TeamMemberInfoDto
│   │   │       │   ├── MessageResponse
│   │   │       │   └── ObjectWithMessageResponse
│   │   │       ├── mapper/
│   │   │       │   ├── InvoiceMapper
│   │   │       │   ├── OfferMapper
│   │   │       │   ├── PaymentMapper
│   │   │       │   ├── ProductMapper
│   │   │       │   ├── SaleItemMapper
│   │   │       │   ├── SaleMapper
│   │   │       │   ├── SalePointMapper
│   │   │       │   ├── SessionMapper
│   │   │       │   └── TeamMemberMapper
│   │   │       ├── pdf/
│   │   │       │   └── PDFGenerator
│   │   │       ├── resource/
│   │   │       │   ├── AuthenticationResource
│   │   │       │   ├── HelloWorldResource
│   │   │       │   ├── InvoiceResource
│   │   │       │   ├── OfferResource
│   │   │       │   ├── ProductResource
│   │   │       │   ├── SalePointResource
│   │   │       │   ├── SaleResource
│   │   │       │   ├── SessionResource
│   │   │       │   └── TeamMemberResource
│   │   │       ├── security/
│   │   │       │   ├── jwt/
│   │   │       │   │   ├── AuthEntryPointJwt
│   │   │       │   │   ├── AuthTokenFilter
│   │   │       │   │   └── JwtUtils
│   │   │       │   ├── service/
│   │   │       │   │   ├── UserDetailsImpl
│   │   │       │   │   └── UserDetailsServiceImpl
│   │   │       │   └── SecurityConfig
│   │   │       ├── service/
│   │   │       │   ├── impl/
│   │   │       │   │   ├── AuthServiceImpl
│   │   │       │   │   ├── InvoiceServiceImpl
│   │   │       │   │   ├── OfferServiceImpl
│   │   │       │   │   ├── PaymentServiceImpl
│   │   │       │   │   ├── ProductServiceImpl
│   │   │       │   │   ├── SaleItemServiceImpl
│   │   │       │   │   ├── SalePointServiceImpl
│   │   │       │   │   ├── SaleServiceImpl
│   │   │       │   │   ├── SessionServiceImpl
│   │   │       │   │   └── TeamMemberServiceImpl
│   │   │       │   ├── AuthService
│   │   │       │   ├── InvoiceService
│   │   │       │   ├── OfferService
│   │   │       │   ├── PaymentService
│   │   │       │   ├── ProductService
│   │   │       │   ├── SaleItemService
│   │   │       │   ├── SalePointService
│   │   │       │   ├── SaleService
│   │   │       │   ├── SessionService
│   │   │       │   └── TeamMemberService
│   │   │       └── CashierSystemApplication
│   │   └── resources/
│   │       ├── application.properties
│   │       └── log4j2.xml
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── _32bit.project.cashier_system/
│       │           ├── tests/
│       │           │   ├── sale
│       │           │   ├── OfferServiceTest
│       │           │   ├── ProductServiceTest
│       │           │   ├── SalePointServiceTest
│       │           │   └── SessionServiceTest
│       │           └── CashierSystemApplicationTests
│       └── resources/
│           └── application-test.properties
├── .gitignore
├── compose.yaml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md

```

## Endpoints


### Test Security Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | /api/admin | only admin allowed endpoint (for testing security)| - | 
| GET | /api/manager | only manager allowed endpoint (for testing security) | - |
| GET | /api/cashier | only cashier allowed endpoint (for testing security) | - |
| GET | /api/erisebilir | permit all endpoint (for testing security) | - |
| GET | /api/erisilmez | for authenticated users (for testing security) | - |

### Login Endpoint
| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | /login | Endpoint for loging in | [Login Body](#Login-Body) |


### Sale Point Endpoints

| Method | Endpoint                                   | Description | Request Body                                                     |
|--------|--------------------------------------------|-------------|------------------------------------------------------------------|
| POST   | /api/sale-point/create-point               | Endpoint for creating sale point | [Create sale point request body](#Create-Sale-Point-Reques-Body) |
| GET    | /api/sale-point/point/{id}                 | Get sale point by id | -                                                                | 
| PUT    | /api/sale-point/update-point               | Endpoint for updating sale point | [Create sale point request body](#Create-Sale-Point-Reques-Body) |
| DELETE | /api/sale-point/delete-point/{id}          | Delete sale point | -                                                                |
| PATCH  | /api/sale-point/restore-point/{id}         | Restore sale point | -                                                                |
| GET    | /api/sale-point/all-points                 | Get all sale points | -                                                                |
| GET    | /api/sale-point/all-deleted-points         | Get all deleted sale points | -                                                                |
| GET    | /api/sale-point/deleted-point/{id}         | Get deleted sale point by id | -                                                                |
| GET    | /api/sale-point/point-by-address/{address} | Get sale point by address | -                                                                |
| GET    | /api/sale-point/point-by-name/{name}       | Get sale point by name | -                                                                |
| GET    | /api/sale-point/poin-sessins/{id}          | Get sale point sessions | -                                                                |
| GET    | /api/sale-point/point-team-members/{id}    | Get sale point members | -                                                                |


### Team Member Endpoints


| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | /api/team-member/register | Register a new team member | [Create Team Member request body](#create-team-member-request-body) |
| GET | /api/team-member/id/{id} | Get a team member by ID | - |
| GET | /api/team-member/username/{username} | Get a team member by username | - |
| GET | /api/team-member/deleted/id/{id} | Get a deleted team member by ID | - |
| GET | /api/team-member/deleted/username/{username} | Get a deleted team member by username | - |
| GET | /api/team-member/all | Get all non-deleted team members | - |
| GET | /api/team-member/all-deleted | Get all deleted team members | - |
| PUT | /api/team-member/update/{id} | Update a team member | [Create Team Member request body](#create-team-member-request-body) |
| DELETE | /api/team-member/delete/{id} | Delete a team member | - |
| PUT | /api/team-member/restore/{id} | Restore a deleted team member | - |
| PUT | /api/team-member/update-authority/{id} | Update a team member's authority | List of strings |
| PUT | /api/team-member/update-password/{id} | Update a team member's password | [Update Password request body](#update-password-request-body) |
| PUT | /api/team-member/update-sale-point/{id}/{salePointId} | Update a team member's sale point | - |

### Session Endpoints


| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | /api/session/sessions | Get all sessions | - |
| GET | /api/session/deleted-sessions | Get all deleted sessions | - |
| GET | /api/session/sale-point-sessions/{id} | Get sessions for a specific sale point | - |
| POST | /api/session/open-session/{salePointId} | Open a new session for a sale point | - |
| POST | /api/session/close-session/{salePointId} | Close the current session for a sale point | - |
| GET | /api/session/{sessionId} | Get information about a specific session | - |
| GET | /api/session/delete-session/{sessionId} | Delete a specific session | - |
| GET | /api/session/restore-session/{sessionId} | Restore a deleted session | - |
| GET | /api/session/sale-point-open-session/{id} | Get the open session for a specific sale point | - |


### Offer Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | /api/offer/all | Get all non-deleted offers | - |
| GET | /api/offer/all-deleted | Get all deleted offers | - |
| GET | /api/offer/by-name/{name} | Get non-deleted offers by name | - |
| GET | /api/offer/deleted-by-name/{name} | Get deleted offers by name | - |
| GET | /api/offer/by-description/{description} | Get non-deleted offers by description | - |
| GET | /api/offer/deleted-by-description/{description} | Get deleted offers by description | - |
| POST | /api/offer/by-start-date | Get non-deleted offers by date range | Query params: startDate, endDate |
| POST | /api/offer/deleted-by-start-date | Get deleted offers by date range | Query params: startDate, endDate |
| POST | /api/offer/create-offer | Create a new offer | [Create Offer request body](#create-offer-request-body) |
| PUT | /api/offer/update-offer/{id} | Update an existing offer | [Create Offer request body](#create-offer-request-body) |
| DELETE | /api/offer/delete-offer/{id} | Delete an offer | - |
| PATCH | /api/offer/restore-offer/{id} | Restore a deleted offer | - |
| GET | /api/offer/by-id/{id} | Get a non-deleted offer by ID | - |
| GET | /api/offer/deleted-by-id/{id} | Get a deleted offer by ID | - |

### Product Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | /api/product/all | Get all non-deleted products | - |
| GET | /api/product/all-deleted | Get all deleted products | - |
| GET | /api/product/by-name/{name} | Get non-deleted products by name | - |
| GET | /api/product/by-brand/{brand} | Get non-deleted products by brand | - |
| GET | /api/product/by-category/{category} | Get non-deleted products by category | - |
| GET | /api/product/deleted-by-name/{name} | Get deleted products by name | - |
| GET | /api/product/deleted-by-brand/{brand} | Get deleted products by brand | - |
| GET | /api/product/deleted-by-category/{category} | Get deleted products by category | - |
| GET | /api/product/by-id/{id} | Get a non-deleted product by ID | - |
| GET | /api/product/deleted-by-id/{id} | Get a deleted product by ID | - |
| POST | /api/product/create | Create a new product | [Create Product request body](#create-product-request-body) |
| PUT | /api/product/update/{id} | Update an existing product | [Create Product request body](#create-product-request-body) |
| DELETE | /api/product/delete/{id} | Delete a product | - |
| POST | /api/product/restore/{id} | Restore a deleted product | - |
| POST | /api/product/set-discount/{id}/{discountRate} | Set a discount for a product | - |
| DELETE | /api/product/remove-discount/{id} | Remove a discount from a product | - |
| POST | /api/product/set-offer/{productId}/{offerId} | Set an offer for a product | - |
| DELETE | /api/product/remove-offer/{productId} | Remove an offer from a product | - |
| POST | /api/product/update-quantities | Update quantities for multiple products | [List of Update Quantity requests](#update-quantity-request-body) |
| POST | /api/product/update-add-quantities | Add quantities for multiple products | [List of Update Quantity requests](#update-quantity-request-body) |
| POST | /api/product/create-products | Create multiple products | [List of Create Products request](#create-products-request-body) |
| POST | /api/product/update-prices | Update prices for multiple products | [List of Update Prices request](#update-prices-request-body) |
| GET | /api/product/price/{id} | Get the price of a product by ID | - |


### Sale Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | /api/sale/sales | Get all non-deleted sales | - |
| GET | /api/sale/deleted-sales | Get all deleted sales | - |
| GET | /api/sale/sale/{id} | Get a non-deleted sale by ID | - |
| GET | /api/sale/deleted-sale/{id} | Get a deleted sale by ID | - |
| GET | /api/sale/sale-point-sales/{id} | Get sales for a specific sale point | - |
| GET | /api/sale/session-sales/{id} | Get sales for a specific session | - |
| POST | /api/sale/create-sale | Create a new sale | [Create Sale request body](#create-sale-request-body) |
| POST | /api/sale/payment/{saleId} | Process payment for a sale | [List of Payment of Sale DTOs](#payment-of-sale-dto) |
| POST | /api/sale/invoice/{saleId} | Generate invoice for a sale | - |
| PUT | /api/sale/update-sale/{id} | Update an existing sale | [Create Sale request body](#create-sale-request-body) |
| DELETE | /api/sale/delete-sale/{id} | Delete a sale | - |
| PUT | /api/sale/restore-sale/{id} | Restore a deleted sale | - |
| GET | /api/sale/paged-sales | Get paginated sales with filters | Query parameters |
| GET | /api/sale/paged-session-sales/{sessionId} | Get paginated sales for a session with filters | Query parameters |
| GET | /api/sale/paged-sale-point-sales/{salePointId} | Get paginated sales for a sale point with filters | Query parameters |



### Invoice Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| GET | /api/invoice/invoices | Get all invoices | - |
| GET | /api/invoice/invoice/{id} | Get an invoice by ID | - |
| GET | /api/invoice/sale-invoice/{saleId} | Get an invoice by sale ID | - |
| GET | /api/invoice/sale-point-invoices/{salePointId} | Get invoices for a specific sale point | - |
| GET | /api/invoice/team-member-invoices/{teamMemberId} | Get invoices for a specific team member | - |
| GET | /api/invoice/generate-invoice/{invoiceId} | Generate an invoice PDF | - |



## Request Bodies

### Login Body
```JSON
{
    "username" : "admin",
    "password" : "32-bit"
}
```

### Create Sale Point Reques Body

```JSON
{
    "name" : "istanbul",
    "address" : "fatih"
}
```

### Create Team Member Request Body

```JSON
{
    "firstname" : "ismail",
    "lastname" : "kattan",
    "username" : "smlkttn_cashier",
    "email" : "smlkttn855@gmail.com",
    "phoneNumber" : "05314326135",
    "password" : "32-bit",
    "roles" : ["cashier"],
    "salePointId" : 2
}
```

### Update Password Request Body
```JSON
{
    "password" : "32-bit"
}
```

### Create Offer Request Body
```JSON
{
    "name" : "3 al 2 ode",
    "description" : "2 alana 1 bedava",
    "startDate" : "01-01-2025",
    "endDate" : "01-01-2026",
    "getCount" : 3,
    "payFor" : 2
}
```

### Create Product Request Body
```JSON
{
    "name" : "telefon",
    "brand"  : "samsung",
    "category" : "ELeCTRONIC",
    "quantity" : "100",
    "unit" : "adet",
    "price" : 4000
}
```

# Create Products Request Body
```JSON
[
    {
        "name" : "telefon",
        "brand"  : "samsung",
        "category" : "ELeCTRONIC",
        "quantity" : "100",
        "unit" : "adet",
        "price" : 4000
    },
    {
        "name" : "kulaklik",
        "brand"  : "polo-smart",
        "category" : "ELECTRONIC",
        "quantity" : "1000",
        "unit" : "adet",
        "price" : 25.0
    },
    ...
]
```

### Update Quantity Request Body
```JSON
[
    {
        "id" : 1,
        "quantity" : 15
    },
    {
        "id" : 2,
        "quantity" : 15
    },
    ...
]
```

### Update Prices Request Body
```JSON
[
    {
        "id" : 1,
        "price" : 15
    },
    {
        "id" : 2,
        "price" : 15
    },
    ...
]
```

### Create Sale Request Body
```JSON
{
    "salePointId" : 1,
    "items" : [
        {
            "productId" : 1,
            "quantity" : 5
        },
        {
            "productId" : 2,
            "quantity" : 5
        }
    ]
}
```


## Author

Ismail KATTAN

- Github : https://github.com/IsmailKattan

- Email  : ismailkattan.contact@gmail.com 
