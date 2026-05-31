# Stage 1: Build the application
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make the wrapper executable and download dependencies
# This is done before copying the source code to cache dependencies
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:24-jdk AS runner
WORKDIR /app

# The PORT environment variable is automatically provided by Render.
# We expose 8080 as a default fallback, though Render uses its own PORT routing.
EXPOSE 8080

# Copy the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
