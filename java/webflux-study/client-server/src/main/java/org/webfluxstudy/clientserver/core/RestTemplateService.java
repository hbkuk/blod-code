package org.webfluxstudy.clientserver.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestTemplateService {
    
    private final RestTemplate restTemplate;
    
    public String getFoodById(String id) {
        
        // 카테고리 정보를 얻는 외부 API 호출 (동기)
        String categoryResponse = restTemplate.getForObject(
            "http://localhost:8081/api/food/category/{id}", String.class, id); // 2
        
        // URL 정보를 얻는 외부 API 호출 (동기)
        String urlResponse = restTemplate.getForObject(
            "http://localhost:8081/api/food/url/{id}", String.class, id); // 10
        
        // 사진 정보를 얻는 외부 API 호출 (동기)
        String photoResponse = restTemplate.getForObject(
            "http://localhost:8081/api/food/photo/{id}", String.class, id); // 2
        
        // 완료된 세부정보 DB에 저장
        return categoryResponse + urlResponse + photoResponse;
    }
}

