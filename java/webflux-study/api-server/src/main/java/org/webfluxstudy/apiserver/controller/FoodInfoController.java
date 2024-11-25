package org.webfluxstudy.apiserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/food")
public class FoodInfoController {
    
    @GetMapping("/category/{id}")
    public String getCategory(@PathVariable String id) throws InterruptedException {
        Thread.sleep(2000); // 응답 시간 가정
        
        return "카테고리 정보 - ".concat(id);
    }
    
    @GetMapping("/url/{id}")
    public String getUrl(@PathVariable String id) throws InterruptedException {
        Thread.sleep(2000); // 응답 시간 가정
        
        return "URL 정보 - ".concat(id);
    }
    
    @GetMapping("/photo/{id}")
    public String getPhoto(@PathVariable String id) throws InterruptedException {
        Thread.sleep(2000); // 응답 시간 가정
        
        return "사진 정보 - ".concat(id);
    }
}