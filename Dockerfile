# Stage 1: Build the JAR file using Maven with Amazon Corretto 18
FROM maven:3.8.6 AS builder
# Install Amazon Corretto 18 in the Maven image
RUN apt-get update && apt-get install -y wget && \
    wget -O corretto.tar.gz https://corretto.aws/downloads/latest/amazon-corretto-18-x64-linux-jdk.tar.gz && \
    mkdir -p /usr/lib/jvm/corretto-18 && \
    tar -xzf corretto.tar.gz -C /usr/lib/jvm/corretto-18 --strip-components=1 && \
    rm corretto.tar.gz && \
    update-alternatives --install /usr/bin/java java /usr/lib/jvm/corretto-18/bin/java 1 && \
    update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/corretto-18/bin/javac 1
# Set JAVA_HOME to Corretto 18
ENV JAVA_HOME=/usr/lib/jvm/corretto-18
ENV PATH=$JAVA_HOME/bin:$PATH
# Verify Java version
RUN java -version
# Set the working directory
WORKDIR /app
# Copy the project files
COPY . .
# Build the JAR file (skip tests for faster build)
RUN mvn clean package -DskipTests
# Stage 2: Create the runtime image using Amazon Corretto 18
FROM amazoncorretto:18
# Set the working directory
WORKDIR /app
# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Expose the port (Render will override this with the PORT environment variable)
EXPOSE 8080
# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]