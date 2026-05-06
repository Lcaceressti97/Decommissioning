FROM eclipse-temurin:8-jdk-alpine
ENV TZ=America/Tegucigalpa
COPY ./target/ComodatosApi-1.0.0.jar ComodatosApi-1.0.0.jar
EXPOSE 4460
ENTRYPOINT ["java","-jar","/ComodatosApi-1.0.0.jar"]