# spring-autoconfiguration-custom-log-and-metrics

A sample of how to implements spring auto configuration for loging outgoing request and 
adding custom Prometheus metrics to it. Also  I am showing here how to add custom logic to all your web client the proper way. 

This sample is a maven project composed by in three modules : 
 
<!-- TOC -->
* [spring-autoconfiguration-custom-log-and-metrics](#spring-autoconfiguration-custom-log-and-metrics)
* [custom-metrics-customizer](#custom-metrics-customizer)
* [custom-metrics-customizer-app](#custom-metrics-customizer-app)
* [custom-metrics-customizer-prometheus-server](#custom-metrics-customizer-prometheus-server)
    * [Dockerize your spring app :](#dockerize-your-spring-app-)
    * [Run the entire stack](#run-the-entire-stack)
    * [Navigate to http://localhost:9090](#navigate-to-httplocalhost9090)
<!-- TOC -->

# custom-metrics-customizer

This module is the spring autoconfiguration library used by the app.

Here you just have to create the beans you want to be available to your app or any users of your autoconfiguration library. 

As you can see I add a custom behavior to my webClient Bean by implementing the WebClientCustomizer interface. 

Also, I decided to add custom metrics on my webClient to see the clientName. 

On each aspect of autoconfiguration library, you have to create a `@Configuration` annotated class to expose your desired beans. 

Important : Since spring 3, the spring.factories file is no more used, and you will have to use `src/main/resources/org.springframework.boot.autoconfigure.AutoConfiguration.imports file`. 

# custom-metrics-customizer-app 

This module is the application using the library above.h

Just for the example, I created a very simple REST API to use the webClient bean created by the autoconfiguration library.

This REST API will be scraped by the prometheus server and we will se our customs metrics appear. 

Off course you have to add the actuator dependency to your pom.xml file : 

```xml

<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
And then don't forget to expose metrics endpoints, just add this to your configuration file : 

```properties
management.endpoints.web.exposure.include=*
```

# custom-metrics-customizer-prometheus-server

This modules is the prometheus server. 

This server will scrape your app on the /actuator/prometheus endpoint, exposed. 

How to run it : 

### Dockerize your spring app : 

The dockerization of the spring is made very simple by the help of this google maven plugin : 

```xml
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>3.3.1</version>
    <configuration>
        <to>
            <image>custom-metrics-customizer-app</image>
        </to>
    </configuration>
</plugin>
```
Once this plugin added, you just have to run this command in the root of the app module : 

```bash
mvn compile jib:dockerBuild
```

This will create for you a brand new docker image to your local docker repo on your laptop. 

### Run the entire stack

Then run your prometheus and your spring app: 

```bash

docker run -p 8081:8081 --name=app --net mynet -d custom-metrics-customizer-app

docker run -d --name=prometheus -p 9090:9090 --net mynet -v /home/william/Documents/william/it-projects/spring-autoconfiguration-custom-log-and-metrics/custom-metrics-customizer-prometheus-server/src/main/resources/prometheus-config.yaml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml

```

As you notice, you will have to create a docker virtual network for your containers to be able to talk to each others. This the --net option of the dockers commands. 

### Navigate to http://localhost:9090

Explore all your customs metrics with promQL query into the prometheus interface : 

You can see here our custom tag clientName, for example : 

```bash
http_server_requests_seconds_count{clientName = "userApi"}
```