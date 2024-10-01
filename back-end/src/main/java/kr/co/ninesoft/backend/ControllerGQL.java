package kr.co.ninesoft.backend;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.stream.Stream;

@AllArgsConstructor
@Controller
public class ControllerGQL {
    final ServiceApp serviceApp;

   /* @SubscriptionMapping
    public Publisher<Message> newMessage() {
        return Flux.create(emitter -> {
            // 메시지 생성 및 발행 로직
            Message message = new Message("1", "Hello, Subscription!");
            emitter.next(message);
        });
    }*/



    @SubscriptionMapping
    public Flux<Integer> counter() {
        return Flux.fromStream(Stream.iterate(0, i -> i + 1))
                .delayElements(Duration.ofSeconds(1));
    }

    @SubscriptionMapping
    public Flux<String> newMessage() {
        return serviceApp.getMessages();
    }

    @MutationMapping
    Mono<String> sendMessage(@Argument("msg") String message) {
        serviceApp.sendMessage(message);
        return Mono.just(message);
    }
}
