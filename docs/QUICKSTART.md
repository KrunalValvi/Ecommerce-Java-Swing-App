# Quick Start Guide

Get Ellison Electronics running in 5 minutes!

## Prerequisites
- Java 8+
- Maven 3.6+
- MySQL 8.0+

## Installation

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/ajt-ecommerce.git
cd AJT_E-commerce
```

### 2. Setup Database
```bash
# Create database
mysql -u root -p
CREATE DATABASE `shopping-cart`;
EXIT;

# Import schema
mysql -u root -p shopping-cart < databases/schema.sql
```

### 3. Configure Environment
```bash
# Copy environment template
cp .env.example .env

# Edit with your settings
nano .env
```

Update these values:
```env
DB_CONNECTION_STRING=jdbc:mysql://localhost:3306/shopping-cart
DB_USERNAME=root
DB_PASSWORD=your_password
MAILER_EMAIL=your-email@gmail.com
MAILER_PASSWORD=your-app-password
```

### 4. Build Project
```bash
mvn clean install
```

### 5. Deploy & Run
```bash
# Copy WAR to Tomcat
cp target/shopping-cart.war $CATALINA_HOME/webapps/

# Start Tomcat
$CATALINA_HOME/bin/startup.sh

# Access application
# Open browser: http://localhost:8080/shopping-cart
```

## Test Credentials

### Customer Account
- **Email:** customer@test.com
- **Password:** password123

### Admin Account
- **Email:** admin@test.com
- **Password:** admin123

## Common Commands

```bash
# Build
mvn clean install

# Run tests
mvn test

# Check dependencies
mvn dependency:tree

# Clean build artifacts
mvn clean

# Package only
mvn package

# Skip tests during build
mvn clean install -DskipTests
```

## Project Structure

```
src/
├── com/shashi/beans/        # Data models
├── com/shashi/service/      # Business logic interfaces
├── com/shashi/service/impl/ # Service implementations
├── com/shashi/srv/          # Servlets (controllers)
├── com/shashi/utility/      # Helper utilities
└── com/shashi/constants/    # Constants

WebContent/
├── css/                     # Stylesheets
├── js/                      # JavaScript
├── images/                  # Product images
└── *.jsp                    # Web pages
```

## Key Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven configuration |
| `src/application.properties` | Database & email config |
| `WebContent/index.jsp` | Home page |
| `WebContent/WEB-INF/web.xml` | Servlet mapping |

## Troubleshooting

### Database Connection Error
```bash
# Check MySQL is running
sudo systemctl status mysql

# Test connection
mysql -u root -p shopping-cart
```

### Application Won't Start
```bash
# Check Tomcat logs
tail -f $CATALINA_HOME/logs/catalina.out

# Verify WAR deployed
ls $CATALINA_HOME/webapps/shopping-cart.war
```

### Port Already in Use
```bash
# Find process using port 8080
netstat -tulpn | grep 8080

# Kill process
kill -9 <PID>
```

## Next Steps

1. **Read Documentation**
   - [README.md](README.md) - Full overview
   - [ARCHITECTURE.md](ARCHITECTURE.md) - System design
   - [DEPLOYMENT.md](DEPLOYMENT.md) - Production setup

2. **Explore Code**
   - Check `UserServiceImpl.java` for example
   - Review servlet implementations in `srv/`
   - Understand database layer in `utility/`

3. **Contribute**
   - See [CONTRIBUTING.md](CONTRIBUTING.md)
   - Create feature branch
   - Submit pull request

4. **Deploy**
   - Follow [DEPLOYMENT.md](DEPLOYMENT.md)
   - Set up production environment
   - Configure SSL/HTTPS

## Support

- 📖 [FAQ.md](FAQ.md) - Frequently asked questions
- 🔒 [SECURITY.md](SECURITY.md) - Security guidelines
- 📝 [CHANGELOG.md](CHANGELOG.md) - Version history
- 📧 Email: support@ellison-electronics.com

---

**Happy coding! 🚀**
