# Architecture Documentation

## System Overview

Ellison Electronics is a **three-tier web application** built with Java, following the **MVC (Model-View-Controller)** pattern.

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
│  (JSP Pages, HTML, CSS, JavaScript, Bootstrap)              │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    BUSINESS LOGIC LAYER                      │
│  (Servlets, Service Classes, Business Rules)                │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                    DATA ACCESS LAYER                         │
│  (Database Utilities, JDBC, MySQL)                          │
└─────────────────────────────────────────────────────────────┘
```

---

## Layer Descriptions

### 1. Presentation Layer (View)
**Location:** `WebContent/`

Responsible for user interface and interaction:
- **JSP Pages** - Dynamic web pages with embedded Java
- **CSS** - Bootstrap framework for responsive design
- **JavaScript** - Client-side interactivity
- **Images** - Product images and assets

**Key Files:**
- `index.jsp` - Home page with product listing
- `cartDetails.jsp` - Shopping cart view
- `adminHome.jsp` - Admin dashboard
- `header.jsp` - Navigation component
- `footer.html` - Footer component

---

### 2. Business Logic Layer (Controller)
**Location:** `src/com/shashi/`

Handles application logic and request processing:

#### Servlets (`srv/` package)
Controllers that handle HTTP requests:
- `LoginSrv` - User authentication
- `RegisterSrv` - User registration
- `AddtoCart` - Add items to cart
- `OrderServlet` - Order processing
- `ShipmentServlet` - Shipment management
- `AddProductSrv` - Product creation (Admin)
- `UpdateProductSrv` - Product updates (Admin)
- `RemoveProductSrv` - Product deletion (Admin)

#### Services (`service/` package)
Business logic interfaces and implementations:

**Interfaces:**
- `UserService` - User operations
- `ProductService` - Product operations
- `CartService` - Cart operations
- `OrderService` - Order operations
- `TransService` - Transaction operations
- `DemandService` - Demand tracking

**Implementations** (`service/impl/`):
- `UserServiceImpl` - User business logic
- `ProductServiceImpl` - Product business logic
- `CartServiceImpl` - Cart business logic
- `OrderServiceImpl` - Order business logic
- `TransServiceImpl` - Transaction business logic
- `DemandServiceImpl` - Demand business logic

#### Beans (`beans/` package)
Data models representing domain objects:
- `UserBean` - User information
- `ProductBean` - Product details
- `CartBean` - Cart items
- `OrderBean` - Order information
- `OrderDetails` - Order line items
- `TransactionBean` - Payment transactions
- `DemandBean` - Product demand tracking

---

### 3. Data Access Layer (Model)
**Location:** `src/com/shashi/utility/`

Manages database connections and operations:

#### Core Utilities
- `DBUtil` - Database connection management
  - `provideConnection()` - Get database connection
  - `closeConnection()` - Close resources
  - Uses ResourceBundle for configuration

- `IDUtil` - ID generation utilities
- `JavaMailUtil` - Email sending
- `MailMessage` - Email message formatting

#### Configuration
- `src/application.properties` - Database and email credentials

---

## Data Flow

### User Registration Flow
```
User Input (JSP)
    ↓
RegisterSrv (Servlet)
    ↓
UserServiceImpl (Business Logic)
    ↓
DBUtil + SQL (Database)
    ↓
UserBean (Response)
    ↓
JSP (Display Result)
```

### Shopping Flow
```
Browse Products (index.jsp)
    ↓
ProductServiceImpl.getAllProducts()
    ↓
Database Query
    ↓
ProductBean List
    ↓
Display in JSP
    ↓
Add to Cart (AddtoCart Servlet)
    ↓
CartServiceImpl.addToCart()
    ↓
Store in Session/Database
    ↓
cartDetails.jsp (View Cart)
```

### Order Processing Flow
```
Checkout (cartDetails.jsp)
    ↓
OrderServlet
    ↓
OrderServiceImpl.createOrder()
    ↓
TransServiceImpl.processTransaction()
    ↓
Database Insert
    ↓
JavaMailUtil.sendConfirmation()
    ↓
Confirmation Page
```

---

## Database Schema

### Core Tables

**users**
```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    user_type ENUM('customer', 'admin'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**products**
```sql
CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    product_type VARCHAR(50),
    product_price DECIMAL(10, 2),
    product_quantity INT,
    product_description TEXT,
    product_image LONGBLOB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**cart**
```sql
CREATE TABLE cart (
    cart_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

**orders**
```sql
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2),
    status VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

**order_details**
```sql
CREATE TABLE order_details (
    detail_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT,
    price DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

---

## Key Design Patterns

### 1. Service Layer Pattern
- Separates business logic from presentation
- Enables code reuse and testing
- Interfaces define contracts

### 2. DAO (Data Access Object) Pattern
- Abstracts database operations
- Centralizes SQL queries
- Simplifies maintenance

### 3. Session Management
- User credentials stored in session
- Session attributes: `username`, `password`, `usertype`
- Validated on each request

### 4. Resource Bundle Pattern
- Configuration externalized in `application.properties`
- Loaded via `ResourceBundle.getBundle()`
- Easy environment switching

---

## Security Architecture

### Authentication
- Username/password validation
- Session-based authentication
- User type differentiation (customer/admin)

### Authorization
- Session validation on protected pages
- User type checks for admin operations
- Role-based access control

### Data Protection
- Password encryption using Apache Commons Codec
- PreparedStatements for SQL injection prevention
- Input validation in servlets

---

## Deployment Architecture

```
┌──────────────────────────────────────┐
│      Apache Tomcat Server            │
│  ┌────────────────────────────────┐  │
│  │  shopping-cart.war             │  │
│  │  ├── WEB-INF/                  │  │
│  │  │   ├── web.xml               │  │
│  │  │   └── lib/ (JARs)           │  │
│  │  ├── JSP Pages                 │  │
│  │  ├── CSS/JS/Images             │  │
│  │  └── Classes (Compiled)        │  │
│  └────────────────────────────────┘  │
└──────────────────────────────────────┘
            ↓
┌──────────────────────────────────────┐
│      MySQL Database Server           │
│  └── shopping-cart (Database)        │
└──────────────────────────────────────┘
```

---

## Configuration Management

### application.properties
```properties
# Database
db.driverName=com.mysql.cj.jdbc.Driver
db.connectionString=jdbc:mysql://localhost:3306/shopping-cart
db.username=root
db.password=

# Email
mailer.email=your-email@gmail.com
mailer.password=your-app-password
```

### web.xml
Servlet mappings and configuration:
- Servlet definitions
- URL patterns
- Welcome files
- Error pages

---

## Scalability Considerations

### Current Limitations
- Single database connection (not pooled)
- Session stored in memory (not distributed)
- No caching layer
- Synchronous email sending

### Future Improvements
- Implement connection pooling (HikariCP)
- Distributed session management (Redis)
- Caching layer (Memcached/Redis)
- Asynchronous email (Message Queue)
- Microservices architecture
- API-first design with Spring Boot

---

## Performance Optimization

### Current Optimizations
- PreparedStatements (prevent SQL injection)
- Connection reuse in DBUtil
- Bootstrap CSS framework (CDN)

### Recommended Optimizations
- Database query optimization and indexing
- Lazy loading for product images
- Pagination for product listings
- Response compression (gzip)
- Static asset caching headers
- Database connection pooling

---

## Testing Strategy

### Unit Tests
- Test service implementations
- Mock database calls
- Validate business logic

### Integration Tests
- Test servlet endpoints
- Database integration
- End-to-end workflows

### Manual Testing
- Browser compatibility
- User workflows
- Admin operations

---

## Deployment Checklist

- [ ] Update `application.properties` with production credentials
- [ ] Configure MySQL database on production server
- [ ] Set up HTTPS certificates
- [ ] Configure Tomcat for production
- [ ] Set up backup strategy
- [ ] Configure monitoring and logging
- [ ] Test all workflows
- [ ] Deploy WAR file
- [ ] Verify email sending
- [ ] Monitor application logs

---

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database exists
- Check firewall rules

### Email Not Sending
- Verify Gmail app-specific password
- Check email configuration
- Review application logs
- Verify SMTP settings

### JSP Compilation Errors
- Check JSP syntax
- Verify imports
- Review Tomcat logs
- Clear Tomcat work directory

---

**For more details, see README.md and CONTRIBUTING.md**
