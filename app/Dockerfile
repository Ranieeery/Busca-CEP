FROM maven:3.9.9-amazoncorretto-17
WORKDIR /app
COPY .. .
RUN mvn clean install
CMD mvn spring-boot:run