# Stage 1: Build the JAR
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Copy only the files needed for dependency resolution (optimizes caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src src
RUN ./mvnw clean package -DskipTests && \
    ls -la target/  # Debug: Verify JAR creation

# Stage 2: Runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR (using the exact name from pom.xml's <finalName>)
COPY --from=builder /app/target/pgbuddy-app.jar app.jar

# Set memory limits (matches your pom.xml settings)
# Increase Metaspace to 128MB and total heap to match Render's 512MB free tier
ENV JAVA_OPTS="-Xmx384m -Xms128m -XX:MaxMetaspaceSize=128m -XX:+UseContainerSupport"

# Run with exec form for proper signal handling
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]