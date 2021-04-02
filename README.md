# CSA Deployment Guide

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
$ git clone https://github.com/nighthawkcoders/nighthawk_csa.git
$ cd nighthawk_csa
$ ./mvnw package
```
Run and test you java project
```
$ cd
$ java -jar java -jar nighthawk_csa/target/csa-0.0.1-SNAPSHOT.jar
```

## Java service configuration
To run and start application automatically it will require a the JAR file from previous step to run from a .service file. 
In this service file we are providing details the service: 
* Start after “network.target” has been started
* ExecStart is the command that executes JAR

Create a 'service' file like the one below and place it in: /etc/systemd/system/<your_service_file>.service. 
* Replace nighthawk_csa reference or jar file name as applicable to your project
* Ubuntu images replace User=pi with User=ubuntu

    [Unit]
    Description=Java
    After=network.target

    [Service]
    User=pi
    Restart=always
    ExecStart=java -jar /home/pi/nighthawk_csa/target/csa-0.0.1-SNAPSHOT.jar
    
    [Install]
    WantedBy=multi-user.target 
    

Run and enable your service file
```
$ sudo systemctl start nighthawk_csa
$ systemctl status nightawk_csa
```

If succesfull, enable your service file to be persistant on machine
```
$ sudo systemctl enable nighthawk_csa

```

