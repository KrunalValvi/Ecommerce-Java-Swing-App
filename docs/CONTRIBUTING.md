# Contributing to Ellison Electronics

Thank you for your interest in contributing! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on the code, not the person
- Help others learn and grow

## Getting Started

### 1. Fork and Clone
```bash
git clone https://github.com/yourusername/ajt-ecommerce.git
cd AJT_E-commerce
```

### 2. Create a Feature Branch
```bash
git checkout -b feature/your-feature-name
```

### 3. Set Up Development Environment
```bash
mvn clean install
# Configure .env with your local settings
```

## Development Guidelines

### Code Style
- Follow Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Use meaningful variable and method names
- Keep methods focused and under 50 lines when possible
- Add comments for complex logic

### Commit Messages
Use clear, descriptive commit messages:
```
feat: Add product search functionality
fix: Resolve cart total calculation bug
docs: Update README with API endpoints
refactor: Simplify database connection logic
```

### Pull Request Process

1. **Update your branch** with the latest main:
   ```bash
   git fetch origin
   git rebase origin/main
   ```

2. **Test your changes**:
   ```bash
   mvn clean test
   mvn clean install
   ```

3. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

4. **Create a Pull Request** with:
   - Clear title and description
   - Reference to related issues
   - Screenshots (if UI changes)
   - Testing instructions

5. **Address review feedback** and update your PR

## Types of Contributions

### Bug Fixes
- Create an issue first describing the bug
- Reference the issue in your PR
- Include steps to reproduce
- Add test cases if applicable

### Features
- Discuss in an issue before starting work
- Follow the existing code structure
- Add documentation
- Update README if needed

### Documentation
- Fix typos and unclear sections
- Add examples and use cases
- Improve API documentation
- Add architecture diagrams

### Performance Improvements
- Benchmark before and after
- Document the improvement
- Ensure no functionality is lost

## Testing

### Running Tests
```bash
mvn test
```

### Adding Tests
- Create test classes in `src/test/java`
- Use JUnit for unit tests
- Name tests descriptively: `testFeatureName()`
- Aim for >80% code coverage

### Manual Testing
1. Deploy to local Tomcat
2. Test all affected features
3. Verify in different browsers
4. Check database operations

## Documentation

### Code Comments
Add comments for:
- Complex algorithms
- Non-obvious business logic
- Important configuration
- Workarounds or hacks

Example:
```java
// Calculate discount based on order total and customer tier
// Premium customers get 15%, regular get 10%
double discount = isPremium ? total * 0.15 : total * 0.10;
```

### README Updates
Update README.md if you:
- Add new features
- Change installation steps
- Modify configuration
- Add new dependencies

## Reporting Issues

### Bug Reports
Include:
- Clear title and description
- Steps to reproduce
- Expected vs actual behavior
- Screenshots/logs if applicable
- Environment details (Java version, OS, etc.)

### Feature Requests
Include:
- Clear description of the feature
- Use case and benefits
- Potential implementation approach
- Any related issues or discussions

## Project Structure Guidelines

When adding new code:
- **Beans** - Data models in `com.shashi.beans`
- **Services** - Business logic in `com.shashi.service`
- **Servlets** - Controllers in `com.shashi.srv`
- **Utilities** - Helper classes in `com.shashi.utility`
- **JSP** - Views in `WebContent/`

## Dependency Management

### Adding Dependencies
1. Add to `pom.xml` with appropriate version
2. Document why it's needed
3. Check for conflicts with existing dependencies
4. Update `.env.example` if configuration needed

### Removing Dependencies
1. Ensure no code uses it
2. Update documentation
3. Verify build succeeds

## Performance Considerations

- Use connection pooling for database
- Cache frequently accessed data
- Optimize database queries
- Minimize JSP processing
- Use CDN for static assets

## Security Guidelines

- Never commit credentials or secrets
- Use parameterized queries to prevent SQL injection
- Validate all user inputs
- Use HTTPS in production
- Keep dependencies updated

## Release Process

Maintainers will:
1. Review and merge PRs
2. Update version in `pom.xml`
3. Update CHANGELOG
4. Create release tag
5. Deploy to production

## Questions?

- Check existing issues and discussions
- Review documentation
- Ask in pull request comments
- Email maintainers

---

**Thank you for contributing to Ellison Electronics!** 🎉
