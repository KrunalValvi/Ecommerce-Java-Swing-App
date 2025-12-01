# Deployment Guide

This guide covers deploying Ellison Electronics to various environments.

## Table of Contents
1. [Local Development](#local-development)
2. [Staging Environment](#staging-environment)
3. [Production Deployment](#production-deployment)
4. [Docker Deployment](#docker-deployment)
5. [Troubleshooting](#troubleshooting)

---

## Local Development

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- MySQL 8.0+
- Tomcat 9+ (optional, for testing)

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/ajt-ecommerce.git
   cd AJT_E-commerce
   ```

2. **Create database**
   ```bash
   mysql -u root -p
   CREATE DATABASE `shopping-cart`;
   EXIT;
   ```

3. **Import schema**
   ```bash
   mysql -u root -p shopping-cart < databases/schema.sql
   ```

4. **Configure environment**
   ```bash
   cp .env.example .env
   # Edit .env with your local settings
   ```

5. **Build project**
   ```bash
   mvn clean install
   ```

6. **Deploy to Tomcat**
   ```bash
   cp target/shopping-cart.war $CATALINA_HOME/webapps/
   $CATALINA_HOME/bin/startup.sh
   ```

7. **Access application**
   ```
   http://localhost:8080/shopping-cart
   ```

---

## Staging Environment

### Prerequisites
- Ubuntu 20.04 LTS or similar
- Java 8+
- MySQL 8.0+
- Tomcat 9+
- Git

### Deployment Steps

1. **SSH into staging server**
   ```bash
   ssh user@staging-server.com
   ```

2. **Install dependencies**
   ```bash
   sudo apt-get update
   sudo apt-get install -y openjdk-8-jdk mysql-server tomcat9
   ```

3. **Clone repository**
   ```bash
   cd /opt
   sudo git clone https://github.com/yourusername/ajt-ecommerce.git
   sudo chown -R $USER:$USER ajt-ecommerce
   cd ajt-ecommerce
   ```

4. **Configure database**
   ```bash
   sudo mysql -u root -p
   CREATE DATABASE `shopping-cart`;
   CREATE USER 'ecommerce'@'localhost' IDENTIFIED BY 'secure_password';
   GRANT ALL PRIVILEGES ON `shopping-cart`.* TO 'ecommerce'@'localhost';
   FLUSH PRIVILEGES;
   EXIT;
   
   mysql -u ecommerce -p shopping-cart < databases/schema.sql
   ```

5. **Configure application**
   ```bash
   cp .env.example .env
   # Edit .env with staging credentials
   nano .env
   ```

6. **Build and deploy**
   ```bash
   mvn clean install
   sudo cp target/shopping-cart.war /var/lib/tomcat9/webapps/
   sudo systemctl restart tomcat9
   ```

7. **Verify deployment**
   ```bash
   curl http://localhost:8080/shopping-cart
   ```

---

## Production Deployment

### Prerequisites
- Ubuntu 20.04 LTS or similar
- Java 8+
- MySQL 8.0+ (managed service recommended)
- Tomcat 9+ or similar application server
- Nginx/Apache for reverse proxy
- SSL certificate (Let's Encrypt)
- Monitoring and logging setup

### Security Checklist

- [ ] Update all system packages
- [ ] Configure firewall (UFW/iptables)
- [ ] Set up SSH key-based authentication
- [ ] Disable root login
- [ ] Configure SSL/TLS certificates
- [ ] Set up HTTPS redirect
- [ ] Configure database backups
- [ ] Set up monitoring and alerts
- [ ] Configure log aggregation
- [ ] Implement rate limiting

### Deployment Steps

1. **SSH into production server**
   ```bash
   ssh -i private-key.pem user@production-server.com
   ```

2. **Update system**
   ```bash
   sudo apt-get update && sudo apt-get upgrade -y
   ```

3. **Install dependencies**
   ```bash
   sudo apt-get install -y openjdk-8-jdk mysql-client tomcat9 nginx
   ```

4. **Configure firewall**
   ```bash
   sudo ufw enable
   sudo ufw allow 22/tcp
   sudo ufw allow 80/tcp
   sudo ufw allow 443/tcp
   ```

5. **Clone repository**
   ```bash
   cd /opt
   sudo git clone https://github.com/yourusername/ajt-ecommerce.git
   sudo chown -R tomcat:tomcat ajt-ecommerce
   cd ajt-ecommerce
   ```

6. **Configure application**
   ```bash
   sudo cp .env.example .env
   sudo nano .env
   # Set production credentials and settings
   ```

7. **Build application**
   ```bash
   mvn clean install
   sudo cp target/shopping-cart.war /var/lib/tomcat9/webapps/
   ```

8. **Configure Nginx reverse proxy**
   ```nginx
   server {
       listen 80;
       server_name yourdomain.com;
       
       # Redirect HTTP to HTTPS
       return 301 https://$server_name$request_uri;
   }
   
   server {
       listen 443 ssl http2;
       server_name yourdomain.com;
       
       ssl_certificate /etc/letsencrypt/live/yourdomain.com/fullchain.pem;
       ssl_certificate_key /etc/letsencrypt/live/yourdomain.com/privkey.pem;
       
       location / {
           proxy_pass http://localhost:8080/shopping-cart;
           proxy_set_header Host $host;
           proxy_set_header X-Real-IP $remote_addr;
           proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
           proxy_set_header X-Forwarded-Proto $scheme;
       }
   }
   ```

9. **Set up SSL certificate**
   ```bash
   sudo apt-get install -y certbot python3-certbot-nginx
   sudo certbot certonly --nginx -d yourdomain.com
   ```

10. **Start services**
    ```bash
    sudo systemctl restart nginx
    sudo systemctl restart tomcat9
    ```

11. **Verify deployment**
    ```bash
    curl https://yourdomain.com
    ```

---

## Docker Deployment

### Prerequisites
- Docker 20.10+
- Docker Compose 1.29+

### Dockerfile

Create `Dockerfile` in project root:

```dockerfile
FROM tomcat:9-jdk8-openjdk

# Copy WAR file
COPY target/shopping-cart.war /usr/local/tomcat/webapps/

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
```

### Docker Compose

Create `docker-compose.yml`:

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: shopping-cart
      MYSQL_USER: ecommerce
      MYSQL_PASSWORD: ecommerce_password
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./databases/schema.sql:/docker-entrypoint-initdb.d/schema.sql

  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      DB_CONNECTION_STRING: jdbc:mysql://mysql:3306/shopping-cart
      DB_USERNAME: ecommerce
      DB_PASSWORD: ecommerce_password
    depends_on:
      - mysql
    volumes:
      - ./logs:/usr/local/tomcat/logs

volumes:
  mysql_data:
```

### Build and Run

```bash
# Build Docker image
docker build -t ajt-ecommerce:1.0 .

# Run with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down
```

---

## Monitoring and Maintenance

### Health Checks

```bash
# Check application status
curl http://localhost:8080/shopping-cart

# Check database connection
mysql -u ecommerce -p -e "SELECT 1;"

# Check Tomcat logs
tail -f /var/lib/tomcat9/logs/catalina.out
```

### Backup Strategy

```bash
# Backup database
mysqldump -u ecommerce -p shopping-cart > backup_$(date +%Y%m%d).sql

# Backup application
tar -czf shopping-cart_backup_$(date +%Y%m%d).tar.gz /opt/ajt-ecommerce

# Automated backup (cron job)
0 2 * * * /path/to/backup-script.sh
```

### Log Management

```bash
# View application logs
tail -f /var/lib/tomcat9/logs/catalina.out

# View error logs
tail -f /var/lib/tomcat9/logs/catalina.err

# Rotate logs
logrotate /etc/logrotate.d/tomcat9
```

---

## Troubleshooting

### Database Connection Issues

**Problem:** `Connection refused`

**Solution:**
```bash
# Check MySQL is running
sudo systemctl status mysql

# Check connection string in .env
cat .env | grep DB_CONNECTION_STRING

# Test connection
mysql -u ecommerce -p -h localhost shopping-cart
```

### Application Not Starting

**Problem:** Tomcat starts but application not accessible

**Solution:**
```bash
# Check Tomcat logs
tail -f /var/lib/tomcat9/logs/catalina.out

# Check WAR file deployed
ls -la /var/lib/tomcat9/webapps/

# Restart Tomcat
sudo systemctl restart tomcat9
```

### Email Not Sending

**Problem:** Order confirmation emails not received

**Solution:**
```bash
# Verify email configuration in .env
cat .env | grep MAILER

# Check application logs for email errors
grep -i "mail" /var/lib/tomcat9/logs/catalina.out

# Test email credentials
java -cp target/shopping-cart.war com.shashi.utility.TestMail
```

---

## Rollback Procedure

```bash
# Stop application
sudo systemctl stop tomcat9

# Restore previous WAR file
sudo cp /backups/shopping-cart-previous.war /var/lib/tomcat9/webapps/shopping-cart.war

# Restore database if needed
mysql -u ecommerce -p shopping-cart < /backups/shopping-cart_backup.sql

# Start application
sudo systemctl start tomcat9
```

---

## Performance Optimization

### Database Optimization
```sql
-- Add indexes for frequently queried columns
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_product_type ON product(product_type);
CREATE INDEX idx_order_user ON orders(user_id);
```

### Tomcat Configuration
Edit `/var/lib/tomcat9/conf/server.xml`:
```xml
<Connector port="8080" protocol="HTTP/1.1"
    connectionTimeout="20000"
    maxThreads="200"
    minSpareThreads="10"
    maxConnections="10000"
    redirectPort="8443" />
```

### Caching
- Enable browser caching for static assets
- Implement database query caching
- Use CDN for static content

---

**For more information, see README.md and ARCHITECTURE.md**
