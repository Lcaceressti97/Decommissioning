FROM openjdk:8-jdk-alpine
ENV TZ=America/Tegucigalpa

COPY ./target/EquipmentBlacklist-1.0.0.jar EquipmentBlacklist-1.0.0.jar
EXPOSE 4420

ENTRYPOINT ["java","-jar","/EquipmentBlacklist-1.0.0.jar"]