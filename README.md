# nighthawk_csa
CSA Course Guide

## Setting up Java runtime and development
Java is two pieces, we will need both if you want to run and build 
```
$ sudo apt update
$ sudo apt upgrade
```
Install Java Runtime Environment
```
$ sudo apt install default-jre
$ java -version
```
Install Java Development Kit
```
$ sudo apt install default-jdk
$ javac -version
```

## Build and run project
Prerequisite is Maven install, as well as MongoDB and SQLite (above)
```
$ sudo apt update
$ sudo apt upgrade
$ sudo apt install maven
$ mvn -version
```
Clone and build spring-idea repository
```
$ cd
$ git clone https://github.com/nighthawkcoders/spring-idea.git
$ cd spring-idea
$ ./mvnw package
```
Run and test you java project
```
$ cd
$ java -jar spring-idea/target/serving-web-content-0.0.1-SNAPSHOT.jar
