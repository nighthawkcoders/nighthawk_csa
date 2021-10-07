# Deployment Guide

## Instruction on purchasing a Raspberry Pi and preparing for Webserver deployment
<details>
  <summary>Click for Raspberry Pi 4 specs!</summary>

### RPi specs
<OL> 
<li> Raspberry Pi 4 4GB Model B with 1.5GHz 64-bit quad-core CPU (4GB RAM) </li>
<li> 32GB Samsung EVO+ Micro SD Card (Class 10) Pre-loaded with NOOBS, USB MicroSD Card Reader </li>
<li> Raspberry Pi 4 Case </li>
<li> 3.5A USB-C Raspberry Pi 4 Power Supply (US Plug) with Noise Filter</li>
<li> Set of Heat Sinks </li>
<li> Micro HDMI to HDMI Cable - 6 foot (Supports up to 4K 60p) </li>
<li> USB-C PiSwitch (On/Off Power Switch for Raspberry Pi 4) </li>
</OL> 
</details>

<details>
  <summary>Click for Raspberry Pi 4 help!</summary>

Purchase Notes:  Keyboard, Mouse, Monitor are optional.  RPi advantages over AWS: 1. One time cost  2. All kinds of tinker projects in IOT realm can be performed using GPIO pins.  As for purchase options, CanaKit (my prefered) has options on Amazon that meet the bulleted list of requirements. There is a new option on raspberrypi.org that describes RPi as built into a keybaord (could be bulky in my use cases).   

Webserver deployment preparation: RPi with NOOBS installed on SSD is very simple.  At boot select Raspberry Pi OS (or pick Ubuntu because of Java and Mongo incompatibilities on RPi OS) and you are on your way.  Since this will be private IP host on your home network, Port Forwarding is required to make your website visible on the Internet. 

Runtime Notes: VNC Viewer can connect to the RPi.  This is a full desktop remote display tool.  RealVNC lets you share full desktop with cohorts.  If you reboot RPi, you need a monitor connected at reboot to maintain VNC screen share functionality.  Reboot will cause screen buffer not to be recognized unless HDMI is present.  There may be a dummy (mini) HDMI plug that could overcomee this issue.  Otherwise, after setup your RPi could be headless.
</details>


## Setting up Java runtime and development
<details>
  <summary>Java JDK and JRE setup!</summary>
Java is two pieces (JDK and JRE), both parts are needed in order to run and build 
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
</details>

## Build and run project
<details>
  <summary>Java setup Maven build tool!</summary>
Prerequisite is Maven install in order to build project
```
$ sudo apt update
$ sudo apt upgrade
$ sudo apt install maven
$ mvn -version
```
</details>

<details>
  <summary>Clone and test run Java projectl!</summary>
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
$ java -jar nighthawk_csa/target/csa-0.0.1-SNAPSHOT.jar
```
Test on localhost browser
```
localhost:8080
```
</details>


## Java service configuration
<details>
  <summary>Establish a service to enable Java Applet to always run!</summary>
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
</details>
  
## Nginx service configuration
<details>
  <summary>Reverse proxy points internet request to enabled Java Applet!</summary>
  
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
  
</details>
    

## Goto freenom.com and register public IP Address to a Domain
<details>
  <summary>DNS setup!</summary>

```diff
- Domain and Public IP Address match your nginx configuration files 
+ REPLACE freenom config with your-domain and your-public-ip, make one or more a records for each project
```

#### This illustration shows configuration of A records within the domain
<img src="https://github.com/nighthawkcoders/nighthawk_csp/blob/master/static/assets/freenom.png">
</details>


## Port Forward your Web application on Internal Host (aka RPi) to the Internet
<details>
  <summary>Port Forwarding setup!</summary>
```diff
- Your Public IP Address needs to connect to your host on Private IP network through Port Forwarding 
+ PROCESS will vary on every home network, but basic premis is to Port forward external port 80 to your Private Host (aka RPi) on internal port 80
```

#### This illustration shows configuration of HTTP, as well as some other common service to access a Private IP host computer through port forwarding.  It is always recommended to minimize access points from internet to your home network.
<img src="https://github.com/nighthawkcoders/nighthawk_csp/blob/master/static/assets/portforward.png" width="600">
</details>
  
## AWS EC2 Setup
<details>
  <summary>Instruction on preparing AWS EC2 instance for Webserver deployment!</summary>
  
Login into your AWS IAM user, search for EC2.

To get started, launch an Amazon EC2 instance, which is a virtual server in the cloud.

![Launch EC2 instance](https://github.com/nighthawkcoders/flask-idea-homesite/tree/master/assets/ec2launch.png)

## Step 1: Choose an Amazon Machine Image (AMI)Cancel and Exit
An AMI is a template that contains the software configuration (operating system, application server, and applications) required to launch your instance. Pick Ubuntu free tier operating system that uses the Linux kernel.  Note, this is very compatible Raspberry Pi's OS.

Select EC2 OS
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/tree/master/assets/ec2os.png" width="600">

## Step 2: Choose an Instance Type
Amazon EC2 provides a wide selection of instance types optimized to fit different use cases. Instances have varying combinations of CPU, memory, storage, and networking capacity.   Stick with Free Tier options, as of this writing t2.mico with free tier designation is suggested.

## No action on Steps #3 through #4
Step 3: Configure Instance Details
Stick with default.  Your will launch a single instance of the AMI by using defaults

Step 4: Add Storage
Stick with default.  Your instance will be launched with 8gb of storage.

## Step 5: Add Tags
Tag your Amazon EC2 resources.  This is not required but you could name your volume for future identification.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/tree/master/assets/ec2tags.png" width="600">

## Step 6: Configure Security Group
A security group is a set of firewall rules that control the traffic for your instance. On this page, you can add rules to allow specific traffic to reach your instance. In this example, a web server is setup to allow Internet traffic to reach EC2 instance, this allows unrestricted access to the HTTP and HTTPS ports.  Also, this example restricts SSH from my IP.

![Select EC2 OS](https://github.com/nighthawkcoders/flask-idea-homesite/tree/master/assets/ec2security.png)

## Step 7: Review Instance Launch
Review your instance launch details. Click Launch to assign a key pair to your instance and complete the launch process.

![Build EC2 Keypair](https://github.com/nighthawkcoders/flask-idea-homesite/tree/master/assets/ec2keypair.png)

## Before you leave your ADMIN session on AWS go to EC2 running instances and find your IPV4 address.

![Find EC2 IPv4](https://github.com/nighthawkcoders/flask-idea-homesite/tree/master/assets/ec2ipv4.png)

# Start a terminal session on you localhost.
</details>


