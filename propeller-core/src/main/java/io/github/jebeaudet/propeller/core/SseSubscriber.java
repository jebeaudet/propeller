package io.github.jebeaudet.propeller.core;

import cloud.prefab.sse.events.Event;

import java.util.concurrent.Flow;

public class SseSubscriber implements Flow.Subscriber<Event> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("onSubscribe");
        this.subscription = subscription;
        subscription.request(2);
    }

    @Override
    public void onNext(Event item) {
        System.out.println(Thread.currentThread().getName() + item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("complete");
    }
}
