package ru.kozlovva.investservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.invest.openapi.OpenApi;
import ru.tinkoff.invest.openapi.models.streaming.StreamingEvent;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class Tester implements CommandLineRunner {

    private final OpenApi openApi;

    @Override
    public void run(String... args) throws Exception {
        openApi.getStreamingContext().getEventPublisher().subscribe(new Subscriber<StreamingEvent>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.info("Subscribe {}", s);
            }

            @Override
            public void onNext(StreamingEvent streamingEvent) {
                log.info("Next {}", streamingEvent.toString());
            }

            @Override
            public void onError(Throwable t) {
                log.info("Error", t);
            }

            @Override
            public void onComplete() {
                log.info("Complete}");
            }
        });
    }
}
