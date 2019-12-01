FROM java:8-jdk-alpine
COPY ./target/appraisal-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch appraisal-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","appraisal-0.0.1-SNAPSHOT.jar"]