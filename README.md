# README #

## What is this repository for? ##

Quickly spin up a Dockerized demo environment for Otelscope. In this demo, we use Otelscope to push logs, metrics, and traces from the webMethods API Gateway and Microservices Runtime to the Grafana stack composed of Grafana, Loki, Prometheus, and Tempo. The setup also includes two spring services that show traces that include spans from non-webMethods components.

## Pre-requisites ##

Prior to setting up this environment, you must:

1. Install Docker
1. Go to https://containers.webmethods.io, login with your account, and generate credentials from the User Profile page.
1. Open a command prompt on your machine and issue a 'docker login' command as follows:
    ```
    docker login ibmwwebmethods.azurecr.io -u user-nibl-tech -p abcd1234efgh5678iklm
    ```
1. If using Windows, navigate to your home directory (i.e. %HOMEPATH%) create a file named .wslconfig if it doesn't exist and add the following lines to it:
    ```
    [wsl2]
    kernelCommandLine = "sysctl.vm.max_map_count=262144"
    ```
    If using linux, use the instructions appropriate for the flavor of the OS. For example, in RHEL 9, edit the /etc/sysctl.conf file to add the following:
   ```
   vm.max_map_count = 262144
   ```
   In order for the changes to reflect without a restart run"
   ```
   sysctl --system
   ```

## Setting up the demo environment ##

1. Clone this repository onto your machine, e.g.: 
    ```
    git clone --recursive https://github.com/nibltech/demo.git
    ```
1. Switch to the 'otelscope-with-spring' branch
1. Copy the package files (Nt*.zip) and license file received from Nibble Technologies to the directory wM/nibble. (NOTE: If you do not have an Otelscope distribution or license file yet, please reach out to info@nibl.tech and we'll set you up with a trial license.)
1. Open a DOS command prompt, or a Gitbash terminal, and navigate to the location where your repository was cloned.
1. In Windows, navigate to scripts/bat, and on MacOS or Linux, navigate to scripts/sh.
1. Make sure the JAVA_HOME environment variable is set to a JDK location.
1. Run 'build.[bat|sh]'. This will build 6 Docker images:
    * nibl-apig: webMethods API Gateway
    * nibl-msr: webMethods Microservices Runtime
    * nibl-um: webMethods Universal Messaging
    * nibl-api-cnsumer: Springboot application to send API requests
    * nibl-order: Springboot application to provide order IDs
    * nibl-inventory: Springboot application that updates inventory
 
1. Run 'up.[bat|sh]'. This will start all the containers plus containers for the OpenTelemetry Collector, Grafana, Loki, Prometheus, and Tempo.
    ```
✔ Container demo-inventory-1
 Started                                                                          0.7s
 ✔ Container demo-otel-lgtm-1     Started                                                                          1.2s
 ✔ Container demo-orders-1        Started                                                                          0.8s
 ✔ Container demo-api-consumer-1  Started                                                                          0.6s
 ✔ Container demo-msr-1           Started                                                                          1.0s
 ✔ Container demo-apig-1          Started                                                                          1.1s
 ✔ Container demo-newman-1        Started                                                                          1.0s
 ✔ Container elasticsearch        Started                                                                          0.9s
 ✔ Container demo-um-1            Started                                                                          0.7s

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
1. Once the containers are up, the MSR logs will show some Orders being submitted. This is because the demo is
configured to submit these.

```
msr-1  | ISSERVER|| 2026-03-15 03:41:28 GMT [ISP.0090.0003C] (tid=236) [traceId=e5854fdb7341f139d804f0f5061d469a spanId=545db12f812e77db] Received order from customer Company B
``` 

1. You can use the 'compose.[bat|sh]' script as a shortcut to check on the containers' status or interacting with the containers themselves, e.g.:
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

Note: The data.bat|sh script uses a Newman container that makes API calls to the API GW using a postman collection. If you are interested in having the trace start before they arrive in webMethods, run the data.bat|sh script with a single parameter who value is 'consumer', as shown below:
```
data.sh consumer
```
This helps to demonstrate the case where the trace starts in Springboot and then gets propagated to webMethods.