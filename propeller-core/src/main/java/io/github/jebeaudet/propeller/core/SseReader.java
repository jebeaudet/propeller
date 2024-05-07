package io.github.jebeaudet.propeller.core;

import cloud.prefab.sse.SSEHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SseReader {

    public static void main(String[] args) throws Exception {
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                                 .uri(URI.create("http://localhost:5002/sse?value=2"))
                                 .GET()
                                 .build();

        var request2 = HttpRequest.newBuilder()
                                  .uri(URI.create("http://localhost:5003/sse?value=3"))
                                  .GET()
                                  .build();

        SSEHandler sseHandler = new SSEHandler();
        sseHandler.subscribe(new SseSubscriber());
        SSEHandler sseHandler2 = new SSEHandler();
        sseHandler.subscribe(new SseSubscriber());
        var future1 = client.sendAsync(request, HttpResponse.BodyHandlers.fromLineSubscriber(sseHandler));
        var future2 = client.sendAsync(request2, HttpResponse.BodyHandlers.fromLineSubscriber(sseHandler2));

        new Thread(() -> {
            try {
                future1.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        future2.get();
    }
}
