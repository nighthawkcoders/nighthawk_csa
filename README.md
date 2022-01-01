{% include navigation.html %}

# <img src="src/resources/static/images/ncs_logo.png" height="60" alt=""> GitHub Activity


# Deployment Guide

## Raspberry Pi (RPi) Purchase and Setup
<details>
  <summary>Click for Raspberry Pi 4 purchase!</summary>

### RPi recommended specs
<OL> 
<li> Raspberry Pi 4 4GB Model B with 1.5GHz 64-bit quad-core CPU (4GB RAM) </li>
<li> 32GB Samsung EVO+ Micro SD Card (Class 10) Pre-loaded with NOOBS, USB MicroSD Card Reader </li>
<li> Raspberry Pi 4 Case </li>
<li> 3.5A USB-C Raspberry Pi 4 Power Supply (US Plug) with Noise Filter</li>
<li> Set of Heat Sinks </li>
<li> Micro HDMI to Full HDMI Cable - 6 foot (Supports up to 4K 60p) </li>
<li> USB-C PiSwitch (On/Off Power Switch for Raspberry Pi 4) </li>
</OL> 
Purchase Notes:  Keyboard, Mouse, Monitor are optional.  RPi advantages over AWS: 1. One time cost  2. All kinds of tinker projects in IOT realm can be performed using GPIO pins.  As for purchase options, CanaKit (my prefered) has options on Amazon that meet the bulleted list of requirements. There is a new option on raspberrypi.org that describes RPi as built into a keybaord (could be bulky in my use cases). 
</details>

<details>
  <summary>Click for Raspberry Pi 4 OS and Software!</summary>

RPi OS deployment preparation: RPi with NOOBS installed on SSD is very simple.  At boot select Raspberry Pi OS and you are on your way.  Since this will be private IP host on your home network, ultimately Port Forwarding is required to make your machine visible to the Internet. 

VNC Viewer can connect to the RPi for desktop display.  This is a full desktop remote display tool, SSH is a terminal only option.  RealVNC lets you share full desktop with cohorts.  If you reboot RPi, you need a monitor connected at reboot to maintain VNC screen share functionality.  Reboot will cause screen buffer not to be recognized unless HDMI is present.  There may be a dummy (mini) HDMI plug that could overcomee this issue.  Otherwise, after setup your RPi could be headless.
</details>

## AWS EC2 Setup (an alternative to RPi)
<details>
  <summary>Instruction on preparing AWS EC2 instance for Webserver deployment!</summary>
  
Login into your AWS IAM user, search for EC2.

#### To get started, launch an Amazon EC2 instance, which is a a computer server in the cloud.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/ec2launch.png">

## Step 1: Choose an Amazon Machine Image (AMI)Cancel and Exit
#### An AMI is a template that contains the software configuration (operating system, application server, and applications) required to launch your instance. Pick Ubuntu free tier operating system that uses the Linux kernel.  Note, this is very compatible Raspberry Pi's OS.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/ec2os.png">

## Step 2: Choose an Instance Type
Amazon EC2 provides a wide selection of instance types optimized to fit different use cases. Instances have varying combinations of CPU, memory, storage, and networking capacity.   Stick with Free Tier options, as of this writing t2.mico with free tier designation is suggested.

## No action on Steps #3 through #4
Step 3: Configure Instance Details
Stick with default.  Your will launch a single instance of the AMI by using defaults

Step 4: Add Storage
Stick with default.  Your instance will be launched with 8gb of storage.

## Step 5: Add Tags
#### Tag your Amazon EC2 resources.  This is not required but you could name your volume for future identification.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/ec2tags.png">

## Step 6: Configure Security Group
#### A security group is a set of firewall rules that control the traffic for your instance. On this page, you can add rules to allow specific traffic to reach your instance. In this example, a web server is setup to allow Internet traffic to reach EC2 instance, this allows unrestricted access to the HTTP and HTTPS ports.  Also, this example restricts SSH from my IP.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/ec2security.png">

## Step 7: Review Instance Launch
#### Review your instance launch details. Click Launch to assign a key pair to your instance and complete the launch process.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/ec2keypair.png">

## Before you leave your ADMIN session on AWS go to EC2 running instances and find your IPV4 address.
#### Find IPv4 address and IPv4 DNS
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/ec2ipv4.png">

# Start a terminal session on you localhost.
</details>

## Setting up Java Web Application
<details>
  <summary>Java setup, Maven build and Runtime Test!</summary>
  
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

Maven is required to build project
```
$ sudo apt update
$ sudo apt upgrade
$ sudo apt install maven
$ mvn -version
```
</details>

<details>
  <summary>Clone, build and test Java Web Application</summary>
  
Clone and build repository
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


## Prepare Java Web Application for Internet access
<details>
  <summary>Establish a service to enable Java Web Application to always run on Server!</summary>
To run and start application automatically it will require a the JAR file from previous step to run from a .service file. 
In this service file we are providing details of the java runtime service: 
* start after “network.target” has been started
* the ExecStart is the same as command you validated to executes JAR

Create a 'service' file as administratr: 
* sudo nano <filename> 
* change nighthawk_csa reference or jar file name as applicable to your project
* replace User=pi with User=ubuntu if on AWS

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

<details>
  <summary>Enable Nginx to retrieve Java Web Application on external request (Reverse Proxy)!</summary>
  
File is located at /etc/nginx/sites-available/nighthawk 
```
server {
    listen 80;
    listen [::]:80;
    server_name nighthawkcodingsociety.com;

    location / {
        proxy_pass http://localhost:8080;
    }
}
```
Link configuration to enabled location and test to make sure there are no errors:

    $ sudo ln -s /etc/nginx/sites-available/nighthawk_csa /etc/nginx/sites-enabled
    $ sudo nginx -t

If there are no errors, restart NGINX so the changes take effect:

    $ sudo systemctl restart nginx

Advanced, if you setup a second application, you will need a subdomain (csa) and alter runtime port (8081).  File is located at /etc/nginx/sites-available/nighthawk_csa 
```
server {
    listen 80;
    listen [::]:80;
    server_name csa.nighthawkcodingsociety.com;

    location / {
        proxy_pass http://localhost:8081;
    }
}
```
  
</details>
    

## Prepare Internet to route to Nginx server
<details>
  <summary>Goto freenom.com and register your public IP Address to a Domain!</summary>

```diff
- Domain and Public IP Address match your nginx configuration files 
+ REPLACE freenom config with your-domain and your-public-ip, make one or more a records for each project
```

#### This illustration shows configuration of A records within the domain.  As an advanced configuration, it shows configurations of CNAME records which are used for setting up subdomains.  All records resolve to the same IP address.
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/dns.png" width="600">
</details>


<details>
  <summary>Port Forward your public IP address access to your Nginx/RPi server!</summary>

```diff
- Your Public IP Address needs to connect to your host on Private IP network through Port Forwarding 
+ PROCESS will vary on every home network, but basic premiss is to Port forward HTTP external port 80 to your Private Host (aka RPi) on internal port 80.  Also HTTPS external port 443 to Private HOST internal port 443
```

#### This illustration shows configuration of HTTP, as well as some other common service to access a Private IP host computer through port forwarding.  It is always recommended to minimize access points from internet to your home network.
<img src="https://github.com/nighthawkcoders/nighthawk_csp/blob/master/static/assets/portforward.png" width="600">

#### This illustration shows configuration of HTTPS, required for SSL
<img src="https://github.com/nighthawkcoders/flask-idea-homesite/blob/master/assets/SSLPortForward.png" width="600">

</details>

## Prepare Nginx configurations for HTTPS

<details>
  <summary>Use Certbot on Nginx to support HTTPS</summary>
  
There is a process using the tool "certbot --nginx" that will turn your original nginx configuration file into a https supported configuration.  To install certbot, you first need to install snap.

Installing snap on Raspberry Pi OS requires reboot.
```
$ sudo apt update
$ sudo apt install snapd
$ sudo reboot
```

After this, install the core snap and refresh in order to get the latest snapd.
```
$ sudo snap install core; sudo snap refresh core
```

Run this command on the command line on the machine to install Certbot.
```
$ sudo snap install --classic certbot
$ sudo ln -s /snap/bin/certbot /usr/bin/certbot
```

Run this command to get a certificate and have Certbot edit your nginx configuration automatically to serve it, turning on HTTPS access in a single step.  Sample dialog and prompt responses follow.

```
$ sudo certbot --nginx
```
```
Saving debug log to /var/log/letsencrypt/letsencrypt.log
Plugins selected: Authenticator nginx, Installer nginx

Which names would you like to activate HTTPS for?
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
1: nighthawkcodingsociety.com
2: csa.nighthawkcodingsociety.com
3: csp.nighthawkcodingsociety.com
4: flm.nighthawkcodingsociety.com
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Select the appropriate numbers separated by commas and/or spaces, or leave input
blank to select all options shown (Enter 'c' to cancel):    
Cert not yet due for renewal

You have an existing certificate that has exactly the same domains or certificate name you requested and isn't close to expiry.
(ref: /etc/letsencrypt/renewal/nighthawkcodingsociety.com-0001.conf)

What would you like to do?
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
1: Attempt to reinstall this existing certificate
2: Renew & replace the cert (limit ~5 per 7 days)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Select the appropriate number [1-2] then [enter] (press 'c' to cancel): 2
Renewing an existing certificate
Performing the following challenges:
http-01 challenge for nighthawkcodingsociety.com
http-01 challenge for csa.nighthawkcodingsociety.com
http-01 challenge for cso.nighthawkcodingsociety.com
http-01 challenge for flm.nighthawkcodingsociety.com
Waiting for verification...
Cleaning up challenges
Deploying Certificate to VirtualHost /etc/nginx/sites-enabled/nighthawk_society
Deploying Certificate to VirtualHost /etc/nginx/sites-enabled/nighthawk_csa
Deploying Certificate to VirtualHost /etc/nginx/sites-enabled/nighthawk_csp
Deploying Certificate to VirtualHost /etc/nginx/sites-enabled/nighthawk_flm

Please choose whether or not to redirect HTTP traffic to HTTPS, removing HTTP access.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
1: No redirect - Make no further changes to the webserver configuration.
2: Redirect - Make all requests redirect to secure HTTPS access. Choose this for
new sites, or if you're confident your site works on HTTPS. You can undo this
change by editing your web server's configuration.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Select the appropriate number [1-2] then [enter] (press 'c' to cancel): 2
Traffic on port 80 already redirecting to ssl in /etc/nginx/sites-enabled/nighthawk_society
Traffic on port 80 already redirecting to ssl in /etc/nginx/sites-enabled/nighthawk_csa
Traffic on port 80 already redirecting to ssl in /etc/nginx/sites-enabled/nighthawk_csp
Traffic on port 80 already redirecting to ssl in /etc/nginx/sites-enabled/nighthawk_flm

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Your existing certificate has been successfully renewed, and the new certificate
has been installed.

The new certificate covers the following domains:
https://nighthawkcodingsociety.com, 
https://csa.nighthawkcodingsociety.com, 
https://csp.nighthawkcodingsociety.com, and
https://flm.nighthawkcodingsociety.com,

You should test your configuration at:
https://www.ssllabs.com/ssltest/analyze.html?d=nighthawkcodingsociety.com
https://www.ssllabs.com/ssltest/analyze.html?d=csa.nighthawkcodingsociety.com
https://www.ssllabs.com/ssltest/analyze.html?d=csp.nighthawkcodingsociety.com
https://www.ssllabs.com/ssltest/analyze.html?d=flm.nighthawkcodingsociety.com
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

IMPORTANT NOTES:
 - Congratulations! Your certificate and chain have been saved at:
   /etc/letsencrypt/live/nighthawkcodingsociety.com-0001/fullchain.pem
   Your key file has been saved at:
   /etc/letsencrypt/live/nighthawkcodingsociety.com-0001/privkey.pem
   Your cert will expire on 2022-03-06. To obtain a new or tweaked
   version of this certificate in the future, simply run certbot again
   with the "certonly" option. To non-interactively renew *all* of
   your certificates, run "certbot renew"
 - If you like Certbot, please consider supporting our work by:

   Donating to ISRG / Let's Encrypt:   https://letsencrypt.org/donate
   Donating to EFF:                    https://eff.org/donate-le

```
</details>

<details>
  <summary>Illustration of Nginx configuration after Certbot additions for HTTPS</summary>
  
This configuration is not typed.  It is provided here to illustrate how SSL is incorporated.   This file is changed by certbot from our  originally typed nginx configuration.  Pure magic!!!

```
server { 

    server_name csa.nighthawkcodingsociety.com;

    location / {
        include proxy_params;
        proxy_pass http://localhost:8081;
    }


    listen [::]:443 ssl; # managed by Certbot
    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/nighthawkcodingsociety.com-0001/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/nighthawkcodingsociety.com-0001/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}

server {
    if ($host = csa.nighthawkcodingsociety.com) {
        return 301 https://$host$request_uri;
    } # managed by Certbot

    listen 80;
    listen [::]:80; 

    server_name csa.nighthawkcodingsociety.com;
    return 404; # managed by Certbot

```
</details>
