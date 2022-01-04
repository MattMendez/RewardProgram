# Rewards program

Homework Problem solved with [Spring Boot](http://projects.spring.io/spring-boot/)

## Problem to solve
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).

Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

* Make up a data set to best demonstrate your solution
* Check solution into GitHub

## Requirements

For building and running the application you need:
- [JDK 11](https://www.oracle.com/ar/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [Git](https://git-scm.com)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.exercise.homeworkproblem.HomeWorkProblemApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

If the application is not in your pc yet use this: 
```shell
$ git clone https://github.com/cylixx/rewards-restful.git
$ mvn spring-boot: run
```

## Documentation
For testing and documentation [Swagger](https://swagger.io) was used, you can enter Swagger-ui for testing the API when the project is running:

[Swagger-UI](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/)