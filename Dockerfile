# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies first (cache layer)
COPY pom.xml .
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline -B

# Copy the source code
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Run the application (without .original)
CMD ["java", "-jar", "target/order-service-0.0.1-SNAPSHOT.jar"]
