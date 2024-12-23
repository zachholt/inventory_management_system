FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY target/inventoryManagement-0.0.1-SNAPSHOT.jar inventoryManagement.jar
EXPOSE 8082
CMD ["java", "-jar", "inventoryManagement.jar"]