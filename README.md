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