import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncBlockingExample {
    public static void main(String[] args) {
        System.out.println("음식 주문 (치킨, 피자, 파스타)");
        
        // 비동기적으로 여러 개의 요리 주문
        CompletableFuture<String> chicken = CompletableFuture.supplyAsync(() -> cookFood("치킨", 3000));
        CompletableFuture<String> pizza = CompletableFuture.supplyAsync(() -> cookFood("피자", 5000));
        CompletableFuture<String> pasta = CompletableFuture.supplyAsync(() -> cookFood("파스타", 4000));
        
        // 모든 요리가 완료될 때까지 대기 (블로킹 발생)
        CompletableFuture<Void> allOrders = CompletableFuture.allOf(chicken, pizza, pasta);
        try {
            allOrders.get();  // 모든 요리가 완성될 때까지 블로킹
            System.out.println("모든 음식이 준비되었습니다!");
            System.out.println("주문 완료: " + chicken.get() + ", " + pizza.get() + ", " + pasta.get());
        } catch (InterruptedException | ExecutionException e) {
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
