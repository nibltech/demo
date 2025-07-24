package com.example.apicaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.time.Instant;
import java.util.*;

@SpringBootApplication
public class ApiCallerApplication implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${CALL_COUNT:100}")
    private int count;

    @Value("${CALL_DELAY:1000}")
    private long delay;

    @Value("${PORT:5555}")
    private int port;

    @Value("${HOST:apig}")
    private String host;

    @Value("${AUTH_USERNAME:Administrator}")
    private String username;

    @Value("${AUTH_PASSWORD:manage}")
    private String password;

    @Value("${CLIENT_IDS:Castro LLC,Singh Consortium,Shoup Enterprises}")
    private String clientIdsRaw;

    private static List<String> clientIds;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiCallerApplication.class, args);
        context.close();
    }


    private List<String> getClientIds() {
        return Arrays.stream(clientIdsRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private void waitForPort(String host, int port, int maxAttempts, long delayMs) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), 1000);
                System.out.println("Port " + port + " is available.");
                sleep(delayMs); //Extra sleep to let API come up
                return;
            } catch (IOException e) {
                attempts++;
                System.out.println("Waiting for port " + port + " on host " + host + " to become available... attempt " + attempts);
                sleep(delayMs);
            }
        }
        throw new RuntimeException("Port " + port + " not available after " + maxAttempts + " attempts.");
    }

    private static void sleep(long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for port", ie);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        waitForPort(host, port, 60, 10000); // wait up to 60 seconds, 10s per attempt
        clientIds = getClientIds();
        for (int i = 0; i < count; i++) {
            sendRequest(host, port);
            Thread.sleep(delay);
        }
    }

    private void sendRequest(String host, int port) {
        String URL = "http://" + host + ":" + port +"/gateway/nibble-demo-api/1.0/order";
        Map<String, Object> payload = generatePayload();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!username.isEmpty() && !password.isEmpty()) {
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
            headers.set("Authorization", "Basic " + encodedAuth);
        }

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
            System.out.println("Response: " + response.getStatusCode() + " - " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
        }
    }

    private Map<String, Object> generatePayload() {
        Random rand = new Random();
        int numItems = rand.nextInt(10) + 1;
        List<Map<String, Object>> lines = new ArrayList<>();

        for (int i = 0; i < numItems; i++) {
            Map<String, Object> line = new HashMap<>();
            line.put("itemId", String.valueOf(rand.nextInt(10000) + 1));
            line.put("orderLineId", String.valueOf(i + 1));
            line.put("quantity", rand.nextInt(1000) + 1);
            lines.add(line);
        }

        Map<String, Object> header = new HashMap<>();
        header.put("clientId", clientIds.get(rand.nextInt(clientIds.size())));

        header.put("dateTime", Instant.now().toString());
        header.put("orderId", UUID.randomUUID().toString());

        Map<String, Object> payload = new HashMap<>();
        payload.put("header", header);
        payload.put("lines", lines);

        return payload;
    }
}
