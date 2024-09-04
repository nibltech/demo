# README #

## What is this repository for? ##

Quickly spin up a Dockerized demo environment for Otelscope.

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
1. Run 'up.[bat|sh]'. This will start the 3 containers above in addition to the OpenTelemetry Collector container.
    ```
    [+] Running 6/6
      ✔ otel-collector 3 layers [⣿⣿⣿]      0B/0B     Pulled                                                                                                     10.4s 
       ✔ c974271cb9a3 Pull complete                                                                                                                               0.4s 
       ✔ ea407d55838f Pull complete                                                                                                                               9.3s 
       ✔ 0524fe5c5a9e Pull complete                                                                                                                               9.4s 
      ✔ newman 1 layers [⣿]      0B/0B     Pulled                                                                                                                14.4s 
       ✔ fa637f525e1a Pull complete                                                                                                                              13.5s 
    [+] Building 0.0s (0/0)
    [+] Running 6/6
     ✔ Network demo_default             Created                                                                                                                   0.0s 
     ✔ Container demo-apig-1            Started                                                                                                                   1.7s 
     ✔ Container demo-newman-1          Started                                                                                                                   1.1s 
     ✔ Container demo-msr-1             Started                                                                                                                   1.5s 
     ✔ Container demo-um-1              Started                                                                                                                   1.3s 
     ✔ Container demo-otel-collector-1  Started                                                                                                                   1.3s 
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

1. After MSR and APIG are up and running, first navigate to the API Gateway and confirm the Nibble Demo API was automatically registered:
    1. Open http://localhost:9072 to access the API Gateway UI
    1. Navigate to APIs
    1. You should see the nibble-demo-api listed
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
1. The inclusion of a traceId and spanId in the log entries above, indicate that Otelscope is up and running successfully. Time to check your Otel Collector target for metrics, traces, and logs.
1. Navigate to the new folder called 'logs' under the otel folder of your cloned demo project
1. Open the files and otlpLogs.json, otlpMetrics.json, and otlpTraces.json to observe as logs, metrics, and traces are sent from Otelscope to the OpenTelemetry Collector
