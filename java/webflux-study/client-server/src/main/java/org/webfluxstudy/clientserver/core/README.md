## Spring WebFlux와 핵심 용어

### WebClient

- `WebClient`는 `Spring`에서 비동기, 논블로킹 방식으로 HTTP 요청을 할 수 있는 클라이언트입니다.
- 기존의 `RestTemplate`과 비슷한 역할을 하지만, 비동기 및 리액티브 프로그래밍 환경에 최적화되어 있습니다.


**주요 메서드**
- `get()`, `post()`, `put()`, `delete()` 등 HTTP 메서드 별로 API 요청 수행 가능
- `retrieve()`: 요청 후 HTTP 상태 코드와 응답을 직접 다룸.
- `bodyToMono()`: HTTP 응답의 본문을 특정 타입으로 변환하여 `Mono`로 반환


### Mono와 Flux

- 리액티브 프로그래밍의 핵심 컨셉으로, 두 개념 모두 `Publisher` 인터페이스를 구현하고 있음.
  - `Mono`
    - 하나의 결과값 또는 에러를 비동기적으로 반환
    - 예) 단일 HTTP 응답, 특정 ID로 데이터를 가져오는 API 응답
  - `Flux`
    - 다수의 결과값(스트림)을 비동기적으로 반환
    - 예) 데이터의 스트링밍 처리, 리스트 형태의 데이터 응답

**주요 메서드**
- `subscribeOn()`: 어떤 스레드에서 구독할지를 결정함.
- `zip()`: 여러 `Mono`나 `Flux`의 결과를 조합해서 새로운 결과를 반환함.
- `block()`: `Mono` 나 `Flux`가 완료될 때까지 기다렸다가 최종 값을 반환함.