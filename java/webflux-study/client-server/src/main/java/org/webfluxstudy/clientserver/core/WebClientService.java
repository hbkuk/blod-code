package org.webfluxstudy.clientserver.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webfluxstudy.clientserver.common.util.WebClientUtil;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple3;

@Service
@RequiredArgsConstructor
public class WebClientService {
    
    private final WebClientUtil webClientUtil;
    
    public Mono<String> getFoodById(String id) {
        String categoryUrl = String.format("http://localhost:8081/api/food/category/%s", id);
        String urlInfoUrl = String.format("http://localhost:8081/api/food/url/%s", id);
        String photoUrl = String.format("http://localhost:8081/api/food/photo/%s", id);
        
        // 각 API 호출을 비동기적으로 수행하며 스레드 이름 출력
        Mono<String> categoryMono = webClientUtil.get(categoryUrl, String.class)
            .subscribeOn(Schedulers.boundedElastic())
            .doOnSubscribe(sub -> System.out.println("Category request subscribed on: " + Thread.currentThread().getName()))
            .doOnNext(response -> System.out.println("Category response received on: " + Thread.currentThread().getName()));
        
        Mono<String> urlInfoMono = webClientUtil.get(urlInfoUrl, String.class)
            .subscribeOn(Schedulers.boundedElastic())
            .doOnSubscribe(sub -> System.out.println("URL Info request subscribed on: " + Thread.currentThread().getName()))
            .doOnNext(response -> System.out.println("URL Info response received on: " + Thread.currentThread().getName()));
        
        Mono<String> photoMono = webClientUtil.get(photoUrl, String.class)
            .subscribeOn(Schedulers.boundedElastic())
            .doOnSubscribe(sub -> System.out.println("Photo request subscribed on: " + Thread.currentThread().getName()))
            .doOnNext(response -> System.out.println("Photo response received on: " + Thread.currentThread().getName()));
        
        // 모든 API 호출이 완료되었을 때 결과를 조합하며 스레드 이름 출력
        return Mono.zip(categoryMono, urlInfoMono, photoMono)
            .map(tuple -> {
                System.out.println("Mapping results on: " + Thread.currentThread().getName());
                String categoryResponse = tuple.getT1();
                String urlResponse = tuple.getT2();
                String photoResponse = tuple.getT3();
                
                return categoryResponse + urlResponse + photoResponse;
            });
    }
}
