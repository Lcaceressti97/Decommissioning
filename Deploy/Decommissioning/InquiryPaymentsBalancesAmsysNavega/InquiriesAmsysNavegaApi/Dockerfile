FROM openjdk:8-jdk-alpine
ENV TZ=America/Tegucigalpa

COPY ./target/InquiriesAmsysNavega-1.0.0.jar InquiriesAmsysNavega-1.0.0.jar
EXPOSE 4430
ENTRYPOINT ["java","-jar","/InquiriesAmsysNavega-1.0.0.jar"]