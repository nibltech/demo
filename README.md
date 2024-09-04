# README #

## What is this repository for? ##

Quickly spin up a Dockerized demo environment for Otelscope. In this demo, we use Otelscope to push logs, metrics, and traces from the webMethods API Gateway and Microservices Runtime to the Grafana stack composed of Grafana, Loki, Prometheus, and Tempo.

## Pre-requisites ##

Prior to setting up this environment, you must:

1. Install Docker
1. Go to https://containers.webmethods.io, login with your account, and generate credentials from the User Profile page.
1. Open a command prompt on your machine and issue a 'docker login' command as follows:
    ```
    docker login sagcr.azurecr.io -u user-nibl-tech -p abcd1234efgh5678iklm
    ```
1. In Windows, navigate to your home directory (i.e. %HOMEPATH%) create a file named .wslconfig if it doesn't exist and add the following lines to it:
    ```
    [wsl2]
    kernelCommandLine = "sysctl.vm.max_map_count=262144"
    ```

## Setting up the demo environment ##

1. Clone this repository onto your machine, e.g.: 
    ```
    git clone --recursive https://github.com/nibltech/demo.git
    ```
1. Switch to the 'otelscope-to-grafana-stack' branch
1. Copy the IBM license XML files to the following directories:
    1. Copy API Gateway license to wM/apig/config with the file name licenseKey.xml
    1. Copy Microservices Runtime license to wM/msr/config with the file name licenseKey.xml
    1. Copy Universal Messaging license to wM/um/config with the file name licence.xml
1. Copy the package files (Nt*.zip) and license file received from Nibble Technologies to the directory wM/nibble. (NOTE: If you do not have an Otelscope distribution or license file yet, please reach out to info@nibl.tech and we'll set you up with a trial license.)
1. Open a DOS command prompt and navigate to the location where your repository was cloned.
1. In Windows, navigate to scripts/bat, and on MacOS or Linux, navigate to scripts/sh.
1. Run 'build.[bat|sh]'. This will build 3 Docker images:
    * nibl-apig: webMethods API Gateway
    * nibl-msr: webMethods Microservices Runtime
    * nibl-um: webMethods Universal Messaging
1. Run 'up.[bat|sh]'. This will start the 3 containers plus containers for the OpenTelemetry Collector, Grafana, Loki, Prometheus, and Tempo.
    ```
    [+] Running 48/48
     ✔ newman Pulled                                                                                                                                                                                                                                                                  29.2s
       ✔ 7264a8db6415 Pull complete                                                                                                                                                                                                                                                   15.7s
       ✔ eee371b9ce3f Pull complete                                                                                                                                                                                                                                                   22.2s
       ✔ 93b3025fe103 Pull complete                                                                                                                                                                                                                                                   22.4s
       ✔ d9059661ce70 Pull complete                                                                                                                                                                                                                                                   22.5s
       ✔ 950c5e76b10c Pull complete                                                                                                                                                                                                                                                   27.4s
       ✔ e8d77df97bba Pull complete                                                                                                                                                                                                                                                   27.4s
     ✔ grafana Pulled                                                                                                                                                                                                                                                                 13.9s
       ✔ 4abcf2066143 Pull complete                                                                                                                                                                                                                                                    0.7s
       ✔ b03b7e4dc6ec Pull complete                                                                                                                                                                                                                                                    0.7s
       ✔ bc75fdc748a7 Pull complete                                                                                                                                                                                                                                                    1.3s
       ✔ ee7634418d4d Pull complete                                                                                                                                                                                                                                                    1.5s
       ✔ bc8cbc4d3b53 Pull complete                                                                                                                                                                                                                                                    1.6s
       ✔ b28fcad58785 Pull complete                                                                                                                                                                                                                                                    1.6s
       ✔ 523b402e7b72 Pull complete                                                                                                                                                                                                                                                    7.2s
       ✔ fe85b93446d3 Pull complete                                                                                                                                                                                                                                                   12.2s
       ✔ c8e0096204b6 Pull complete                                                                                                                                                                                                                                                   12.2s
       ✔ 0f74ad7318b5 Pull complete                                                                                                                                                                                                                                                   12.3s
     ✔ loki Pulled                                                                                                                                                                                                                                                                    17.9s
       ✔ c6a83fedfae6 Already exists                                                                                                                                                                                                                                                   0.0s
       ✔ 0f20e825297d Pull complete                                                                                                                                                                                                                                                   13.6s
       ✔ 92134d5e22c6 Pull complete                                                                                                                                                                                                                                                   16.0s
       ✔ 9905beb22ae4 Pull complete                                                                                                                                                                                                                                                   16.1s
       ✔ c9bc48169e35 Pull complete                                                                                                                                                                                                                                                   16.2s
       ✔ 41c82947998a Pull complete                                                                                                                                                                                                                                                   16.2s
     ✔ tempo Pulled                                                                                                                                                                                                                                                                   21.6s
       ✔ d25f557d7f31 Pull complete                                                                                                                                                                                                                                                   14.5s
       ✔ 58d8c981f42d Pull complete                                                                                                                                                                                                                                                   14.8s
       ✔ c49c02809f91 Pull complete                                                                                                                                                                                                                                                   19.8s
       ✔ 0ed949ddcab9 Pull complete                                                                                                                                                                                                                                                   19.8s
       ✔ e7bf9fa60037 Pull complete                                                                                                                                                                                                                                                   19.9s
     ✔ otel-collector Pulled                                                                                                                                                                                                                                                          14.3s
       ✔ c449b9766f64 Pull complete                                                                                                                                                                                                                                                    2.7s
       ✔ 75af9fbb5b0d Pull complete                                                                                                                                                                                                                                                   12.6s
       ✔ e96c0d151180 Pull complete                                                                                                                                                                                                                                                   12.6s
     ✔ prometheus Pulled                                                                                                                                                                                                                                                              18.8s
       ✔ 9fa9226be034 Pull complete                                                                                                                                                                                                                                                    6.2s
       ✔ 1617e25568b2 Pull complete                                                                                                                                                                                                                                                    6.6s
       ✔ ec307c9fbf62 Pull complete                                                                                                                                                                                                                                                   14.7s
       ✔ d4e715947f0e Pull complete                                                                                                                                                                                                                                                   16.7s
       ✔ c522420720c6 Pull complete                                                                                                                                                                                                                                                   16.8s
       ✔ 18d28937c421 Pull complete                                                                                                                                                                                                                                                   16.8s
       ✔ 873361efd54d Pull complete                                                                                                                                                                                                                                                   16.9s
       ✔ dd44465db85c Pull complete                                                                                                                                                                                                                                                   16.9s
       ✔ 0636908550c9 Pull complete                                                                                                                                                                                                                                                   17.0s
       ✔ cd795675b8a2 Pull complete                                                                                                                                                                                                                                                   17.0s
       ✔ 407f3c6e3260 Pull complete                                                                                                                                                                                                                                                   17.1s
       ✔ 67fb76c620a2 Pull complete                                                                                                                                                                                                                                                   17.1s
    [+] Running 10/10
     ✔ Network demo_default             Created                                                                                                                                                                                                                                        0.1s
     ✔ Container demo-msr-1             Started                                                                                                                                                                                                                                        2.4s
     ✔ Container prometheus-1           Started                                                                                                                                                                                                                                        2.4s
     ✔ Container loki-1                 Started                                                                                                                                                                                                                                        2.4s
     ✔ Container demo-um-1              Started                                                                                                                                                                                                                                        2.7s
     ✔ Container demo-newman-1          Started                                                                                                                                                                                                                                        2.3s
     ✔ Container demo-apig-1            Started                                                                                                                                                                                                                                        2.8s
     ✔ Container demo-tempo-1           Started                                                                                                                                                                                                                                        2.5s
     ✔ Container demo-otel-collector-1  Started                                                                                                                                                                                                                                        2.0s
     ✔ Container demo-grafana-1         Started
    ```
1. On startup, the IS will automatically register the Nibble Demo API with the API Gateway. You can monitor the MSR server.log to determine when the MSR and APIG have started successfully. Run the command:
    ```
    logs.[bat|sh] -f msr
    ```
    ... and you should eventually see the following messages:
    ```
    demo-msr-1  | ISSERVER|| 2023-10-03 03:12:38 GMT [ISP.0090.0003C] (tid=243) [traceId=2080e2b31831ea25443cbfdce9423a79 spanId=82b90a547cd32f01] Waiting for IS running on apig:5555 to start...
    demo-msr-1  | ISSERVER|| 2023-10-03 03:12:40 GMT [ISP.0090.0003C] (tid=243) [traceId=2080e2b31831ea25443cbfdce9423a79 spanId=82b90a547cd32f01] Waiting for IS running on apig:5555 to start...
    demo-msr-1  | ISSERVER|| 2023-10-03 03:12:42 GMT [ISP.0090.0003C] (tid=243) [traceId=2080e2b31831ea25443cbfdce9423a79 spanId=82b90a547cd32f01] Waiting for IS running on apig:5555 to start...
    demo-msr-1  | ISSERVER|| 2023-10-03 03:12:42 GMT [ISP.0090.0003C] (tid=243) [traceId=2080e2b31831ea25443cbfdce9423a79 spanId=82b90a547cd32f01] Connected to IS running on apig:5555 successfully!
    ```
1. Once the containers are up, you can use the 'compose.[bat|sh]' script as a shortcut to check on the containers' status or interacting with the containers themselves, e.g.:
    * compose.[bat|sh] ps
    * compose.[bat|sh] exec apig bash

## Running the the demo ##

1. First, navigate to the API Gateway and confirm the Nibble Demo API was automatically registered:
    1. Open http://localhost:9072 to access the API Gateway UI
    1. Navigate to APIs
    1. You should see the 'nibble-demo-api' listed
1. Next, confirm Otelscope was properly installed on the API Gateway Integration Server:
    1. Open http://localhost:6660 to access the API Gateway Integration Server administration console
    1. Navigate to Packages
    1. Confirm the package NtOtelscope is installed
    1. Click on the home icon for the NtOtelscope page
    1. Confirm Otelscope shows a state of 'Running'
1. Repeat the steps above against the MSR:
    1. Open http://localhost:5550 to access the MSR administration console
    1. Navigate to Packages
    1. Confirm the package NtOtelscope is installed
    1. Click on the home icon for the NtOtelscope page
    1. Confirm Otelscope shows a state of 'Running'
1. Now you can start pumping some data to the Nibble Demo API by running data.[bat|sh] and tailing the MSR logs again:
    1. In Windows, navigate to scripts/bat, and on MacOS or Linux, navigate to scripts/sh.
    1. Run:
        ```
        data.[bat|sh] ; logs.[bat|sh] -f msr
        ```
        ... and you should see the following:
        ```
        demo-msr-1  | ISSERVER|| 2023-10-03 03:18:48 GMT [ISP.0090.0003C] (tid=246) [traceId=96b30dce6d4afcff612d72cb1ed2b49f spanId=e00d7c9ecb40fc98] Received Order b396869f-151c-45f7-9b10-6b4f631166c1
        demo-msr-1  | ISSERVER|| 2023-10-03 03:18:49 GMT [ISP.0090.0003C] (tid=243) [traceId=8df0d1a9a6f08c0b3f26b2abfeb5ce78 spanId=0761bfa6c341a29f] Received Order 7751b1d3-c02f-4643-b141-ebf3ebcefc73
        demo-msr-1  | ISSERVER|| 2023-10-03 03:18:50 GMT [ISP.0090.0003C] (tid=246) [traceId=0ed0eeafbe5cdfa20c660d7f6de9b219 spanId=243a33fd5e2ead10] Received Order 9c43d270-171b-4cc6-8820-25a5aed79ed8
        ```
1. The inclusion of a traceId and spanId in the log entries above, indicate that Otelscope is up and running successfully
1. Time to check Grafana for metrics, traces, and logs:
    1. Open http://localhost:3000 to access the Grafana web UI
    1. Click on Explore
    1. Choose Loki from the drop-down
    1. Under Label filters, set 'job=msr' to look for MSR logs
    1. Run the query. You should see log events that were emitted by the MSR
    1. Click on a log entry with the message "Received Order" to expand all the attributes
    1. Scroll down to see the traceId and the Tempo button
    1. Click the Tempo button to take you directly to that trace
    1. Explore the trace
    1. You may also choose to search for traces directly by going to Explore and choosing Tempo from the drop-down
    1. Next, let's explore metrics
    1. Click on Explore and choose Prometheus from the drop-down
    1. Explore the metrics in the 'Select metrics' drop-down and experiment with filtering the metrics
    1. Finally, go to Dashboards and explore the dashboards under Otelscope Demo
