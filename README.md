# Ellison Electronics - E-Commerce Platform

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-1.8+-blue.svg)](https://www.java.com)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-orange.svg)](https://www.mysql.com/)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen.svg)](#)

A full-featured e-commerce platform built with **Java, JSP, Servlets, and MySQL**. Supports customer shopping, product management, order processing, and admin controls.

---

## 🎯 Problem Statement

Managing an online electronics store requires a robust system to handle:
- **Product Catalog Management** - Add, update, and remove products with images
- **Shopping Cart Operations** - Add/remove items, manage quantities
- **Order Processing** - Create orders, track shipments, manage transactions
- **User Authentication** - Secure login/registration for customers and admins
- **Inventory Management** - Track stock levels and demand

This project provides a complete solution for these requirements.

---

## ✨ Features

- **User Management**
  - Customer registration and login
  - Admin authentication
  - Session-based security
  - Password encryption

- **Product Management**
  - Browse all products with search functionality
  - Filter products by category/type
  - Product images and details
  - Admin product CRUD operations
  - Stock management

- **Shopping Cart**
  - Add/remove items from cart
  - Update quantities
  - Real-time cart calculations
  - Persistent cart storage

- **Order Management**
  - Create and process orders
  - Order tracking and history
  - Shipment management
  - Transaction records

- **Admin Dashboard**
  - View all products
  - Manage inventory
  - Process orders
  - Track shipments

- **Email Notifications**
  - Order confirmation emails
  - Shipment notifications
  - User registration confirmation

---

## 📸 Demo

### Home Page
![Home Page](assets/demo/home-page.png)

### Product Catalog
![Product Catalog](assets/demo/product-catalog.png)

### Shopping Cart
![Shopping Cart](assets/demo/shopping-cart.png)

### Admin Dashboard
![Admin Dashboard](assets/demo/admin-dashboard.png)

---

## 🚀 Installation

### Prerequisites
- **Java 8+** installed
- **Maven 3.6+** installed
- **MySQL 8.0+** running
- **Tomcat 9+** (or any servlet container)

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/ajt-ecommerce.git
cd AJT_E-commerce
```

### Step 2: Configure Database
1. Create a MySQL database:
```sql
CREATE DATABASE `shopping-cart`;
```

2. Import the database schema:
```bash
mysql -u root -p shopping-cart < databases/schema.sql
```

### Step 3: Configure Environment Variables
1. Copy `.env.example` to `.env`:
```bash
cp .env.example .env
```

2. Edit `.env` with your database and email credentials:
```env
DB_CONNECTION_STRING=jdbc:mysql://localhost:3306/shopping-cart
DB_USERNAME=root
DB_PASSWORD=your_password
MAILER_EMAIL=your-email@gmail.com
MAILER_PASSWORD=your-app-specific-password
```

### Step 4: Build the Project
```bash
mvn clean install
```

### Step 5: Deploy to Tomcat
1. Copy the generated WAR file to Tomcat's webapps directory:
```bash
cp target/shopping-cart.war $CATALINA_HOME/webapps/
```

2. Start Tomcat:
```bash
$CATALINA_HOME/bin/startup.sh
```

3. Access the application:
```
http://localhost:8080/shopping-cart
```

---

## 📖 Usage Examples

### Customer Workflow
1. **Register** - Create a new account
2. **Browse Products** - Search or filter by category
3. **Add to Cart** - Select items and quantities
4. **Checkout** - Review cart and place order
5. **Track Order** - View order status and shipment

### Admin Workflow
1. **Login** - Access admin dashboard
2. **Manage Products** - Add/edit/delete products
3. **View Orders** - Process customer orders
4. **Update Shipments** - Track and update delivery status
5. **View Analytics** - Monitor sales and inventory

---

## 🔌 API Endpoints

### Authentication
- `POST /shopping-cart/login` - User login
- `POST /shopping-cart/register` - User registration
- `GET /shopping-cart/logout` - User logout

### Products
- `GET /shopping-cart/index.jsp` - View all products
- `GET /shopping-cart/index.jsp?search=keyword` - Search products
- `GET /shopping-cart/index.jsp?type=category` - Filter by category
- `POST /shopping-cart/AddProductSrv` - Add product (Admin)
- `POST /shopping-cart/UpdateProductSrv` - Update product (Admin)
- `POST /shopping-cart/RemoveProductSrv` - Delete product (Admin)

### Shopping Cart
- `POST /shopping-cart/AddtoCart` - Add item to cart
- `GET /shopping-cart/cartDetails.jsp` - View cart
- `POST /shopping-cart/UpdateToCart` - Update cart item

### Orders
- `POST /shopping-cart/OrderServlet` - Create order
- `POST /shopping-cart/ShipmentServlet` - Update shipment

### Images
- `GET /shopping-cart/ShowImage?id=productId` - Retrieve product image

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | JSP, HTML5, CSS3, Bootstrap 3, jQuery |
| **Backend** | Java 8, Servlets, JSP |
| **Database** | MySQL 8.0 |
| **Build Tool** | Maven 3.6+ |
| **Server** | Apache Tomcat 9+ |
| **Email** | Jakarta Mail API |
| **Security** | Apache Commons Codec |

---

## 📁 Project Structure

```
AJT_E-commerce/
├── src/
│   └── com/shashi/
│       ├── beans/              # Data models (ProductBean, UserBean, etc.)
│       ├── service/            # Service interfaces
│       ├── service/impl/       # Service implementations
│       ├── srv/                # Servlet controllers
│       ├── utility/            # Utility classes (DBUtil, MailUtil, etc.)
│       └── constants/          # Application constants
├── WebContent/
│   ├── css/                    # Stylesheets
│   ├── js/                     # JavaScript files
│   ├── images/                 # Product images
│   ├── WEB-INF/
│   │   ├── web.xml            # Servlet configuration
│   │   └── lib/               # JAR dependencies
│   ├── index.jsp              # Home page
│   ├── header.jsp             # Header component
│   ├── footer.html            # Footer component
│   ├── cartDetails.jsp        # Shopping cart
│   ├── adminHome.jsp          # Admin dashboard
│   └── ...                    # Other JSP pages
├── databases/
│   └── schema.sql             # Database schema
├── pom.xml                    # Maven configuration
├── .env.example               # Environment variables template
├── .gitignore                 # Git ignore rules
├── .gitattributes             # Git attributes
├── LICENSE                    # MIT License
└── README.md                  # This file
```

---

## 🔧 Configuration

### Database Configuration
Edit `src/application.properties`:
```properties
db.driverName=com.mysql.cj.jdbc.Driver
db.connectionString=jdbc:mysql://localhost:3306/shopping-cart
db.username=root
db.password=your_password
```

### Email Configuration
Update mailer credentials in `src/application.properties`:
```properties
mailer.email=your-email@gmail.com
mailer.password=your-app-specific-password
```

**Note:** For Gmail, use an [App-Specific Password](https://support.google.com/accounts/answer/185833).

---

## 🧪 Testing

### Build and Test
```bash
mvn clean test
```

### Run Locally
```bash
mvn clean install
# Deploy to Tomcat as described in Installation section
```

---

## 🔐 Security Considerations

- ✅ Password encryption using Apache Commons Codec
- ✅ Session-based authentication
- ✅ SQL injection prevention with PreparedStatements
- ⚠️ TODO: Add CSRF tokens
- ⚠️ TODO: Implement HTTPS enforcement
- ⚠️ TODO: Add input validation framework

---

## 🚧 Future Improvements

- [ ] **Payment Gateway Integration** - Stripe/PayPal integration
- [ ] **Advanced Search** - Elasticsearch integration
- [ ] **User Reviews & Ratings** - Product feedback system
- [ ] **Wishlist Feature** - Save favorite products
- [ ] **Recommendation Engine** - ML-based product suggestions
- [ ] **Mobile App** - React Native mobile client
- [ ] **API Modernization** - RESTful API with Spring Boot
- [ ] **Analytics Dashboard** - Sales and traffic analytics
- [ ] **Multi-language Support** - i18n implementation
- [ ] **Performance Optimization** - Caching and CDN integration

---

## 📝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👥 Authors

- **Shashi Kumar** - Initial development
- **Contributors** - See [CONTRIBUTING.md](CONTRIBUTING.md)

---

## 🤝 Support

For support, email support@ellison-electronics.com or open an issue on GitHub.

---

## 🔍 SEO Keywords

`java e-commerce`, `shopping cart application`, `jsp servlet project`, `mysql database`, `java web application`, `e-commerce platform github`, `shopping cart github`, `java project example`, `servlet tutorial`, `jsp tutorial`, `maven project`, `tomcat deployment`, `java backend`, `web development java`, `full-stack java`, `electronics store`, `online shopping`, `inventory management`, `order processing`, `user authentication java`

---

**Last Updated:** December 2024  
**Version:** 1.0.0
