# Deployment Guide

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
Prerequisite is Maven install in order to build project
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
Run your java project, after test ctl-C to stop service
```
$ cd
$ java -jar java -jar nighthawk_csa/target/csa-0.0.1-SNAPSHOT.jar
```
Test on localhost browser
```
localhost:8080
```



## Java service configuration
To run and start application automatically it will require a the JAR file from previous step to run from a .service file. 
In this service file we are providing details of the java runtime service: 
* start after “network.target” has been started
* the ExecStart is the same as command you validated to executes JAR

Create a 'service' file as administratr: 
* sudo nano <filename> 
* change nighthawk_csa reference or jar file name as applicable to your project
* replace User=pi with User=ubuntu if applicable

File is located at /etc/systemd/system/nighthawk_csa.service. 
```
[Unit]
Description=Java
After=network.target

[Service]
User=ubuntu
Restart=always
ExecStart=java -jar /home/ubuntu/nighthawk_csa/target/csa-0.0.1-SNAPSHOT.jar

[Install]
WantedBy=multi-user.target 
```    

Run and enable your service file
```
$ sudo systemctl start nighthawk_csa
$ systemctl status nightawk_csa
```

If succesfull, enable your service file to be persistant on machine
```
$ sudo systemctl enable nighthawk_csa

```

## Nginx service configuration
File is located at /etc/nginx/sites-available/nighthawk_csa 
```
server {
    listen 80;
    server_name csa.nighthawkcoders.cf;

    location / {
        proxy_pass http://localhost:8080;
    }
}
```
Test the configuration to make sure there are no errors:

    $ sudo ln -s /etc/nginx/sites-available/nighthawk_csa /etc/nginx/sites-enabled
    $ sudo nginx -t

If there are no errors, restart NGINX so the changes take effect:

    $ sudo systemctl restart nginx
    
