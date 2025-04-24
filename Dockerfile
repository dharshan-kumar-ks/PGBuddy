FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy Maven Wrapper files
COPY mvnw .
COPY .mvn/ .mvn/      # ← Critical!
COPY pom.xml .
COPY src ./src

# Build the JAR (ensure mvnw is executable)
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Run the app
CMD ["java", "-jar", "target/pgbuddy-app.jar"]