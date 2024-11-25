package org.webfluxstudy.clientserver.core;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-template/api")
public class RestTemplateController {
    
    private final RestTemplateService restTemplateService;
    
    
    @GetMapping("/food/{id}")
    public String getFoodById(@PathVariable String id) {
        String foodInfo = restTemplateService.getFoodById(id);
        
        return foodInfo;
    }
}