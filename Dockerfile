# syntax=docker/dockerfile:1
FROM openjdk:16-alpine3.13
WORKDIR /app
RUN apk update && apk upgrade && \
    apk add --no-cache git 
RUN git clone https://github.com/nighthawkcoders/nighthawk_csa.git /app
RUN ./mvnw package
CMD ["java", "-jar", "target/csa-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081
