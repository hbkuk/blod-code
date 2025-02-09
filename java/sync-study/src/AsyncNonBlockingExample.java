import java.util.concurrent.CompletableFuture;

public class AsyncNonBlockingExample {
    public static void main(String[] args) {
        System.out.println("음식 주문 (치킨, 피자, 파스타)");
        
        // 비동기적으로 여러 개의 요리 주문 후, 콜백을 사용하여 처리
        CompletableFuture.supplyAsync(() -> cookFood("치킨", 3000))
            .thenAccept(food -> System.out.println("음식 받음: " + food));
        
        CompletableFuture.supplyAsync(() -> cookFood("피자", 5000))
            .thenAccept(food -> System.out.println("음식 받음: " + food));
        
        CompletableFuture.supplyAsync(() -> cookFood("파스타", 4000))
            .thenAccept(food -> System.out.println("음식 받음: " + food));
        
        System.out.println("다른 작업 수행 중... (요리가 끝날 때까지 기다리지 않음)");
        
        // 메인 스레드 종료 방지
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // 비동기 요리 함수
    public static String cookFood(String food, int time) {
        try {
            Thread.sleep(time);  // 요리하는 시간 (비동기 실행)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return food;
    }
}
