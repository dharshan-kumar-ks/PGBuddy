# Use OpenJDK 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven Wrapper files (remove the comment after .mvn/)
COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .
COPY src ./src

# Build the JAR (ensure mvnw is executable)
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Run the app
CMD ["java", "-jar", "target/pgbuddy-app.jar"]

