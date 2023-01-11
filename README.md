# spring-autoconfiguration-custom-log-and-metrics

A sample of how to implements spring auto configuration for loging outgoing request and 
adding custom Prometheus metrics to it. Also  I am showing here how to add custom logic to all your web client the proper way. 

This sample is a maven project composed by in three modules : 
 
<!-- TOC -->
* [custom-metrics-customizer](#custom-metrics-customizer)
* [custom-metrics-customizer-app](#custom-metrics-customizer-app)
* [custom-metrics-customizer-prometheus-server](#custom-metrics-customizer-prometheus-server)
<!-- TOC -->

# custom-metrics-customizer

This module is the spring autoconfiguration library used by the app. 

# custom-metrics-customizer-app 

This module is the application using the library above.

# custom-metrics-customizer-prometheus-server

This modules is the prometheus server. 


How to run it : 

Dockerize your spring app : 

```bash
mvn compile jib:dockerBuild
```

Run your promethus and your spring app: 

```bash
docker run -d --name=prometheus -p 9090:9090 --net mynet -v /home/william/Documents/william/it-projects/spring-autoconfiguration-custom-log-and-metrics/custom-metrics-customizer-prometheus-server/src/main/resources/prometheus-config.yaml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml

docker run -p 8081:8081 --name=app --net mynet -d custom-metrics-customizer-app
```

Navigate to http://localhost:9090

Explore all your customs metrics with promQL querys into the prometheus interface : 

You can see here our custom tag clientName : 

```bash
http_server_requests_seconds_count{clientName = "userApi"}
```