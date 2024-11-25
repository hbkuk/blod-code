package org.webfluxstudy.clientserver.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompletableFutureService {
    
    private final RestTemplate restTemplate;
    
    public String getFoodById(String id) throws ExecutionException, InterruptedException {
        
        // CompletableFuture로 비동기 API 호출 설정
        CompletableFuture<String> categoryFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Category request executed by thread: " + Thread.currentThread().getName());
            return restTemplate.getForObject("http://localhost:8081/api/food/category/{id}", String.class, id);
        });
        
        CompletableFuture<String> urlFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("URL request executed by thread: " + Thread.currentThread().getName());
            return restTemplate.getForObject("http://localhost:8081/api/food/url/{id}", String.class, id);
        });
        
        CompletableFuture<String> photoFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Photo request executed by thread: " + Thread.currentThread().getName());
            return restTemplate.getForObject("http://localhost:8081/api/food/photo/{id}", String.class, id);
        });
        
        // 모든 요청이 완료될 때까지 기다림
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(categoryFuture, urlFuture, photoFuture);
        
        // 모든 응답을 받고, 각각의 결과를 조합하여 반환
        allFutures.join(); // 모든 비동기 작업이 완료될 때까지 기다림
        
        String categoryResponse = categoryFuture.get();
        String urlResponse = urlFuture.get();
        String photoResponse = photoFuture.get();
        
        return categoryResponse + urlResponse + photoResponse;
    }
}

