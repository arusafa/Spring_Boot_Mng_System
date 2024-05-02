FROM openjdk:17
ADD target/projectmanagementsystem.jar projectmanagementsystem.jar
ENTRYPOINT ["java","-jar","/projectmanagementsystem.jar"]

