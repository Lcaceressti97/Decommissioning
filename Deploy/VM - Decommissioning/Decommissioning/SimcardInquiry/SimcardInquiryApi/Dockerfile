FROM openjdk:8-jdk-alpine
ENV TZ=America/Tegucigalpa
ENV SPRING_APPLICATION_JSON='{"spring.servlet.multipart.max-file-size":"10MB","spring.servlet.multipart.max-request-size":"10MB"}'
COPY ./target/SimcardInquiry-1.0.0.jar SimcardInquiry-1.0.0.jar
EXPOSE 4434
ENTRYPOINT ["java","-jar","/SimcardInquiry-1.0.0.jar"]
