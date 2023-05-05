# Admissions
# Project Name "Admissions" 

Assumptions:- The back-end is running at 8080 port and the front-end at 3000.

#Description
The application allows to perform CRUD operations realated to Admission records. 

## Requirements

For building and running the application you need:

JDK 17
Springboot 3.x
Maven 3.x
Lombok 1.18.x


## Before Running the application locally

Add the lombok jar to your classpath

Run the below command from the roor directory of the "admission" projcet to install dependencies required for the Taps project to run
```
mvn clean install
```

## Running the application locally


There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.promed.admission.AdmissionApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
and run the below command from the root directory of the "admission" project

```
mvn spring-boot:run
```

To run the untit test cases only, please run the below command
```
mvn test
```


**Curl command for the external admission record creation**

curl -X POST -H "Content-Type: application/json" -H "Accept: application/json" -d "{\"dateOfAdmission\":\"\",\"name\":\"gdggdeyrgeyeritirythjvdhfggskhgfurghoreugergoh\",\"dateOfBirth\":\"04/05/1984\",\"categoryId\":1,\"genderId\":1,\"sourceSystemName\":\"Sunshine Hospital 3\"}" http://localhost:8080/promed/api/v1/ext/admission

(for windows cmd)

