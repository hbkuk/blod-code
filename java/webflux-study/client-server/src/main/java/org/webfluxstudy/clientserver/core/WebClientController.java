package org.webfluxstudy.clientserver.core;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web-client/api")
public class WebClientController {
    
    private final WebClientService webClientService;
    
    /**
     * 컨트롤러에서 Mono<T>를 반환하면, 클라이언트는 Mono가 제공하는 데이터 T 자체를 받습니다.
     * 즉, 예를 들어 Mono<User>를 반환하면, 클라이언트는 User 객체(또는 그 JSON 변환 결과)를 응답으로 받게 됩니다.
     *
     * 위 코드는 클라이언트가 /user/{id}에 요청을 보내면 비동기로 User 객체가 준비되는 대로 JSON 형식으로 반환됩니다.
     * 클라이언트가 필요한 데이터를 정상적으로 응답받게 하죠.
     *
     * @param id
     * @return
     */
    @GetMapping("/food/{id}")
    public Mono<String> getFoodById(@PathVariable String id) {
        Mono<String> food = webClientService.getFoodById(id);
        
        return food;
    }
}