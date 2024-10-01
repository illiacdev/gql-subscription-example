package kr.co.ninesoft.backend;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ServiceApp {
    private final Sinks.Many<String> sink;

    public ServiceApp() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void sendMessage(String message) {
        sink.tryEmitNext(message);  // 데이터를 구독자에게 푸시
    }

    public Flux<String> getMessages() {
        return sink.asFlux();  // 구독을 통해 실시간 메시지 스트림 제공
    }
}
