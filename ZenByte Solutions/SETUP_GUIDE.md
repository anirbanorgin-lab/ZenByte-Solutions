# ZenByte Solutions - Setup & Deployment Guide

## Quick Start

### 1. Clone or Extract Project
```bash
cd "ZenByte Solutions"
```

### 2. Frontend Quick Start (No Installation Needed)
```bash
cd frontend

# Option A: Use Python's built-in HTTP server
python -m http.server 8000

# Option B: Use Node.js http-server
npm install -g http-server
http-server

# Option C: Open directly in browser
# Open index.html in any modern web browser
```

Frontend will be available at: `http://localhost:8000` (or `http://localhost:3000` with http-server)

### 3. Backend Quick Start

#### Prerequisites
- Java 17+ installed
- Maven 3.6+ installed
- MySQL 8.0+ running

#### Steps

1. **Create Database**
   ```bash
   mysql -u root -p < database/schema.sql
   ```

2. **Configure Backend**
   ```bash
   cd backend
   # Edit src/main/resources/application.properties
   # Update: spring.datasource.username and spring.datasource.password
   ```

3. **Build Backend**
   ```bash
   mvn clean install
   ```

4. **Run Backend**
   ```bash
   mvn spring-boot:run
   ```

Backend API will be available at: `http://localhost:8080/api`

## Full Development Setup

### Prerequisites Installation

#### Windows
1. **Java 17**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Set JAVA_HOME environment variable

2. **Maven**
   - Download from: https://maven.apache.org/download.cgi
   - Set M2_HOME environment variable
   - Add %M2_HOME%\bin to PATH

3. **MySQL**
   - Download from: https://dev.mysql.com/downloads/mysql/
   - Install with default settings
   - Default port: 3306, default user: root

4. **Git** (Optional)
   - Download from: https://git-scm.com/

#### macOS
```bash
# Using Homebrew
brew install java@17
brew install maven
brew install mysql
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-17-jdk
sudo apt install maven
sudo apt install mysql-server
```

### Database Setup

1. **Start MySQL**
   ```bash
   # Windows
   mysql -u root -p
   
   # macOS/Linux
   sudo systemctl start mysql
   mysql -u root -p
   ```

2. **Create Database and User**
   ```sql
   source database/schema.sql;
   
   -- Create dedicated user (optional)
   CREATE USER 'zenbyte'@'localhost' IDENTIFIED BY 'SecurePassword123!';
   GRANT ALL PRIVILEGES ON zenbyte_db.* TO 'zenbyte'@'localhost';
   FLUSH PRIVILEGES;
   ```

### Backend Setup

1. **Navigate to backend**
   ```bash
   cd backend
   ```

2. **Configure Database Connection**
   - Edit: `src/main/resources/application.properties`
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/zenbyte_db
   spring.datasource.username=zenbyte
   spring.datasource.password=SecurePassword123!
   ```

3. **Configure JWT Secret** (Important for Security)
   ```properties
   jwt.secret=YourVerySecureJWTSecretKeyChangeInProduction2024
   ```

4. **Build Project**
   ```bash
   mvn clean package -DskipTests
   ```

5. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

6. **Verify Backend is Running**
   ```bash
   curl http://localhost:8080/api/contacts
   ```

### Frontend Setup

1. **No installation needed for static version**
   - Open `frontend/index.html` directly in browser

2. **For development with live reload** (Optional)
   ```bash
   cd frontend
   python -m http.server 8000
   # or
   npx http-server
   ```

3. **Update API Endpoint** (if needed)
   - Edit: `frontend/js/script.js`
   - Update API base URL if backend is on different server

## Testing the Application

### Test Frontend
1. Open `http://localhost:8000` or file path in browser
2. Navigate through pages
3. Test forms (Contact, Quote Request, Job Application)

### Test Backend API

1. **Using curl**
   ```bash
   # Get all contacts
   curl http://localhost:8080/api/contacts
   
   # Create new contact
   curl -X POST http://localhost:8080/api/contacts \
     -H "Content-Type: application/json" \
     -d '{
       "fullName":"Test User",
       "email":"test@example.com",
       "phone":"+91-9999999999",
       "subject":"Test",
       "service":"Web Development",
       "message":"Test message"
     }'
   ```

2. **Using Postman**
   - Import API collection (if provided)
   - Test each endpoint
   - Verify responses

## Deployment Options

### Local Development
```bash
# Terminal 1: Backend
cd backend
mvn spring-boot:run

# Terminal 2: Frontend (optional)
cd frontend
python -m http.server 8000
```

### Docker Deployment
```dockerfile
# backend/Dockerfile
FROM openjdk:17-jdk-slim
COPY target/zenbyte-solutions-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
docker build -t zenbyte-backend:1.0 backend/
docker run -p 8080:8080 -e DB_HOST=host.docker.internal zenbyte-backend:1.0
```

### Production Deployment

#### AWS Deployment
1. Upload JAR to S3
2. Create EC2 instance
3. Install Java and MySQL
4. Deploy and run application

#### Heroku Deployment
```bash
heroku login
heroku create zenbyte-solutions
git push heroku main
```

## Troubleshooting

### Issue: MySQL Connection Error
**Solution:**
```bash
# Verify MySQL is running
mysql -u root -p

# Check port 3306 is open
netstat -tuln | grep 3306

# Restart MySQL service
sudo systemctl restart mysql  # Linux
brew services restart mysql   # macOS
```

### Issue: Port 8080 Already in Use
**Solution:**
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or use different port
# Edit: src/main/resources/application.properties
server.port=8081
```

### Issue: Maven Build Fails
**Solution:**
```bash
# Clear Maven cache
mvn clean
rm -rf ~/.m2/repository

# Rebuild
mvn clean install -U
```

### Issue: Java Version Mismatch
**Solution:**
```bash
# Check Java version
java -version

# Should be 17 or higher
# If not, set JAVA_HOME environment variable

# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.0

# Linux/macOS
export JAVA_HOME=/usr/libexec/java_home -v 17
```

## Performance Tips

1. **Database Indexing** - Already included in schema.sql
2. **Connection Pooling** - Configured in Spring Boot
3. **Caching** - Can be added for frequently accessed data
4. **Pagination** - Implement for large datasets

## Security Checklist

- [ ] Change JWT secret in production
- [ ] Update database credentials
- [ ] Enable HTTPS/SSL certificates
- [ ] Implement rate limiting
- [ ] Add input validation
- [ ] Use environment variables for sensitive data
- [ ] Enable CORS selectively
- [ ] Regular security updates

## Useful Commands

```bash
# Backend
mvn clean package            # Build for production
mvn spring-boot:run         # Run development server
mvn test                     # Run unit tests
mvn verify                   # Full build verification

# Database
mysql -u root -p < schema.sql    # Initialize database
mysqldump -u root -p zenbyte_db > backup.sql  # Backup database

# Git
git init                     # Initialize repository
git add .                    # Stage all files
git commit -m "message"      # Commit changes
git push origin main         # Push to remote
```

## Support & Documentation

- **Frontend**: Static HTML/CSS/JS - No special documentation needed
- **Backend**: Spring Boot REST API - See README.md for API endpoints
- **Database**: MySQL - See schema.sql for database structure

## Next Steps

1. Customize branding (colors, logo, text)
2. Add real images for projects and team members
3. Integrate email notifications
4. Set up CI/CD pipeline
5. Configure production environment
6. Launch and monitor

---

**Happy Coding! 🚀**

For issues or questions, contact: support@zenbytesolutions.com
