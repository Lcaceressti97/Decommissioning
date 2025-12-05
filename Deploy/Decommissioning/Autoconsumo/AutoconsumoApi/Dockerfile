FROM openjdk:8-jdk-alpine
ENV TZ=America/Tegucigalpa
COPY ./target/SelfConsumptionModule-1.0.0.jar SelfConsumptionModule-1.0.0.jar
EXPOSE 4475
ENTRYPOINT ["java","-jar","/SelfConsumptionModule-1.0.0.jar"]