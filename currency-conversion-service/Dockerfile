FROM maven:3.8.6-openjdk-18-slim AS build
WORKDIR home/app

COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/manhpro/springcloud/currency_conversion/currency_conversion_service/CurrencyConversionServiceApplication.java /home/app/src/main/java/com/manhpro/springcloud/currency_conversion/currency_conversion_service/CurrencyConversionServiceApplication.java
RUN mvn -f /home/app/pom.xml clean package

COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18.0-slim
EXPOSE 8100
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]