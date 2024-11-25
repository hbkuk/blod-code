package org.webfluxstudy.clientserver.core;

import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/completable-future/api")
public class CompletableFutureController {
    
    private final CompletableFutureService completableFutureService;
    
    @GetMapping("/food/{id}")
    public String getFoodById(@PathVariable String id) throws ExecutionException, InterruptedException {
        String foodInfo = completableFutureService.getFoodById(id);
        
        return foodInfo;
    }
}