FROM openjdk:8-jdk-alpine
ENV TZ=America/Tegucigalpa
COPY ./target/EquipmentAccessoriesBilling-1.0.0.jar EquipmentAccessoriesBilling-1.0.0.jar
EXPOSE 4437
ENTRYPOINT ["java","-jar","/EquipmentAccessoriesBilling-1.0.0.jar"]