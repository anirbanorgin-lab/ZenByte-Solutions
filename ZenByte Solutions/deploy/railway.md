# Railway Configuration Guide

## Environment Variables for Railway

Add these to your Railway project's environment variables:

### Database Configuration
```env
MYSQL_ROOT_PASSWORD=generate_a_strong_password
MYSQL_DATABASE=zenbyte
MYSQL_USER=zenbyte_user
MYSQL_PASSWORD=generate_a_strong_password
```

### Spring Boot Configuration
```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/zenbyte
SPRING_DATASOURCE_USERNAME=zenbyte_user
SPRING_DATASOURCE_PASSWORD=your_password_from_above
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQL8Dialect

# CORS Configuration
CORS_ALLOWED_ORIGINS=https://your-frontend-domain.vercel.app,http://localhost:3000
```

### Application Configuration
```env
SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/api
LOGGING_LEVEL_ROOT=INFO
```

### Security
```env
JWT_SECRET=your_super_secret_jwt_key_at_least_32_characters_long
JWT_EXPIRATION=86400000
```

## Railway Procfile (if needed)

Create `Procfile` in backend root:
```
web: java -jar target/zenbyte-*.jar
```

## Dockerfile Configuration

Your existing Dockerfile should work with Railway. Make sure it includes:
- Java 17
- Maven (for building)
- Proper port exposure (8080)

## Railway MySQL Setup

1. In Railway dashboard, add MySQL service
2. Connect it to your Java service
3. Run migration scripts:
   ```bash
   mysql -h <railway-host> -u root -p < database/schema.sql
   ```

## Troubleshooting

**Service won't start:**
- Check logs in Railway dashboard
- Verify database is running
- Ensure environment variables are set

**Database connection fails:**
- Get correct Railway database host from dashboard
- Use password set in MYSQL_PASSWORD variable

**Application crashes:**
- Check free tier resource limits
- May need to upgrade if memory exceeds 512MB
