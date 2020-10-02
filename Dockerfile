FROM openjdk:14-alpine
COPY target/poc-mongodb-*.jar poc-mongodb.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "poc-mongodb.jar"]