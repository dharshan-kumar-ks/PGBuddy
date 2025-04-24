# Use OpenJDK 17
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy build files (Maven/Gradle)
COPY pom.xml .
COPY src ./src

# Build the JAR (Maven)
RUN ./mvnw clean package -DskipTests

# Run the app (replace "your-app.jar" with your JAR name)
CMD ["java", "-jar", "target/pgbuddy-app.jar"]