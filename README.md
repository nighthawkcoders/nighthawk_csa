# CSA Ubuntu Deployment Guide

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

Create a 'service' file like the one below and place it in: /etc/systemd/system/<your_service_file>.service. Replace nighthawk reference and other content as applicable to your project.

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
● nighthawk_csa.service - Java
   Loaded: loaded (/etc/systemd/system/nighthawk_csa.service; disabled; vendor preset: enabled)
   Active: active (running) since Fri 2021-04-02 06:35:34 PDT; 5s ago
 Main PID: 22968 (java)
    Tasks: 21 (limit: 4915)
   CGroup: /system.slice/nighthawk_csa.service
           └─22968 /usr/bin/java -jar /home/pi/nighthawk_csa/target/csa-0.0.1-SNAPSHOT.jar

Apr 02 06:35:34 raspberrypi systemd[1]: Started Java.
Apr 02 06:35:39 raspberrypi java[22968]:   .   ____          _            __ _ _
Apr 02 06:35:39 raspberrypi java[22968]:  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
Apr 02 06:35:39 raspberrypi java[22968]: ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
Apr 02 06:35:39 raspberrypi java[22968]:  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
Apr 02 06:35:39 raspberrypi java[22968]:   '  |____| .__|_| |_|_| |_\__, | / / / /
Apr 02 06:35:39 raspberrypi java[22968]:  =========|_|==============|___/=/_/_/_/
Apr 02 06:35:39 raspberrypi java[22968]:  :: Spring Boot ::                (v2.4.4)


Emable your service file, persistant on machine
```
$ sudo systemctl enable nighthawk_csa

```

