# Frequently Asked Questions

## Installation & Setup

### Q: What are the system requirements?
**A:** You need:
- Java 8 or higher
- Maven 3.6+
- MySQL 8.0+
- Tomcat 9+ (for deployment)
- 2GB RAM minimum
- 500MB disk space

### Q: How do I set up the database?
**A:** Follow these steps:
```bash
# Create database
mysql -u root -p
CREATE DATABASE `shopping-cart`;
EXIT;

# Import schema
mysql -u root -p shopping-cart < databases/schema.sql
```

### Q: Can I use a different database?
**A:** Currently, the project is configured for MySQL. To use PostgreSQL or Oracle:
1. Update the JDBC driver in `pom.xml`
2. Modify the connection string in `application.properties`
3. Adjust SQL syntax in service implementations
4. Update database schema

### Q: How do I configure email notifications?
**A:** Edit `src/application.properties`:
```properties
mailer.email=your-email@gmail.com
mailer.password=your-app-specific-password
```

For Gmail, use an [App-Specific Password](https://support.google.com/accounts/answer/185833).

---

## Development

### Q: How do I run the application locally?
**A:** 
```bash
# Build
mvn clean install

# Deploy to Tomcat
cp target/shopping-cart.war $CATALINA_HOME/webapps/

# Start Tomcat
$CATALINA_HOME/bin/startup.sh

# Access at http://localhost:8080/shopping-cart
```

### Q: How do I add a new feature?
**A:** 
1. Create a feature branch: `git checkout -b feature/my-feature`
2. Implement the feature following the existing code structure
3. Add tests if applicable
4. Update documentation
5. Submit a pull request

See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

### Q: How do I debug the application?
**A:**
1. Enable debug mode in Tomcat
2. Use IDE debugger (Eclipse, IntelliJ)
3. Check logs: `/var/lib/tomcat9/logs/catalina.out`
4. Add logging statements using `System.out.println()` or a logging framework

### Q: Can I use a different IDE?
**A:** Yes! The project is IDE-agnostic. Supported IDEs:
- Eclipse
- IntelliJ IDEA
- NetBeans
- Visual Studio Code (with extensions)

### Q: How do I run tests?
**A:**
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceImplTest

# Run with coverage
mvn test jacoco:report
```

---

## Deployment

### Q: How do I deploy to production?
**A:** See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed instructions covering:
- Local development setup
- Staging environment
- Production deployment
- Docker deployment

### Q: How do I set up HTTPS?
**A:** 
1. Obtain SSL certificate (Let's Encrypt recommended)
2. Configure Tomcat with SSL connector
3. Set up Nginx reverse proxy
4. Redirect HTTP to HTTPS

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed steps.

### Q: How do I backup the database?
**A:**
```bash
# Manual backup
mysqldump -u ecommerce -p shopping-cart > backup.sql

# Automated backup (cron job)
0 2 * * * mysqldump -u ecommerce -p shopping-cart > /backups/backup_$(date +\%Y\%m\%d).sql
```

### Q: How do I restore from a backup?
**A:**
```bash
mysql -u ecommerce -p shopping-cart < backup.sql
```

### Q: How do I monitor the application?
**A:**
- Check logs: `tail -f /var/lib/tomcat9/logs/catalina.out`
- Monitor database: `mysql -u ecommerce -p -e "SHOW PROCESSLIST;"`
- Use monitoring tools: Prometheus, Grafana, New Relic

---

## Troubleshooting

### Q: Database connection fails
**A:** Check:
1. MySQL is running: `sudo systemctl status mysql`
2. Connection string in `application.properties`
3. Database exists: `mysql -u root -p -e "SHOW DATABASES;"`
4. User has permissions: `mysql -u ecommerce -p shopping-cart -e "SELECT 1;"`

### Q: Application won't start
**A:** Check:
1. Tomcat logs: `tail -f /var/lib/tomcat9/logs/catalina.out`
2. Java version: `java -version`
3. Port 8080 is available: `netstat -tulpn | grep 8080`
4. WAR file deployed: `ls /var/lib/tomcat9/webapps/`

### Q: Email not sending
**A:** Check:
1. Email configuration in `application.properties`
2. Gmail app-specific password is correct
3. Application logs for email errors
4. Firewall allows SMTP (port 587)

### Q: Slow performance
**A:** Try:
1. Add database indexes
2. Increase Tomcat thread pool
3. Enable caching
4. Optimize database queries
5. Use CDN for static assets

### Q: Users can't login
**A:** Check:
1. Database has user records
2. Password is correct
3. Session is enabled
4. Cookies are allowed in browser

---

## Security

### Q: Is the application secure?
**A:** The application implements:
- PreparedStatements for SQL injection prevention
- Password encryption
- Session-based authentication
- HTTPS support

See [SECURITY.md](SECURITY.md) for detailed security information.

### Q: How do I report a security vulnerability?
**A:** Email security@ellison-electronics.com with:
- Vulnerability description
- Steps to reproduce
- Potential impact
- Suggested fix

### Q: How often are dependencies updated?
**A:** We monitor dependencies regularly and update:
- Security patches: immediately
- Minor updates: monthly
- Major updates: quarterly

---

## Features

### Q: Can customers track their orders?
**A:** Currently, customers can view order history. Order tracking features are planned for v1.1.

### Q: Can I add payment gateway integration?
**A:** Yes! This is planned for v1.1. See [CHANGELOG.md](CHANGELOG.md) for roadmap.

### Q: Can I customize the UI?
**A:** Yes! The UI is built with Bootstrap and can be customized by:
1. Modifying CSS in `WebContent/css/`
2. Editing JSP templates
3. Updating HTML structure

### Q: Can I add more product categories?
**A:** Yes! Update the database schema and add category options in the application.

### Q: Can I integrate with external APIs?
**A:** Yes! Add dependencies to `pom.xml` and implement integration in service layer.

---

## Performance

### Q: How many concurrent users can the application handle?
**A:** With default configuration: ~100-200 concurrent users. To increase:
1. Increase Tomcat thread pool
2. Implement connection pooling
3. Add database indexes
4. Use caching layer

### Q: How do I optimize database performance?
**A:**
```sql
-- Add indexes
CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_product_type ON product(product_type);
CREATE INDEX idx_order_user ON orders(user_id);

-- Analyze query performance
EXPLAIN SELECT * FROM product WHERE product_type = 'Electronics';
```

### Q: Should I use a CDN?
**A:** Yes, for production:
- Serve static assets (CSS, JS, images) from CDN
- Reduces server load
- Improves page load time
- Better user experience

---

## Contributing

### Q: How do I contribute?
**A:** See [CONTRIBUTING.md](CONTRIBUTING.md) for:
- Code style guidelines
- Pull request process
- Testing requirements
- Commit message format

### Q: Can I suggest a feature?
**A:** Yes! Open an issue on GitHub with:
- Feature description
- Use case
- Proposed implementation
- Related issues

### Q: How do I report a bug?
**A:** Open an issue with:
- Clear title
- Steps to reproduce
- Expected vs actual behavior
- Screenshots/logs
- Environment details

---

## Licensing

### Q: What license is this project under?
**A:** MIT License. See [LICENSE](LICENSE) file for details.

### Q: Can I use this in a commercial project?
**A:** Yes! MIT license allows commercial use. Just include the license notice.

### Q: Can I modify the code?
**A:** Yes! MIT license allows modifications. Share improvements via pull requests.

---

## Support

### Q: Where can I get help?
**A:** 
- Check this FAQ
- Read [README.md](README.md) and [ARCHITECTURE.md](ARCHITECTURE.md)
- Open an issue on GitHub
- Email support@ellison-electronics.com

### Q: Is there a community forum?
**A:** Not yet, but we're considering it. Follow the project for updates.

### Q: How do I stay updated?
**A:** 
- Star the repository
- Watch for releases
- Subscribe to email notifications
- Follow on social media

---

## Version & Updates

### Q: What version is the current release?
**A:** Check [CHANGELOG.md](CHANGELOG.md) for version history.

### Q: How do I update to a newer version?
**A:**
```bash
# Pull latest changes
git pull origin main

# Rebuild
mvn clean install

# Redeploy
cp target/shopping-cart.war $CATALINA_HOME/webapps/
```

### Q: How often are new versions released?
**A:** 
- Security patches: as needed
- Minor releases: monthly
- Major releases: quarterly

---

## Still have questions?

- 📧 Email: support@ellison-electronics.com
- 🐛 Report bugs: GitHub Issues
- 💡 Suggest features: GitHub Discussions
- 📖 Read docs: README.md, ARCHITECTURE.md, DEPLOYMENT.md

---

**Last Updated:** December 2024
