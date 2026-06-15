# Backend Dockerfile for ZenByte Solutions

FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Set environment variables
ENV APP_NAME=zenbyte-solutions
ENV JAVA_OPTS="-Xmx512m -Xms512m"

# Copy Maven build output
COPY target/zenbyte-solutions-1.0.0.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD java -cp app.jar org.springframework.boot.loader.JarLauncher && \
      java -cp app.jar -Dspring.boot.application.version && \
      exit 0 || exit 1

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
