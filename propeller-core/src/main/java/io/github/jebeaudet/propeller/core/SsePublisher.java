package io.github.jebeaudet.propeller.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public class SsePublisher implements Flow.Publisher<String> {
    private final String endpoint;

    public SsePublisher(String endpoint) {
        this.endpoint = Objects.requireNonNull(endpoint);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        subscriber.onSubscribe(new SseSubscription(endpoint));
    }

    static class SseSubscription implements Flow.Subscription {
        private final String endpoint;

        SseSubscription(String endpoint) {
            this.endpoint = endpoint;
        }

        @Override
        public void request(long n) {
        }

        @Override
        public void cancel() {
        }
    }
}
