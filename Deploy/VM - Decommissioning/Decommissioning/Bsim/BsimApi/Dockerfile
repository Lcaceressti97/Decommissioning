FROM openjdk:8-jdk-alpine
ENV TZ=America/Tegucigalpa
COPY ./target/BsimModule-1.0.0.jar BsimModule-1.0.0.jar
EXPOSE 4490
ENTRYPOINT ["java","-jar","/BsimModule-1.0.0.jar"]